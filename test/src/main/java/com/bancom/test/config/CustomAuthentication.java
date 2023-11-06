package com.bancom.test.config;

import com.bancom.test.entity.Usuario;
import com.bancom.test.repository.UsuarioRepository;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthentication implements AuthenticationProvider {

    @Autowired
    UsuarioRepository sesionService;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var credentials = (Usuario) authentication.getPrincipal();
        final var account = sesionService.findById(credentials.getId()).get();
        entityManager.detach(account);
        return new UsernamePasswordAuthenticationToken(account, credentials.getPassword(), Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
