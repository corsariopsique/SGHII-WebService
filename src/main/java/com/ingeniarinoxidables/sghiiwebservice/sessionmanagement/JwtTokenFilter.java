package com.ingeniarinoxidables.sghiiwebservice.sessionmanagement;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.UPATDto;
import com.ingeniarinoxidables.sghiiwebservice.servicio.AutenticacionServicio;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {


    @Autowired
    private AutenticacionServicio authServicio;

    public JwtTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, jakarta.servlet.ServletException, ServletException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            UPATDto plantilla = authServicio.getAuth(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(plantilla.getPrincipal(),null, plantilla.getAuthorities());

            if (plantilla!= null) {
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}

