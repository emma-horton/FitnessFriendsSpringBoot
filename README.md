# Fitness Friends

**Fitness Friends** is a virtual pet application designed to make fitness tracking fun and engaging. By integrating with the Strava API, the app allows users to track their fitness activities, set goals, and care for a virtual pet whose health and behavior are influenced by the user's progress. Whether you're using a Garmin device, another fitness tracker, or no tracker at all, Fitness Friends provides a unique way to stay motivated on your fitness journey.


## **Features**
- **Virtual Pet Interaction**: Create and care for a virtual pet (e.g., Parrot, Turtle) whose health and behavior change based on your fitness progress.
- **Fitness Goal Tracking**: Set weekly goals for distance, duration, or frequency across various sports (e.g., running, cycling, swimming).
- **Strava Integration**: Connect your Strava account to sync fitness activities from multiple trackers.
- **Dynamic Pet Behavior**: Pets respond to your progress with different states (e.g., Healthy, Sick, Dead).
- **User-Friendly Interface**: Register, log in, and interact with your pet through a simple console-based interface.


## **Technologies Used**
- **Java**: Core application logic and object-oriented design.
- **H2 Database**: Database for storing user data, fitness goals, and pet details.
- **Hibernate**: Manage database interactions
- **SpringBoot**: Build and run the application
- **Strava API**: Sync fitness data from multiple trackers.
- **PlantUML**: UML diagrams for design documentation.
- **Maven**: Build and dependency management.


## **Design Patterns Implemented**
This project leverages several software design patterns to ensure a robust, maintainable, and scalable architecture:
1. **Abstract Factory Pattern**: For generating sport-specific fitness goals (e.g., Running, Cycling, Swimming).
2. **State Pattern**: For managing dynamic pet behaviors (e.g., Healthy, Sick, Dead).


## **Setup and Installation**

### **Steps to Run the Project**
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/emma-horton/FitnessFriendsSpringBoot.git
   cd FitnessFriendsSpringBoot
   ```
2. **Build the Project:**
    ```bash
    mvn clean install
    ```
3. **Run the Application:**
    ```bash
    ./mvnw spring-boot:run
    ```

## **How to Use**
1. Register:
* Register a new account and connect your strava 
    ```bash
    POST /api/users/register
    ```
2. Login: 
* Login to account 
    ```bash
    POST /api/users/login
    ```
3. Create a Pet:
* Choose a pet type (e.g., Parrot, Turtle) and name your pet.
    ```bash
    POST /api/pets
    ```
4. Set Fitness Goals:
* Define weekly goals for distance, duration, or frequency in your preferred sport (run, ride or swim).
    ```bash
    POST /api/goals
    ```
5. Interact with Your Pet:
* View your pet's status, play with it, and ensure its health by meeting your fitness goals.
    ```bash
    GET /api/pets/{userId}
    GET /api/pets/{userId}/move
    GET /api/pets/{userId}/eat
    GET /api/pets/{userId}/play
    ```

### **Future Plans**
* Add more pet types (e.g., Dog, Cat) with unique behaviors.
* Introduce new fitness goal categories (e.g., calories burned).
* Implement a graphical user interface (GUI) for a more interactive experience.
* Add real-time notifications for pet health updates.
* Explore additional design patterns, such as the Observer Pattern for real-time updates.

## **Contributing**
Contributions are welcome! If you’d like to contribute, please fork the repository and submit a pull request.


## To access DB 
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:./data/fitness-friends
username: sa

## Testing endpoints 
/register 

curl -X POST http://localhost:8080/api/users/register \
-H "Content-Type: application/json" \
-d '{
    "username": "newuser",
    "password": "password123"
}'

/goals POST 
curl -X POST http://localhost:8080/api/goals \
-H "Content-Type: application/json" \
-d '{
    "userId": 1,
    "goalType": "DURATION",
    "sport": "RUNNING",
    "targetValue": 5.0
}'

/pets POST 
curl -X POST http://localhost:8080/api/pets \
-H "Content-Type: application/json" \
-d '{
    "petName": "Fluffy",
    "petType": "TURTLE",
    "healthStatus": "HEALTHY",
    "user": {
        "userId": 1
    }
}'

/pets GET
curl -X GET http://localhost:8080/api/pets/1