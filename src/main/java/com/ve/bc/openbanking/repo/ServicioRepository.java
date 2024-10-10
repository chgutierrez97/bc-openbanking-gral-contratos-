package com.ve.bc.openbanking.repo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
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

import com.ve.bc.openbanking.dto.ServicioRequest;
import com.ve.bc.openbanking.dto.ResponseServicio;
import com.ve.bc.openbanking.exception.ResourceErroServicesException;


@Repository
public class ServicioRepository {

	@Value("${url.servi.consulta}")
    String UrlCccte;
	@Value("${api.contrato.canal}")
	String Canal;
	
	@Value("${api.ssl.status}")
    Boolean statusMetodo;
	@Value("${api.ssl.certif.name}")
    String certifName;
	
				
			
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioRepository.class);

	public ResponseServicio getConsultaServicio(ServicioRequest valiServicioRequest, String tracerId) {
		LOGGER.info("Start ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
		ResponseServicio valiServicioResponse = new ResponseServicio();
		if(statusMetodo){
			valiServicioResponse = getConsultaServiciosCtsSsl(valiServicioRequest, tracerId);
		}else {
			 valiServicioResponse = getConsultaServiciosCts(valiServicioRequest, tracerId);
		}
		
		LOGGER.info("End  ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
		return valiServicioResponse;
	}

	public ResponseServicio getConsultaServiciosCts(ServicioRequest valiServicioRequest, String tracerId) {

		LOGGER.info("Start ServicioRepository  : getConsultaServiciosCts  RequestId :" + tracerId);
		ResponseContratoCts responseContratoCts = new ResponseContratoCts();
		ServicioResponse servicioResponse = new ServicioResponse();
		List<ServicioResponse> listServicioResponse = new ArrayList<>();
		
		ResponseServicio valiServicioResponse = new ResponseServicio();
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
				
				NodeList nList = document.getElementsByTagName("ns2:servicios");
				System.out.println(nList.getLength());
				for (int i = 0; i < nList.getLength(); i++) {
					ServicioResponse servicioResponse2 = new ServicioResponse();
					
					Element elemA = (Element) nList.item(i);
					
					NodeList nodeLstId = elemA.getElementsByTagName("ns1:id");
					String Id = nodeLstId.item(0).getTextContent();
					servicioResponse2.setId(Integer.valueOf(Id));
					
					NodeList nodeLstDescrip = elemA.getElementsByTagName("ns1:descripcion");
					String Descripcion = nodeLstDescrip.item(0).getTextContent();
					servicioResponse2.setDescripcion(Descripcion);
					
					NodeList nodeLstIdentific= elemA.getElementsByTagName("ns1:identificador");
					String Identificador = nodeLstIdentific.item(0).getTextContent();
					servicioResponse2.setIdentificador(Identificador);
					
					NodeList nodeLstEstado = elemA.getElementsByTagName("ns1:estado");
					String Estado = nodeLstEstado.item(0).getTextContent();
					servicioResponse2.setEstado(Estado);
					
					NodeList nodeLstNombre = elemA.getElementsByTagName("ns1:nombre");
					String Nombre = nodeLstNombre.item(0).getTextContent();
					servicioResponse2.setNombre(Nombre);
					
					listServicioResponse.add(servicioResponse2);
					
				}
				if (listServicioResponse.size() == 0) {
					errorConsulta.setCodigoError("901432");
					errorConsulta.setDescripcionError("No hay registros para mostrar");
					errorConsulta.setStatus(Boolean.TRUE);
				}else {
					errorConsulta.setStatus(Boolean.FALSE);
				}
				
				
				
				
				
				valiServicioResponse.setErrorConsulta(errorConsulta);
				valiServicioResponse.setServicios(listServicioResponse);
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

			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		} catch (Exception e) {		
			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		}
	}

//*********************************************************** - CON SERTIFICADO TSL - ***********************************************************************
	

	public ResponseServicio getConsultaServiciosCtsSsl(ServicioRequest valiServicioRequest, String tracerId) {

		LOGGER.info("Start ServicioRepository  : getConsultaServiciosCts  RequestId :" + tracerId);
		ResponseContratoCts responseContratoCts = new ResponseContratoCts();
		ServicioResponse servicioResponse = new ServicioResponse();
		ResponseServicio valiServicioResponse = new ResponseServicio();
		RespuestaConError errorConsulta = new RespuestaConError();
		URL url = null;
		HttpsURLConnection connection = null;
		HttpsURLConnection httpConn = null;
		String responseString = null;
		String outputString = "";
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		String operacion = "ser:ValidarServicios";
		Certificate ca;
	

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
			
			//LOGGER.info("Paso 2 "+xmlInput);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			LOGGER.info("Paso 3  " +certifName);
			InputStream caInput = new BufferedInputStream(new FileInputStream(certifName));
			//LOGGER.info("Paso 4");
			ca = cf.generateCertificate(caInput);
			//LOGGER.info("Paso 5");
			String keyStoreType = KeyStore.getDefaultType();
			//LOGGER.info("Paso 6 " +keyStoreType);
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			//LOGGER.info("Paso 7");
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);
			//LOGGER.info("Paso 8");
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			//LOGGER.info("Paso 9 "+tmfAlgorithm);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			//LOGGER.info("Paso 10");
			tmf.init(keyStore);
			SSLContext context = SSLContext.getInstance("TLS");
			//LOGGER.info("Paso 11");
			context.init(null, tmf.getTrustManagers(), null);
			//LOGGER.info("Paso 12");
			url = new URL(UrlCccte);
			//LOGGER.info("Paso 13");
			connection = (HttpsURLConnection) url.openConnection();
			//LOGGER.info("Paso 14");
			connection.setSSLSocketFactory(context.getSocketFactory());
			//LOGGER.info("Paso 15");
			httpConn = (HttpsURLConnection) connection;
			//LOGGER.info("Paso 16");
			byte[] buffer = new byte[xmlInput.length()];
			//LOGGER.info("Paso 17");
			buffer = xmlInput.getBytes();
			//LOGGER.info("Paso 18");

			String SOAPAction = "";

			httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			//LOGGER.info("Paso 19");
			out = httpConn.getOutputStream();
			//LOGGER.info("Paso 20");
			out.write(buffer);
			//LOGGER.info("Paso 21");
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
				servicioResponse.setId(Integer.valueOf(Id));
				
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
				//valiServicioResponse.setServicio(servicioResponse);
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
			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		} catch (Exception e) {		
			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
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
