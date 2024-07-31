package com.ve.bc.openbanking.repo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ve.bc.openbanking.dto.ResponseContratoCts;
import com.ve.bc.openbanking.dto.RespuestaConError;
import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.dto.ValiServicioRequest;
import com.ve.bc.openbanking.dto.ValiServicioResponse;
import com.ve.bc.openbanking.exception.ResourceErroServicesException;


@Repository
public class ServicioRepository {

	@Value("${url.servi.consulta}")
    String UrlCccte;
	@Value("${api.contrato.canal}")
	String Canal;
	
				
			
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioRepository.class);

	public ValiServicioResponse getConsultaServicio(ValiServicioRequest valiServicioRequest, String tracerId) {
		LOGGER.info("Start ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
		ValiServicioResponse valiServicioResponse = getConsultaServiciosCts(valiServicioRequest, tracerId);
		LOGGER.info("End  ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
		return valiServicioResponse;
	}

	public ValiServicioResponse getConsultaServiciosCts(ValiServicioRequest valiServicioRequest, String tracerId) {

		LOGGER.info("Start ServicioRepository  : getConsultaServiciosCts  RequestId :" + tracerId);
		ResponseContratoCts responseContratoCts = new ResponseContratoCts();
		ServicioResponse servicioResponse = new ServicioResponse();
		ValiServicioResponse valiServicioResponse = new ValiServicioResponse();
		RespuestaConError errorConsulta = new RespuestaConError();
		URL url = null;
		URLConnection connection = null;
		HttpURLConnection httpConn = null;
		String responseString = null;
		String outputString = "";
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		String operacion = "ser:ValidarServicios";
	

		String xmlInput =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.afiliacion.general.openbanking.ecobis.cobiscorp\" xmlns:dto2=\"http://dto2.sdf.cts.cobis.cobiscorp.com\" xmlns:dto21=\"http://dto2.commons.ecobis.cobiscorp\" xmlns:dto=\"http://dto.payload.afiliacion.general.openbanking.ecobis.cobiscorp\">\r\n"
				
				+ "<soapenv:Header/>" + "<soapenv:Body>" + "<"+ operacion +">" + "<ser:inServicioRequest>"
				+ "<dto:cedruc>" + valiServicioRequest.getClienteRIF() + "</dto:cedruc>" 
				+"<dto:hash>"+ valiServicioRequest.getClienteHash() + "</dto:hash>" 
				+"<dto:servicio>"+ valiServicioRequest.getServicio() + "</dto:servicio>" 
				+"<dto:canal>"	+ Canal +"</dto:canal>"
				+"<dto:numeroCuenta>"+ valiServicioRequest.getNumeroCuenta() + "</dto:numeroCuenta>" 
				+ "</ser:inServicioRequest>" + "</"+operacion+">" 
				+ "</soapenv:Body>" + "</soapenv:Envelope>";
 
		try {
			
			url = new URL(UrlCccte);
			connection = url.openConnection();

			httpConn = (HttpURLConnection) connection;

			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			
			String SOAPAction = "";
			// Set the appropriate HTTP parameters.
			httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			// httpConn.setRequestProperty ("Authorization", "Basic " + Llave);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			out = httpConn.getOutputStream();
			out.write(buffer);
			out.close();

			// Read the response and write it to standard out.
			isr = new InputStreamReader(httpConn.getInputStream());
			in = new BufferedReader(isr);

			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
			}
			
			// Get the response from the web service call
			Document document = parseXmlFile(outputString);

			document.getDocumentElement().normalize();

			NodeList nodeLst = document.getElementsByTagName("ns3:success");
			String Status = nodeLst.item(0).getTextContent();

			if (Boolean.valueOf(Status)) {
				
				NodeList nodeLstId = document.getElementsByTagName("ns1:id");
				String Id = nodeLstId.item(0).getTextContent();
				servicioResponse.setId(Id);
				
				NodeList nodeLstDescrip = document.getElementsByTagName("ns1:descripcion");
				String Descripcion = nodeLstDescrip.item(0).getTextContent();
				servicioResponse.setDescripcion(Descripcion);
				
				NodeList nodeLstIdentific= document.getElementsByTagName("ns1:identificador");
				String Identificador = nodeLstIdentific.item(0).getTextContent();
				servicioResponse.setIdentificador(Identificador);
				
				NodeList nodeLstEstado = document.getElementsByTagName("ns1:estado");
				String Estado = nodeLstEstado.item(0).getTextContent();
				servicioResponse.setEstado(Estado);
				
				NodeList nodeLstNombre = document.getElementsByTagName("ns1:nombre");
				String Nombre = nodeLstNombre.item(0).getTextContent();
				servicioResponse.setNombre(Nombre);
				
				
				errorConsulta.setStatus(Boolean.FALSE);
				valiServicioResponse.setErrorConsulta(errorConsulta);
				valiServicioResponse.setServicio(servicioResponse);
				valiServicioResponse.setTracerId(tracerId);
				
			
				return valiServicioResponse;
			} else {
				
				NodeList nodeCod = document.getElementsByTagName("ns0:code");
				String Cod = nodeCod.item(0).getTextContent();
				NodeList nodeMsn = document.getElementsByTagName("ns0:message");
				String Mensaje = nodeMsn.item(0).getTextContent();

				errorConsulta.setCodigoError(Cod);
				errorConsulta.setDescripcionError(Mensaje);
				errorConsulta.setStatus(Boolean.TRUE);
				LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId);
				valiServicioResponse.setErrorConsulta(errorConsulta);
				valiServicioResponse.setTracerId(tracerId);
				return valiServicioResponse;
			}
		} catch (IOException e) {
			System.out.println(e.toString());

			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId);;
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		} catch (Exception e) {		
			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId);
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		}
	}

	private Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
