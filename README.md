
# Prueba Tecnica Nissum

This program is in charge of creating users with their respective phones.

## Deployment

To execute this project, just execute the main method of the main class. 



## Running Tests

To execute the unit tests, just right click on the tests package and select the "Run tests" option.


## API Reference

#### Get all users

```http
  GET /users/v1
```

#### Patch: Create user

```http
  Patch /users/v1
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. name of the user |
| `email`      | `string` | **Required**. email of the user |
| `password`      | `string` | **Required**. password of the user.  |
| `phones`      | `array` | **Required**. phones of the user |

### Password criteria: 
It have at least 8 characters long.

It is composed exclusively of upper and lower case letters, numerical digits and the special characters mentioned (!@#$%.^&*()\-+=). 

### Json request example

```http
{
  "email": "migue@hotmail.com",
  "name": "Migue",
  "password": "Expell1armus*.0",
  "phones": [
    {
      "city_code": 1,
      "country_code": 1,
      "number": "3045758056"
    }
  ]
}
```

### Json Response example

```http
{
  "id": "480c0b7d-8af9-46ff-8225-5c11028285db",
  "token": "630d4906-5f5d-44b2-98e8-2f06a6c0ca36",
  "timeLastLogin": "2023-10-19T08:29:52.6128795",
  "is_active": true,
  "created": "2023-10-19T08:29:52.6128795",
  "modified": "2023-10-19T08:29:52.6128795"
}
```





## Tech Stack

**Client:** Swagger

**Server:** Java, JPA, gradle, JUnit5

To access swagger use: http://localhost:8081/swagger-ui/index.html and 
to access database use: http://localhost:8081/h2-console/login.jsp and use the word **password** as password. 

In the resources package is the class diagram. 


## Authors

- [@mpedraza](https://www.linkedin.com/in/miguel-p-874297b9/)

