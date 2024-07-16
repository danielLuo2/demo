## API Sample 
### 1. /user/resource
curl --location 'http://localhost:8080/user/resource B' \
--header 'Content-Type: application/json' \
--header 'RoleInfo: eyJ1c2VySWQiOjEwODYsImFjY291bnROYW1lIjoiTEREIiwicm9sZSI6IlVTRVIifQ==' \
--header 'Cookie: JSESSIONID=D389E93255BF23D8B3DE20969474B049' \
--data ''


### 2./admin/addUser
curl --location 'http://localhost:8080/admin/addUser' \
--header 'Content-Type: application/json' \
--header 'RoleInfo: eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJEYW5pZWwiLCJyb2xlIjoiQURNSU4ifQ==' \
--header 'Cookie: JSESSIONID=D389E93255BF23D8B3DE20969474B049' \
--data '{
    "userId": 12345956,
    "endpoint": [
        "resource A",
        "resource B",
        "resource C"
    ]
}'


