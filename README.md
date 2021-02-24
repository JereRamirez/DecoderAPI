# Decoder API

Decoder is an application that decodes the message intercepted and the position from a spaceship.

## SATELLITES
This aplication counts with three satellites that intercept the message sent from the spaceship.
They are located at:

```bash
Kenobi
     { x: -500, y: -200 }

Skywalker
     { x:  100, y: -100 }
      
Sato
     { x: 500, y: 100 }

```

## ENDPOINTS


 **GET -> /secret_controller/topsecret_split**
 * ***Returns the message and position of the spacechip based on the requests persisted in the database.***

 **POST -> /secret_controller/topsecret_split/{satelliteName} with Body like:**
```bash
          {
            "distance": 200.0,
            "message": ["este", "", "", "mensaje", ""]

          }
```

 * ***Adds a request to the database for the satellite.***
 
 **POST -> /secret_controller/topsecret with Body like:**
```bash
          {
            "satellites": 
            [
                {
                  "name": "kenobi",
                  "distance": 200.0,
                  "message": ["este", "", "", "mensaje", ""]
                },
                {
                  "name": "skywalker",
                  "distance": 470.4,
                  "message": ["", "es", "", "", "secreto"]
                },
                {
                  "name": "sato",
                  "distance": 880,
                  "message": ["este", "", "un", "", ""]
                }
              ] 
            }
```
 * ***With this request you get a valid response with response status 200 OK with Body:***
```bash  
        {
          "position": {
              "x": -367.7332,
              "y": -49.98171
          },
          "message": "este es un mensaje secreto"
        }
```
## ALGORITHMS
* The message is built with **Backtracking**.
* The position is obtained with **Trilateracion**.

## HOST
The application is host on: https://limitless-shelf-40709.herokuapp.com/
```
It does not have a user interface but all endpoints are available.
```
