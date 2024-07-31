package com.ve.bc.openbanking.service;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.dto.ValiServicioRequest;
import com.ve.bc.openbanking.dto.ValiServicioResponse;
import com.ve.bc.openbanking.exception.ResourceErroNoFoundServicesException;
import com.ve.bc.openbanking.repo.ServicioRepository;

@Component
public class ServicioServices {
	
	@Autowired
	ServicioRepository contratoRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioServices.class);
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<?> getConsulta(ValiServicioRequest request, String tracerId) {
		ValiServicioResponse responseServicio = new ValiServicioResponse();
		Map<String,String> error = new HashMap<>();
		responseServicio = contratoRepository.getConsultaServicio(request, tracerId);
		
		if (responseServicio.getErrorConsulta().getStatus().equals(Boolean.FALSE)) {
			return new ResponseEntity<ServicioResponse>(responseServicio.getServicio(), HttpStatus.OK);
		} else {
			error.put("codigoError", responseServicio.getErrorConsulta().getCodigoError());
            error.put("descripcionError", responseServicio.getErrorConsulta().getDescripcionError());            
            return new ResponseEntity<Map<String,String>>(error, HttpStatus.NOT_FOUND);
		}
	}

}

