package ssf.day15.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/new")
    public String postNewCart() {
        logger.info("New cart added");
        return "";
    }

    @PostMapping("/save")
    public String postSaveCart() {
        return "";
    }
}
