package ssf.day15.repositories;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssf.day15.models.Cart;

// Interfaces with db of key=CART:<cartID>
// K: CART:<cartID>, HK: itemName, HV: quantity
@Repository     
public class CartRepository {
    private final Logger logger = Logger.getLogger(CartRepository.class.getName());
    private final String CART_KEY_PREFIX = "CART:";

    // DependencyInject (DI) the RedisTemplate into ContactRepository
    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    // HVALS CART
    // 
    public List<Cart> getUserCartList(String username) {

        return null;
    }
}
