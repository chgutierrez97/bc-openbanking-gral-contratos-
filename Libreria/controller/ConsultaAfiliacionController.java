package com.ve.bc.openbanking.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ve.bc.openbanking.dto.ValiAfiliacionRequest;
import com.ve.bc.openbanking.dto.ValiAfiliacionResponse;
import com.ve.bc.openbanking.utils.Utilis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/afiliacion")

@Tag(name = "afiliacion")
public class ConsultaAfiliacionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaAfiliacionController.class);

	@Autowired
	Utilis utils;
	
	
	@Operation(summary = "Consulta si existe una afiliacion para ese cliente", description = "Consulta si existe una afiliacion para ese cliente y ese producto")
	@PostMapping("/verifica")
	public ResponseEntity<ValiAfiliacionResponse> getCosultaAfiliacion( @RequestHeader(value = "X-Tracer-Id", required = false) String tracerId,
			 @RequestBody ValiAfiliacionRequest request){
		
		if (tracerId == null || tracerId == ""){
			tracerId = utils.generarCodigoTracerId();
		}
		LOGGER.info("Start ConsultaAfiliacionController : getCosultaAfiliacion  TracerId :" + tracerId);
		
		ValiAfiliacionResponse valiAfiliacionResponse = new ValiAfiliacionResponse();
		
		LOGGER.info(" End  ConsultaAfiliacionController : getCosultaAfiliacion  TracerId :" + tracerId);
	
		return new ResponseEntity<ValiAfiliacionResponse>(valiAfiliacionResponse,HttpStatus.OK);
		
	}

}