# demo_home_test

Demonstrates the change password validator function which checks the below conditions when a new password is set or when an existing password is modified.

1. At least 18 alphanumeric characters and list of special chars !@#$&*
2. At least 1 Upper case, 1 lower case ,least 1 numeric, 1 special character
3. No duplicate repeat characters more than 4
4. No more than 4 special characters
5. 50 % of password should not be a number

Steps to run the spring boot application

1. Install maven if not already installed.
2. Run the below commands to start the application.

```
mvn clean
```
```
mvn org.springframework.boot:spring-boot-maven-plugin:run
```
Usage:

1. Register User: To create a new user, call the following API
``
http://localhost:8081/registerUser
``

2. Update the password: To update the password, call the following API.
``
http://localhost:8081/updatePassword
``
3. Get list of registered users: To get the list of all users, call the following API.
``
http://localhost:8081/getUsers
``

Samples Requests:

1. Register User:

```yaml
POST http://localhost:8081/registerUser

{
    "userName": "DemoUser",
    "password": "Newpassword213!@#$"
}
```

2. Update User:
```yaml
POST http://localhost:8081/updatePassword

{
    "userName": "DemoUser",
    "password": "Newpassword213!@#$"
}
```

3. Get users:
```yaml
GET http://localhost:8081/getUsers
```
