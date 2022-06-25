package com.example.Security20.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class deleteCpontroller {

    @GetMapping("/all")
    String forall(){
        log.info("in the all");
        return "all";
    }

    @GetMapping("/onlyauth")
    String forauth(){
        log.info("in the auth");
        return "onlyauth";
    }

    @GetMapping("/onlyadmin")
    String foradmin(){
        log.info("in the onlyadmin");
        return "onlyadmin";
    }

    @GetMapping("/success")
    public String message(){
        return "user3";
    }
}
