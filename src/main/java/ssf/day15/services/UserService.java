package ssf.day15.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.day15.repositories.UserRepository;

// Handles user logic/queries (ie. get list of users, get particular user's list of carts)
@Service    
public class UserService {
    private final Logger logger = Logger.getLogger(UserService.class.getName());
    public final String USER_SESS_ATTR = "USER";

    @Autowired
    private UserRepository userRepo;

    // Returns cart list of particular user
    public List<String> getUserCartList(String username) {
        // Get user's current cartList
        String userCartListCSV = userRepo.getUserCartListCSV(username);

        List<String> cartList = new ArrayList<>();
        
        if(!userCartListCSV.isBlank())
            cartList = Arrays.asList(userCartListCSV.split(","));

        // return empty list if csv contains "" (ie. empty cartList)
        return cartList;
    }    

    // Returns cartID of newly created cart
    public String addNewCart(String username) {
        // Generate random cartID
        String cartID = UUID.randomUUID().toString().substring(0, 8);

        // Get user's current cartList
        String userCartListCSV = userRepo.getUserCartListCSV(username);

        if(userCartListCSV.isBlank())
            userRepo.updateCartList(username, cartID);
        else
            userRepo.updateCartList(username, userCartListCSV + "," + cartID);

        return cartID;
    }
}
