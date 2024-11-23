package ssf.day15.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    // HEXISTS CART fred
    // HSET CART fred ""
    public List<String> getUserCartList(String username) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
                
        // Create user if they don't exist
        hashOps.putIfAbsent(USER_KEY, username, "");

        // Convert cartsCSV to list of string (of cartIDs)
        String cartsCSV = (String) hashOps.get(USER_KEY, username);
        
        logger.info("cartCSV: [%s]".formatted(cartsCSV));

        // User has no carts
        if(cartsCSV.isBlank())
            return null;
        
        List<String> cartList = Arrays.asList(cartsCSV.split(","));
        
        logger.info("cartList: %s".formatted(cartList));

        return cartList;
    }
}