# Mandacaru Broker API

###### Don't speak portuguese? <a href="https://github.com/I-Lima/Mandacaru-Broker-API/blob/main/README-en.md">Click here</a> to view this pagae in English

## Sumário

- [Mandacaru Broker API](#mandacaru-broker-api)
  - [Sumário](#sumário)
  - [Descrição](#descrição)
    - [O que é um Home Broker ?](#o-que-é-um-home-broker-)
  - [Desenvolvedores](#desenvolvedores)
  - [Recursos](#recursos)
    - [Login no sistema](#login-no-sistema)
    - [Registro no sistema](#registro-no-sistema)
    - [Excluir registro no sistema](#excluir-registro-no-sistema)
    - [Obter todos os Exchange Traded Funds](#obter-todos-os-exchange-traded-funds)
    - [Obter um Exchange Traded Fund específico](#obter-um-exchange-traded-fund-específico)
    - [Registrar um Exchange Traded Fund](#registrar-um-exchange-traded-fund)
    - [Atualizar um Exchange Traded Fund](#atualizar-um-exchange-traded-fund-específico)
    - [Excluir um Exchange Traded Fund](#excluir-um-exchange-traded-fund)
    - [Listar todos os investimentos](#listar-todos-os-investimentos)
    - [Registrar um novo investimento](#registrar-um-novo-investimento)
    - [Excluir um investimento](#excluir-um-investimento)
    - [Listar todos os fundos imobiliários](#listar-todos-os-fundos-imobiliários)
    - [Obter um fundo imobiliário específico](#obter-um-fundo-imobiliário-específico)
    - [Registrar um novo fundo imobiliário](#registrar-um-novo-fundo-imobiliário)
    - [Atualizar um fundo imobiliário específico](#atualizar-um-fundo-imobiliário-específico)
    - [Excluir um fundo imobiliário específico](#excluir-um-fundo-imobiliário-específico)
    - [Listar todas os registros](#listar-todos-os-registros)
    - [Listar um Registro](#listar-um-registro)
    - [Criar um novo registro](#criar-um-novo-registro)
    - [Atualizar um registro por ID](#atualizar-um-registro)
    - [Excluir um registro](#excluir-um-registro)
    - [Listar todos os ativos](#listar-todos-os-ativos)
    - [Obter um ativo específico](#obter-um-ativo-específico)
    - [Registrar um ativo](#registrar-um-ativo)
    - [Atualizar um ativo](#atualizar-um-ativo)
    - [Excluir um ativo](#excluir-um-ativo)
    - [Login do usuário no sistema](#login-do-usuário-no-sistema)
    - [Cadastro de usuário no sistema](#cadastro-de-usuário-no-sistema)
    - [Atualizar usuário no sistema](#atualizar-usuário-no-sistema)
    - [Exclusão de usuário no sistema](#exclusão-de-usuário-no-sistema)
  - [Collection do Postman](#collection-do-postman)
  - [Swagger](#swagger)
  - [Testes](#testes)
  - [Passo-a-passo de como configurar o ambiente](#passo-a-passo-de-como-configurar-o-ambiente)
  - [Requisitos](#requisitos)
  - [Tecnologias Utilizadas](#tecnologias-utilizadas)
  - [Contribuições](#contribuições)
  - [Licença](#licença)

## Descrição

A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).

### O que é um Home Broker ?

Um Home Broker é uma plataforma online que permite aos investidores comprar e vender ativos financeiros,
como ações, opções, títulos públicos, entre outros, diretamente pela internet. Através dele, os investidores podem
acessar informações de mercado em tempo real, analisar gráficos, executar ordens de compra e venda,
monitorar suas carteiras de investimentos, entre outras funcionalidades, tudo de forma online.

Se deseja aprender mais sobre o Homebroker e como ele funciona,
acesse o Hub de educação da B3 (bolsa de valores brasileira): [Como operar com o Homebroker](https://edu.b3.com.br/play/curso/21491369?institution=edub3?gclid=Cj0KCQiA2eKtBhDcARIsAEGTG41VZsqjGKousZxPKj1yB86mGX_QKuBxdxrg-qM9ymZ4w6mBQFPk930aArFTEALw_wcB)

## Desenvolvedores

O projeto foi elaborado em colaboração pelos seguintes desenvolvedores:

| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/83174653?s=400&u=515b4de4d50855ea8a8dea0d554e3ed0d87bca9d&v=4" width=80><br><sub>Ingrid Lima</sub>](https://github.com/I-Lima) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/40150125?s=48&v=4" width=80><br><sub>Wesley Maciel</sub>](https://github.com/uezili) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/143536750?v=4" width=80><br><sub>João Paulo</sub>](https://github.com/jaopaulomilitao) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/60359281?v=4" width=80><br><sub>Moisés Oliveira</sub>](https://github.com/moisesocosta) |
|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------:|

## Recursos

### Login no sistema

Efetua o login no sistema.

**Endpoint:**

```http
POST /auth/login
```

**Respostas:**

```http
200: Login bem-sucedido
400: Credenciais inválidas
401: Usuário não registrado
```

### Registro no sistema

Efetua o registro no sistema.

**Endpoint:**

```http
POST /auth/register
```

**Respostas:**

```http
200: Registro bem-sucedido
409: Usuário já registrado
```

### Excluir registro no sistema

Exclui o registro no sistema.

**Endpoint:**

```http
DELETE /auth/{id}
```

**Respostas:**

```http
200: Exclusão bem-sucedida
```

### Obter todos os Exchange Traded Funds

Retorna todos os Exchange Traded Funds disponíveis.

**Endpoint:**

```http
GET /etf
```

### Obter um Exchange Traded Fund específico

Retorna os detalhes de um Exchange Traded Fund específico com base no ID fornecido.

**Endpoint:**

```http
GET /etf/{id}
```

### Registrar um Exchange Traded Fund

Registra um novo Exchange Traded Fund com base nos dados fornecidos.

**Endpoint:**

```http
POST /etf/register
```


### Atualizar um Exchange Traded Fund

Atualiza os detalhes de um Exchange Traded Fund específico com base no ID fornecido.

**Endpoint:**

```http
PUT /etf/{id}
```

### Excluir um Exchange Traded Fund

Exclui um Exchange Traded Fund específico com base no ID fornecido.

**Endpoint:**

```http
DELETE /etf/{id}
```

### Listar todos os investimentos

Retorna uma lista de todos os tipos de investimentos disponíveis.

**Endpoint:**

```http
GET /assets
```

### Registrar um novo investimento

Cria um novo investimento com base nos dados fornecidos.

**Endpoint:**

```http
POST /assets/register
```

**Respostas:**

```http
200: Registro bem-sucedido
```

### Excluir um investimento

Exclui um investimento específico com base no ID fornecido.

**Endpoint:**

```http
DELETE /assets/{id}
```

**Respostas:**

```http
200: Exclusão bem-sucedida
```

### Listar todos os fundos imobiliários

Obtém todos os fundos imobiliários cadastrados.

**Endpoint:**

```http
GET /ref
```

### Obter um fundo imobiliário específico

Obtém os detalhes de um fundo imobiliário específico com base no ID.

**Endpoint:**

```http
GET /ref/{id}
```

### Registrar um novo fundo imobiliário

Registra um novo fundo imobiliário com base nos dados fornecidos.

**Endpoint:**

```http
POST /ref/register
```

### Atualizar um fundo imobiliário específico

Atualiza os detalhes de um fundo imobiliário específico com base no ID.

**Endpoint:**

```http
PUT /ref/{id}
```

### Excluir um fundo imobiliário específico

Exclui um fundo imobiliário específico com base no ID.

**Endpoint:**

```http
DELETE /ref/{id}
```

**Respostas:**

```http
200: Exclusão bem-sucedida
```

### Listar todos os registros

Retorna um array de objetos com todos os registros disponíveis.

**Endpoint:**

```http
GET /stocks
```

### Listar um registro

Retorna os detalhes de um registro específico com base no ID.

**Endpoint:**

```http
GET /stocks/{id}
```

### Criar um novo registro

Cria um novo registro com base nos dados fornecidos.

**Endpoint:**

```http
POST /stocks
```

**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}

```

### Atualizar um registro

Atualiza os detalhes de um registro específico com base no ID.

**Endpoint:**

```http
PUT /stocks/{id}
```

**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}

```

### Excluir um registro

Exclui um registro específico com base no ID.

**Endpoint:**

```http
DELETE /stocks/{id}
```

### Listar todos os ativos

Obtém todos os ativos.

**Endpoint:**

```http
GET /treasury
```

### Obter um ativo específico

Obtém detalhes de um ativo específico com base no ID.

**Endpoint:**

```http
GET /treasury/{id}
```

### Registrar um ativo

Registra um novo ativo com base no dados fornecidos

**Endpoint:**

```http
POST /treasury/register
```

### Atualizar um ativo

Atualiza os detalhes de um ativo específico com base no ID.

**Endpoint:**

```http
PUT /treasury
```

### Excluir um ativo

Exclui um ativo específico com base no ID.

**Endpoint:**

```http
DELETE /treasury/{id}
```

### Login do usuário no sistema

Realiza o login de um usuário com base nas informações fornecidas.

**Endpoint:**

```http
POST /user/login
```

**Respostas:**

```http
200: Login bem-sucedido
400: Usuário não registrado
401: Credenciais inválidas
```

### Cadastro de usuário no sistema

Registra um novo usuário com base nas informações fornecidas.

**Endpoint:**

```http
POST /user/register
```

**Respostas:**

```http
200: Cadastro bem-sucedida
409: Username já está em uso
```

### Atualizar usuário no sistema

Atualiza as informações de um usuário específico com base no ID fornecido.

**Endpoint:**

```http
PUT /user/{id}
```

**Respostas:**

```http
200: Atualização bem-sucedida
404: Usuário não encontrado
```

### Exclusão de usuário no sistema

Exclui um usuário específico com base no ID fornecido.

**Endpoint:**

```http
DELETE /user/{id}
```

**Respostas:**

```http
200: Exclusão bem-sucedida
404: Usuário não encontrado
```

## Collection do Postman

A coleção a seguir compreende todas as rotas configuradas, juntamente com seus testes de API correspondentes já implementados.

[Link para download](https://drive.google.com/file/d/1G9hVXLiOBNB_Zi5lONOyz7CmCpJ7u6J_/view?usp=sharing)

## Swagger
O Swagger é uma ferramenta de código aberto que permite documentar APIs de forma clara e interativa.
Esta interface facilita a compreensão e integração com a Mandacaru Broker API.

`http://localhost:8080/swagger-ui/index.html#/`

## Testes

Este repositório foi submetido a uma análise estática utilizando as ferramentas Checkstyle, SonarQube e SonarLint. Além disso, possui casos de teste implementados em testes unitários, organizados por classes, bem como um teste de integração que verifica as operações CRUD da API.

## Passo-a-passo de como configurar o ambiente

1. Clone o repositório: `git clone https://github.com/I-Lima/Mandacaru-Broker-API.git`
2. Importe o projeto em sua IDE preferida.
3. Para executar o banco de dados no Docker, utilize o comando a seguir:

   ```bash
   docker-compose up -d
    ```

4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`.

## Requisitos

- Java 11 ou superior
- Maven
- Banco de dados
- Docker

## Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- Maven
- JUnit
- Mockito
- JaCoCo
- Checkstyle
- SonarQube
- SonarLint
- PostgresSQL
- Docker
- Docker Compose

## Contribuições

Contribuições são bem-vindas!

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

<img src="./public/footer.png" width=" 100%" />
