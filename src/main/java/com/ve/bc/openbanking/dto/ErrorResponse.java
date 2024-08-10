package com.ve.bc.openbanking.dto;

import lombok.Data;

@Data
public class ErrorResponse {
	private String codigoError;
	private String descripcionError;
}
