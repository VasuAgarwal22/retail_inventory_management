package org.example.retail_inventory_managment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.retail_inventory_managment.dto.requestDTO.UserPreferenceRequest;
import org.example.retail_inventory_managment.dto.resposneDTO.UserPreferenceResponse;
import org.example.retail_inventory_managment.service.Implementation.UserPreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/preferences") @RequiredArgsConstructor
public class UserPreferenceController {
    private final UserPreferenceService service;
    @GetMapping public ResponseEntity<List<UserPreferenceResponse>> list(Authentication authentication) { return ResponseEntity.ok(service.list(authentication.getName())); }
    @PutMapping public ResponseEntity<UserPreferenceResponse> save(@Valid @RequestBody UserPreferenceRequest request, Authentication authentication) { return ResponseEntity.ok(service.save(request, authentication.getName())); }
}
