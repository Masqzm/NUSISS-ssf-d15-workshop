package ssf.day15.repositories;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

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

    // HGETALL CART:cartID
    public Optional<Cart> getCart(String cartID) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        Map<String, String> itemsStringMap = hashOps.entries(CART_KEY_PREFIX+cartID);
        TreeMap<String, Integer> itemsMap = new TreeMap<>();

        // CartID not found
        if(itemsStringMap.isEmpty())
            return Optional.empty(); 

        // Check if "" found as cart item (if found, itemsMap is empty)
        if( !(itemsStringMap.size() == 1 && itemsStringMap.containsKey("")) )
        {
            // remove any blank keys if any
            itemsStringMap.remove("");

            // Convert map values to int
            for(String key : itemsStringMap.keySet()) {
                int entryValue = 0;

                try {
                    entryValue = Integer.parseInt(itemsStringMap.get(key));
                } catch(NumberFormatException ex) {
                }

                itemsMap.put(key, entryValue);
            }
        }

        Cart cart = new Cart();
        cart.setId(cartID);
        cart.setItemsMap(itemsMap);

        return Optional.of(cart);
    }

    // HSET CART:<cartID> "" ""
    public void createCart(String cartID) {
        HashOperations<String, String, String> hashOps = template.opsForHash();

        hashOps.put(CART_KEY_PREFIX+cartID, "", "");
    }

    // HSET CART:<cartID> <item> <qty>
    public void updateCart(String cartID, TreeMap<String, Integer> cartTreeMap) {
        HashOperations<String, String, String> hashOps = template.opsForHash();

        Map<String, String> itemsStringMap = new TreeMap<>();
        
        // Convert values to string
        for(String key : cartTreeMap.keySet()) 
            itemsStringMap.put(key, cartTreeMap.get(key).toString());
        
        hashOps.putAll(CART_KEY_PREFIX+cartID, itemsStringMap);
    }
}
