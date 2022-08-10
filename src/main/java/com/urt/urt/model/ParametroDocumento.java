package com.urt.urt.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Entity
@Table(name = "parametro_documento")
@Data
public class ParametroDocumento  implements Serializable {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "tipo_documento")
	    private String tipoDocumento;

	    @OneToMany(mappedBy = "parametroDocumento")
	    @JsonIgnoreProperties(value = { "parametroDocumento", "parametroGenero" }, allowSetters = true)
	    private Set<Usuario> usuarios = new HashSet<>();

}
