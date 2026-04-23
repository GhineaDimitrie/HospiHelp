package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.config.CustomUserDetailsService;
import com.hospihelp.hospihelp.config.JwtUtil;
import com.hospihelp.hospihelp.dto.LoginRequest;
import com.hospihelp.hospihelp.dto.LoginResponse;
import com.hospihelp.hospihelp.model.Angajat;
import com.hospihelp.hospihelp.service.AngajatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AngajatService angajatService;



    @GetMapping("/test-hash")
    public ResponseEntity<String> testHash() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return ResponseEntity.ok(encoder.encode("admin123"));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getParola()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                    .body("Email sau parola incorecte");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        Angajat angajat = angajatService.getAngajatByEmail(request.getEmail());
        String token = jwtUtil.genereazaToken(userDetails, angajat.getRol().name());

        return ResponseEntity.ok(new LoginResponse(
                token,
                angajat.getEmail(),
                angajat.getRol().name(),
                angajat.getNume(),
                angajat.getPrenume()
        ));
    }
}