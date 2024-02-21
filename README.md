# MandaCaru Broker API

## Descrição
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).

### URL Base:
*`http://localhost:8080`*

## Recursos

### Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis.

**Endpoint:**
```http
GET /stocks
```

**Resposta:**
Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
[
  {
    "id": "String",
    "symbol": "String",
    "companyName": "String",
    "price": double
  },
  {
    "id": "String",
    "symbol": "String",
    "companyName": "String",
    "price": double
  },
]
```

### Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
GET /stocks/{id}
```
**Resposta:**
Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "id": "String",
  "symbol": "String",
  "companyName": "String",
  "price": double
}
```

### Criar uma Nova Ação
Cria uma nova ação com base nos dados fornecidos.

**Endpoint:**
```http
POST /stocks
```
**Corpo da Solicitação (Request Body):**

**Observação:** *O símbolo/sigla da ação deve ter 3 letras seguidas por 1 número.*

```JSON
{
  "symbol": "BBA3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}
```
**Resposta:**
Se a solicitação for bem-sucedida, o corpo da resposta será `json` e o código de status será `200`:
```JSON
{
  "id": "f1d3a12a-6431-45e6-a805-7d1f64084d65",
  "symbol": "BBA3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}
```
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
  "price": 59.97
}
```

### Excluir uma Ação por ID
Exclui uma ação específica com base no ID.

**Endpoint:**
```http
DELETE /stocks/{id}
```
**Resposta:**
Se a solicitação for bem-sucedida, o código de status será `200` e com a seguinte mensagem:
```JSON
Item {id} deletado com sucesso!
```

## Uso
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`. Utilize algum software como Postman para testar o funcionamento da API.

## Requisitos
- Java 11 ou superior
- Maven
- Banco de dados

## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

## Contribuições
Contribuições são bem-vindas!

## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).