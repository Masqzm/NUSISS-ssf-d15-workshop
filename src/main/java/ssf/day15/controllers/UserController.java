package ssf.day15.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ssf.day15.services.UserService;

@Controller
@RequestMapping
public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userSvc;

    
    @GetMapping(path={"/", "index.html"})
    public String getIndex(HttpSession sess) {
        sess.invalidate();

        return "index";
    }
    
    @GetMapping("/user")
    public String getUser(@RequestParam String username, HttpSession sess, Model model) {

        // TODO: 
        // validation of name - only allow alphanumeric

        // Sign in to session
        sess.setAttribute(userSvc.USER_SESS_ATTR, username);

        List<String> cartList = userSvc.getUserCartList(username.toLowerCase());

        logger.info("cartList: %s".formatted(cartList));

        model.addAttribute("username", username);
        model.addAttribute("cartList", cartList);

        return "cart-list";
    }
}
