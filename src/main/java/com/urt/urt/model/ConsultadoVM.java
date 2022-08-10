package com.urt.urt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultadoVM {
	
	private Long id;
	
	private String nombreCompleto;
	
	private String tipoDocumento;
	
	private Long numeroDocumento;
	
	private String genero;

	public ConsultadoVM(Long id, String nombreCompleto, String tipoDocumento, Long numeroDocumento, String genero) {
		super();
		this.id = id;
		this.nombreCompleto = nombreCompleto;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.genero = genero;
	}

	
}
