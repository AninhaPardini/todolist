# Projeto TODO LIST em Java
by Rocketseat feat Daniela Leão

Neste projeto eu aprendi tudo sobre como funciona a base de uma API REST em Java e em como trabalhar os métodos das requisições e rotas de maneira fácil e intuitiva.

## Aula 1

Trabalhei os princípais conceitos para trabalhar com métodos e encaminhar rotas da aplicação com seus devidos atributos para recolhimento de dados de um user, criando o modelo do mesmo e criando a requisição com o api.http.

## Aula 2

Aprendi sobre como buscar informações dentro de uma classe com os métodos getters e setters.

## Getters e Setters
- Getters: Buscando informações de uma classe privada
  - getUsername
  ```java
    public String getUsername() {
      return username;
    }
  ```
- Setters: inserir um valor privado de uma classe
  - setUsername
  ```java
    public void setUsername(String username) {
      this.username = username;
    }
  ```

Após isso a professora mostrou uma lib para poder otimizar essa criação de getters e setters da minha aplicação chamada [Lombok](https://projectlombok.org/) onde apenas com um decorator @Data em cima do meu modelo, já tenho tudo configurado de getters e setters automáticamente.

## Banco de Dados 
Foi mostrado um ORM que é um projeto do própio springbot chamado [Spring Data JPA](https://spring.io/projects/spring-data-jpa) para podermos usar como camada de comunicação na nossa aplicação para o banco de dados. E o banco de dados que iremos utilizar nessa aplicação é o [H2 Database Engine](https://www.h2database.com/html/main.html) que é um banco de dados em memória, que não precisa de instalação alguma na aplicação, ele é apenas recomendado para a parte de development de um projeto, para prod é preciso um banco de dados em nuvem ou algo do tipo.

Com isso, fui em resources/application.properties e inserir as váriaveis de ambiente e suas configurações e consegui logar no console dentro de [http://localhost:8080/h2-console] onde posso ter acesso visualmente ao banco de dados e suas tabelas.

Para poder fazer a conversão do objeto para dentro do banco de dados, é usado o Repository como interface extendendo do JpaRepository que é uma classe vinda do Spring Bot.
Após isso configurei no UserController a interface UIUserRepository para ser o userRepository e armazenei o resultado do usuário salvo numa variável para retorna-lá quando o usuário fosse criado.

## Validando Dados

### - Username
  Usando o decorator @Column com atributo unique true toda vez que for criado um usuário com os mesmos dados ele retorna o erro 500. Como o erro 500 não é uma boa conduta para informar algum erro foi ensinado a fazer a verificação dentro do IUserRepository criando o metodo para buscar se há o mesmo username no banco de dados(findByUsername).
  Depois disto usei o ResponseEntity


## Aula 3

Nesta aula trabalhamos criptografar senhas dentro do banco de dados com a lib [bcripty](https://github.com/patrickfav/bcrypt).

Então começamos a trabalhar as tarefas criando uma pasta separada em todolist e um modelo e suas regras de negócio que serão:

- ID
- Usuário (ID_USUARIO)
- Descrição
- Título (limite de 50 caracteres)
- Status (ideia minha)
- Data de Início
- Data de término
- Data de última atualização
- Prioridade

### Filtro

Na aula a professora ensinou um conceito de filtro para delimitarmos acessos e restrições para criação de uma task, esse modelo exite dentro do springBot chamado OncePerRequestFilter e como no FilterTaskAuth segue uma interface que quando importado aponta para criar dando CTRL + .

### Validação dentro do filtro

O processo de validação começou desde a utilização do servletPath onde captamos o caminho utilizados e se caso for coincidente com o que queremos damos continuidade as verificações, isso é importante pois se não ele irá buscar em todas rotas.
Após isso tivemos que adaptar o que é recebido pelo auth, vindo de uma string com o nome basic e uma série de caracteres compactados que devemos descriptar para validação dos mesmos e depois atribuimos a um array chamado credentials e definimos as variantes como username index 0 e password index [1] assim como separamos.
Com isso feito, partimos com o auxilio do Bcripty para decodificar o password e conseguir verificar a senha para finalmente permitir ou não o usuário de cadastrar sua task.

### Depreciando o userId e injetando para mostrar dentro do auth



## Aula 4

Coming soon.

## Aula 5

Coming soon.