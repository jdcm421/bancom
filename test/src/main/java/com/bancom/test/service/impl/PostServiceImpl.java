package com.bancom.test.service.impl;

import com.bancom.test.entity.Post;
import com.bancom.test.entity.Usuario;
import com.bancom.test.repository.PostRepository;
import com.bancom.test.request.PostRequest;
import com.bancom.test.response.ExcepcionResponse;
import com.bancom.test.response.Response;
import com.bancom.test.service.PostService;
import java.util.Date;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository repository;

    @Override
    public ResponseEntity<?> crear(PostRequest request) {
        Response response = new Response();
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var authAccount = (Usuario) auth.getPrincipal();

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<PostRequest>> constraintViolations = validator.validate(request);
            if (constraintViolations.iterator().hasNext()) {
                response.setMessage(constraintViolations.iterator().next().getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Post entity = new Post();
            entity.setText(request.getText());
            entity.setUsuario(authAccount);
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
    public ResponseEntity<?> modificar(Long id, PostRequest request) {
        Response response = new Response();
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var authAccount = (Usuario) auth.getPrincipal();

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<PostRequest>> constraintViolations = validator.validate(request);
            if (constraintViolations.iterator().hasNext()) {
                response.setMessage(constraintViolations.iterator().next().getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (repository.existsById(id)) {
                response.setMessage("No existe post");
                response.setStatus(HttpStatus.BAD_REQUEST);
            }

            Post entity = repository.findById(id).get();
            if (!entity.getUsuario().equals(authAccount)) {
                response.setMessage("No puede actualizar este post");
                response.setStatus(HttpStatus.BAD_REQUEST);
            }
            entity.setText(request.getText());
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
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var authAccount = (Usuario) auth.getPrincipal();
            if (repository.existsById(id)) {
                response.setMessage("No existe post");
                response.setStatus(HttpStatus.BAD_REQUEST);
            }

            Post entity = repository.findById(id).get();
            if (!entity.getUsuario().equals(authAccount)) {
                response.setMessage("No puede eliminar este post");
                response.setStatus(HttpStatus.BAD_REQUEST);
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
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var authAccount = (Usuario) auth.getPrincipal();
            var result = repository.findByUser(authAccount.getId());
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
