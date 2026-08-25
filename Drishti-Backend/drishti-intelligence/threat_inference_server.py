import torch
import traceback
from flask import Flask, jsonify, request
from torch_geometric.nn import GCNConv
import torch.nn.functional as F

class GNN(torch.nn.Module):
    def __init__(self):
        super().__init__()
        self.conv1 = GCNConv(5, 32)
        self.conv2 = GCNConv(32, 16)
        self.fc = torch.nn.Linear(16, 1)
    
    def forward(self, x, edge_index):
        x = self.conv1(x, edge_index).relu()
        x = self.conv2(x, edge_index)
        x = self.fc(x)
        return x

try:
    model = GNN()
    model.load_state_dict(torch.load('gnn_threat_model.pth'))
    model.eval()
    print("✓ Model loaded successfully")
except Exception as e:
    print(f"✗ Model load failed: {e}")
    traceback.print_exc()

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json
        # 5 features: lat, lon, casualties, militants, weapons
        x = torch.tensor([[data['lat'], data['lon'], data['casualties'], 
                          data.get('militants', 5), data.get('weapons', 2)]], dtype=torch.float)
        # Normalize
        x = (x - torch.tensor([34, 75, 2, 7, 2], dtype=torch.float)) / torch.tensor([1, 1, 4, 4, 2], dtype=torch.float)
        
        edge_index = torch.tensor([[0], [0]], dtype=torch.long)
        
        with torch.no_grad():
            out = model(x, edge_index)
        
        risk = max(0, float(out.item()) * 12)  # Scale back to 0-12
        threat_level = "HIGH" if risk > 2.5 else "MEDIUM" if risk > 1.5 else "LOW"
        return jsonify({"risk_score": risk, "threat_level": threat_level})
    except Exception as e:
        print(f"Prediction error: {e}")
        traceback.print_exc()
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(port=5001, debug=True)