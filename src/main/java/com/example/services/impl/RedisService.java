package com.example.services.impl;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.ReplicatedServersConfig;
import org.redisson.connection.RedisClientEntry;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.GetExParams;
import redis.clients.jedis.params.Params;
import redis.clients.jedis.params.SetParams;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Singleton
public class RedisService {

    Jedis jedis = new Jedis("dale-portal-redis.gyk9d1.0001.use2.cache.amazonaws.com", 6379);

    public void save(String value) throws ExecutionException, InterruptedException, IOException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        //upsertCacheEntry("Name" + 1, "Value" + 1, false);

        String result = jedis.set("key", value, (new SetParams()));
jedis.get("key");
        jedis.close();

        // AWS Elasticache Replicated config

        /**Config config = new Config();
        config.useReplicatedServers()
                .addNodeAddress("redis://test-dev.amazonaws.com:6379");
*/
        // AWS Elasticache Cluster config
/**
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://dale-portal-redis.gyk9d1.0001.use2.cache.amazonaws.com:6379")
                ;


        RedissonClient redisson = Redisson.create(config);

        // perform operations

        RBucket bucket = redisson.getBucket("simpleObject");
        bucket.set("this is object");

        RMap map = redisson.getMap("simpleMap");
        map.put("mapKey", "This is map value");

        Object objectValue = bucket.get();
        System.out.println("stored object value: " + objectValue.toString());

        Object mapValue = map.get("mapKey");
        System.out.println("stored map value: " + mapValue);

        redisson.shutdown();*/
    }


    /**
     * Upsert cache entry - adds if not present; else updates based on the key.
     *
     * @param key the key
     * @param value the value
     * @param checkExists the check exists
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException the execution exception
     */
    private void upsertCacheEntry(String key, String value, boolean checkExists)
            throws InterruptedException, ExecutionException {
        boolean valueExists = false;
        if (checkExists && (getCacheValue(key) != null)) {
            valueExists = true;
        }
        jedis.get("name");
        String result = jedis.set(key, value);
        if (result.equalsIgnoreCase("OK")) {
            if (checkExists) {
                if (valueExists) {
                    System.out.println("Updated = {key=" + key + ", value=" + value + "}");
                } else {
                    System.out.println("Inserted = {key=" + key + ", value=" + value + "}");
                }
            } else {
                System.out.println("Upserted = {key=" + key + ", value=" + value + "}");
            }
        } else {
            System.out.println("Could not upsert key '" + key + "'");
        }
    }

    /**
     * Gets the specified cache value.
     *
     * @param key the key
     * @return the cache value
     */
    private String getCacheValue(String key) {
        long startTime = (new Date()).getTime();
        String value = jedis.get(key);
        long endTime = (new Date()).getTime();
        if (value != null) {
            System.out.println("Retrieved value='" + value + "' for key= '" + key + "' in " + (endTime - startTime)
                    + " millisecond(s).");
        } else {
            System.out.println("Key '" + key + "' not found.");
        }
        return value;
    }


}
