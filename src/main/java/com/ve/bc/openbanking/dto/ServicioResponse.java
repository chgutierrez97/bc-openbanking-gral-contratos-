package com.ve.bc.openbanking.dto;

import lombok.Data;

@Data
public class ServicioResponse {
	private Integer id;
	private String descripcion;
	private String identificador;
	private String estado;
	private String nombre;
}
