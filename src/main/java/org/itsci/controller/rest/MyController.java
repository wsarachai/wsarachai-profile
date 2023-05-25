package org.itsci.controller.rest;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class MyController {

    // Define your API endpoint
    @PostMapping("/api/resource")
    public ResponseEntity<String> createResource(@RequestBody String requestBody) {


        return ResponseEntity.ok("Resource created successfully");
    }
}

