package com.exemplo.mock;

import com.exemplo.ADAPTER.ConverterMoeda;
import com.exemplo.CORE.erros.ServicoCotacaoIndisponivelError;
import com.exemplo.INFRAESTRUCTURE.cambio.ApiCambio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unitario")
class ConverterMoedaTest {

    private static final double MARGEM_ERRO = 0.001;

    @Mock
    private ApiCambio apiCambio;

    private ConverterMoeda converterMoeda;

    @BeforeEach
    void configurar() {
        converterMoeda = new ConverterMoeda(apiCambio);
    }

    @Test
    void deveConverterDeRealParaDolarEAcrescentarSpread() {
        when(apiCambio.converterMoeda("BRL", "USD", 100.00))
                .thenReturn(0.20);

        double resultado =
                converterMoeda.converterMoeda(
                        "BRL",
                        "USD",
                        100.00
                );

        assertEquals(20.30, resultado, MARGEM_ERRO);

        verify(apiCambio)
                .converterMoeda("BRL", "USD", 100.00);
    }

    @Test
    void deveConverterDeDolarParaRealEAcrescentarSpread() {
        when(apiCambio.converterMoeda("USD", "BRL", 10.00))
                .thenReturn(5.00);

        double resultado =
                converterMoeda.converterMoeda(
                        "USD",
                        "BRL",
                        10.00
                );

        assertEquals(50.75, resultado, MARGEM_ERRO);

        verify(apiCambio)
                .converterMoeda("USD", "BRL", 10.00);
    }

    @Test
    void deveConsultarApiComOsParametrosCorretos() {
        when(apiCambio.converterMoeda("EUR", "BRL", 50.00))
                .thenReturn(6.00);

        double resultado =
                converterMoeda.converterMoeda(
                        "EUR",
                        "BRL",
                        50.00
                );

        /*
         * Conversão: 50 × 6 = 300
         * Spread: 300 × 1,5% = 4,50
         * Total: 304,50
         */
        assertEquals(304.50, resultado, MARGEM_ERRO);

        verify(apiCambio)
                .converterMoeda("EUR", "BRL", 50.00);

        verifyNoMoreInteractions(apiCambio);
    }

    @Test
    void devePropagarErroQuandoApiEstiverIndisponivel() {
        when(apiCambio.converterMoeda("BRL", "USD", 100.00))
                .thenThrow(
                        new ServicoCotacaoIndisponivelError(
                                "Não foi possível consultar a API de câmbio."
                        )
                );

        assertThrows(
                ServicoCotacaoIndisponivelError.class,
                () -> converterMoeda.converterMoeda(
                        "BRL",
                        "USD",
                        100.00
                )
        );

        verify(apiCambio)
                .converterMoeda("BRL", "USD", 100.00);

        verifyNoMoreInteractions(apiCambio);
    }
}