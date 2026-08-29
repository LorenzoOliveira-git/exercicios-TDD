package com.exemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.exemplo.CORE.Cartao;
import com.exemplo.CORE.Conta;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private static final double MARGEM_ERRO = 0.001;

    private Conta conta;
    private Cartao cartao;

    @BeforeEach
    void prepararTeste() {
        cartao = new Cartao();
        conta = new Conta();
        conta.setCartao(cartao);
    }

    @Test
    @DisplayName("Deve negar crédito quando a renda for menor que R$ 1.500")
    void deveNegarCreditoQuandoRendaForMenorQue1500() {
        conta.setRenda_mensal(1_499.99);
        conta.setNome_sujo(false);
        conta.setScore(700);

        conta.avaliacao_limite();

        assertAll(
            () -> assertEquals(
                0.0,
                cartao.getLimite(),
                MARGEM_ERRO
            ),
            () -> assertFalse(cartao.isCredito_aprovado()),
            () -> assertTrue(cartao.isDebito_aprovado())
        );
    }

    @Test
    @DisplayName("Deve negar crédito quando o cliente possuir nome sujo")
    void deveNegarCreditoQuandoClientePossuirNomeSujo() {
        conta.setRenda_mensal(10_000.00);
        conta.setNome_sujo(true);
        conta.setScore(900);

        conta.avaliacao_limite();

        assertAll(
            () -> assertEquals(
                0.0,
                cartao.getLimite(),
                MARGEM_ERRO
            ),
            () -> assertFalse(
                cartao.isCredito_aprovado(),
                "Cliente com nome sujo não pode ter crédito aprovado"
            ),
            () -> assertTrue(cartao.isDebito_aprovado())
        );
    }

    @Test
    @DisplayName("Deve aprovar 30% da renda quando não houver restrição")
    void deveAprovarTrintaPorCentoDaRenda() {
        conta.setRenda_mensal(2_000.00);
        conta.setNome_sujo(false);
        conta.setScore(700);

        conta.avaliacao_limite();

        assertAll(
            () -> assertEquals(
                600.00,
                cartao.getLimite(),
                MARGEM_ERRO
            ),
            () -> assertTrue(cartao.isCredito_aprovado()),
            () -> assertTrue(cartao.isDebito_aprovado())
        );
    }

    @Test
    @DisplayName("Deve considerar renda de exatamente R$ 1.500 para aprovação")
    void deveAprovarCreditoParaRendaExatamente1500() {
        conta.setRenda_mensal(1_500.00);
        conta.setNome_sujo(false);
        conta.setScore(800);

        conta.avaliacao_limite();

        assertAll(
            () -> assertEquals(
                450.00,
                cartao.getLimite(),
                MARGEM_ERRO
            ),
            () -> assertTrue(cartao.isCredito_aprovado())
        );
    }

    @Test
    @DisplayName("Não deve aplicar bônus quando o score for exatamente 800")
    void naoDeveAplicarBonusParaScoreIgualA800() {
        conta.setRenda_mensal(3_000.00);
        conta.setNome_sujo(false);
        conta.setScore(800);

        conta.avaliacao_limite();

        // 30% de 3.000 = 900
        assertEquals(
            900.00,
            cartao.getLimite(),
            MARGEM_ERRO
        );
    }

    @Test
    @DisplayName("Deve aplicar bônus de 50% quando o score for maior que 800")
    void deveAplicarBonusParaScoreMaiorQue800() {
        conta.setRenda_mensal(3_000.00);
        conta.setNome_sujo(false);
        conta.setScore(801);

        conta.avaliacao_limite();

        // Limite base: 30% de 3.000 = 900
        // Bônus: 50% de 900 = 450
        // Limite final: 1.350
        assertAll(
            () -> assertEquals(
                1_350.00,
                cartao.getLimite(),
                MARGEM_ERRO
            ),
            () -> assertTrue(cartao.isCredito_aprovado())
        );
    }

    @ParameterizedTest(
        name = "[{index}] renda={0}, nomeSujo={1}, score={2} => limite={3}, crédito={4}"
    )
    @CsvSource({
        // renda, nome sujo, score, limite esperado, crédito esperado
        "1000.00,  false, 500,    0.00, false",
        "1499.99,  false, 900,    0.00, false",
        "1500.00,  false, 700,  450.00, true",
        "2000.00,  false, 800,  600.00, true",
        "2000.00,  false, 801,  900.00, true",
        "3000.00,  false, 900, 1350.00, true",
        "1000.00,  true,  900,    0.00, false",
        "5000.00,  true,  900,    0.00, false"
    })
    @DisplayName("Deve avaliar diferentes perfis financeiros")
    void deveAvaliarDiferentesPerfisFinanceiros(
        double renda,
        boolean nomeSujo,
        double score,
        double limiteEsperado,
        boolean creditoEsperado
    ) {
        conta.setRenda_mensal(renda);
        conta.setNome_sujo(nomeSujo);
        conta.setScore(score);

        conta.avaliacao_limite();

        assertAll(
            () -> assertEquals(
                limiteEsperado,
                cartao.getLimite(),
                MARGEM_ERRO,
                "Limite calculado incorretamente"
            ),
            () -> assertEquals(
                creditoEsperado,
                cartao.isCredito_aprovado(),
                "Situação do crédito incorreta"
            ),
            () -> assertTrue(
                cartao.isDebito_aprovado(),
                "O débito deveria ser aprovado"
            )
        );
    }
}
