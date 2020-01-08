* Start MongoDB:

```
docker run -ti --rm -p 27017:27017 mongo:4.0
```

* Start app:

```
./mvnw compile quarkus:dev
```

* Create a User:

```
http POST localhost:8080/users id=42 name="Bob the Builder"
```

* Query a User using GraphQL:

```
http POST localhost:8080/graphql <<< '{"query": "query UserQuery($userId: ID!){user(userId: $userId) { id name }}", "variables": { "userId": "42" } }'
```
