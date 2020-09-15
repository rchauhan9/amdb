package com.rchauhan.amdb;

//import com.rchauhan.amdb.exceptions.GraphQLErrorAdaptor;
import graphql.GraphQLError;
//import graphql.servlet.DefaultGraphQLErrorHandler;
//import graphql.servlet.GenericGraphQLError;
//import graphql.servlet.GraphQLErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class AmdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmdbApplication.class, args);
	}

//	@Bean
//	public GraphQLErrorHandler graphQLErrorHandler() {
//		return new DefaultGraphQLErrorHandler() {
//			@Override
//			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
//				List<GraphQLError> clientErrors = errors.stream()
//						.filter(e -> isClientError(e))
//						.map(GraphQLErrorAdaptor::new)
//						.collect(Collectors.toList());
//
//				List<GraphQLError> serverErrors = errors.stream()
//						.filter(e -> !isClientError(e))
//						.map(GraphQLErrorAdaptor::new)
//						.collect(Collectors.toList());
//
//				if (!serverErrors.isEmpty()) {
//					serverErrors = new ArrayList<>();
//					serverErrors.add(new GenericGraphQLError("Internal Server Error(s) while executing query"));
//				}
//
//				List<GraphQLError> e = new ArrayList<>();
//				e.addAll(clientErrors);
//				e.addAll(serverErrors);
//				return e;
//			}
//		};
//	}

}
