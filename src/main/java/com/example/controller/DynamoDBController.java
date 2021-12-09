package com.example.controller;
import com.example.services.impl.DynamoDBService;
import com.example.services.impl.DynamoDBServiceAws;
import com.example.vo.Event;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/dynamoDB")
public class DynamoDBController {

    private final DynamoDBService dynamoDBService;

    public DynamoDBController(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    @Post("/createCollection")
    public String createElement(String collectionName, String idKey, String campoUno) {
        return dynamoDBService.createCollection(collectionName, idKey, campoUno);
    }
}
