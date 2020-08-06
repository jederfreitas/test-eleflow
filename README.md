**Informações gerais do projeto**

Este projeto foi desenvolvido utilizando a seguintes stack:

- **Java 11**
- **Spring Boot Webflux**
- **Spring Data Mongodb Reactive**
- **Open API WebFlux, para facilitar a documentação e testes dos microsserviços**
- **Mongodb embedded** 
- **Gradle**

Basicamente 70% do código foi coberto com teste unitário.

Para facilitar os testes, foi adotado o banco de dados NoSQL MongoDB  embedded , portanto não será necessário realizar nenhuma instação ou configuração adicional, uma vez que ao iniciar a aplicação o serviço é automaticamente baixado, configurado e inicializado com a aplicação.

Para realizar os testes das API's basta acessar http://localhost:8080/swagger.html

## Operações:

  **Consultar um planeta por id:**

    ​/planets​/{id}

**Listar todos os planetas:**

    /planets

**Consultar um planeta pelo nome:**

     ​/planets​/name​/{name}

**Consultar um planeta pelo nome na API externa swapi:**

    /swapi​/name​/{name}

**Consultar todos os planetas de forma paginada na API externa swapi:**
    
    ​/swapi​/page​/{page}

**Deletar um planeta pelo id**

    /planets/{id}

**Deletar um planeta pelo nome**

    /planets/name/{name}

**Inserir um planeta**

    /planets

Também foi disponibilizado no projeto o arquivo EleflowTest.postman_collection.json, que poderá ser importado no PostMan para realizar os testes se julgar necessário.

