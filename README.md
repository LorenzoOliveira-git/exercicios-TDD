# exercicios-TDD
Repositório destinado ao armazenamento dos exercícios sobre TDD aprendidos na aula de Engenharia e Qualidade de Software.

Exercícios 1 e 3 foram realizados em Python, utilizando o `pytest`.

## Enunciado -  Validador de Chave Pix 

1. Validador de Chave Pix
Objetivo: Criar um módulo responsável por identificar e validar diferentes tipos de chaves Pix antes de processar uma transferência.
Regras de Negócio:
 - CPF: Deve ter 11 dígitos numéricos e passar no cálculo dos dígitos verificadores.
- E-mail: Deve possuir formato válido (usuario@dominio.com).
- Telefone: Deve conter o código do país (+55), DDD e número (11 dígitos numéricos após o código).
- Chave Aleatória (EVP): Deve ser um UUIDv4 válido de 36 caracteres.
- Desafio TDD: Escrever testes que garantam o lançamento de exceções do tipo ChavePixInvalidaError para cada formato incorreto antes de implementar o código de parsing.

## Enunciado -  Ledger de Transações

3. Processador de Extrato Extornável (Ledger de Transações)
Objetivo: Implementar um livro-razão (ledger) de conta corrente simples que gerencie saldo, depósitos, saques e estornos.
Regras de Negócio:
- Cada transação possui id, valor, tipo (CREDITO ou DEBITO) e status (CONCLUIDO ou ESTORNADO).
- Saldo disponível = soma dos créditos CONCLUIDO − soma dos débitos CONCLUIDO.
- Não é permitido realizar um saque se o saldo resultante ficar negativo (deve lançar SaldoInsuficienteError).
- Um estorno só pode ser aplicado a uma transação com status CONCLUIDO. Após o estorno, o status muda para ESTORNADO e o saldo deve ser recalculado imediatamente.
- Desafio TDD: Testar o estado final da conta e o histórico de transações após sequências complexas de operações e estornos.


## Instalar as dependências

No terminal, dentro da pasta `TDD`, execute:

```bash
pip install pytest
pip install validate-docbr
```

## Rodar os testes do Pix

Entre na pasta `pix`:

```bash
cd pix
```

Execute:

```bash
python -m pytest -v
```

Para voltar para a pasta `TDD`:

```bash
cd ..
```

## Rodar os testes do Ledger

Entre na pasta `ledger`:

```bash
cd ledger
```

Execute:

```bash
python -m pytest -v
```

## Resultado esperado

Se os testes estiverem corretos, o terminal mostrará:

```text
PASSED
```

Caso algum teste apresente problema, será mostrado:

```text
FAILED
```

Os testes são executados separadamente porque **Pix e Ledger estão em pastas diferentes**.
