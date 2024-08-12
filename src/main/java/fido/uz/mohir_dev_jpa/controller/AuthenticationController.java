package fido.uz.mohir_dev_jpa.controller;

import fido.uz.mohir_dev_jpa.dto.request.SignUpRequest;
import fido.uz.mohir_dev_jpa.dto.request.SigninRequest;
import fido.uz.mohir_dev_jpa.dto.response.JwtAuthenticationResponse;
import fido.uz.mohir_dev_jpa.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }
    @PostMapping("/create-admin")
    public ResponseEntity<JwtAuthenticationResponse> createAdmin(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.createAdmin(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
