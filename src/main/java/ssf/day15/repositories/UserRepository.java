package ssf.day15.repositories;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

// Interfaces with db of key=USER
// K: USER, HK: username, HV: csv of cartIDs
@Repository     
public class UserRepository {
    private final Logger logger = Logger.getLogger(UserRepository.class.getName());
    private final String USER_KEY = "USER";

    // DependencyInject (DI) the RedisTemplate into ContactRepository
    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;
    
    // HEXISTS USER fred
    // # if fred not avail:
    // HSET USER fred ""
    // HGET USER fred
    // Returns a user's cart list as csv. Will create new user if user doesnt exist
    public String getUserCartListCSV(String username) {
        HashOperations<String, String, String> hashOps = template.opsForHash();

        // Create user if they don't exist
        hashOps.putIfAbsent(USER_KEY, username, "");

        return (String) hashOps.get(USER_KEY, username);
    }

    // HSET USER fred "<cartListCSV>"
    public void updateCartList(String username, String cartListCSV) {
        HashOperations<String, String, String> hashOps = template.opsForHash();

        hashOps.put(USER_KEY, username, cartListCSV);
    }
}