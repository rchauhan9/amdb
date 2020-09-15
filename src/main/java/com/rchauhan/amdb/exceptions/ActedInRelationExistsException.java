package com.rchauhan.amdb.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class ActedInRelationExistsException extends RuntimeException implements GraphQLError {
    public ActedInRelationExistsException(String message) {
        super(message);
    }


    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }
}
