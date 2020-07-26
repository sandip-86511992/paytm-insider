# paytm-insider

## Application Url : http://localhost:8080

## Get Top Stories : /top-stories

## Get Top Comments : /comments/<story-id>

## Get Past Stories : /past-stories

## Build command : ./mvnw package && java -jar target paytminsider-0.1.0.jar

## Docker build : docker build -t paytminsider/hacker-news .

## Docker run : docker run -d -p 8080:8080 paytminsider/hacker-news