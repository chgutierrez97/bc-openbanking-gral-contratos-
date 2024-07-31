package com.ve.bc.openbanking.dto;

import java.util.List;

import lombok.Data;

@Data
public class ValiAfiliacionResponse {
	private String tracerId;
	private RespuestaConError errorConsulta;
	private AfiliacionEstado estado;

}
