package com.fatec.feriado;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

class REQ01ConsultarFeriado {

	@Test
	void ct01_consultar_feriado_com_sucesso() {
		String urlBase = "https://api.invertexto.com/v1/holidays/2023?token=&state=SP";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		record Feriado(String date, String name, String type, String level) {
		}
		HttpEntity<Object> request = new HttpEntity<>(headers);
		// make an HTTP GET request with headers
		ResponseEntity<String> response = restTemplate.exchange(urlBase, HttpMethod.GET, request, String.class);
		// validar o status retornado
		assertEquals("200 OK", response.getStatusCode().toString());
		// validar o headers retornado
		assertEquals("application/json", response.getHeaders().getContentType().toString());
		// validar o body
		System.out.println(response.getBody());
		assertEquals("application/json", response.getHeaders().getContentType().toString());
		converteUTF8(response.getBody());

	}

	@Test
	void ct02_cosulta_feriados_token_invalida() {
		ResponseEntity<String> response = null;
		String urlBase = "https://api.invertexto.com/v1/holidays/2023?token=&state=SP";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		record Feriado(String date, String name, String type, String level) {
		}
		HttpEntity request = new HttpEntity<>(headers);

		try {
			response = restTemplate.exchange(urlBase, HttpMethod.GET, request, String.class);
		} catch (HttpClientErrorException e) {
			assertEquals("401 UNAUTHORIZED", e.getStatusCode().toString());
		}

	}

	public void converteUTF8(String response) {
		Gson gson = new Gson();
		try {
			String lista1 = response;
			byte[] lista2 = lista1.getBytes("UTF-8");
			String str2 = new String(lista2, "UTF-8");
			record Feriado(String date, String name, String type, String level) {}
			Feriado[] lista = gson.fromJson(response, Feriado[].class);
			System.out.println(lista[0]);
			assertEquals(17, lista.length);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
	@Test
	void ct03_ano_invalido() {
		ResponseEntity<String> response = null;
		String urlBase = "https://api.invertexto.com/v1/holidays/?token=&state=SP";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		record Feriado(String date, String name, String type, String level) {
		}
		HttpEntity request = new HttpEntity<>(headers);

		try {
			response = restTemplate.exchange(urlBase, HttpMethod.GET, request, String.class);
		} catch (HttpClientErrorException e) {
			assertEquals("404 NOT_FOUND", e.getStatusCode().toString());
		}

	}
	
}
