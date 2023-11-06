package com.bancom.test.service.impl;

import com.bancom.test.config.security.SecurityMain;
import com.bancom.test.entity.Usuario;
import com.bancom.test.repository.UsuarioRepository;
import com.bancom.test.request.UsuarioRequest;
import com.bancom.test.request.UsuarioUpRequest;
import com.bancom.test.response.ExcepcionResponse;
import com.bancom.test.response.Response;
import com.bancom.test.service.UsuarioService;
import java.util.Date;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    SecurityMain passwordEncode;

    @Override
    public ResponseEntity<?> crear(UsuarioRequest request) {
        Response response = new Response();
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UsuarioRequest>> constraintViolations = validator.validate(request);
            if (constraintViolations.iterator().hasNext()) {
                response.setMessage(constraintViolations.iterator().next().getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Usuario entity = new Usuario();
            entity.setName(request.getName());
            entity.setLastName(request.getLastName());
            entity.setCellPhone(request.getCellPhone());
            entity.setPassword(passwordEncode.passwordEncoder().encode(request.getPassword()));
            var result = repository.save(entity);

            if (result != null) {
                var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(result, request));
                var authAccount = (Usuario) auth.getPrincipal();
                response.setMessage("solicitud exitosa");
                response.setStatus(HttpStatus.OK);
                response.setData(authAccount.getPassword());
            }
        } catch (Exception ex) {
            log.error("Error de servidor ", ex);
            ExcepcionResponse exceptionResponse = new ExcepcionResponse();
            exceptionResponse.setCodigo(500);
            exceptionResponse.setMensajes("Error");
            exceptionResponse.setDetalles(ex.getMessage());
            exceptionResponse.setTimestamp(new Date());
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> modificar(Long id, UsuarioUpRequest request) {
        Response response = new Response();
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UsuarioUpRequest>> constraintViolations = validator.validate(request);
            if (constraintViolations.iterator().hasNext()) {
                response.setMessage(constraintViolations.iterator().next().getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (!repository.existsById(id)) {
                response.setMessage("No existe usuario");
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Usuario entity = repository.findById(id).get();
            entity.setName(request.getName());
            entity.setLastName(request.getLastName());
            entity.setCellPhone(request.getCellPhone());
            var result = repository.save(entity);
            if (result != null) {
                response.setMessage("solicitud exitosa");
                response.setStatus(HttpStatus.OK);
            }
        } catch (Exception ex) {
            log.error("Error de servidor ", ex);
            ExcepcionResponse exceptionResponse = new ExcepcionResponse();
            exceptionResponse.setCodigo(500);
            exceptionResponse.setMensajes("Error");
            exceptionResponse.setDetalles(ex.getMessage());
            exceptionResponse.setTimestamp(new Date());
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> eliminar(Long id) {
        Response response = new Response();
        try {
            if (!repository.existsById(id)) {
                response.setMessage("No existe usuario");
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            repository.deleteById(id);
            response.setMessage("solicitud exitosa");
            response.setStatus(HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error de servidor ", ex);
            ExcepcionResponse exceptionResponse = new ExcepcionResponse();
            exceptionResponse.setCodigo(500);
            exceptionResponse.setMensajes("Error");
            exceptionResponse.setDetalles(ex.getMessage());
            exceptionResponse.setTimestamp(new Date());
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> listar() {
        Response response = new Response();
        try {
            var result = repository.findAll();
            response.setMessage("solicitud exitosa");
            response.setStatus(HttpStatus.OK);
            response.setData(result);
        } catch (Exception ex) {
            log.error("Error de servidor ", ex);
            ExcepcionResponse exceptionResponse = new ExcepcionResponse();
            exceptionResponse.setCodigo(500);
            exceptionResponse.setMensajes("Error");
            exceptionResponse.setDetalles(ex.getMessage());
            exceptionResponse.setTimestamp(new Date());
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
