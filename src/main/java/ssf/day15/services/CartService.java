package ssf.day15.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssf.day15.models.Cart;
import ssf.day15.repositories.CartRepository;

// Handles cart logic/queries
@Service    
public class CartService {
    @Autowired
    private CartRepository cartRepo;

    public Optional<Cart> getCart(String cartID) {
        return cartRepo.getCart(cartID);
    }

    public Cart createCart(String cartID) {
        cartRepo.createCart(cartID);

        Cart cart = new Cart();
        cart.setId(cartID);

        return cart;
    }
}
