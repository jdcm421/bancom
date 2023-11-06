package com.bancom.test.controller;

import com.bancom.test.request.UsuarioRequest;
import com.bancom.test.request.UsuarioUpRequest;
import com.bancom.test.service.UsuarioService;
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

@Tag(name = "Usuarios", description = "Controlador de Usuario")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    UsuarioService usuarioService;
    
    @Operation(
            summary = "Listar Usuario",
            description = "Se listan todos los Usuario por usuario",tags = "Usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> lista() {
        return usuarioService.listar();
    }
    
    @Operation(
            summary = "Crear Usuario",
            description = "Se registra Usuario",tags = "Usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @PostMapping(value = "/crear",consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> crear(@RequestBody UsuarioRequest request) {
        return usuarioService.crear(request);
    }
    
    @Operation(
            summary = "Actualizar",
            description = "Se actualiza Usuario",tags = "Usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @PutMapping(value = "/actualizar/{id}",consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody UsuarioUpRequest request) {
        return usuarioService.modificar(id, request);
    }
    
    @Operation(
            summary = "Eliminar",
            description = "Se elimina Usuario",tags = "Usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @DeleteMapping(value = "/eliminar/{id}", produces = "application/json")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return usuarioService.eliminar(id);
    }
}
