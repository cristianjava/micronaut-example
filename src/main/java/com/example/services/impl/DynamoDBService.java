package com.example.services.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import javax.inject.Singleton;
@Singleton
public class DynamoDBService {

    public static final String TABLE_NAME = "events";
    public static final String ID_COLUMN = "id";
    public static final String BODY_COLUMN = "body";

    private final AmazonDynamoDBAsync client;

    public DynamoDBService(AmazonDynamoDBAsync client) {
        this.client = client;
    }


    public String createCollection(String collectionName, String idKey, String campoUno) {
        DynamoDB dynamoDB = new DynamoDB(client);
        try {

            Table table = dynamoDB.createTable(collectionName,
                    Arrays.asList(new KeySchemaElement(idKey, KeyType.HASH), // Partition
                            // key
                            new KeySchemaElement(campoUno, KeyType.RANGE)), // Sort key
                    Arrays.asList(new AttributeDefinition(idKey, ScalarAttributeType.N),
                            new AttributeDefinition(campoUno, ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            return table.getDescription().getTableStatus();

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    public void updateItem() {
        AmazonDynamoDBAsyncClientBuilder clientBuilder = AmazonDynamoDBAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(null))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("dynamodb.sa-east-1.amazonaws.com", "sa-east-1")
                );
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();

        Map keys = new HashMap();
        keys.put("username", "cristian.castano@ceiba.com.co");
        UpdateItemRequest updateItemRequest = new UpdateItemRequest();
        updateItemRequest
                .withTableName("")
                .withKey(keys)
                .withUpdateExpression("")
                .withExpressionAttributeValues(keys);

        amazonDynamoDB.updateItem(updateItemRequest);
    }
}
