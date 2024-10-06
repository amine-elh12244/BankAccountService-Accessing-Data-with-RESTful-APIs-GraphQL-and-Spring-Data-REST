package org.sid.bankaccountservice.exceptions;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomDataFetcherExceptionResolver extends DataFetcherExceptionResolverAdapter {


    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        return new GraphQLError() {
            @Override
            public String getMessage() {
                return ex.getMessage();
            }

            @Override
            public List<SourceLocation> getLocations() {
                return null; // You can customize the location of the error if needed
            }

            @Override
            public ErrorClassification getErrorType() {
                return ErrorType.DataFetchingException; // Or a different error type based on your scenario
            }
        };
    }

}
