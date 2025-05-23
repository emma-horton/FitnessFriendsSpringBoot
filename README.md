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
  - JPA: Object-relational mapping to map Java classes to database tables.
  - Spring Data: Simplifies database operations using repository interfaces.
  - Spring Controllers: Implements RESTful APIs
- **Strava API**: Sync fitness data from multiple trackers.
- **PlantUML**: UML diagrams for design documentation.
- **Maven**: Build and dependency management.

## **Design Patterns Implemented**
This project leverages several software design patterns to ensure a robust, maintainable, and scalable architecture:

### **Abstract Factory Pattern**: For generating sport-specific fitness goals (e.g., Running, Cycling, Swimming).
<div align="center">
    <img src="images/AbstractFactoryPatternSpringBoot.png" alt="Abstract Factory Pattern Class Diagram" width="950">
</div>

- **Concrete classes (DistanceGoal, DurationGoal, FrequencyGoal) implement the GoalTypeStrategy interface:** This design follows the Interface Segregation Principle by ensuring consistency across all states. Each concrete class is required to implement isGoalAchieved() and wasLastWeeksGoalAchieved() methods. 
- **GoalTypeStrategyFactory depends on GoalTypeStrategy interface because it creates objects that follow the rules defined by that interface.** This allows the factory to produce different goal strategies while maintaining flexibility and adhering to the Dependency Inversion Principle.
### **State Pattern**: For managing dynamic pet behaviors (e.g., Healthy, Sick, Dead).
<div align="center">
    <img src="images/StatePatternPetBehaviour.png" alt="State Pattern Class Diagram" width="650">
</div>

- **Concrete States (HealthyState, SickState, DeadState) implement the PetState interface.** This design follows the Interface Segregation Principle by ensuring consistency across all states. Each state is required to implement the methods: move(), eat(), and play().
- **PetBehaviour class inherits from both the VirtualPet class and the PetState interface.** It leverages attributes such as petID, name, and type defined in the VirtualPet class, while its behavior dynamically depends on the current PetState (e.g., healthy, sick, or dead). This follows the Open/Closed Principle (OCP) since inheritance allows extending behavior without modifying existing code.

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

## Project Structure 
```bash
FitnessFriendsSpringBoot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── fitnessfriends/
│   │   │   │   │   ├── controller/          # REST API controllers
│   │   │   │   │   ├── dto/                 # Data Transfer Objects (DTOs)
│   │   │   │   │   ├── entity/              # JPA entities for database mapping
│   │   │   │   │   ├── repository/          # Spring Data JPA repositories
│   │   │   │   │   ├── service/             # Business logic and services
│   │   │   │   │   │   ├── pet/             # Pet-related logic
│   │   │   │   │   │   ├── goal/            # Goal evaluation strategies
│   │   │   │   │   ├── config/              # Application configuration
│   │   │   │   │   ├── utils/               # Utility classes
│   │   │   │   │   ├── FitnessFriendsApplication.java # Main application entry point
│   │   ├── resources/
│   ├── test/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── fitnessfriends/
│   │   │   │   │   ├── controller/          # Controller tests
│   │   │   │   │   ├── service/             # Service tests
│   │   │   │   │   ├── repository/          # Repository tests
│   │   │   │   │   ├── entity/              # Entity tests
├── pom.xml                                  # Maven configuration file
├── README.md                                # Project documentation
├── mvnw                                     # Maven wrapper script (Linux/Mac)
├── mvnw.cmd                                 # Maven wrapper script (Windows)
```
