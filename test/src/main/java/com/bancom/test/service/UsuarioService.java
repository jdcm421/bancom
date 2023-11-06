package com.bancom.test.service;

import com.bancom.test.request.UsuarioRequest;
import com.bancom.test.request.UsuarioUpRequest;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    public ResponseEntity<?> crear(UsuarioRequest request);
    public ResponseEntity<?> modificar(Long id,UsuarioUpRequest request);
    public ResponseEntity<?> eliminar(Long id);
    public ResponseEntity<?> listar();
}
