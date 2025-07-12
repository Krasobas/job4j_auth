package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.dto.AuthRequest;
import ru.job4j.auth.dto.AuthResponse;
import ru.job4j.auth.dto.RegistrationRequest;
import ru.job4j.auth.service.JwtService;
import ru.job4j.auth.service.PersonService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
  private final PersonService personService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
    personService.save(request);

    return ResponseEntity.ok("User registered successfully!");
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.username(), request.password())
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
    String token = jwtService.generateToken(userDetails);

    return ResponseEntity.ok(new AuthResponse(token));
  }
}