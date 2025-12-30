import re 
import string 
from nltk.tokenize import word_tokenize
import nltk 
from nltk.corpus import stopwords 
import joblib
import ssl


try:
    _create_unverified_https_context = ssl._create_unverified_context
except AttributeError:
    pass
else:
    ssl._create_default_https_context = _create_unverified_https_context

nltk.download('punkt_tab')
nltk.download("stopwords")

def preprocess(text) : 
    text = text.lower() 
    text = re.sub(r'\d+' , '' , text) 
    text = re.sub(r'\W' , ' ',text) 
    text = text.translate(str.maketrans('' , '' , string.punctuation)) 
    text = " ".join(text.strip().split())
    return text 

def filter_list(list) : 
  filtered_list = [word for word in list if word not in stopwords.words('english')]
  return filtered_list


tfidTokenizer = joblib.load("models/tfid.pkl")

nb_model = joblib.load("models/model.pkl")



def classify(text) : 
   text = preprocess(text) 

   tokenized_list = word_tokenize(text) 

   filter_tokenized_list = filter_list(tokenized_list) 
   x = tfidTokenizer.transform([ " ".join(filter_tokenized_list) ] ) 
   return nb_model.predict(x)[0]
