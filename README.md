# CRUD(Create, Read, Update, Delete) de Aluno

# Tecnologias
- Java (Versão 17)
- Maven
- Lombok
- JDBC
- JUnit 
- Banco de dados PostgreSQL
- Docker
- Jacoco

# Sobre o projeto

Esta aplicação mostra como realizar um CRUD de usuário, também pode servir como base de estudo. O usuário pode ser aluno, professor ou adminstrador. 

O banco de dados que foi utilizado é o PostgreSQL, dentro do projeto tem um arquivo de docker compose, ao rodar este arquivo, é criado o contêiner desse banco de dados.

Para criar o contêiner basta entrar na pasta **/docker-compose-dev** do projeto e digitar o comando **docker-compose up -d**, mas para funcionar tem que ter o docker instalado na máquina. 

Para criar as tabelas do ambiente teste e dev é necessário pegar os dados do arquivo **Criacao_Tabelas_Test** e **Criacao_Tabelas_Dev**. Esses dois arquivos é o script das criação de tabelas. 

# Modelo Conceitual

![Modelo Conceitual](https://github.com/zGabrielZ/assets/blob/main/Cadastro%20Aluno%20JDBC/modelo-conceitual.png)
