# Mercado API
Este projeto é uma API para gerenciamento de produtos e marcas em um mercado. Utiliza o Spring Boot com Spring Data JPA, caching com Caffeine, e está configurado para ser executado em contêineres Docker com PostgreSQL como banco de dados.

## Estrutura do Projeto
### Configurações
* CacheCaffeineConfig: Configuração para o cache Caffeine, utilizando especificações fornecidas através de variáveis de ambiente.
* SwaggerConfig: Configuração para Swagger, disponível apenas no perfil dev, que define a documentação da API.

### Entidades
* Marca: Entidade que representa uma marca de produtos.
* Produto: Entidade que representa um produto, associado a uma marca.

### Repositórios
* MarcaRepository: Interface de repositório para a entidade Marca. Estende JpaRepository para fornecer operações CRUD e consultas básicas.
* ProdutoRepository: Interface de repositório para a entidade Produto. Também estende JpaRepository, oferecendo suporte a operações CRUD e consultas básicas.

### Exceções
* CustomExceptionHandler: Manipulador global de exceções, que lida com ResourceNotFoundException e exceções gerais.
* ResourceNotFoundException: Exceção personalizada para quando um recurso não é encontrado.

### Recursos
* MarcaResource: Endpoint para operações CRUD em marcas, incluindo busca paginada de marcas.
* ProdutoResource: Endpoint para operações CRUD em produtos, incluindo busca paginada de produtos.

### Serviços
* MarcaService: Serviço para manipulação de marcas com suporte a cache.
* ProdutoService: Serviço para manipulação de produtos com suporte a cache.

## Como Executar
### 1. Construir e Rodar os Contêineres:
Navegue até a pasta raiz do projeto e execute o comando:
```
    docker-compose up
```

Note que, quando executado em um contêiner, o Swagger não estará disponível.

### 2. Acessar a API:
* API de Marca: http://localhost:8080/marcas
* API de Produto: http://localhost:8080/produtos

Documentação Swagger estará disponível apenas no ambiente de desenvolvimento local, não estando acessível quando o projeto é executado via contêiner.

## Variáveis de Ambiente
* SPRING_DATASOURCE_URL: URL de conexão com o banco de dados PostgreSQL.
* SPRING_DATASOURCE_USERNAME: Nome de usuário para o banco de dados.
* SPRING_DATASOURCE_PASSWORD: Senha para o banco de dados.
* CACHE_CAFFEINE_SPEC: Configuração do cache Caffeine.

## Contribuição
Sinta-se à vontade para contribuir com melhorias ou correções. Para começar, clone o repositório e crie uma branch para suas alterações.