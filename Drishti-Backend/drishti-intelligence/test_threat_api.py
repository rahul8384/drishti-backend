import requests

response = requests.post('http://127.0.0.1:5001/predict', json={
    'lat': 34.0837,
    'lon': 74.7973,
    'casualties': 2
})

print("Status:", response.status_code)
print("Text:", response.text)
print("Headers:", response.headers)