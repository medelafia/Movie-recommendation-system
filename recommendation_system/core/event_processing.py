from kafka import KafkaConsumer
import json 
import redis

consumer = KafkaConsumer(
    'interaction-event' , 
    bootstrap_servers=['localhost:9092'] , 
    auto_offset_reset='earliest',  # Start from the earliest message
    enable_auto_commit=True,
    group_id='interaction-group',
    value_deserializer=lambda x: json.loads(x.decode('utf-8'))
)
r = redis.Redis(host="localhost" , port=6379 )



def start_event_consuming(): 

    for event in consumer : 
        data = event.value
        if r.get(str(data['userId'])): 
            print(f"found {data['userId']}")
            preferences = list(set(json.loads(r.get(str(data['userId'])))))
            print("current preferences")
            preferences.append(data['contentId']) 

            print("update preferences " , preferences)
            r.set(str(data['userId']) , json.dumps(preferences))

