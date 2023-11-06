package com.bancom.test.config;

import com.bancom.test.repository.UsuarioRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {


    @Autowired
    UsuarioRepository sesionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fc) throws ServletException, IOException {
        try {
            String token = getToken(request);
            if (token != null) {
                final var adminDTO = sesionService.findByPassword(token).get();

                if (adminDTO != null) {
                    UsernamePasswordAuthenticationToken auth
                            = new UsernamePasswordAuthenticationToken(adminDTO, token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.info("Solicitud realizada es " + request.getMethod() + " " + request.getRequestURI());
                }

            }
        } catch (Exception e) {
            log.error("Fallo en el filtro de Token " + e.getMessage());
        }
        fc.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }
}
