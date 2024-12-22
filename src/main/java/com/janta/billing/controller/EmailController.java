package com.janta.billing.controller;

import com.janta.billing.configuration.EmailService;
import com.janta.billing.dto.MailServiceDTO;
import com.janta.billing.service.DueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private DueRecordService dueRecordService;

    @PostMapping("/send/due-notifcation")
    public ResponseEntity<?> sendDueEmail( @RequestBody MailServiceDTO body) {
        try{
            body.setTemplateName("due");
            String  result = dueRecordService.sendDueNotification(body);

            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

