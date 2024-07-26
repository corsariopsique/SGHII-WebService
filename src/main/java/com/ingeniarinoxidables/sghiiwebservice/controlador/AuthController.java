package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.TokenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.User;
import com.ingeniarinoxidables.sghiiwebservice.sessionmanagement.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationProvider verificador;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@RequestBody UserDto user){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        if (!manager.userExists(user.getUsername())) {
            UserDetails userSpring = User.withUsername(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(user.getRole())
                    .build();
            manager.createUser(userSpring);
            return ResponseEntity.ok("Usuario creado exitosamente");
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto dataLogin, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = verificador.authenticate(
                    new UsernamePasswordAuthenticationToken(dataLogin.getUsername(),dataLogin.getPassword(), Collections.emptyList())
            );

            String jwt = tokenProvider.generateToken(authentication);
            TokenDto token = new TokenDto(jwt);

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
