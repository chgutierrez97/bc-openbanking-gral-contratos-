package com.ve.bc.openbanking.dto.services;

import java.util.List;

import com.ve.bc.openbanking.dto.AfiliacionEstado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsultaCtsResponse {
	private String tracerId;
	private ErrorDto errorDto;
	private AfiliacionEstado estado;
	


}
