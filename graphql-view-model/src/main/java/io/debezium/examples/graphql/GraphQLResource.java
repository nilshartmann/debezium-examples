package io.debezium.examples.graphql;

import java.util.Map;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

@Path("/graphql")
public class GraphQLResource {

  @Inject
  private GraphQLSchema graphQLSchema;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response executeGraphQL(JsonObject graphQLRequest) throws Exception {
    String query = graphQLRequest.getString("query");
    String operationName = graphQLRequest.getString("operationName", "");
    Map<String, Object> variables = JsonUtil.jsonToMap(graphQLRequest.getJsonObject("variables"));

    GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
    ExecutionInput input = ExecutionInput.newExecutionInput().query(query).operationName(operationName)
        .variables(variables).build();

    ExecutionResult executionResult = build.execute(input);
    return Response.ok(executionResult.toSpecification()).build();
  }
}
