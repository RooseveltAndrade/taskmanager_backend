
Abaixo está a arquitetura de dados do sistema:
![Arquitetura do Sistema](https://github.com/user-attachments/assets/829591a6-e02e-4fc6-bb0b-685a2b30bbd0)

# Backend - Gerenciador de Tarefas

Este é o backend do sistema de Gerenciamento de Tarefas, responsável por fornecer APIs para autenticação, gerenciamento de usuários, equipes e tarefas.

## 📋 Requisitos Funcionais Implementados

### 1. Usuário e Autenticação
- Cadastro de usuário.
- Login com autenticação JWT.
- Cada usuário pertence a uma única equipe.
- Usuário pode visualizar e gerenciar apenas tarefas da sua equipe.

### 2. Tarefas
- Criar tarefa com:
  - Título.
  - Descrição.
  - Data limite.
  - Status (Pendente, Em andamento, Concluída).
  - Responsável.
- Editar tarefa.
- Listar tarefas com filtro por status.
- Trocar status de tarefa.

---

## 🚀 Tecnologias Utilizadas
- *Java 17*: Linguagem principal.
- *Spring Boot*: Framework para desenvolvimento rápido e eficiente.
- *Spring Security*: Gerenciamento de autenticação e autorização.
- *JWT (JSON Web Token)*: Para autenticação segura.
- *JPA/Hibernate*: Para persistência de dados.
- *H2 Database*: Banco de dados em memória para desenvolvimento e testes.
- *Maven*: Gerenciamento de dependências.

---

## ⚙ Configuração do Projeto

### Pré-requisitos
- *Java 17* ou superior instalado.
- *Maven* instalado.
- *Postman* ou outra ferramenta para testar as APIs (opcional).

### Passos para Rodar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/RooseveltAndrade/taskmanager_backend.git
2. Navegue até a pasta do backend: cd backend
3. Compile e rode o projeto:
     mvn spring-boot:run
   4.O backend estará disponível em:
    http://localhost:8080 

GitHub Copilot
Aqui está o README.md para o backend:

Navegue até a pasta do backend:
Compile e rode o projeto:
O backend estará disponível em:
🛠 Estrutura do Projeto
Controller: Contém os endpoints da API.
Service: Contém a lógica de negócios.
Repository: Gerencia a persistência de dados.
Model: Contém as entidades do sistema.
Security: Configurações de autenticação e autorização.

🔑 Endpoints Principais
Autenticação
POST /auth/login: Realiza login e retorna o token JWT.
Usuários
POST /users: Cadastra um novo usuário.
GET /users/{id}: Retorna informações de um usuário.
Tarefas
POST /tasks: Cria uma nova tarefa.
GET /tasks: Lista tarefas com filtros opcionais.
PUT /tasks/{id}: Atualiza uma tarefa existente.
DELETE /tasks/{id}: Remove uma tarefa.

🧪 Testes

Testes de integração e unitários foram implementados para garantir a qualidade do código.
Para rodar os testes:
mvn test

📚 Boas Práticas Adotadas
Separação de camadas (Controller, Service, Repository).
Uso de DTOs para transferir dados entre camadas.
Configuração de segurança com Spring Security e JWT.
Normalização do banco de dados com uso adequado de chaves e relacionamentos.

📄 Licença
Este projeto está sob a licença MIT. Sinta-se à vontade para usá-lo e modificá-lo conforme necessário.
