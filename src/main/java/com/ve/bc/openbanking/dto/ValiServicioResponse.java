package com.ve.bc.openbanking.dto;

import java.util.List;

import lombok.Data;

@Data
public class ValiServicioResponse {
	private String tracerId;
	
	private RespuestaConError errorConsulta;
	
	private ServicioResponse servicio;

}
