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