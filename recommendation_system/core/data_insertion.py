import pandas as pd 
from pydantic import BaseModel 
from typing import Optional
import re
import requests
def clean(x) :
    new_x = []
    for i in x.split(",") :
        new_x.append(i.strip().lower())
    return new_x

df_series = pd.read_csv('datasets/series_data.csv')
df_movies = pd.read_csv('datasets/movies_data.csv')
#df = pd.concat([df_movies[["Series_Title" , "Genre" , "pip install bullmqStar1" , "Star2"]] ,df_series[["Series_Title" , "Genre" , "Star1" , "Star2"]] ], axis=0 ,ignore_index=True)

df_movies['Genre'] = df_movies['Genre'].apply(clean)
df_movies['ID'] = df_movies.index + 2001 
df_movies[['Star1','Star2','Star3','Star4'] ] = df_movies[['Star1','Star2','Star3','Star4']].fillna('')


df_series['Genre'] = df_series['Genre'].apply(clean)
df_series['ID'] = df_series.index + 1 
df_series[['Star1','Star2','Star3','Star4'] ] = df_series[['Star1','Star2','Star3','Star4']].fillna('')


class Actor(BaseModel ) : 
    name : Optional[str] 

class Genre(BaseModel) : 
    name : str 
class Content(BaseModel) : 
    id : int 
    title : str
    overview : str
    posterLink : str 
    externalRating : float 
    voteCount :int
    releasedYear : int
    certification : str
    actors : list
    genres : list 

class Series(Content) : 
    episodeRuntime :int 
    endYear : int



class Movie(Content) : 
    runtime : int 
    director : str 



def insert_series() : 
    for element in df_series.iloc : 
        serie = Series(
            id=element['ID'] ,
            title=element['Series_Title'] , 
            voteCount=element['No_of_Votes'] , 
            externalRating=element['IMDB_Rating'] , 
            certification=str(element['Certificate']) , 
            overview=element['Overview'] , 
            posterLink=element['Poster_Link'] ,
            episodeRuntime=int(element['Runtime_of_Episodes'][:-3] if type(element['Runtime_of_Episodes']) == str else 0),
            releasedYear=int(re.search(r"\d{4}(?=[–-])", element['Runtime_of_Series']).group() if bool(re.search(r"\d{4}(?=[–-])", element['Runtime_of_Series'])) else 0),
            endYear=int(re.search(r"\d{4}(?=[–-])", element['Runtime_of_Series']).group() if bool(re.search(r"\d{4}[–-]\d{4}", element['Runtime_of_Series'])) else 0),
            actors=[Actor(name=actor) for actor in element[["Star1" , "Star2" ,  "Star3" , "Star4" ]] if type(actor) == str ],
            genres=[Genre(name=genre) for genre in element['Genre']] )

        response = requests.post("http://localhost:8080/api/content/series" , json=serie.model_dump()) 

        print(response.status_code)
        print(response.text)



def insert_movies() : 
    for element in df_movies.iloc : 
        serie = Movie(
            id=element['ID'] ,
            title=element['Series_Title'] , 
            voteCount=element['No_of_Votes'] , 
            externalRating=element['IMDB_Rating'] , 
            certification=str(element['Certificate']) , 
            overview=element['Overview'] , 
            posterLink=element['Poster_Link'] ,
            runtime=int(element['Runtime'][:-3] if type(element['Runtime']) == str else 0),
            releasedYear=int( element['Released_Year'] if element['Released_Year'].isdigit() else 0),
            director=element['Director'] ,
            actors=[Actor(name=actor) for actor in element[["Star1" , "Star2" ,  "Star3" , "Star4" ]] if type(actor) == str ],
            genres=[Genre(name=genre) for genre in element['Genre']] )


        print(serie)
        response = requests.post("http://localhost:8080/api/content/movies" , json=serie.model_dump() ) 

        print(response.status_code)
        print(response.text)
    


insert_movies()


