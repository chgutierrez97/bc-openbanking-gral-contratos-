package com.ve.bc.openbanking.service;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ve.bc.openbanking.dto.ValiAfiliacionRequest;
import com.ve.bc.openbanking.dto.ValiAfiliacionResponse;
import com.ve.bc.openbanking.repo.ContratoRepository;

@Component
public class ContratoServices {
	
	@Autowired
	ContratoRepository contratoRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContratoServices.class);
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<ValiAfiliacionResponse> getConsulta(ValiAfiliacionRequest request, String tracerId) {
		ValiAfiliacionResponse responseConsulta = new ValiAfiliacionResponse();
		Integer tabname = Integer.valueOf(0);
		
			responseConsulta = contratoRepository.getConsultaCotrato(request, tracerId);
		
		if(responseConsulta.getEstado().getIdProducto()!=0 && Objects.nonNull(responseConsulta.getErrorConsulta().getCodigoError()) ) {
			return new ResponseEntity<ValiAfiliacionResponse>(responseConsulta,HttpStatus.OK);
		}else if(Objects.isNull(responseConsulta.getErrorConsulta())) {
			return new ResponseEntity<ValiAfiliacionResponse>( responseConsulta,HttpStatus.BAD_REQUEST);
		}else{
			return new ResponseEntity<ValiAfiliacionResponse>( responseConsulta,HttpStatus.NOT_FOUND);
		}		 
	}

}
