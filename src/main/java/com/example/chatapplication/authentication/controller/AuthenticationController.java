package com.example.chatapplication.authentication.controller;

import com.example.chatapplication.authentication.model.User;
import com.example.chatapplication.authentication.service.UserService;
import com.example.chatapplication.authentication.validator.NewUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private NewUserValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }

    @GetMapping("/login")
    public String loginPage() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/room/123";
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/room/123";
    }

    @GetMapping("/new-account")
    public String newAccount(ModelMap model) {
        model.put("user", new User());
        return "new-account";
    }

    @PostMapping("/new-account")
    public String saveNewAccount(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "new-account";
        }
        userService.createUser(user);
        return "redirect:/";
    }
}
