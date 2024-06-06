# Sistema de Biblioteca

Este projeto é um sistema gerenciador de biblioteca, desenvolvido como parte da disciplina de Linguagem de Programação II, oferecida no curso de Bacharelado em Tecnologia da Informação II na UFRN.

## Descrição
O sistema foi criado para gerenciar livros e usuários em uma biblioteca, permitindo operações como cadastro, remoção, pesquisa, empréstimo e devolução de livros. Este projeto visa aplicar conceitos avançados de programação orientada a objetos, como herança, interfaces, enumerações e padrões de projeto como Singleton.

## Requisitos Funcionais
- O sistema possui três tipos de usuários: Estudante, Professor e Bibliotecário.
- Apenas o Bibliotecário pode inserir, emprestar, devolver e excluir livros.
- Estudantes podem pegar até 3 livros por 15 dias.
- Professores e Bibliotecários podem pegar até 5 livros por 30 dias.
- Livros, usuários e empréstimos são persistidos em arquivos binários e recuperados ao iniciar o programa.
  
## Estrutura do Projeto
### Pacotes

- **model:** Contém as classes `Usuario`, `Estudante`, `Professor`, `Bibliotecario`, `Livro`, `Emprestimo` e a interface `LimitePrazoEmprestimo`.
    - Interface `LimitePrazoEmprestimo`: Define métodos para obter o limite de empréstimos e o prazo de empréstimo.
    - Classe `Usuario`: Classe abstrata que implementa a interface LimitePrazoEmprestimo e define os atributos comuns aos usuários.
    - Classes `Estudante`, `Professor`, `Bibliotecario`: Extendem Usuario e implementam os métodos da interface LimitePrazoEmprestimo.
    - Classe `Livro`: Representa um livro com seus atributos e estado.
    - Classe `Emprestimo`: Representa um empréstimo, contendo informações sobre o usuário, livro e datas de empréstimo e devolução.
    - Enum `EstadoLivro`: Enumeração para representar os estados de um livro (DISPONIVEL, EMPRESTADO).
  
- **dao:** Inclui a classe `BancoDAO`, que implementa o padrão Singleton para gerenciamento da coleção de livros, usuários e empréstimos.
- **service:** Contém a classe `Operacoes`, que oferece métodos para manipulação dos livros, usuários e empréstimos da biblioteca.
- **utils:** Contém a classe `ErroTratamento`, que oferece métodos estáticos para o tratamento de erros do sistema.
- **view:** Contém a classe principal `Main`, que implementa a interface do usuário.

### Funcionalidades
- **Cadastro de Livro:** Adiciona novos livros à biblioteca.
- **Remoção de Livro:** Remove um livro específico da biblioteca.
- **Cadastro de Usuário:** Adiciona novos usuários à biblioteca.
- **Remoção de Usuário:** Remove um usuário específico da biblioteca.
- **Empréstimo de Livro:** Empresta um livro a um usuário.
- **Devolução de Livro:** Devolve um livro emprestado.
- **Pesquisa de Livro por Título:** Busca por um livro pelo título.
- **Pesquisa de Livro por Autor:** Busca por livros de um autor específico.
- **Verificação da Situação do Usuário:** Exibe informações sobre os empréstimos de um usuário.
- **Exibição de Todos os Usuários:** Lista todos os usuários cadastrados na biblioteca.
- **Exibição de Todos os Livros:** Lista todos os livros cadastrados na biblioteca.


### Persistência de Dados
O sistema utiliza serialização para persistência de dados, garantindo que as informações sejam salvas e recuperadas entre as execuções do programa. Os dados são armazenados em arquivos binários, facilitando a gestão e manutenção dos registros de livros, usuários e empréstimos.

#### Gravação e Leitura de Arquivos
- Gravação de Dados:
Os dados dos livros, usuários e empréstimos são gravados em arquivos binários (.bin) usando o mecanismo de serialização do Java. A classe BancoDAO é responsável por gerenciar essa persistência, utilizando os métodos ObjectOutputStream para escrever objetos em arquivos.

- Leitura de Dados:
Na inicialização do sistema, os dados são lidos dos arquivos binários usando ObjectInputStream. Isso permite que o estado anterior do sistema seja restaurado, carregando os dados previamente salvos.

## Como Usar
1. Compilar o Projeto: Compile todas as classes do projeto.

2. Executar a Classe Principal: Execute a classe BibliotecaApp.

3. Tela de Login:

- Use as credenciais:
  - Login: admin
  - Senha: admin
- Caso a autenticação falhe, você será solicitado a tentar novamente até inserir as credenciais corretas.

## Uso do Sistema
Após a autenticação bem-sucedida, o menu principal será exibido com várias opções de funcionalidades. Selecione a opção desejada digitando o número correspondente e siga as instruções fornecidas pelo sistema.

## Tratamento de Erros
O sistema lida com situações comuns de erro, como:

- Tentativa de adicionar livros ou usuários duplicados.
- Tentativa de emprestar livros já emprestados.
- Usuário não encontrado.
- Livro não encontrado.
- Limite de empréstimos atingido.
- Entrada inválida.

## Licença
Este projeto é licenciado sob a Licença MIT - veja o arquivo LICENSE.md para detalhes.
