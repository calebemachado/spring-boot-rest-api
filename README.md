### API criada para fornecer CRUD de clientes

Você precisa ter instalado na sua máquina:
- [Java](https://www.java.com/pt_BR/download/)
- [Git](https://git-scm.com/book/pt-br/v2/Come%C3%A7ando-Instalando-o-Git)
- [Docker](https://hub.docker.com/)
- [Docker-Compose](https://docs.docker.com/compose/install/)

- Obs: A variável de ambiente JAVA_HOME deve está configurada.

Instalação

Você pode clonar a API e executar diretamente com o comando abaixo. Sua aplicação estará disponível na porta 8080. 
Certifique-se de ter criado a base de dados postgres ou altere o arquivo application.properties com a base desejada.
```
./mvnw spring-boot:run
```

Outra forma de executar a aplicação diretamente junto com o banco de dados postgres é utilizando o comando docker-compose up.
Sua API estará rodando na porta 9000
```
git clone https://github.com/calebemachado/spring-boot-rest-api.git rest-api
cd rest-api
./mvnw clean package

cp target/builders-rest-api-0.0.1-SNAPSHOT.jar src/main/docker

cd src/main/docker
docker-compose up
```

OBS.: Para parar o docker compose utilize ```docker-compose down```. Caso necessite alterar algum arquivo, remova a 
Docker image existente com ```docker rmi builders_rest_api:latest``` e docker-compose up novamente.

Para documentação da API foi utilizado Swagger, acesse em:
- [Swagger-UI localhost](http://localhost:8080/swagger-ui.html)
- [Swagger-UI docker-compose](http://localhost:9000/swagger-ui.html)

## Rotas presentes na API

| ROTAS                   | VERBOS | OBJETIVO                                                                   |
|-------------------------|--------|----------------------------------------------------------------------------|
| api/clients               | GET     | Lista todos os clientes por filtros via queryParams                         |
| api/clients               | POST    | Cria um novo cliente                                                       |
| api/clients/{id}          | GET     | Lista um único cliente por id                                              |
| api/clients/{id}          | PUT     | Atualiza um cliente por id                                                  |
| api/clients/{id}          | DELETE  | Remove um cliente por id                                                   |
| api/clients/{id}          | PATCH   | Atualiza o nome, cpf ou data de nascimento de um cliente por ID            |

## Tecnologias usadas

* [JAVA](https://www.java.com/pt_BR/) - A linguagem usada
* [DOCKER](https://hub.docker.com/) - Container utilizado para o banco de dados e aplicação via compose
* [SPRING-BOOT](https://start.spring.io/) - Framework para criação da API
* [SPRING-JPA](https://spring.io/projects/spring-data-jpa) - Gerenciamento e persistência dos dados
* [SWAGGER](https://swagger.io/) - Documentação da API
* [SPECIFICATION-ARG-RESOLVER](https://github.com/tkaczmarzyk/specification-arg-resolver) - Uma API alternativa para filtrar dados com Spring MVC e Spring Data JPA

## Authors

* **Calebe Machado** - [calebemachado](https://github.com/calebemachado)
