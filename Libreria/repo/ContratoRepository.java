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

import com.ve.bc.openbanking.dto.ValiAfiliacionRequest;
import com.ve.bc.openbanking.dto.ValiAfiliacionResponse;
import com.ve.bc.openbanking.dto.services.ConsultaCtsRequest;
import com.ve.bc.openbanking.dto.services.ConsultaCtsResponse;
import com.ve.bc.openbanking.dto.services.ErrorDto;
import com.ve.bc.openbanking.exception.ResourceErroServicesException;

@Repository
public class ContratoRepository {

	@Value("${url.servi.consulta}")
    String UrlCccte;
				
			
	private static final Logger LOGGER = LoggerFactory.getLogger(ContratoRepository.class);

	public ValiAfiliacionResponse getConsultaCotrato(ValiAfiliacionRequest valiAfiliacionRequest, String tracerId) {


		LOGGER.info("Start contratoRepository  : getConsultaCotrato  TracerId :" + tracerId);

		ErrorDto errorSql = new ErrorDto();
		ValiAfiliacionResponse response = new ValiAfiliacionResponse();	
		
		//TasaResponseCts tasasActuales = getConsultaContratosCts(valiAfiliacionRequest, tracerId);

		response.setErrorConsulta(null);
		response.setEstado(null);
		response.setTracerId(tracerId);
		LOGGER.info("End  contratoRepository  : getConsultaCotrato  TracerId :" + tracerId);
		return response;

	}

	/*public TasaResponseCts getConsultaContratosCts(ValiAfiliacionRequest requestContratos, String tracerId) {

		LOGGER.info("Inicio TasaRepository : getConsultaTasaCts  TracerId :" + tracerId);
		TasaResponseCts tasaResponseCts = new TasaResponseCts();
		ErrorDto errorDto = new ErrorDto();
		URL url = null;
		URLConnection connection = null;
		HttpURLConnection httpConn = null;
		String responseString = null;
		String outputString = "";
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		List<TasaBcvDto> listTasaBcv = new ArrayList<>();

		String operacion = "ser:ConsultaTasaBCV";
	

		String xmlInput =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.openbankingconsultatasa.ecobis.cobiscorp\" xmlns:dto2=\"http://dto2.sdf.cts.cobis.cobiscorp.com\" xmlns:dto21=\"http://dto2.commons.ecobis.cobiscorp\" xmlns:dto=\"http://dto.openbankingconsultatasa.ecobis.cobiscorp\">\r\n"
				+ " <soapenv:Header/>" + "<soapenv:Body>" + "<"+ operacion +">" + "<ser:inRequestConsTasa>"
				+ "<dto:numMeses>" + requestConsTasa.getI_num_meses() + "</dto:numMeses>" + "<dto:requestHash>"
				+ requestConsTasa.getI_request_hash() + "</dto:requestHash>" + "<dto:cedulaRif>"
				+ requestConsTasa.getI_cedula_rif() + "</dto:cedulaRif>" + "</ser:inRequestConsTasa>" + "</"+operacion
				+">" + "</soapenv:Body>" + "</soapenv:Envelope>";
		
		
		 
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
			// List<ConsCtaResponse> listCuentas = new ArrayList<>();
			List<TasaBcvDto> listTasaBcvDto = new ArrayList<>();
			

			// Read the response and write it to standard out.
			isr = new InputStreamReader(httpConn.getInputStream());
			in = new BufferedReader(isr);

			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
			}
			
			// Get the response from the web service call
			Document document = parseXmlFile(outputString);

			document.getDocumentElement().normalize();

			NodeList nodeLst = document.getElementsByTagName("ns2:success");
			String Status = nodeLst.item(0).getTextContent();

			if (Boolean.valueOf(Status)) {
						
				// String ouputs = typeOperation == 1 ? "outResponseCuenta" : "ns2:cuentas";
				String ouputs = "ns1:listTasaBcv";
				
				NodeList nList = document.getElementsByTagName("ns1:listTasaBcv");
				System.out.println(nList.getLength());

				for (int i = 0; i < nList.getLength(); i++) {
					TasaBcvDto tasaBcvDto = new TasaBcvDto();
					Element elemA = (Element) nList.item(i);
					
					NodeList nodeLst1 = elemA.getElementsByTagName("ns1:panemonico");
					String panemonico = nodeLst1.item(0).getTextContent();
					tasaBcvDto.setPanemonico(panemonico);
					NodeList nodeLst2 = elemA.getElementsByTagName("ns1:pavalor");
					String pavalor = nodeLst2.item(0).getTextContent();
					tasaBcvDto.setPavalor(pavalor);
					NodeList nodeLst3 = elemA.getElementsByTagName("ns1:fechaAct");
					String fechaAct = nodeLst3.item(0).getTextContent();
					tasaBcvDto.setFechaAct(fechaAct);
					NodeList nodeLst4 = elemA.getElementsByTagName("ns1:descripcion");
					String descripcion = nodeLst4.item(0).getTextContent();
					tasaBcvDto.setDescripcion(descripcion);
					NodeList nodeLst5 = elemA.getElementsByTagName("ns1:parametro");
					String parametro = nodeLst5.item(0).getTextContent();
					tasaBcvDto.setParametro(parametro);
					NodeList nodeLst6 = elemA.getElementsByTagName("ns1:funcionario");
					String funcionario = nodeLst6.item(0).getTextContent();
					tasaBcvDto.setFuncionario(funcionario);
					listTasaBcvDto.add(tasaBcvDto);

				}

				if (listTasaBcvDto.size() == 0) {
					errorDto.setW_codigo_error("901432");
					errorDto.setW_descripcion_error("No hay registros para mostrar");

				}
				tasaResponseCts.setErrorDto(errorDto);
				tasaResponseCts.setListTasaBcv(listTasaBcvDto);
				return tasaResponseCts;
			} else {
				
				NodeList nodeCod = document.getElementsByTagName("ns0:code");
				String Cod = nodeCod.item(0).getTextContent();
				NodeList nodeMsn = document.getElementsByTagName("ns0:message");
				String Mensaje = nodeMsn.item(0).getTextContent();

				errorDto.setW_codigo_error(Cod);
				errorDto.setW_descripcion_error(Mensaje);

				LOGGER.info("Fin TasaRepository : getConsultaTasaCts  TracerId :" + tracerId);
				tasaResponseCts.setErrorDto(errorDto);
				return tasaResponseCts;
			}
		} catch (IOException e) {
			System.out.println(e.toString());

			LOGGER.info("Fin TasaRepository : getConsultaTasaCts  TracerId :" + tracerId);
			throw new ResourceErroServicesException("ConsultaCtaCteService", "getCosultaCtaCtte");
		} catch (Exception e) {
		
			LOGGER.info("Fin TasaRepository : getConsultaTasaCts  TracerId :" + tracerId);
			throw new ResourceErroServicesException("ConsultaCtaCteService", "getCosultaCtaCtte");
		}

	}*/

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
