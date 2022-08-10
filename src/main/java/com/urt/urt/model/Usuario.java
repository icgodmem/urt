package com.urt.urt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
    @Column(name = "primer_nombre", nullable = false)
	private String primerNombre;
	
    @NotNull
    @Column(name = "segundo_nombre", nullable = false)
	private String segundoNombre;
    
    @NotNull
    @Column(name = "primer_apellido", nullable = false)
	private String primerApellido;
    
    @NotNull
    @Column(name = "segundo_apellido", nullable = false)
	private String segundoApellido;
    
    @NotNull
    @Column(name = "numero_documento", nullable = false, unique = true)
	private Long numeroDocumento;
	
    @NotNull
    @Column(name = "nombre_usuario", nullable = false, unique = true)
	private String nombreUsuario;
    
    @NotNull
    @Pattern(regexp = "^(.+)@(.+)$")
    @Column(name = "correo_electronico", nullable = false)
	private String correoElectronico;
    
    @ManyToOne
    @JsonIgnoreProperties(value = { "usuarios" }, allowSetters = true)
    private ParametroDocumento parametroDocumento;

    @ManyToOne
    @JsonIgnoreProperties(value = { "usuarios" }, allowSetters = true)
    private ParametroGenero parametroGenero;
    

}
