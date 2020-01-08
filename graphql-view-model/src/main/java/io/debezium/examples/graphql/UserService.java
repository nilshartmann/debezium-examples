package io.debezium.examples.graphql;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class UserService {

  @Inject
  MongoClient mongoClient;

  public Document getUserById(final String id) {
    final BasicDBObject query = new BasicDBObject();
    query.put("id", id);
    Document document = getCollection().find(query).first();
    return document;
  }

  public Document addUser(Document user) {
    getCollection().insertOne(user);
    return user;
  }

  private MongoCollection<Document> getCollection() {
    return mongoClient.getDatabase("user").getCollection("user");
  }
}