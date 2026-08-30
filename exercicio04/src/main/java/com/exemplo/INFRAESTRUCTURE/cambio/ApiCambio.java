package com.exemplo.INFRAESTRUCTURE.cambio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.exemplo.ADAPTER.infra.ConversorMoeda;
import com.exemplo.CORE.erros.ServicoCotacaoIndisponivelError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiCambio implements ConversorMoeda {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public double converterMoeda(String moedaIn, String moedaOut, double valor) throws ServicoCotacaoIndisponivelError{
        
        try{
            String url = "https://currencyrateapi.com/api/latest?base="+moedaIn+"&codes="+moedaOut;

            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Accept", "application/json")
            .GET()
            .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200){
                throw new ServicoCotacaoIndisponivelError("Api retornou erro: "+ response.statusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode json = objectMapper.readTree(response.body());

            double responseValor = json.get("rates").get(moedaOut.toLowerCase()).asDouble();

            return responseValor;
        }catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            throw new ServicoCotacaoIndisponivelError(
                    "Consulta de cotação interrompida. Erro: "+exception
            );

        } catch (IOException exception) {
            throw new ServicoCotacaoIndisponivelError(
                    "Não foi possível consultar a API de câmbio. Erro: "+exception
            );
        }
    }
}
