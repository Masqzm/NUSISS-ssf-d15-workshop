package ssf.day15.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.day15.models.Cart;
import ssf.day15.repositories.CartRepository;
import ssf.day15.repositories.UserRepository;

// Handles cart logic/queries
@Service    
public class CartService {
    @Autowired
    private CartRepository cartRepo;
}
