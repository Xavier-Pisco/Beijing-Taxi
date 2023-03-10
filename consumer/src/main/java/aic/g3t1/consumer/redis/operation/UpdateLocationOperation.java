package aic.g3t1.consumer.redis.operation;

import aic.g3t1.common.redis.RedisHashes;
import redis.clients.jedis.JedisCommands;

public class UpdateLocationOperation implements RedisOperation {
    private static final long serialVersionUID = -1845286694277615005L;

    private final Integer taxiNumber;
    private final String location;

    // Kryo no-arg constructor
    public UpdateLocationOperation() {
        taxiNumber = -1;
        location = "";
    }

    public UpdateLocationOperation(int taxiNumber, String location) {
        this.taxiNumber = taxiNumber;
        this.location = location;
    }

    @Override
    public void perform(JedisCommands commands) {
        commands.hset(RedisHashes.R_LOCATION_HASH, String.valueOf(taxiNumber),  location);
    }

    @Override
    public String toString() {
        return "{ " +
                "\"taxiNumber\": " + taxiNumber +
                ", \"location\": " + location +
                " }";
    }
}
