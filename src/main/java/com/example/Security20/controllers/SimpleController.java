package com.example.Security20.controllers;

import com.example.Security20.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//@RestController
public class SimpleController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;

    }

    /**
     * любой человек сможет сюда зайти даже неаутентифицированный
     */
    @GetMapping("/")
    public String homepage() {
        return "home";
    }


    @GetMapping("/readprofile")
    public String readPro() {
        return "readprofilepage";
    }

    @GetMapping("/foradmin")
    public String admins() {
        return "adminpage";
    }

    /**
     * только аутентифицированный сможет зайти
     */
    @GetMapping("/auth")
    public String onlyAuth(Principal principal) {
        return "websecured" + principal.getName();
    }

    @GetMapping("/success")
    public String success(){
        return "yeahhh";
    }
}
