package com.ve.bc.openbanking.dto;

import lombok.Data;

@Data
public class RespuestaConError {
	private String codigoError;
	private String descripcionError;
}
