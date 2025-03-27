package com.janta.billing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/home")
public class HomePageController {

    @GetMapping("/product-list")
    public ResponseEntity<?> productList() {

        return ResponseEntity.ok().body("");
    }
}
