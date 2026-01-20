import pandas as pd
import numpy as np 
from sklearn.neighbors import NearestNeighbors
import redis
import json
from pydantic import BaseModel
from typing import Union
import requests 


class RecommendationResponse(BaseModel) : 
    recommendation : Union[list , None] = None
    user_id : Union[int , None ] = None

r = redis.Redis(host="localhost" , port=6379 )

def clean(x) :
    new_x = []
    for i in x.split(",") :
        new_x.append(i.strip().lower())
    return new_x

df_series = pd.read_csv('datasets/series_data.csv')
df_movies = pd.read_csv('datasets/movies_data.csv')

df_movies = df_movies[["Series_Title" , "Genre"]]
df_series = df_movies[["Series_Title" , "Genre"]]

df_movies['Genre'] = df_movies['Genre'].apply(clean)
df_expanded_movies = (
    df_movies
    .explode('Genre')
    .dropna(subset=['Genre'])
    .pipe(pd.get_dummies , columns=['Genre'])
    .groupby(level=0)
    .sum()
)

df_series['Genre'] = df_series['Genre'].apply(clean)
df_expanded_series = (
    df_series
    .explode('Genre')
    .dropna(subset=['Genre'])
    .pipe(pd.get_dummies , columns=['Genre'])
    .groupby(level=0)
    .sum()
)

df_expanded_movies = df_expanded_movies.drop(["Series_Title"] , axis=1)
df_expanded_series = df_expanded_series.drop(["Series_Title"] , axis=1)

nn_movies = NearestNeighbors(n_neighbors=6 )
nn_movies.fit(df_expanded_movies.values)

nn_series = NearestNeighbors(n_neighbors=6 )
nn_series.fit(df_expanded_series.values)

def recommend(user_id , type="movies") :
    if r.get(str(user_id)) : 
        preferences = json.loads(r.get(str(user_id)))
        print("loaded from cache")
    else : 
        
        print("set new values")
        preferences = requests.get(f"http://localhost:8080/api/user/{user_id}/preferences").json()
        r.set(str(user_id) , json.dumps(preferences))

    print("preferences" , preferences) 
    if type == "movies" : 
        preferences = list(filter(lambda x : x > 2000 , preferences))
        user_preferences = df_expanded_movies.iloc[preferences] 


    
        user_preferences_mean = np.array(user_preferences).mean(axis=0)
        _ , indices = nn_movies.kneighbors(user_preferences_mean.reshape(1 , -1) , return_distance=True)
        indices = indices + 2001

    else : 
        preferences = list(filter(lambda x : x <= 2000 , preferences))
        user_preferences = df_expanded_movies.iloc[preferences] 
        

        user_preferences_mean = np.array(user_preferences).mean(axis=0)
        _ , indices = nn_series.kneighbors(user_preferences_mean.reshape(1 , -1) , return_distance=True)

    response = requests.get("http://localhost:8080/api/content/all" , params={"ids" : indices.tolist()[0]} )      
    return  response.json()