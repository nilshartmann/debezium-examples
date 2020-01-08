package io.debezium.examples.graphql;

import java.net.URI;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

@Path("/users")
public class ExampleResource {

  @Inject
  UserService userService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello";
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(User user) {
    Document userDocument = new Document().append("id", user.id).append("name", user.name);
    System.out.println(userDocument);
    Document result = userService.addUser(userDocument);
    System.out.println(result);
    return Response.created(URI.create("/users/" + result.get("id"))).entity(result).build();
  }

  public static class User {
    public String id;
    public String name;
  }
}