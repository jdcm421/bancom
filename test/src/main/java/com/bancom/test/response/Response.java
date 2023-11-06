package com.bancom.test.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response <E> implements Serializable {

    private E data;
    private String message;
    private HttpStatus status;
}
