# Building Mock Server Product Management

Express makes it easy to create decoupled applications and easy to configure.
This helps us in the creation of stand-alone microservices, as we see in the following
example:


**Getting Started** 

  * Express js
  * Nodejs
  * Java Script
  * Faker
  
**Previous knowledge**

  :radio_button: Javascript
  :radio_button: Express JS
  :radio_button: Docker
  :radio_button: RESTful API
  :radio_button: Postman

**Structure Project**
 
```text
.
├─ mock-server
├─── .env
├─── boot-application.js
├─── product-repository.js
├─── Dockerfile
├─── PRODUCT_MANAGEMENT.postman_collection.json
└─── package.json
```

### HTTP Operation that exposes our API

| Path | Operation | Summary             |
| ------ | ------ |---------------------|
|/api/v1/products|GET| Get Products        |
|/api/v1/products/{{PRODUCT_CODE}}|GET| Get Product by Code |
|/api/v1/product-search?name=Pizza|GET| Get Product by Name |



# Container Microservices 

Docker is a perfect combination to power our microservices and distribute our APIs in Cloud environments,
On-premise or try our microservices locally.

## Docker File Java
```dockerfile
FROM node:10-alpine
RUN mkdir -p /home/node/app/node_modules && chown -R node:node /home/node/app
WORKDIR /home/node/app
COPY package*.json ./
USER node
RUN npm install
COPY --chown=node:node . .
EXPOSE 8080
CMD [ "node", "boot-application.js" ]
```

### Docker Build and Docker Run Images
```bash
$ docker login
$ docker build . -t product-management:${ARTIFACT_VERSION}
$ docker images
$ docker run -d --name product-management-local -p 8080:8080 product-management:${ARTIFACT_VERSION}
$ docker ps
$ docker tag product-management gcr.io/${PROJECT_ID}/product-management:${ARTIFACT_VERSION}
```

### Local expose 

```sh
127.0.0.1:3000
```

## Postman Collection

Postman collection of the root project: PRODUCT_MANAGEMENT.postman_collection.json

### Conclusions

We've seen in these examples, the creation of microservices with Express, simple development with functional programming and
how to containerize our REST API quickly and easily.

## References

| Concepts                                                 |              Approved            |
|----------------------------------------------------------|----------------------------------|
| [FakerJs](https://fakerjs.dev/api/commerce.html#product) |          :heavy_check_mark:      |
