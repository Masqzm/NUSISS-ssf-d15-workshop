package ssf.day15.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.day15.repositories.UserRepository;

// Handles user logic/queries (ie. get list of users, get particular user's list of carts)
@Service    
public class UserService {
    @Autowired
    private UserRepository userRepo;

    // Returns cart list of particular user
    public List<String> getUserCartList(String username) {
        return userRepo.getUserCartList(username);
    }    
}
