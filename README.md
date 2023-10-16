# Projeto TODO LIST em Java
by Rocketseat feat Daniela Leão

Neste projeto eu aprendi tudo sobre como funciona a base de uma API REST em Java e em como trabalhar os métodos das requisições e rotas de maneira fácil e intuitiva.

## Aula 1

Trabalhei os princípais conceitos para trabalhar com métodos e encaminhar rotas da aplicação com seus devidos atributos para recolhimento de dados de um user, criando o modelo do mesmo e criando a requisição com o api.http.

## Aula 2

Aprendi sobre como buscar informações dentro de uma classe com os métodos getters e setters.

### Getters e Setters
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

### Banco de Dados 
Foi mostrado um ORM que é um projeto do própio springbot chamado [Spring Data JPA](https://spring.io/projects/spring-data-jpa) para podermos usar como camada de comunicação na nossa aplicação para o banco de dados. E o banco de dados que iremos utilizar nessa aplicação é o [H2 Database Engine](https://www.h2database.com/html/main.html) que é um banco de dados em memória, que não precisa de instalação alguma na aplicação, ele é apenas recomendado para a parte de development de um projeto, para prod é preciso um banco de dados em nuvem ou algo do tipo.

Com isso, fui em resources/application.properties e inserir as váriaveis de ambiente e suas configurações e consegui logar no console dentro de [http://localhost:8080/h2-console] onde posso ter acesso visualmente ao banco de dados e suas tabelas.

Para poder fazer a conversão do objeto para dentro do banco de dados, é usado o Repository como interface extendendo do JpaRepository que é uma classe vinda do Spring Bot.
Após isso configurei no UserController a interface UIUserRepository para ser o userRepository e armazenei o resultado do usuário salvo numa variável para retorna-lá quando o usuário fosse criado.

### Validando Dados

 - Username
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

## Aula 4

### Depreciando o userId e injetando para mostrar dentro do auth

Nesta fase dentro do TaskController e Filter configuramos um atributo ao auth com o nome de userId e recuperamos ele dentro do controller atribuindo-o como o get.userId e assim podendo o userId ser passado dentro do auth e não no body da requisição.

### Validação das horas
  
  - StartAt
    Com a validação de horas usamos a data atual dentro de uma variavel para checar se o startAt é depois desta.
  - EndAt
    Fazemos o uso do pipe e utilizamos a mesma lógica
  - Sentido entre as datas
    Também validamos se a data de inicio é menor que a de término para ter sentido a criação da task.

### Listando todas as tarefas

Para listar todas as tarefas apenas captamos o id do usuário dentro do auth e retornamos as tasks linkadas ao mesmo utilizando do método findByUserId.

### Fazendo o update das tarefas

Com o put foi usado o parâmetro do @PathVariable que pega o id da task e linka a rota apresentada. Além disso tivemos que fazer algumas validações se a tarefa existe, depois captamos o id do usuário para permitir ou não que ele edite aquela tarefa, baseado que só o usuário dono da tarefa pode altera-lá.

Com isso a professora apresentou uma forma de maximizar nosso trabalho evitando o uso intenso de ifs a todo canto, criando o utils pudemos usar o Beans e o Util do própio java e spring bot para checar os campos nulos da requisição e copiar os que não foram alterados, tornando facíl a edição pelo lado do usuário.

## Aula 5

### Tratando erros

A professora mostrou uma forma mais indicada que é configurando um handler que irá pegar o throw exception error e irá enviar para o body do usuário de forma mais amigável com um texto explicando o motivo do erro.

### Dependência Devtools 

O [devtools](https://www.baeldung.com/spring-boot-devtools) é um conjunto de ferramentas para facilitar o desenvolvimento dentro do spring bot/java. Ela instruiu a tirar a versão e deixar a que foi colocada no início do projeto também e colocar o optional como true.

✨ mvn spring-boot:run para dar start mais fácil na aplicação

## Regras de negócio

O gerenciador de tarefas deve conter um método para cadastrar usuários onde nele:
- Não se pode ter dois usuários com o mesmo nick
- A senha deve ser criptografada

Também deve ter tarefas onde:
- O título não pode conter mais de 50 caracteres
- A tarefa deve ser atrelada e somente editada pelo usuário que a criou
- A tarefa deve conter os seguintes campos:
- - Id
  - Título
  - Descrição
  - Status de conclusão
    - A FAZER
    - EM ANDAMENTO
    - FEITO
  - Data de Início
  - Data de término
  - Data da útlima atualização
  - Prioridade
- A tarefa deve poder ser:
  - Criada
  - Editada
  - Deletada - Feito fora do curso
  - Encontrada (todas)
  - Encontrada indivídualmente - Feito fora do curso
  - Encontrada por status
  - Encontrada por prioridade


## Considerações finais

Adorei conhecer Java e com certeza esse foi um dos cursos que mais empenhei tanto no estudo quanto em armazenar meu aprendizado e me dedicar para fazer além quanto no código em si. Adorei a oportunidade que a Rocketseat deu de podermos aprender sobre Java e ter um gostinho de como tudo funciona, é algo que vou ser eternamente grata. #FogueteNãoTemRé 🚀
