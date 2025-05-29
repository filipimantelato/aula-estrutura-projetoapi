# 📋 Projeto CRUD com Spring Boot, H2 e Autenticação via BCrypt

Este projeto consiste em uma API RESTful desenvolvida com **Java Spring Boot**, utilizando **banco de dados H2 em memória**, **JPA/Hibernate** para persistência de dados e autenticação básica com criptografia de senha usando **BCrypt**. A aplicação permite realizar operações de **CRUD** sobre duas entidades principais: `User` e `Info`.

---

## 🧱 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.2
- Spring Web
- Spring Data JPA
- H2 Database
- BCrypt (at.favre.lib)
- Maven
- Postman (para testes)

---

## 📦 Funcionalidades da API

### 🔐 Usuários (`User`)
- **Cadastro de usuário**
- **Armazenamento seguro de senha com BCrypt**
- **Autenticação básica via header Authorization**

### 📝 Informações Pessoais (`Info`)
- **Listagem de informações** (GET `/info/listar`)
- **Criação de nova informação** (POST `/info/criar`)
    - Requer autenticação via `Authorization: Basic`
- **Atualização de dados** (PUT `/info/atualizar`)
- **Remoção por ID** (DELETE `/info/deletar/{id}`)

---

## 📁 Estrutura do Projeto

```bash
src/
├── main/
│   ├── java/com/aula/projeto/
│   │   ├── info/
│   │   │   ├── InfoModel.java          # Modelo da tabela 'tb_info'
│   │   │   ├── InfoController.java     # Endpoints REST para Info
│   │   │   └── IInfoRepository.java    # Interface de acesso ao banco (JPA)
│   │   ├── user/
│   │   │   ├── UserModel.java          # Modelo da tabela 'tb_user'
│   │   │   └── IUserRepository.java    # Repositório JPA para usuários
│   │   └── FilterAuth.java             # Filtro personalizado para autenticação básica
│   └── resources/
│       ├── application.properties      # Configurações do banco e console H2
│       └── static/                     # HTML, CSS e JavaScript
├── test/                               # Testes automatizados (não utilizados)
```
---

## ⚙️ Configuração do Banco (H2)

# application.properties
```
spring.datasource.url=jdbc:h2:mem:spring
spring.datasource.username=admin
spring.datasource.password=admin
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update
```

---

## 🚀 Como Executar o Projeto

```bash
git clone https://github.com/seuusuario/seuprojeto.git
```

---

## 📬 Testes com Postman
 🔑 Autenticação
As requisições ao endpoint POST /info/criar exigem autenticação com usuário e senha registrados. Use o formato:
 No Postman:

- Aba Authorization
- Tipo: Basic Auth
- Username: seu usuário cadastrado
- Password: senha

---

## 🛠 Dependências do Projeto (pom.xml)

- spring-boot-starter-web: estrutura web RESTful
- spring-boot-starter-data-jpa: persistência com JPA e Hibernate
- h2: banco de dados leve em memória
- lombok: geração automática de getters/setters
- bcrypt: criptografia de senha
- spring-boot-starter-test: testes (não utilizado)

---

## 📚 Licença
Este projeto é destinado para fins acadêmicos e aprendizado.
Todos os direitos reservados a Filipi Mantelato Gonçalves e Lucas Leite Vieira.

## 👨‍🏫 Orientadora
- Profª Vanessa Felix Macedo
- Curso: Análise e Desenvolvimento de Sistemas – 4º Semestre
- Instituição: Universidade de Mogi das Cruzes
