# Exercícios de TDD

Repositório destinado ao armazenamento dos exercícios sobre **Desenvolvimento Orientado a Testes (TDD)** realizados na disciplina de Engenharia e Qualidade de Software.

Os exercícios 1 e 3 foram desenvolvidos em Python, utilizando o `pytest`. Os exercícios 2 e 4 foram desenvolvidos em Java, utilizando JUnit 5, Mockito e Maven.

## O que é TDD?

TDD, sigla para *Test-Driven Development* ou Desenvolvimento Orientado a Testes, é uma prática de desenvolvimento na qual os testes são escritos antes da implementação da funcionalidade.

O processo normalmente segue um ciclo composto por três etapas:

1. **Red:** escrever um teste para o comportamento desejado e executá-lo, confirmando que ele falha porque a funcionalidade ainda não foi implementada.
2. **Green:** implementar apenas o código necessário para fazer o teste passar.
3. **Refactor:** melhorar a organização e a qualidade do código, mantendo todos os testes funcionando.

Esse ciclo ajuda a esclarecer as regras de negócio, detectar erros mais cedo e tornar o código mais seguro para futuras alterações.

## Organização dos exercícios

```text
exercicios-TDD/
├── exercicio01/
├── exercicio02/
├── exercicio03/
└── exercicio04/
```

## Exercício 01 — Validador de Chave Pix

### Objetivo

Criar um módulo responsável por identificar e validar diferentes tipos de chaves Pix antes de processar uma transferência.

### Regras de negócio

- Uma chave do tipo CPF deve possuir 11 dígitos numéricos e passar no cálculo dos dígitos verificadores.
- Uma chave do tipo e-mail deve possuir um formato válido, como `usuario@dominio.com`.
- Uma chave do tipo telefone deve conter o código do país `+55`, o DDD e 11 dígitos numéricos após o código do país.
- Uma chave aleatória, também chamada de EVP, deve ser um UUID versão 4 válido com 36 caracteres.

### Desafio TDD

Escrever testes que garantam o lançamento da exceção `ChavePixInvalidaError` para cada formato incorreto antes de implementar o código responsável pela identificação e validação das chaves.

### Tecnologias utilizadas

- Python
- pytest
- validate-docbr

## Exercício 02 — Motor de Análise de Risco de Crédito

### Objetivo

Construir a lógica de avaliação automática do limite de cartão de crédito com base no perfil financeiro do cliente.

### Regras de negócio

- Se a renda mensal do cliente for inferior a R$ 1.500,00, o limite aprovado será de R$ 0,00 e o crédito será negado.
- Se o cliente possuir restrições no CPF, o limite aprovado será de R$ 0,00, independentemente da renda mensal ou do score.
- Se a renda mensal for igual ou superior a R$ 1.500,00 e o cliente não possuir restrições, o limite inicial será equivalente a 30% da renda mensal.
- Se o score externo for superior a 800, será aplicado um bônus de 50% sobre o limite inicial.

### Desafio TDD

Utilizar testes parametrizados para verificar múltiplas combinações de renda, restrição no CPF e score com poucas linhas de código.

Em Java, essa parametrização pode ser implementada com recursos do JUnit 5, como `@ParameterizedTest` e `@CsvSource`.

### Tecnologias utilizadas

- Java
- JUnit 5
- Maven

## Exercício 03 — Processador de Extrato Estornável

### Objetivo

Implementar um livro-razão, ou *ledger*, de uma conta corrente simples, responsável por gerenciar saldo, depósitos, saques e estornos.

### Regras de negócio

- Cada transação possui um identificador, um valor, um tipo e um status.
- O tipo da transação pode ser `CREDITO` ou `DEBITO`.
- O status da transação pode ser `CONCLUIDO` ou `ESTORNADO`.
- O saldo disponível corresponde à soma dos créditos concluídos menos a soma dos débitos concluídos.
- Não é permitido realizar um saque caso o saldo resultante fique negativo.
- Uma tentativa de saque sem saldo suficiente deve lançar `SaldoInsuficienteError`.
- Um estorno somente pode ser aplicado a uma transação com status `CONCLUIDO`.
- Depois do estorno, o status da transação deve mudar para `ESTORNADO`.
- O saldo da conta deve ser recalculado imediatamente após o estorno.

