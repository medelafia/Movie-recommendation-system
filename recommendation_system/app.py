from core.sentiment_analyser import classify , ReviewClassificationRequest
from core.content_recommender import recommend 
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from core.event_processing import start_event_consuming
import threading


app = FastAPI()

origins = [
    "http://localhost:4200"
]
app.add_middleware(
    CORSMiddleware , 
    allow_origins=origins,        
    allow_credentials=True,         
    allow_methods=["*"],          
    allow_headers=["*"], 
)

@app.on_event("startup") 
async def on_startup() : 
    thread = threading.Thread(target=start_event_consuming , daemon=True)
    thread.start()

@app.post("/reviews/classify")
async def classify_review( review : ReviewClassificationRequest):
    
    result =  "positive" if classify(review.content) == 1 else "negative"
    return { "result" : result }

@app.get("/{userId}/recommendation/movies") 
async def recommend_movies(userId : int) :
    return recommend(userId)

@app.get("/{userId}/recommendation/series") 
async def recommend_series(userId : int ) :
    return recommend(userId , type="series")