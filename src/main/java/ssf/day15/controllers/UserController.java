package ssf.day15.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssf.day15.services.UserService;

@Controller
@RequestMapping
public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userSvc;
    
    @GetMapping("/user")
    public String getUser(@RequestParam String name, Model model) {

        // TODO: 
        // validation of name - only allow alphanumeric

        List<String> cartList = userSvc.getUserCartList(name.toLowerCase());

        model.addAttribute("userName", name);
        model.addAttribute("cartList", cartList);

        return "cart-list";
    }
}
