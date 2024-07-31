package com.ve.bc.openbanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String nombreDelRecurso;
	private String nombeDelCampo;
	private long valorDelCampo;

	public ResourceNotFoundException(String nombreDelRecurso, String nombeDelCampo, long valorDelCampo) {
		super(String.format("%s No encontrado con  : %s  : '%s'", nombreDelRecurso, nombeDelCampo, valorDelCampo));
		this.nombreDelRecurso = nombreDelRecurso;
		this.nombeDelCampo = nombeDelCampo;
		this.valorDelCampo = valorDelCampo;
	}

	public String getNombreDelRecurso() {
		return nombreDelRecurso;
	}

	public void setNombreDelRecurso(String nombreDelRecurso) {
		this.nombreDelRecurso = nombreDelRecurso;
	}

	public String getNombeDelCampo() {
		return nombeDelCampo;
	}

	public void setNombeDelCampo(String nombeDelCampo) {
		this.nombeDelCampo = nombeDelCampo;
	}

	public long getValorDelCampo() {
		return valorDelCampo;
	}

	public void setValorDelCampo(long valorDelCampo) {
		this.valorDelCampo = valorDelCampo;
	}

}
