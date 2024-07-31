package com.ve.bc.openbanking.dto;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValiServicioRequest {

	
	@NotBlank(message = " Es un dato requerido para la solicitd.")
	private String servicio;
	
	@NotBlank(message = " Es un dato requerido para la solicitd.")
	@Size(min = 36,  message = "Debe tener minimo 36 digitos.")
	private String clienteHash;
	
	@Pattern(regexp = "^[A-Z0-9]*$", message = "El campo debe ser alfa numerico y Mayuscula.")
	@Size(min = 10,  message = "Debe tener minimo 10 digitos.")
	@NotBlank(message = " Es un dato requerido para la solicitd.")
	private String clienteRIF;
	
	
	@NotBlank(message = " Es un dato requerido para la solicitd.")
	@Size(min = 20,  message = "Debe tener una longitud de 20 characters.")
	private String numeroCuenta;
}
