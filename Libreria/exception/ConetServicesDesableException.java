package com.ve.bc.openbanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConetServicesDesableException  extends RuntimeException {
	private String mensaje;

	public ConetServicesDesableException(String mensaje) {
		super(String.format(" Falla de conexcion con el servicio CTS  : '%s'", mensaje));
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	

}
