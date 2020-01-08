package io.debezium.examples.graphql.config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import graphql.schema.DataFetcher;
import io.debezium.examples.graphql.UserService;

@ApplicationScoped
public class QueryDataFetcher {

  @Inject
  private UserService userService;

  private DataFetcher<Object> userFetcher = env -> {
    String userId = env.getArgument("userId");
    System.out.println("Fetching User with id from MongoDB: " + userId);

    return userService.getUserById(userId);
  };

  public DataFetcher<Object> getUserFetcher() {
    return userFetcher;
  }

}