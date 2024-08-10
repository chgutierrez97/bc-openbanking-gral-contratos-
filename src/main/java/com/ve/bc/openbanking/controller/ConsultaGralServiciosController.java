package com.ve.bc.openbanking.controller;

import javax.servlet.http.HttpServletResponse;
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

import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.dto.ServicioRequest;

import com.ve.bc.openbanking.dto.ErrorResponse;
import com.ve.bc.openbanking.dto.ResponseServicio;
import com.ve.bc.openbanking.service.ServicioServices;
import com.ve.bc.openbanking.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/afiliacionValidarServicio")

@Tag(name = "Validacion Servicios")
public class ConsultaGralServiciosController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaGralServiciosController.class);

	@Autowired
	Utils utils;
	
	@Autowired
	ServicioServices servicioServices;
	
	
	
	@Operation(summary = "${api.doc.summary.servi.contr}", description = "${api.doc.description.servi.contr}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = {
							@Content(mediaType = "application/json",
							schema = @Schema(implementation = ServicioResponse.class)		)					
							}),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = {
							@Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorResponse.class)		)					
							}),
			@ApiResponse(responseCode = "401", description = "Unauthorized",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					}),
			@ApiResponse(responseCode = "409", description = "Conflict",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					})
	})
	@PostMapping
	public ResponseEntity<?> getCosultaServicios(@RequestHeader(value = "X-Request-IP", required = true) String ip,@RequestHeader(value = "X-Request-Id", required = false) String tracerId,
			@Valid @RequestBody ServicioRequest request, HttpServletResponse response){
		
		if (tracerId == null || tracerId == ""){
			tracerId = utils.generarCodigoTracerId();
		}
		LOGGER.info("Start ConsultaGralServiciosController : getCosultaServicios  RequestId :" + tracerId);
		LOGGER.info("ConsultaGralContratosController Direccion IP : " + ip);
		ResponseEntity<?> valiServiciosResponse = servicioServices.getConsulta(request, tracerId);		
		LOGGER.info(" End  ConsultaGralServiciosController : getCosultaServicios  RequestId :" + tracerId);
		response.setHeader("X-Request-Id", tracerId);
		return valiServiciosResponse;
		
	}

}