package ssf.day15.services;

import java.util.LinkedList;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import ssf.day15.models.Cart;
import ssf.day15.repositories.CartRepository;

// Handles cart logic/queries
@Service    
public class CartService {
    @Autowired
    private CartRepository cartRepo;
    public final String CART_SESS_ATTR = "CART";

    // Update current session's cart
    public void updateCartSession(HttpSession sess, Cart cart) {
        sess.setAttribute(CART_SESS_ATTR, cart);
    }

    public Cart getCartSession(HttpSession sess) {
        Cart cart = (Cart) sess.getAttribute(CART_SESS_ATTR);

        return cart;
    }

    public Optional<Cart> getCart(String cartID) {
        return cartRepo.getCart(cartID);
    }

    public Cart createCart(String cartID) {
        cartRepo.createCart(cartID);

        Cart cart = new Cart();
        cart.setId(cartID);

        return cart;
    }

    public void updateCart(String cartID, TreeMap<String, Integer> cartTreeMap) {
        cartRepo.updateCart(cartID, cartTreeMap);
    }
}
