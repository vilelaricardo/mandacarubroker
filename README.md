# MandaCaru Broker API

## Descrição
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).

## Recursos

### Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis no formato JSON.

**Endpoint:**
```http
GET /stocks
```

### Obter uma Ação por ID

Retorna os detalhes de uma Ação específica no formato JSON com base no ID.
O ID é obrigatório para a requisição, sendo recebido pelo cabeçalho da requisição.
Em caso de não exitir uma Ação com o ID fornecido o retorno é null.

**Endpoint:**
```http
GET /stocks/{id}
```

### Criar uma Nova Ação
Cria uma nova Ação com base nos dados fornecidos.
O objeto da Ação deve ser passado pelo corpo da requisição como JSON e deve conter os campos: symbol, companyName e price.
Symbol deve ser uma string de tamanho 3, onde as duas primeiras posições são letras do alfabeto e a terceira é um dígito.
companyName é uma string que não pode ser vazia.
price é um valor do tipo ponto flutuante e não pode ser vazio.
Em caso de sucesso retorna um JSON com as informações da Ação criada.

**Endpoint:**
```http
POST /stocks
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "AS3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}

```
### Atualizar uma Ação por ID
Atualiza os detalhes de uma ação específica com base no ID.
Tanto o ID quanto, passado pelo cabeçalho, como o objeto da Ação, passado pelo corpo da requisição, são obrigatórios.
O objeto JSON da Ação deve conter os mesmos campos e seguir as mesmas regras especificadas em sua criação.
Em caso de sucesso retorna um JSON com as informações da ação atualizadas.
Em caso de fracasso retorna null.

**Endpoint:**
```http
PUT /stocks/{id}
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "AS3",
  "companyName": "Banco do Brasil SA",
  "price": 59.97
}

```

### Excluir uma Ação por ID
Exclui uma ação específica com base no ID.
O ID é obrigatório e deve ser passado pelo cabeçalho.
Não tem retorno.

**Endpoint:**
```http
DELETE /stocks/{id}
```


## Uso
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
- PostgreSQL

## Contribuições
Contribuições são bem-vindas!

## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).

