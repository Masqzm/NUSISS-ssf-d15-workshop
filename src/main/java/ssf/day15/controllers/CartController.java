package ssf.day15.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import ssf.day15.models.Cart;
import ssf.day15.services.UserService;
import ssf.day15.services.CartService;

// Handles data validation from requests/posts
@Controller     
@RequestMapping
public class CartController {
    private final Logger logger = Logger.getLogger(CartController.class.getName());

    @Autowired
    private UserService userSvc;
    @Autowired
    private CartService cartSvc;

    @GetMapping("/cart/{cartID}")
    public ModelAndView getCart(@PathVariable String cartID, HttpSession sess) {
        String username = (String) sess.getAttribute(userSvc.USER_SESS_ATTR);
        ModelAndView mav = new ModelAndView();
        Optional<Cart> optCart = cartSvc.getCart(cartID);

        if(optCart.isEmpty()) {
            // 404
            mav.setViewName("not-found");
            mav.setStatus(HttpStatusCode.valueOf(404));
            mav.addObject("cartID", cartID);
            return mav;
        }

        Cart cart = optCart.get();

        // Check if the right user is logged in
        //if(username == null || !username.equals(cart.getUsername()))
        if(username == null)
        {
            // 401 unauthorized
            mav.setViewName("unauthorized");
            mav.setStatus(HttpStatusCode.valueOf(401));
            return mav;
        }

        // 200
        mav.setViewName("cart");
        mav.addObject("cart", cart);
        mav.addObject("username", username);
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

    @PostMapping("/new")
    public String postNewCart(HttpSession sess, Model model) {
        String username = (String) sess.getAttribute(userSvc.USER_SESS_ATTR);
        String cartID = userSvc.addNewCart(username);

        Cart cart = cartSvc.createCart(cartID);
        
        model.addAttribute("cart", cart);
        model.addAttribute("username", username);

        //logger.info("%s added new cart [%s]".formatted(username, cart.getId()));

        return "cart";
    }

    @PostMapping("/add")
    public String postAddCartItem(@RequestBody MultiValueMap<String, String> form, HttpSession sess, Model model) {
        String username = (String) sess.getAttribute(userSvc.USER_SESS_ATTR);
        Cart cart = cartSvc.getCartSession(sess);

        if(cart == null) 
            cart = cartSvc.getCart(form.getFirst("cartID")).get();
        
        String item = form.getFirst("item");
        int qty = Integer.parseInt(form.getFirst("qty"));

        // Get cartItemsMap ref
        TreeMap<String, Integer> cartItemsMap = cart.getItemsMap();
        
        // Update qty of item if it exists
        qty += cartItemsMap.getOrDefault(item, 0);

        cartItemsMap.put(item, qty);

        model.addAttribute("cart", cart);
        model.addAttribute("username", username);

        // Update cart of current sess
        cartSvc.updateCartSession(sess, cart);

        return "cart";
    }

    @PostMapping("/save")
    public String postSaveCart(@RequestParam("cartID") String cartID, HttpSession sess, Model model) {
        String username = (String) sess.getAttribute(userSvc.USER_SESS_ATTR);
        Cart cart = cartSvc.getCartSession(sess);
        String saveResult = "";

        logger.info("cartID: %s".formatted(cartID));
        // No current session found (ie. user haven't added anything)
        if(cart == null)
        {
            saveResult = "Error: Please add at least one item first!";
            cart = cartSvc.getCart(cartID).get();
        }
        else
        {
            cartSvc.updateCart(cartID, cart.getItemsMap());
            saveResult = "Saved!";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("username", username);
        model.addAttribute("saveResult", saveResult);

        return "cart";
    }
}
