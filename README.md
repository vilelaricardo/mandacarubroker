# Mandacaru Broker API

## Descrição:
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).

## Recursos:
#### OBS: Recomenda-se o uso de alguma plataforma ou software que facilite a testagem desta API, como, por exemplo, o Postman (Disponível em: https://www.postman.com/downloads/ )

### 1. Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis.

**Endpoint:**
```http
GET /stocks
```

### 2. Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
GET /stocks/{id}
```

### 3. Criar uma Nova Ação
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
### 4. Atualizar uma Ação por ID
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

### 5. Excluir uma Ação por ID
Exclui uma ação específica com base no ID.

**Endpoint:**
```http
DELETE /stocks/{id}
```


## Como usar:
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados (exemplo: PostgreSQL) e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8081`.

## Requisitos:
- Java 11 ou superior
- Maven
- Banco de dados

## Tecnologias Utilizadas:
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

## Gerenciamento de Erros e Solução de Problemas:

Este software implementa estratégias de gerenciamento de erros para garantir a confiabilidade e a estabilidade do sistema. Em caso de falhas ou problemas durante a interação com a API, os usuários podem encontrar informações úteis para solucionar problemas comuns:

- Respostas de Erro Padronizadas: A API fornece respostas de erro padronizadas com códigos de status HTTP apropriados e mensagens descritivas para facilitar a identificação e o diagnóstico de problemas.

- Logging Detalhado: A aplicação realiza o registro detalhado de eventos e erros, o que ajuda na identificação e no diagnóstico de problemas. Os logs contêm informações relevantes para a solução de problemas e podem ser consultados para investigar questões específicas.

## Contribuições:
Contribuições são bem-vindas!

## Licença:
Este projeto está licenciado sob a [Licença MIT](LICENSE).

