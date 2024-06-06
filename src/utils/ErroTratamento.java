package utils;

public class ErroTratamento {

    public void tratarErroLivroDuplicado() {
        System.out.println("Erro: O livro já existe no sistema.");
    }

    public void tratarErroLivroEmprestado() {
        System.out.println("Erro: O livro já está emprestado.");
    }

    public void tratarErroUsuarioNaoEncontrado() {
        System.out.println("Erro: Usuário não encontrado.");
    }

    public void tratarErroLivroNaoEncontrado() {
        System.out.println("Erro: Livro não encontrado.");
    }

    public void tratarErroEntradaInvalida() {
        System.out.println("Erro: Entrada inválida. Por favor, tente novamente.");
    }

    public void tratarErroLimiteEmprestimoAtingido() {
        System.out.println("Erro: Usuário atingiu o limite máximo de empréstimos.");
    }

    public void tratarErroUsuarioExistente() {
        System.out.println("Erro: Usuário já existente. Verifique o CPF e tente novamente.");
    }
}
