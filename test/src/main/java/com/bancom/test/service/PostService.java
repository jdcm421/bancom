package com.bancom.test.service;

import com.bancom.test.request.PostRequest;
import org.springframework.http.ResponseEntity;


public interface PostService {
    public ResponseEntity<?> crear(PostRequest request);
    public ResponseEntity<?> modificar(Long id,PostRequest request);
    public ResponseEntity<?> eliminar(Long id);
    public ResponseEntity<?> listar();
}
