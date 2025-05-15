package com.janta.billing.controller;

import com.janta.billing.dto.ApiResponse;
import com.janta.billing.dto.DueRecordWithCustomerDTO;
import com.janta.billing.entity.DueRecord;
import com.janta.billing.service.DueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/janta-store/due")
public class DueLogController {

    @Autowired
    DueRecordService dueRecordService;
    @GetMapping("/all")
    public ResponseEntity<?> dueLog(){
    List<DueRecordWithCustomerDTO> res = dueRecordService.getDueLogs();
    return ResponseEntity.ok(res);
    }
}
