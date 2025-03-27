package com.janta.billing.controller;

import com.janta.billing.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/janta-store/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;
    @GetMapping("/details")
    public ResponseEntity<?> getDetails() {
        try{
            Map<String,Object> res = dashboardService.getDashboard();
            return ResponseEntity.ok().body(res);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
