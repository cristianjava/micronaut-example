package com.example.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.example.services.IS3Service;
import com.example.services.impl.MongoDBServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.mongodb.client.result.InsertOneResult;
//import com.mongodb.reactivestreams.client.FindPublisher;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/mongo")
public class MongoDBController {

    private static final Logger LOG = LoggerFactory.getLogger(MongoDBController.class);
    private final MongoDBServiceImpl mongoDBService;

    public MongoDBController(MongoDBServiceImpl mongoDBService) {
        this.mongoDBService = mongoDBService;
    }
/**
    @Get("/")
    public FindPublisher<Document> find() {
        LOG.info("Inicio");
        FindPublisher<Document> result = mongoDBService.find();
        return result;
    }

    @Post("/")
    public Publisher<InsertOneResult> insert(@Body ObjectNode objectNode) {
        LOG.info("Inicio");
        return mongoDBService.insert(objectNode);
    }
*/
}
