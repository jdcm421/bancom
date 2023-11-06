package com.bancom.test.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequest {
    
    @NotEmpty(message = "Ingrese text")
    @Size(min = 6, max = 255)
    @ApiModelProperty(position = 1, required = true, name = "text", dataType = "String", example = "Se publica la nueva version")
    private String text;
}
