# Mandacaru Broker API

## Sumário

- [Mandacaru Broker API](#mandacaru-broker-api)
  - [Sumário](#sumário)
  - [Descrição](#descrição)
    - [O que é um Home Broker ?](#o-que-é-um-home-broker-)
  - [Desenvolvedores](#desenvolvedores)
  - [Recursos](#recursos)
    - [Listar Todas as Ações](#listar-todas-as-ações)
    - [Obter uma Ação por ID](#obter-uma-ação-por-id)
    - [Criar uma Nova Ação](#criar-uma-nova-ação)
    - [Atualizar uma Ação por ID](#atualizar-uma-ação-por-id)
    - [Excluir uma Ação por ID](#excluir-uma-ação-por-id)
  - [Collection do Postman](#collection-do-postman)
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

<div style="display: flex; justify-content: center;">
  <a href="https://github.com/I-Lima">
    <img src="https://avatars.githubusercontent.com/u/83174653?s=400&u=515b4de4d50855ea8a8dea0d554e3ed0d87bca9d&v=4"  width="80px"/>
  </a>

  <div style="margin: 20px"></div>

  <a href="https://github.com/uezili">
    <img src="https://avatars.githubusercontent.com/u/40150125?s=48&v=4"  width="80px"/>
  </a>
</div>

## Recursos

### Listar Todas as Ações

Retorna um array de objetos contendo todas as ações disponíveis.

**Endpoint:**

```http
GET /stocks
```

### Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**

```http
GET /stocks/{id}
```

### Criar uma Nova Ação

Cria uma nova ação com base nos dados fornecidos.

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

### Atualizar uma Ação por ID

Atualiza os detalhes de uma ação específica com base no ID.

**Endpoint:**

```http
PUT /stocks/{id}
```

**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 59.97
}

```

### Excluir uma Ação por ID

Exclui uma ação específica com base no ID.

**Endpoint:**

```http
DELETE /stocks/{id}
```

## Collection do Postman

A coleção a seguir compreende todas as rotas configuradas, juntamente com seus testes de API correspondentes já implementados.

[Link para download](https://drive.google.com/file/d/1G9hVXLiOBNB_Zi5lONOyz7CmCpJ7u6J_/view?usp=sharing)

## Testes

Este repositório foi submetido a uma análise estática utilizando as ferramentas Checkstyle, SonarQube e SonarLint. Além disso, possui casos de teste implementados em testes unitários, organizados por classes, bem como um teste de integração que verifica as operações CRUD da API.

## Passo-a-passo de como configurar o ambiente

1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`.

## Requisitos

- Java 11 ou superior
- Maven
- Banco de dados

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

## Contribuições

Contribuições são bem-vindas!

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

<img src="./public/footer.png" width=" 100%" />
