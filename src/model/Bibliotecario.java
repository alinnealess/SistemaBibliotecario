package model;

import java.time.LocalDate;
import java.util.Date;

public class Bibliotecario extends Usuario{
    private String login;
    private String senha;

    public Bibliotecario() {
    }

    public Bibliotecario(String nome, String cpf, String matricula, String dataNascimento, String login, String senha) {
        super(nome, cpf, matricula, dataNascimento);
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public int getLimiteEmprestimos() {
        return 5;
    }

    @Override
    public int getPrazoEmprestimo() {
        return 30;
    }
}
