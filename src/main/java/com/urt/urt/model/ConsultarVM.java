package com.urt.urt.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultarVM {

	private Long numeroDocumento;
	private String nombreUsuario;
	
	public ConsultarVM(Long numeroDocumento, String nombreUsuario) {
		super();
		this.numeroDocumento = numeroDocumento;
		this.nombreUsuario = nombreUsuario;
	}
	
	
}
