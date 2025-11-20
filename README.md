# MWM Bioplantas - Portal Backend

Este é o backend do portal MWM Bioplantas, desenvolvido com Spring Boot. A API fornece todos os dados e funcionalidades necessários para o frontend, incluindo autenticação, gerenciamento de cooperados, agendamentos, controle de portaria e relatórios.

## Arquitetura

O projeto segue uma arquitetura em camadas, padrão em aplicações Spring Boot, para garantir a separação de responsabilidades e a manutenibilidade.

- **Linguagem**: Java 17+
- **Framework**: Spring Boot 3.x
- **Banco de Dados**: H2 (em memória) para ambiente de desenvolvimento. A configuração pode ser facilmente alterada no `application.properties` para um banco de dados de produção (como PostgreSQL ou MySQL).
- **Autenticação**: Segurança baseada em JSON Web Tokens (JWT). O acesso aos endpoints é protegido, exigindo um token válido, com exceção das rotas de autenticação.
- **Dependências Principais**:
  - `spring-boot-starter-web`: Para criar APIs REST.
  - `spring-boot-starter-data-jpa`: Para persistência de dados com o banco.
  - `spring-boot-starter-security`: Para controle de acesso e autenticação.
  - `jjwt-api`, `jjwt-impl`, `jjwt-jackson`: Para geração e validação de tokens JWT.
  - `lombok`: Para reduzir código boilerplate (getters, setters, construtores).
  - `h2database`: Driver do banco de dados em memória.

## Estrutura do Projeto

O código-fonte está organizado nos seguintes pacotes principais dentro de `src/main/java/com/mwm/portal`:

- `config`: Contém as classes de configuração do Spring, como `SecurityConfig` (regras de segurança e CORS), `ApplicationConfig` (provedor de autenticação) e `DataSeeder` (popula o banco de dados inicial).
- `controller`: Define os endpoints da API REST. Cada controlador é responsável por um recurso específico (ex: `CooperadoController`, `AgendaController`).
- `dto`: (Data Transfer Objects) Classes simples usadas para transferir dados entre o cliente e o servidor, como `AuthRequest` para o login.
- `model`: As entidades JPA que representam as tabelas do banco de dados (ex: `User`, `Cooperado`, `PortariaRegistro`).
- `repository`: Interfaces do Spring Data JPA que fornecem os métodos de acesso ao banco de dados (CRUD e consultas customizadas).
- `security`: Classes relacionadas à implementação do JWT, como o filtro de autenticação (`JwtAuthenticationFilter`) e o serviço de manipulação de tokens (`JwtService`).
- `service`: Camada de serviço que contém a lógica de negócio, como o `AuthService` que orquestra o registro e a autenticação de usuários.

## Modelagem de Dados (Entidades)

As principais entidades do sistema são:

- **User**: Armazena as informações de login dos usuários (`username`, `password`, `email`) e seu nível de acesso (`role`), que pode ser `administrador`, `editor` ou `leitor`.
- **Cooperado**: Mantém o cadastro dos cooperados, incluindo matrícula, filial, motorista e tipo de veículo.
- **Agenda**: Registra o planejamento de coletas semanais para cada cooperado.
- **PortariaRegistro**: Controla a entrada e saída de veículos na planta, registrando informações como empresa, motorista, placa, atividade e status (`Pendente`, `Em_processo`, `Concluido`).
- **Abastecimento**: Grava os registros de abastecimento de veículos com biometano, incluindo volume, odômetro e horários.
- **Coleta**: Representa um agendamento ou registro de uma coleta de dejetos específica.
- **QualidadeDejetos**: Armazena os resultados das análises de qualidade dos dejetos coletados.

## Dados Iniciais e `DataSeeder`

Para facilitar o desenvolvimento e os testes, o projeto utiliza um `DataSeeder` que popula o banco de dados na primeira inicialização.

- **Gatilho**: O seeder é executado automaticamente porque a propriedade `spring.jpa.hibernate.ddl-auto` está configurada como `create-drop` no `application.properties`. Isso significa que o banco é recriado a cada reinicialização da aplicação.
- **Dados Criados**:
  - **Usuários Padrão**:
    - `username`: **admin** / `password`: **admin123** (Role: `administrador`)
    - `username`: **editor** / `password`: **editor123** (Role: `editor`)
    - `username`: **porteiro** / `password`: **leitor123** (Role: `leitor`)
  - **Dados de Negócio**: O seeder também popula as tabelas de `Cooperado` e `Agenda` com os dados extraídos dos PDFs fornecidos, além de criar dados de exemplo para `Portaria`, `Abastecimento` e outras entidades, garantindo que a aplicação esteja funcional e com dados para visualização desde o início.

## Como Rodar o Projeto

### Pré-requisitos

1.  **JDK 17 ou superior** instalado.
2.  **Maven** (geralmente já vem integrado com as IDEs modernas).

### Passos para Execução

1.  **Clone o repositório**:

    ```bash
    git clone <url-do-repositorio>
    cd MWM_BIOPLANTAS_BACKEND
    ```

2.  **Execute usando o Maven Wrapper**:
    O projeto já inclui o Maven Wrapper (`mvnw`), que baixa a versão correta do Maven automaticamente.

    - No Windows:
      ```bash
      .\mvnw.cmd spring-boot:run
      ```
    - No Linux ou macOS:
      ```bash
      ./mvnw spring-boot:run
      ```

3.  **Acesse a API**:
    - A aplicação estará rodando em `http://localhost:8081`.
    - A console do banco de dados H2 pode ser acessada em `http://localhost:8081/h2-console`.
      - **JDBC URL**: `jdbc:h2:mem:mwm_db`
      - **Username**: `sa`
      - **Password**: `password`

### Testando a Autenticação

1.  Use uma ferramenta como Postman, Insomnia ou `curl` para fazer uma requisição `POST` para o endpoint de login:

    - **URL**: `http://localhost:8081/api/auth/login`
    - **Method**: `POST`
    - **Body** (raw, JSON):
      ```json
      {
        "username": "admin",
        "password": "admin123"
      }
      ```

2.  A resposta conterá um token JWT:

    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

3.  Para acessar qualquer outro endpoint protegido (ex: `GET /api/cooperados`), adicione o token no cabeçalho da requisição:
    - **Header**: `Authorization`
    - **Value**: `Bearer eyJhbGciOiJIUzI1NiJ9...` (substitua pelo token recebido).
