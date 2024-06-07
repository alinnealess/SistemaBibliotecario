package controller;

import model.*;
import dao.BancoDAO;
import utils.ErroTratamento;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Operacoes {
    private BancoDAO bancoDAO = BancoDAO.getInstance();
    private ErroTratamento erroTratamento = new ErroTratamento();

    // Método para adicionar livro
    public void adicionarLivro(Livro livro) {
        List<Livro> livros = bancoDAO.buscarLivros();
        for (Livro l : livros) {
            if (l.getTitulo().equalsIgnoreCase(livro.getTitulo()) && l.getAutor().equalsIgnoreCase(livro.getAutor())) {
                erroTratamento.tratarErroLivroDuplicado();
                return;
            }
        }
        bancoDAO.adicionarLivro(livro);
        System.out.println("Livro adicionado com sucesso!");
    }

    // Método para remover livro
    public void removerLivro(Livro livro) {
        bancoDAO.removerLivro(livro);
        System.out.println("Livro removido com sucesso!");
    }

    // Método para adicionar usuário
    public void adicionarUsuario(Usuario usuario) {
        List<Usuario> usuarios = bancoDAO.buscarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(usuario.getCpf())) {
                erroTratamento.tratarErroUsuarioExistente();
                return;
            }
        }
        bancoDAO.adicionarUsuario(usuario);
        System.out.println("Usuário adicionado com sucesso!");
    }

    // Método para remover usuário
    public void removerUsuario(Usuario usuario) {
        bancoDAO.removerUsuario(usuario);
        System.out.println("Usuário removido com sucesso!");
    }

    // Método para emprestar livro
    public void emprestarLivro(Usuario usuario, Livro livro) {
        List<Emprestimo> emprestimosAtivos = bancoDAO.buscarEmprestimosAtivos();

        long count = 0;
        for (Emprestimo e : emprestimosAtivos) {
            if (e.getUsuario().getCpf().equals(usuario.getCpf())) {
                count++;
            }
        }

        if (usuario instanceof Estudante && count >= 3 || usuario instanceof Professor && count >= 5 || usuario instanceof Bibliotecario && count >= 5) {
            erroTratamento.tratarErroLimiteEmprestimoAtingido();
            return;
        }

        if (livro.getEstado() == EstadoLivro.EMPRESTADO) {
            erroTratamento.tratarErroLivroEmprestado();
            return;
        }

        Date dataEmprestimo = new Date();
        Date dataDevolucaoPrevista = new Date(dataEmprestimo.getTime() + (usuario instanceof Estudante ? 15L : 30L) * 24 * 60 * 60 * 1000);

        Emprestimo emprestimo = new Emprestimo(usuario, livro, dataEmprestimo, dataDevolucaoPrevista);
        bancoDAO.adicionarEmprestimo(emprestimo);

        livro.setEstado(EstadoLivro.EMPRESTADO);
        bancoDAO.adicionarLivro(livro);  // Atualizar livro com estado alterado

        System.out.println("Livro emprestado com sucesso!");
    }

    // Método para devolver livro
    public void devolverLivro(Usuario usuario, Livro livro) {
        List<Emprestimo> emprestimosAtivos = bancoDAO.buscarEmprestimosAtivos();

        Emprestimo emprestimoParaDevolver = null;
        for (Emprestimo e : emprestimosAtivos) {
            if (e.getUsuario().getCpf().equals(usuario.getCpf()) && e.getLivro().getTitulo().equalsIgnoreCase(livro.getTitulo())) {
                emprestimoParaDevolver = e;
                break;
            }
        }

        if (emprestimoParaDevolver != null) {
            bancoDAO.removerEmprestimo(emprestimoParaDevolver);
            livro.setEstado(EstadoLivro.DISPONIVEL);
            bancoDAO.adicionarLivro(livro);  // Atualizar livro com estado alterado
            System.out.println("Livro devolvido com sucesso!");
        } else {
            erroTratamento.tratarErroLivroNaoEncontrado();
        }
    }

    // Método para pesquisar livros por título
    public List<Livro> pesquisarLivrosPorTitulo(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : bancoDAO.buscarLivros()) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    // Método para pesquisar livros por autor
    public List<Livro> pesquisarLivrosPorAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : bancoDAO.buscarLivros()) {
            if (livro.getAutor().equalsIgnoreCase(autor)) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    // Método para verificar situação do usuário
    public void verificarSituacaoUsuario(Usuario usuario) {
        List<Emprestimo> emprestimosAtivos = bancoDAO.buscarEmprestimosAtivos();
        if (usuario == null) {
            erroTratamento.tratarErroUsuarioNaoEncontrado();
            return;
        }

        System.out.println("Usuário: " + usuario.getNome() + ";");
        System.out.println("Limite de empréstimos: " + usuario.getLimiteEmprestimos()+ ";");
        System.out.println("Prazo de empréstimo: " + usuario.getPrazoEmprestimo() + " dias;");
        System.out.println("Empréstimos ativos:");

        boolean emprestimosEncontrados = false;
        for (Emprestimo emprestimo : emprestimosAtivos) {
            if (emprestimo.getUsuario().getCpf().equals(usuario.getCpf())) {
                emprestimosEncontrados = true;
                System.out.println("Livro: " + emprestimo.getLivro().getTitulo() + ", Data de devolução prevista: " + emprestimo.getDataDevolucaoPrevista());
            }
        }
        if (!emprestimosEncontrados) {
            System.out.println("Nenhum empréstimo realizado pelo(a) usuário(a) até o momento.");
        }
    }

    // Método para exibir todos os usuários
    public void exibirTodosUsuarios() {
        List<Usuario> usuarios = bancoDAO.buscarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Usuario usuario : usuarios) {
                String tipoUsuario = "Desconhecido";
                if (usuario instanceof Estudante) {
                    tipoUsuario = "Estudante";
                } else if (usuario instanceof Professor) {
                    tipoUsuario = "Professor";
                } else if (usuario instanceof Bibliotecario) {
                    tipoUsuario = "Bibliotecário";
                }

                System.out.println("Nome: " + usuario.getNome() +
                        ", CPF: " + usuario.getCpf() +
                        ", Matrícula: " + usuario.getMatricula() +
                        ", Data de Nascimento: " + usuario.getDataNascimento() +
                        ", Tipo: " + tipoUsuario);
            }
        }
    }

    // Método para exibir todos os livros
    public void exibirTodosLivros() {
        List<Livro> livros = bancoDAO.buscarLivros();
        for (Livro livro : livros) {
            System.out.println("Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() + ", Assunto: " + livro.getAssunto() + ", Ano de Lançamento: " + livro.getAnoLancamento() + ", Quantidade em Estoque: " + livro.getQtdEstoque() + ".");
        }
    }
}
