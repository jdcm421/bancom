package com.bancom.test.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioRequest {
    @NotEmpty(message = "Ingrese cellPhone")
    @Size(min = 6, max = 9)
    @ApiModelProperty(position = 3, required = true, name = "cellPhone", dataType = "String", example = "963258741")
    private String cellPhone;
    
    @NotEmpty(message = "Ingrese name")
    @Size(min = 3, max = 100)
    @ApiModelProperty(position = 1, required = true, name = "name", dataType = "String", example = "Steve")
    private String name;
    
    @NotEmpty(message = "Ingrese lastName")
    @Size(min = 3, max = 100)
    @ApiModelProperty(position = 2, required = true, name = "lastName", dataType = "String", example = "Paredes")
    private String lastName;
    
    @NotEmpty(message = "Ingrese password")
    @Size(min = 8, max = 16)
    @ApiModelProperty(position = 4, required = true, name = "password", dataType = "String", example = "Lgoptimusg3$")
    private String password;
}
