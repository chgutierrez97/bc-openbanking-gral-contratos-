package com.ve.bc.openbanking.utils;

import java.util.Base64;

import org.springframework.stereotype.Service;
@Service
public class Encrit64 {

	public String codificaBase64(String textoCofificar) {
		textoCofificar = textoCofificar.trim();
		 String cadenaCodificada = Base64.getEncoder().encodeToString(textoCofificar.getBytes());

		return cadenaCodificada;	
	}
	
	public String decodificarBase64(String textoDecofificar) {
		textoDecofificar = textoDecofificar.trim();
		byte[] bytesDecodificados = Base64.getDecoder().decode(textoDecofificar);
        String cadenaDecodificada = new String(bytesDecodificados);      
		return cadenaDecodificada;	
	}
	
}
