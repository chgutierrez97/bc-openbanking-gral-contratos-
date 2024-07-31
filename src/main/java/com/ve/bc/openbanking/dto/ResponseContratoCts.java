package com.ve.bc.openbanking.dto;

import lombok.Data;

@Data
public class ResponseContratoCts {
	private RespuestaConError errorConsulta;	
	private ServicioResponse datosContrato;
}
