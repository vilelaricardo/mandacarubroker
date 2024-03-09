<a id="voltar-topo"></a>
# MandaCaru Broker API


- [Descrição](#descricao)
- [URL Base](#url-base)
******
- [Recursos - Módulo "Stock"](#recursos-modulo-stock)
  - [Listar Todas as Ações](#listar-todas-as-acoes)
  - [Obter uma Ação por ID](#obter-uma-acao-por-id)
  - [Criar uma Nova Ação](#criar-uma-nova-acao)
  - [Atualizar uma Ação por ID](#atualizar-acao-por-id)
  - [Excluir uma Ação por ID](#excluir-acao-por-id)
******
- [Recursos - Módulo "User"](#recursos-modulo-user)
  - [Listar Todos os Usuários](#listar-todos-os-usuarios)
  - [Obter um Usuário por ID](#obter-um-usuario-por-id)
  - [Criar um Novo Usuário](#criar-um-novo-usuario)
  - [Atualizar um Usuário por ID](#atualizar-usuario-por-id)
  - [Excluir um Usuário por ID](#excluir-usuario-por-id)
  - [Depositar Valor no Saldo de um Usuário](#depositar-valor-usuario-por-id)
  - [Sacar Valor no Saldo de um Usuário](#sacar-valor-usuario-por-id)
******
- [Recursos - Módulo "StockTransaction"](#recursos-modulo-stocktransaction)
  - [Listar Todas as Transações](#listar-todas-as-transacoes)
  - [Obter uma Transação por ID](#obter-uma-transacao-por-id)
  - [Criar uma Nova Transação](#criar-uma-nova-transacao)
  - [Atualizar uma Transação por ID](#atualizar-transacao-por-id)
  - [Excluir uma Transação por ID](#excluir-transacao-por-id)
******
- [Uso da API](#uso-da-api)
- [Requisitos](#requisitos)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Contribuições](#contribuicoes)
- [Licença](#licenca)


******
<a id="descricao"></a>
## Descrição

A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (Stock), usuários (User) e transações de ações (Stock Transaction).

<a id="url-base"></a>
### URL Base:
*`http://localhost:8080`*


******
<a id="recursos-modulo-stock"></a>
## Recursos - Módulo Stock


******
<a id="listar-todas-as-acoes"></a>
### Listar Todas as Ações

Retorna uma lista de todas as ações disponíveis.

**Endpoint:**
```http
GET /stocks/
```

**Resposta:**
Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
[
  {
    "id": "String",
    "symbol": "String",
    "companyName": "String",
    "price": double,
    "quantity": int
  },
  {
    "id": "String",
    "symbol": "String",
    "companyName": "String",
    "price": double,
    "quantity": int
  }
]
```
- Se não houver ações cadastradas, o código de status será `200` e com a seguinte mensagem:
```JSON
[]
```
[voltar ao topo](#voltar-topo)


******
<a id="obter-uma-acao-por-id"></a>
### Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
GET /stocks/{id}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "id": "String",
  "symbol": "String",
  "companyName": "String",
  "price": float,
  "quantity": int
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Ação não encontrada
```
[voltar ao topo](#voltar-topo)


******
<a id="criar-uma-nova-acao"></a>
### Criar uma Nova Ação

Cria uma nova ação com base nos dados fornecidos.

**Endpoint:**
```http
POST /stocks/
```
**Corpo da Solicitação (Request Body):**

**Observação:** *O símbolo/sigla da ação deve ter 3 letras seguidas por 1 número.*

```JSON
{
  "symbol": "BBA3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97,
  "quantity": 500
}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "id": "f1d3a12a-6431-45e6-a805-7d1f64084d65",
  "symbol": "BBA3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97,
  "quantity": 500
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Ação já existe
```
[voltar ao topo](#voltar-topo)


******
<a id="atualizar-acao-por-id"></a>
### Atualizar uma Ação por ID
Atualiza os detalhes de uma ação específica com base no ID.

**Endpoint:**
```HTTP
PUT /stocks/{id}
```
*Tomando como base, a resposta da requisição `POST`, teríamos o seguinte para o `PUT`:*
```HTTP
PUT /stocks/f1d3a12a-6431-45e6-a805-7d1f64084d65
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBA3",
  "companyName": "Banco do Brasil SA",
  "price": 59.97,
  "quantity": 500
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Ação não encontrada
```
[voltar ao topo](#voltar-topo)


******
<a id="excluir-acao-por-id"></a>
### Excluir uma Ação por ID
Exclui uma ação específica com base no ID.

**Endpoint:**
```http
DELETE /stocks/{id}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o código de status será `200` e com a seguinte mensagem:
```JSON
Ação deletada com sucesso!
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Ação não encontrada
```
[voltar ao topo](#voltar-topo)


******
<a id="recursos-modulo-user"></a>
## Recursos - Módulo User


******
<a id="listar-todos-os-usuarios"></a>
### Listar Todos os Usuários
Retorna uma lista de todos os usuários disponíveis.

**Endpoint:**
```http
GET /user/
```

**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
[
  {
    "userId": "String",
    "username": "String",
    "password": "String",
    "email": "String",
    "firstName": "String",
    "lastName": "String",
    "birthDate": LocalDate,
    "balance": float
  },
  {
    "userId": "String",
    "username": "String",
    "password": "String",
    "email": "String",
    "firstName": "String",
    "lastName": "String",
    "birthDate": LocalDate,
    "balance": float
  },
]
```
- Se não houver usuários cadastrados, o código de status será `200` e com a seguinte mensagem:
```JSON
[]
```
[voltar ao topo](#voltar-topo)


******
<a id="obter-um-usuario-por-id"></a>
### Obter um Usuário por ID

Retorna os detalhes de um usuário específico com base no ID.

**Endpoint:**
```http
GET /user/{userId}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "userId": "String",
  "username": "String",
  "password": "String",
  "email": "String",
  "firstName": "String",
  "lastName": "String",
  "birthDate": LocalDate,
  "balance": float
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Usuário não existe
```
[voltar ao topo](#voltar-topo)


******
<a id="criar-um-novo-usuario"></a>
### Criar um Novo Usuário
Cria um novovo usuário com base nos dados fornecidos.

**Endpoint:**
```http
POST /user/
```
**Corpo da Solicitação (Request Body):**

**Observação:**
* *O campo [username] não pode conter espaços;*
* *O campo [email] deve ser um email válido.*

```JSON
{
  "username": "zesilva",
  "password": "senha123",
  "email": "zesilva@email.com",
  "firstName": "José",
  "lastName": "Silva",
  "birthDate": "1985-10-20",
  "balance": 1000.00
}
```
**Resposta:**
Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "userId": "f1d3a12a-6431-45e6-a805-7d1f64084d65",
  "username": "zesilva",
  "password": "senha123",
  "email": "zesilva@email.com",
  "firstName": "José",
  "lastName": "Silva",
  "birthDate": "1985-10-20",
  "balance": 1000.00
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Usuário já existe
```
[voltar ao topo](#voltar-topo)


******
<a id="atualizar-usuario-por-id"></a>
### Atualizar um Usuário por ID
Atualiza os detalhes de um usuário específico com base no ID.

**Endpoint:**
```HTTP
PUT /user/{userId}
```
*Tomando como base, a resposta da requisição `POST`, teríamos o seguinte para o `PUT`:*
```HTTP
PUT /user/f1d3a12a-6431-45e6-a805-7d1f64084d65
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "username": "josesilva",
  "password": "senha@$123",
  "email": "zesilva@email.com",
  "firstName": "José",
  "lastName": "Silva",
  "birthDate": "1988-10-20",
  "balance": 1000.00
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Usuário não existe
```
[voltar ao topo](#voltar-topo)


******
<a id="excluir-usuario-por-id"></a>
### Excluir um Usuário por ID

Exclui um usuario específico com base no ID.

**Endpoint:**
```http
DELETE /user/{id}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o código de status será `200` e com a seguinte mensagem:
```JSON
Usuário deletado com sucesso!
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Usuário não existe
```
[voltar ao topo](#voltar-topo)


******
<a id="depositar-valor-usuario-por-id"></a>
### Depositar Valor no Saldo de um Usuário

Deposita (deposit) um valor no saldo (balance) de um usuário específico com base no ID.

**Endpoint:**
```http
PUT /user/{userId}/deposit
```

*Tomando como base, a resposta da requisição `POST`, teríamos o seguinte para o `PUT` de depósito:*
```HTTP
PUT /user/f1d3a12a-6431-45e6-a805-7d1f64084d65/deposit
```
**Corpo da Solicitação (Request Body):**

**Observação:**
* *O corpo da solicitação deve conter apenas o valor a ser creditado, sem chaves "{}".*

```JSON
115
```
**Resposta:**
- Se a solicitação for bem-sucedida, o código de status será `200` e com a seguinte mensagem:
```JSON
{
  "userId": "f1d3a12a-6431-45e6-a805-7d1f64084d65",
  "username": "zesilva",
  "password": "senha123",
  "email": "zesilva@email.com",
  "firstName": "José",
  "lastName": "Silva",
  "birthDate": "1985-10-20",
  "balance": 1115.00
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Usuário não existe
```
[voltar ao topo](#voltar-topo)


******
<a id="sacar-valor-usuario-por-id"></a>
### Sacar Valor no Saldo de um Usuário

Realiza um saque (withdraw) de um valor no saldo (balance) de um usuário específico com base no ID.

**Endpoint:**
```http
PUT /user/{userId}/withdraw
```

*Tomando como base, a resposta da requisição `PUT` de depósito, teríamos o seguinte para o `PUT` de saque:*
```HTTP
PUT /user/f1d3a12a-6431-45e6-a805-7d1f64084d65/withdraw
```
**Corpo da Solicitação (Request Body):**

**Observação:**
* *O corpo da solicitação deve conter apenas o valor a ser debitado, sem chaves "{}".*

```JSON
230
```
**Resposta:**
- Se a solicitação for bem-sucedida, o código de status será `200` e com a seguinte mensagem:
```JSON
{
  "userId": "f1d3a12a-6431-45e6-a805-7d1f64084d65",
  "username": "zesilva",
  "password": "senha123",
  "email": "zesilva@email.com",
  "firstName": "José",
  "lastName": "Silva",
  "birthDate": "1985-10-20",
  "balance": 885.00
}
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Usuário não existe
```
[voltar ao topo](#voltar-topo)


******
<a id="recursos-modulo-user"></a>
## Recursos - Módulo StockTransaction

******
<a id="listar-todas-as-transacoes"></a>
### Listar Todas as Transações

Retorna uma lista de todas as transações disponíveis.

**Endpoint:**
```http
GET /transaction/
```

**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
[
  {
    "transaction_id": "String",
    "stock": {
      "id": "String",
      "symbol": "String",
      "companyName": "String",
      "price": double,
      "quantity": int
    },
    "stockId": "f166d9a8-8115-45bd-b026-ae1a55874d5f",
    "user": {
      "userId": "String",
      "username": "String",
      "password": "String",
      "email": "String",
      "firstName": "String",
      "lastName": "String",
      "birthDate": LocalDate,
      "balance": float
    },
    "userId": "String",
    "quantity": float
  }
]
```
- Se não houver transações cadastradas, o código de status será `200` e com a seguinte mensagem:
```JSON
[]
```
[voltar ao topo](#voltar-topo)


******
<a id="obter-uma-transacao-por-id"></a>
### Obter uma Transação por ID

Retorna os detalhes de uma transação específica com base no ID.

**Endpoint:**
```http
GET /transaction/{transactionId}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "transaction_id": "String",
  "stock": {
    "id": "String",
    "symbol": "String",
    "companyName": "String",
    "price": double,
    "quantity": int
  },
  "stockId": "f166d9a8-8115-45bd-b026-ae1a55874d5f",
  "user": {
    "userId": "String",
    "username": "String",
    "password": "String",
    "email": "String",
    "firstName": "String",
    "lastName": "String",
    "birthDate": LocalDate,
    "balance": float
  },
  "userId": "String",
  "quantity": float
}
```
- Se a solicitação não for bem-sucedida, o corpo da resposta será `json` e o código de status será `400`:
```JSON
Transação não encontrada
```
[voltar ao topo](#voltar-topo)


******
<a id="criar-uma-nova-transacao"></a>
### Criar uma Nova Transação
Cria uma nova transação com base nos dados fornecidos.

**Endpoint:**
```http
POST /transaction/
```
**Corpo da Solicitação (Request Body):**
```JSON
{
  "stockId": "String",
  "userId": "String",
  "quantity": int
}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "transaction_id": "String",
  "stock": null,
  "stockId": "String",
  "user": null,
  "userId": "String",
  "quantity": int
}
```
- Se a solicitação não for bem-sucedida, o corpo da resposta será `json` e o código de status será `400`:
```JSON
Transação já existe
```
[voltar ao topo](#voltar-topo)


******
<a id="atualizar-transacao-por-id"></a>
### Atualizar uma Transação por ID
Atualiza os detalhes de uma transação específica com base no ID.

**Endpoint:**
```HTTP
PUT /transaction/{transactionId}
```

**Corpo da Solicitação (Request Body):**

```JSON
{
  "stockId": "String",
  "userId": "String",
  "quantity": int
}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
Transação atualizada com sucesso!
```
- Se a solicitação não for bem-sucedida, o corpo da resposta será `json` e o código de status será `400`:
```JSON
Transação não encontrada
```
[voltar ao topo](#voltar-topo)


******
<a id="excluir-transacao-por-id"></a>
### Excluir uma Transação por ID
Exclui uma transação específica com base no ID.

**Endpoint:**
```http
DELETE /transaction/{transactionId}
```
**Resposta:**
- Se a solicitação for bem-sucedida, o código de status será `200` e com a seguinte mensagem:
```JSON
Transação atualizada com sucesso!
```
- Se a solicitação não for bem-sucedida, o código de status será `400` e com a seguinte mensagem:
```JSON
Transação não encontrada
```
[voltar ao topo](#voltar-topo)


******
<a id="uso-da-api"></a>
## Uso da API
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`. Utilize algum software como Postman para testar o funcionamento da API.

[voltar ao topo](#voltar-topo)


******
<a id="requisitos"></a>
## Requisitos
- Java 11 ou superior
- Maven
- Banco de dados

[voltar ao topo](#voltar-topo)


******
<a id="tecnologias-utilizadas"></a>
## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

[voltar ao topo](#voltar-topo)


******
<a id="contribuicoes"></a>
## Contribuições
Contribuições são bem-vindas!

[voltar ao topo](#voltar-topo)


******
<a id="licenca"></a>
## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).

[voltar ao topo](#voltar-topo)