# Building, Run and Test API

**Getting Started** 

  * Express js
  * Nodejs
  * Java Script
  * Faker
  * FS (File System)
  
**Previous knowledge**

  :radio_button: Javascript

  :radio_button: Express JS

  :radio_button: Docker

  :radio_button: RESTful API

  :radio_button: Postman

### HTTP Operation that exposes our API

| Path | Operation | Summary               |
| ------ | ------ |-----------------------|
|/fixer/latest|GET| Get ALL Fixers        |
|/fixer/latest?base=USD&symbols=PEN|GET| Get Product by Params |

# Building Container Microservices

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
$ docker build . -t currency-api:${ARTIFACT_VERSION}
$ docker run -d --name currency-api-local -p 3000:3000 currency-api:${ARTIFACT_VERSION}
```

### Local Build Expose 

```sh
npm install
node boot-application.js
```

```sh
127.0.0.1:3000
```

## Postman Collection

Postman collection of the root project: API-THIRD-PARTY-CURRENCY.postman_collection.json
