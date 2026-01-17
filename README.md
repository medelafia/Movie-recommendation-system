Movie Recommendation System

Overview

A movie recommendation system developed using Angular for the frontend, Spring Boot for the backend, and Python (FastAPI) for generating recommendations using a machine learning model (KNN). The system also features sentiment analysis to classify user reviews and provide robust recommendations based on user preferences. The system utilizes Kafka for event-driven architecture and Redis for cachHere's a sample README file for your GitHub repository:

Movie Recommendation System

Overview

A movie recommendation system developed using Angular for the frontend, Spring Boot for the backend, and Python (FastAPI) for generating recommendations using a machine learning model (KNN). The system also features sentiment analysis to classify user reviews and provide robust recommendations based on user preferences. The system utilizes Kafka for event-driven architecture and Redis for caching user preferences to reduce response latency. MySQL is used as the database.

Technologies Used

- *Frontend:* Angular
- *Backend:* Spring Boot
- *Recommendation Engine:* Python (FastAPI), KNN Machine Learning Model
- *Sentiment Analysis:* Machine Learning Model ( classification of user reviews as positive or negative)
- *Event-Driven Architecture:* Kafka
- *Caching:* Redis
- *Database:* MySQL
- *Security:* JWT (JSON Web Tokens)

System Architecture

The system consists of three main components:

1. *Angular Frontend:* Handles user interactions and displays movie recommendations.
2. *Spring Boot Backend:* Handles user requests, publishes events to Kafka, and interacts with the database.
3. *Python (FastAPI) Recommendation Engine:* Consumes events from Kafka, updates user preferences in Redis, and generates movie recommendations using a KNN machine learning model.

How it Works

1. *User Interaction:* A user interacts with the Angular frontend, rating or liking movies.
2. *Event Publishing:* The Spring Boot backend publishes events to Kafka, which are then consumed by the Python recommendation engine.
3. *User Preference Update:* The Python recommendation engine updates the user's preferences in Redis.
4. *Movie Recommendation:* The Python recommendation engine generates movie recommendations based on the user's preferences and the KNN machine learning model.
5. *Sentiment Analysis:* The system classifies user reviews as positive or negative, which helps to improve the accuracy of the recommendations.

Features

- *Personalized Movie Recommendations:* Based on user preferences and behavior.
- *Sentiment Analysis:* Classifies user reviews as positive or negative.
- *Event-Driven Architecture:* Uses Kafka to handle user events and update user preferences in real-time.
- *Caching:* Uses Redis to reduce response latency and improve system performance.

Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request.

Author : 
Mohamed EL AFIA 
