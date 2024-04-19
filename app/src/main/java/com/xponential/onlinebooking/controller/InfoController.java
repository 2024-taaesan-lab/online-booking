package com.xponential.onlinebooking.controller;

import com.xponential.onlinebooking.model.InfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<InfoResponse> info() {
        long start = System.currentTimeMillis();

        InfoResponse info = new InfoResponse();
        info.setMessage("Test");

        return ResponseEntity.ok(info);
    }
}
