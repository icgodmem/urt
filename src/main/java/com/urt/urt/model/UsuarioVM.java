package com.urt.urt.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class UsuarioVM {

	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private Long numeroDocumento;
	private String nombreUsuario;
	private String correoElectronico;
    private Long IDParametroDocumento;
    private Long IDParametroGenero;
    
}
