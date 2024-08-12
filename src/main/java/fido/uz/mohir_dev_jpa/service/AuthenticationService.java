package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.dto.request.SignUpRequest;
import fido.uz.mohir_dev_jpa.dto.request.SigninRequest;
import fido.uz.mohir_dev_jpa.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    JwtAuthenticationResponse createAdmin(SignUpRequest request);
}