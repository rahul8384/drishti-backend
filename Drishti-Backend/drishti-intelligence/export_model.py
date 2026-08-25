import torch
import json

# Load the model (same architecture as Colab)
from torch.nn import Module, Linear
from torch_geometric.nn import GCNConv
import torch.nn.functional as F

class GNN(Module):
    def __init__(self):
        super().__init__()
        self.conv1 = GCNConv(3, 16)
        self.conv2 = GCNConv(16, 8)
        self.fc = Linear(8, 1)
    
    def forward(self, data):
        x, edge_index = data.x, data.edge_index
        x = self.conv1(x, edge_index).relu()
        x = self.conv2(x, edge_index)
        return self.fc(x)

model = GNN()
model.load_state_dict(torch.load('gnn_threat_model.pth'))

# Export for inference API
torch.onnx.export(model, torch.randn(5, 3), 'gnn_threat_model.onnx')
print("Model exported to ONNX")