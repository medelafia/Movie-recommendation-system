from typing import Union
from pydantic import BaseModel 
from core.sentiment_analyser import classify
from fastapi import FastAPI

app = FastAPI()




class ReviewClassificationRequest(BaseModel) : 
    content : Union[str , None] = None

@app.post("/reviews/classify")
async def classify_review( review : ReviewClassificationRequest):
    
    result =  "positive" if classify(review.content) == 1 else "negative"
    return { "result" : result }