### Desafio TDD

Testar o estado final da conta e o histórico de transações depois de sequências complexas envolvendo depósitos, saques e estornos.

### Tecnologias utilizadas

- Python
- pytest

## Exercício 04 — Conversor de Moedas com Mock de API

### Objetivo

Construir um serviço de conversão monetária que consome uma taxa de câmbio fornecida por uma API externa.

### Regras de negócio

- O serviço recebe a moeda de origem, a moeda de destino e o valor que será convertido.
- O serviço deve chamar um conector externo, como `ApiCambio`, para obter a taxa de câmbio.
- Depois da conversão, deve ser acrescentada uma taxa de serviço, chamada de *spread*, de 1,5% sobre o valor convertido.
- Se a API de cotação estiver fora do ar ou indisponível, o sistema deverá capturar a falha e lançar `ServicoCotacaoIndisponivelError`.

### Desafio TDD

Utilizar objetos simulados para testar a classe de conversão sem realizar chamadas reais à API externa.

Os mocks permitem determinar previamente a taxa que será retornada pelo conector, garantindo que os testes sejam rápidos, previsíveis e independentes da disponibilidade da rede.

Também podem ser mantidos testes de integração separados para verificar o funcionamento do conector com a API real.

### Tecnologias utilizadas

- Java
- JUnit 5
- Mockito
- Maven
- Jackson
- Java HttpClient

## Requisitos para rodar os testes em Python

No terminal, dentro da pasta raiz do repositório, execute:

```bash
pip install pytest
pip install validate-docbr
```

## Requisitos para rodar os testes em Java

Para executar os exercícios desenvolvidos em Java, é necessário ter o Maven instalado.

Acesse a página oficial de download do Maven e baixe a opção Binary zip archive. Depois, extraia o arquivo em uma pasta de sua preferência.

Por fim, localize a pasta bin dentro do diretório extraído e adicione seu caminho à variável de ambiente Path do usuário. Após concluir a configuração, abra um novo terminal e verifique a instalação executando:
```
mvn -version
```

Se a instalação estiver correta, o terminal exibirá as versões do Maven e do Java configuradas no computador.

## Executar o Exercício 01

Entre na pasta do exercício:

```bash
cd exercicio01
```

Execute os testes:

```bash
python -m pytest -v
```

Para retornar à pasta raiz:

```bash
cd ..
```

## Executar o Exercício 02

Entre na pasta do exercício:

```bash
cd exercicio02
```

Execute os testes:

```bash
mvn test
```

Para retornar à pasta raiz:

```bash
cd ..
```

## Executar o Exercício 03

Entre na pasta do exercício:

```bash
cd exercicio03
```

Execute os testes:

```bash
python -m pytest -v
```

Para retornar à pasta raiz:

```bash
cd ..
```

## Executar o Exercício 04

Entre na pasta do exercício:

```bash
cd exercicio04
```

Execute todos os testes:

```bash
mvn test
```

Para executar somente os testes unitários com Mockito:

```bash
mvn test -Dgroups=unitario
```

Para executar somente os testes de integração com a API real (Não funcionará agora pois a cotação provavelmente alterou):

```bash
mvn test -Dgroups=integracao
```

Caso você queira fazer esse teste, acesse esse endpoint: https://currencyrateapi.com/api/latest?base=USD&codes=BRL e pegue o valor atual da cotação. Depois disso, faça esse valor multiplicado por 100 e o resultado deve ser multiplicado por 1.015 a fim de aplicar a taxa de serviço. Por fim, coloque esse resultado no primeiro parâmetro do assertEquals() do teste.

Para retornar à pasta raiz:

```bash
cd ..
```

## Resultados esperados

Nos exercícios desenvolvidos em **Python**, testes aprovados são apresentados com o resultado:

```text
PASSED
```

Testes que apresentarem problemas serão indicados como:

```text
FAILED
```

Nos exercícios desenvolvidos em **Java**, uma execução bem-sucedida do Maven apresentará:

```text
BUILD SUCCESS
```

Quando algum teste ou etapa da compilação falhar, será apresentado:

```text
BUILD FAILURE
```

Os exercícios são executados separadamente porque cada um possui sua própria pasta, implementação, dependências e conjunto de testes.