# Mercado API

API para gerenciamento de marcas, produtos e usuários de um mercado. Desenvolvida em **Spring Boot**, com **Redis** para cache e **Spring Security** para autenticação.

## Estrutura

* **Configurações**: Redis, Swagger (disponível apenas no perfil `dev`).
* **Entidades**: Marca, Produto, User.
* **Segurança**: JWT via Spring Security.
* **Repositórios**: CRUD de entidades.
* **Recursos**: Endpoints para autenticação e gerenciamento.

## Perfis de Aplicação

* `dev`: Desenvolvimento (Swagger habilitado).
* `prod`: Produção.
* `test`: Testes.

Definido em `application.properties` e complementado pelos arquivos de perfil.

## Dependências

* **Spring Boot Starter Web**
* **Spring Boot Starter Data JPA**
* **Spring Boot Starter Security**
* **Spring Boot Starter Validation**
* **Spring Boot Starter Cache**
* **Spring Boot Starter Test**
* **Spring Data Redis**
* **JWT (jjwt)**
* **Swagger (springdoc-openapi)**
* **PostgreSQL Driver**

## Como Executar

1. Suba os contêineres:

   ```bash
   docker-compose up
   ```

2. Endpoints principais:

   * `http://localhost:8080/login` – Autenticação
   * `http://localhost:8080/marcas`
   * `http://localhost:8080/produtos`
   * `http://localhost:8080/users`

 O Swagger está disponível apenas em ambiente local (`dev`).

## Variáveis de Ambiente

* `SPRING_PROFILES_ACTIVE`
* `SPRING_DATASOURCE_URL`, `USERNAME`, `PASSWORD`
* `REDIS_HOST`, `REDIS_PORT`

## 🤝 Contribuição

Clone o repositório, crie uma branch para suas alterações e envie um pull request!
