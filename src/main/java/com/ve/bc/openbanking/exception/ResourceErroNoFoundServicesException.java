package com.ve.bc.openbanking.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceErroNoFoundServicesException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String nombreDelRecurso;
	private String nombeDelService;
	
	public ResourceErroNoFoundServicesException(String nombreDelRecurso, String nombeDelService) {
		super(String.format(" TracerId %s  Error  : '%s'", nombreDelRecurso, nombeDelService));
		this.nombreDelRecurso = nombreDelRecurso;
		this.nombeDelService = nombeDelService;
	}

	public String getNombreDelRecurso() {
		return nombreDelRecurso;
	}

	public void setNombreDelRecurso(String nombreDelRecurso) {
		this.nombreDelRecurso = nombreDelRecurso;
	}

	public String getNombeDelService() {
		return nombeDelService;
	}

	public void setNombeDelService(String nombeDelService) {
		this.nombeDelService = nombeDelService;
	}
	

}
