package com.exemplo.real;

import com.exemplo.INFRAESTRUCTURE.cambio.ApiCambio;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.exemplo.ADAPTER.ConverterMoeda;
import com.exemplo.CORE.erros.ServicoCotacaoIndisponivelError;

@Tag("integracao")
public class ConverterMoedaTest {

    @Test
    void deveConverterUsandoCotacaoRealDaApi() {
        ApiCambio apiCambio = new ApiCambio();
        ConverterMoeda converterMoeda = new ConverterMoeda(apiCambio);

        // Conversão feita com base nos dados do dia 28/08/2026 no horário das 20:26
        double resultado =
                converterMoeda.converterMoeda("USD", "BRL", 100.00);

        assertEquals(527.26839375, resultado, 0.0001);
    }
}
