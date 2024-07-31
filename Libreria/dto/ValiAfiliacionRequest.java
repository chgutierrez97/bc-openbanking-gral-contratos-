package com.ve.bc.openbanking.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValiAfiliacionRequest {
	
	/*@NotNull
	@NotEmpty
	@Size(min = 1)*/
	private Character operacion;
	/*@NotNull
	@NotEmpty*/
	private String indentificadorWsApi;
	/*@NotNull
	@NotEmpty*/
	private String hash;
	/*@NotNull
	@NotEmpty*/
	//@Size(min = 1)
	private Character tipoDoc;
	/*@NotNull
	@NotEmpty*/
	private String numDocumento;
	
	private String ctaBanco;
}
