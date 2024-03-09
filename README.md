# MandaCaru Broker API

## Descrição
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).  
**Uma Stock tem a seguinte estrutura:**  
+ Id
+ symbol : abreviação da stock (composta de 3 letras e 1 número)
+ companyName : descreve o nome da empresa
+ price: preço inicial da ação (deve ser maior que zero)  
Todos os atributos com exceção do id, são obrigatórios um valor associado.
## Recursos

### Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis.

**Endpoint:**
```http
GET /stocks
```
**Response:**
```json
[
  {
    "id": "9a2af560-b610-4a2c-be50-5a9498ffe107",
    "symbol": "BBB1",
    "companyName": "Banco do Brasil SA",
    "price": 259.97
  },
  {
    "id": "1284391a-8edc-4029-959b-5f75d89a97ca",
    "symbol": "BBa1",
    "companyName": "Banco do Brasil SA",
    "price": 10.440000000000005
  },
  {
    "id": "cf2126ae-9ea4-4a5d-b65a-b9ff4d488e3b",
    "symbol": "BBB1",
    "companyName": "Banco do Brasil SA",
    "price": 10.0
  }
]
```
**HttpStatus:200 OK**

### Obter uma Ação por ID
Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
GET /stocks/{id}
```
**Response:**  
Para um id existente:
```json
{
  "id":"9a2af560-b610-4a2c-be50-5a9498ffe107",
  "symbol":"BBB1",
  "companyName":"Banco do Brasil SA",
  "price":259.97
}
```
**HttpStatus:200 OK**

### Criar uma Nova Ação
Cria uma nova ação com base nos dados fornecidos e retorna os dados da ação criada no corpo da resposta.

**Endpoint:**
```http
POST /stocks
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBS3",
  "companyName": "Banco do Brasil SA",
  "price": 50
}
```
**Response:**
```json
{
  "id": "e8d0ca18-7a9e-46d8-b610-b30cb1769c49",
  "symbol": "BBS3",
  "companyName": "Banco do Brasil SA",
  "price": 50.0
}
```
**Header:**  
Retorna o caminho do recurso criado
```http
Location : /stocks/e8d0ca18-7a9e-46d8-b610-b30cb1769c49
```
**Htpp status: 201 created**

### Atualizar uma Ação por ID
Atualiza os detalhes de uma ação específica com base no ID. 
O preço é calculado pela soma do novo preço ao antigo preço.

**Endpoint:**
```http
PUT /stocks/{id}
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBS3",
  "companyName": "Banco do Brasil SA",
  "price": 50
}
```
**Response:**  
Para um id existente:
```json
{
  "id": "9a2af560-b610-4a2c-be50-5a9498ffe107",
  "symbol": "BBB1",
  "companyName": "Banco do Brasil SA",
  "price": 100
}
```
**Http status: 200 OK**

### Excluir uma Ação por ID
Exclui uma ação específica com base no ID.

**Endpoint:**
```http
DELETE /stocks/{id}
```
**Response:**  
>Não há conteúdo no corpo da requsição  

**Http status: 204 No content**

### Possíveis erros:
+ Not found: quando é passado um id inexistente no banco de dados.
+ Unprocessable Entity : O corpo da requisição está no formato correto, porém os dados são inválidos.

### Sumário dos códigos HTTP

| Código | Mensagem             | Descrição                                       |
|:-------|:---------------------|-------------------------------------------------|
| `200`  | Ok                   | Requisição processada com sucesso               |
| `201`  | Created              | Recurso criado com sucesso                      |
| `204`  | No content           | Requisição processada com sucesso e sem retorno |
| `404`  | Not Found            | Recurso não encontrado                          |
| `422`  | Unprocessable Entity | Erro na validação dos campos da requisição      |
| `500`  | Server error         | Um Problema ocorreu no servidor                 |

## Uso
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure a conexão com o banco de dados desejado:
arquivo `application.properties`
```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/mandacaru-broker
spring.datasource.username=peaga
spring.datasource.password=peaga
```
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
- PostgreSQL
- Spring Boot Test
- Junit 5
- Mockito

## Contribuições
Contribuições são bem-vindas!

## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).

