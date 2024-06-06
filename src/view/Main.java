package view;

import service.Operacoes;
import model.*;
import dao.BancoDAO;
import utils.ErroTratamento;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Operacoes operacoes = new Operacoes();
        ErroTratamento erroTratamento = new ErroTratamento();
        boolean autenticado = false;

        System.out.println("### Bem-vindo(a) ao SIS-BIB!");

        // Autenticação simplificada com loop até credenciais corretas
        while (!autenticado) {
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            if (login.equals("admin") && senha.equals("admin")) {
                autenticado = true;
                System.out.println("*** Autenticação bem-sucedida! ***");
            } else {
                System.out.println("\n*** Autenticação falhou. Tente novamente. ***\n");
            }
        }

        while (true) {
            System.out.println("\nEscolha uma operação:");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Remover Livro");
            System.out.println("3. Adicionar Usuário");
            System.out.println("4. Remover Usuário");
            System.out.println("5. Emprestar Livro");
            System.out.println("6. Devolver Livro");
            System.out.println("7. Pesquisar Livro por Título");
            System.out.println("8. Pesquisar Livro por Autor");
            System.out.println("9. Verificar Situação do Usuário");
            System.out.println("10. Exibir Todos os Usuários");
            System.out.println("11. Exibir Todos os Livros");
            System.out.println("0. Sair");
            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                erroTratamento.tratarErroEntradaInvalida();
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("Assunto: ");
                    String assunto = scanner.nextLine();
                    System.out.print("Ano de Lançamento: ");
                    int anoLancamento;
                    try {
                        anoLancamento = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        erroTratamento.tratarErroEntradaInvalida();
                        break;
                    }
                    System.out.print("Quantidade em Estoque: ");
                    int qtdEstoque;
                    try {
                        qtdEstoque = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        erroTratamento.tratarErroEntradaInvalida();
                        break;
                    }

                    Livro livro = new Livro(titulo, autor, assunto, anoLancamento, qtdEstoque);
                    operacoes.adicionarLivro(livro);
                    break;

                case 2:
                    System.out.print("Título do Livro a Remover: ");
                    String tituloRemover = scanner.nextLine();
                    List<Livro> livrosRemover = operacoes.pesquisarLivrosPorTitulo(tituloRemover);
                    if (!livrosRemover.isEmpty()) {
                        operacoes.removerLivro(livrosRemover.get(0));
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Matrícula: ");
                    String matricula = scanner.nextLine();
                    System.out.print("Data de Nascimento: ");
                    String dataNascimento = scanner.nextLine();

                    System.out.println("Tipo de Usuário (1- Estudante, 2- Professor, 3- Bibliotecário): ");
                    int tipoUsuario;
                    try {
                        tipoUsuario = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        erroTratamento.tratarErroEntradaInvalida();
                        break;
                    }

                    Usuario usuario = null;
                    if (tipoUsuario == 1) {
                        System.out.print("Curso: ");
                        String curso = scanner.nextLine();
                        usuario = new Estudante(nome, cpf, matricula, dataNascimento, curso);
                    } else if (tipoUsuario == 2) {
                        System.out.print("Departamento: ");
                        String departamento = scanner.nextLine();
                        usuario = new Professor(nome, cpf, matricula, dataNascimento, departamento);
                    } else if (tipoUsuario == 3) {
                        System.out.print("Login: ");
                        String loginUsuario = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senhaUsuario = scanner.nextLine();
                        usuario = new Bibliotecario(nome, cpf, matricula, dataNascimento, loginUsuario, senhaUsuario);
                    }

                    operacoes.adicionarUsuario(usuario);
                    break;

                case 4:
                    System.out.print("CPF do Usuário a Remover: ");
                    String cpfRemover = scanner.nextLine();
                    Usuario usuarioParaRemover = null;
                    for (Usuario u : BancoDAO.getInstance().buscarUsuarios()) {
                        if (u.getCpf().equals(cpfRemover)) {
                            usuarioParaRemover = u;
                            break;
                        }
                    }
                    if (usuarioParaRemover != null) {
                        operacoes.removerUsuario(usuarioParaRemover);
                    } else {
                        erroTratamento.tratarErroUsuarioNaoEncontrado();
                    }
                    break;

                case 5:
                    System.out.print("CPF do Usuário: ");
                    String cpfEmprestimo = scanner.nextLine();
                    System.out.print("Título do Livro: ");
                    String tituloEmprestimo = scanner.nextLine();

                    Usuario usuarioEmprestimo = null;
                    Livro livroEmprestimo = null;
                    for (Usuario u : BancoDAO.getInstance().buscarUsuarios()) {
                        if (u.getCpf().equals(cpfEmprestimo)) {
                            usuarioEmprestimo = u;
                            break;
                        }
                    }
                    if (usuarioEmprestimo == null) {
                        erroTratamento.tratarErroUsuarioNaoEncontrado();
                        break;
                    }

                    for (Livro l : operacoes.pesquisarLivrosPorTitulo(tituloEmprestimo)) {
                        livroEmprestimo = l;
                        break;
                    }

                    if (livroEmprestimo == null) {
                        erroTratamento.tratarErroLivroNaoEncontrado();
                        break;
                    }

                    operacoes.emprestarLivro(usuarioEmprestimo, livroEmprestimo);
                    break;

                case 6:
                    System.out.print("CPF do Usuário: ");
                    String cpfDevolucao = scanner.nextLine();
                    System.out.print("Título do Livro: ");
                    String tituloDevolucao = scanner.nextLine();

                    Usuario usuarioDevolucao = null;
                    Livro livroDevolucao = null;
                    for (Usuario u : BancoDAO.getInstance().buscarUsuarios()) {
                        if (u.getCpf().equals(cpfDevolucao)) {
                            usuarioDevolucao = u;
                            break;
                        }
                    }
                    if (usuarioDevolucao == null) {
                        erroTratamento.tratarErroUsuarioNaoEncontrado();
                        break;
                    }

                    for (Livro l : operacoes.pesquisarLivrosPorTitulo(tituloDevolucao)) {
                        livroDevolucao = l;
                        break;
                    }

                    if (livroDevolucao == null) {
                        erroTratamento.tratarErroLivroNaoEncontrado();
                        break;
                    }

                    operacoes.devolverLivro(usuarioDevolucao, livroDevolucao);
                    break;

                case 7:
                    System.out.print("Título do Livro: ");
                    String tituloPesquisa = scanner.nextLine();
                    List<Livro> livrosTitulo = operacoes.pesquisarLivrosPorTitulo(tituloPesquisa);
                    if (!livrosTitulo.isEmpty()) {
                        for (Livro l : livrosTitulo) {
                            System.out.println("Título: " + l.getTitulo() + ", Autor: " + l.getAutor());
                        }
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;

                case 8:
                    System.out.print("Autor do Livro: ");
                    String autorPesquisa = scanner.nextLine();
                    List<Livro> livrosAutor = operacoes.pesquisarLivrosPorAutor(autorPesquisa);
                    if (!livrosAutor.isEmpty()) {
                        for (Livro l : livrosAutor) {
                            System.out.println("Título: " + l.getTitulo() + ", Autor: " + l.getAutor() + ", Estoque: " + l.getQtdEstoque());
                        }
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;

                case 9:
                    System.out.print("CPF do Usuário: ");
                    String cpfSituacao = scanner.nextLine();
                    Usuario usuarioSituacao = null;
                    for (Usuario u : BancoDAO.getInstance().buscarUsuarios()) {
                        if (u.getCpf().equals(cpfSituacao)) {
                            usuarioSituacao = u;
                            break;
                        }
                    }
                    if (usuarioSituacao != null) {
                        operacoes.verificarSituacaoUsuario(usuarioSituacao);
                    } else {
                        erroTratamento.tratarErroUsuarioNaoEncontrado();
                    }
                    break;

                case 10:
                    operacoes.exibirTodosUsuarios();
                    break;

                case 11:
                    operacoes.exibirTodosLivros();
                    break;

                case 0:
                    System.out.println("SIS-BIB está sendo encerrado..");
                    scanner.close();
                    return;

                default:
                    erroTratamento.tratarErroEntradaInvalida();
                    break;
            }
        }
    }
}