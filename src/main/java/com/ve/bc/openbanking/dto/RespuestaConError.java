package com.ve.bc.openbanking.dto;

import lombok.Data;

@Data
public class RespuestaConError {
	private Boolean status;
	private String codigoError;
	private String descripcionError;
}
