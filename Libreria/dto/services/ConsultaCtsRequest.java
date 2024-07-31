package com.ve.bc.openbanking.dto.services;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConsultaCtsRequest {
	private Integer i_num_meses;
	private String i_request_hash;
	private String i_cedula_rif;

}
