# API REST com Spring Boot - Sistema Skill

## Sobre

Este é um projeto de backend em Java utilizando Spring Boot para criação de uma API REST. O objetivo da API é fornecer serviços para autenticação de usuários, cadastro de usuários, gerenciamento de skills e associação de skills a usuários.

## Funcionalidades

1. **Serviço de Login**
   - Recebe login e senha, verifica se correspondem aos dados na base de dados.
   - Criptografa a senha.
   - Retorna um token JWT para acesso aos demais serviços.

2. **Serviço de Cadastro**
   - Recebe login e senha para cadastrar na base de dados.
   - Criptografa a senha antes de armazenar.

3. **Serviço de Listagem de Skills**
   - Recebe o ID do usuário e retorna todas as skills associadas a ele, com seus respectivos níveis (levels).

4. **Serviço de Associar Skill**
   - Recebe o ID do usuário, a skill e o level para persistir na base de dados.

5. **Serviço de Atualizar Associação de Skill**
   - Recebe o ID da associação da skill e o novo level para atualização na base de dados.

6. **Serviço de Excluir Associação de Skill**
   - Recebe o ID da associação da skill e exclui da base de dados.

7. **Segurança JWT**
   - Apenas o Serviço de Login é público. Os demais serviços requerem um token JWT válido para acesso.

8. **Documentação Swagger**
   - O projeto utiliza o Spring Fox para gerar automaticamente a documentação dos serviços via Swagger.

## Tecnologias Utilizadas

- Java
- Spring Boot
- JWT (JSON Web Tokens)
- Spring Fox (Swagger)

## Instalação e Uso

1. Clone o repositório:
git clone https://github.com/vs-costa/backend-sistemaSkill

2. Importe o projeto em sua IDE preferida.

3. Configure a conexão com a base de dados no arquivo `application.properties`.

4. Execute o projeto.

5. Acesse a documentação da API pelo Swagger em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Licença

Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).
