package com.ve.bc.openbanking.dto.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorDto {
	private String w_codigo_error;
	private String w_descripcion_error;
	

}
