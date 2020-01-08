package io.debezium.examples.graphql.config;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.quarkus.runtime.StartupEvent;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import java.io.InputStreamReader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class GraphQLConfiguration {

  @Inject
  private QueryDataFetcher queryDataFetcher;

  @Produces
  public GraphQLSchema configureSchema() throws Exception {
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/schema.graphqls"));

    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(reader);

    RuntimeWiring runtimeWiring = newRuntimeWiring()
        .type("Query", builder -> builder.dataFetcher("user", queryDataFetcher.getUserFetcher()))
        .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
  }
}