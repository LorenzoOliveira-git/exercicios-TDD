package com.exemplo.ADAPTER;

import com.exemplo.INFRAESTRUCTURE.cambio.ApiCambio;

public class ConverterMoeda{

    private final ApiCambio apiCambio;

    public ConverterMoeda(ApiCambio apiCambio){
        this.apiCambio = apiCambio;
    }

    public double converterMoeda(String moedaIn, String moedaOut, double valor){
        double valorConversao = apiCambio.converterMoeda(moedaIn, moedaOut, valor);

        return (valor * valorConversao)*1.015;
    }
}