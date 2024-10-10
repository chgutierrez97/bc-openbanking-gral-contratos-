package com.ve.bc.openbanking.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseServicio {
	private String tracerId;
	
	private RespuestaConError errorConsulta;
	
	private List<ServicioResponse> servicios;

}
