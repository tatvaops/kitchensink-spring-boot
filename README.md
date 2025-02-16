# Docker Instructions

## Build the Docker Image

To build the Docker image, run the following command:

```sh
docker build -t kitchensink -f Dockerfile .
```

## Run the Docker Container

To run the Docker container, use the following command:

```sh
docker run -p 4000:4000 \
  -e APP_NAME=demo \
  -e MONGODB_URI="mongodb://localhost:27017" \
  -e LOG_LEVEL=INFO \
  -e PORT=4000 \
  demo-java
```