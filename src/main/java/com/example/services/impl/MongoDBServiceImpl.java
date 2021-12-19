package com.example.services.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
/**import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;*/
import io.micronaut.http.annotation.Body;
import io.reactivex.Flowable;
import org.bson.Document;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;

@Singleton
public class MongoDBServiceImpl {
/**
    private final MongoClient mongoClient;

    public MongoDBServiceImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public FindPublisher<Document> find () {
        MongoCollection<Document> collection = getCollection();
        return collection.find();
     }

    public Publisher<InsertOneResult> insert (ObjectNode objectNode) {
        MongoCollection<Document> collection = getCollection();
        final Document document = Document.parse(objectNode.toString());
        return collection.insertOne(document);
     }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("mi_primer_base").getCollection("example");
    }
*/
}
