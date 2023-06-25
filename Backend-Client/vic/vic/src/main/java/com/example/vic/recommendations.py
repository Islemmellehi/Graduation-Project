import pandas as pd
from surprise import Dataset, Reader, KNNBasic
import requests
from urllib.parse import urlencode

# Fetch client status sample from the API
api_url = 'http://localhost:8200/status'
response = requests.get(api_url)
client_status_sample = response.text.strip()
print(f"Retrieved client status sample: {client_status_sample}")

# Importing data
df = pd.read_csv('C:/Users/islem/OneDrive/Bureau/DATA.csv')
df['rating'] = 3

# Define the reader
reader = Reader(rating_scale=(1, 5))

# Load the data into Surprise Dataset
data = Dataset.load_from_df(df[['ClientSF', 'Rooms', 'rating']], reader)

# Build the full trainset
trainset_full = data.build_full_trainset()

# Create an instance of KNNBasic
knn_basic = KNNBasic()

# Train the model
knn_basic.fit(trainset_full)

# Get the room IDs associated with the client status sample
client_status_sample_ids = df[df['ClientSF'] == client_status_sample]['Rooms'].tolist()

# Create variables to store predictions
prediction1 = None
prediction2 = None
prediction3 = None

# Generate predictions for all possible rooms based on the client sample
for room_id in client_status_sample_ids:
    prediction = knn_basic.predict(0, room_id)
    if prediction1 is None:
        prediction1 = prediction.iid
    elif prediction2 is None:
        prediction2 = prediction.iid
    elif prediction3 is None:
        prediction3 = prediction.iid
    else:
        break

# Prepare the URL with the predictions as parameters
encoded_prediction1 = prediction1.replace(' ', '%20')
encoded_prediction2 = prediction2.replace(' ', '%20')
encoded_prediction3 = prediction3.replace(' ', '%20')

api_url = f'http://localhost:8200/recommendationresponse?rec1={encoded_prediction1}&rec2={encoded_prediction2}&rec3={encoded_prediction3}'

# Send the GET request to the API
response = requests.get(api_url)
recommended_rooms = response.json()

# Print the recommended rooms
for room in recommended_rooms:
    print(f"Recommended Room: {room}")
