package com.pku.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public-key")
public class PublicKeyController {

    private static final String PRIVATE_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALmIDD+4R5QU2yV8CjQX3wMYYo6wRZx3OcC84AN5UncLxyEf5ejhFZ7J6mFK8sM8RNedhoxDEM43UScvBWD42fMCAwEAAQ==";

    @GetMapping("")
    public ResponseEntity<?> getPublicKey() {
        Map<String, String> response = new HashMap<>();
        response.put("publicKey", PRIVATE_KEY);
        return ResponseEntity.ok(response);
    }
}
