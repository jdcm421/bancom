package com.bancom.test.controller;

import com.bancom.test.request.PostRequest;
import com.bancom.test.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post", description = "Controlador de Post")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Operation(
            summary = "Listar post",
            description = "Se listan todos los post por usuario", tags = "Post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> lista() {
        return postService.listar();
    }
    
    @Operation(
            summary = "Crear post",
            description = "Se registra post", tags = "Post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @PostMapping(value = "/crear",consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> crear(@RequestBody PostRequest request) {
        return postService.crear(request);
    }
    
    @Operation(
            summary = "Actualizar",
            description = "Se actualiza post", tags = "Post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @PutMapping(value = "/actualizar/{id}",consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody PostRequest request) {
        return postService.modificar(id, request);
    }
    
    @Operation(
            summary = "Eliminar",
            description = "Se elimina post", tags = "Post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @DeleteMapping(value = "/eliminar/{id}", produces = "application/json")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return postService.eliminar(id);
    }
    
}
