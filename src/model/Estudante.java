package model;

import java.time.LocalDate;

public class Estudantes extends Usuario {
    private String curso;

    public Estudantes() {
    }

    public Estudantes(String nome, String cpf, String matricula, LocalDate dataNascimento) {
        super(nome, cpf, matricula, dataNascimento);
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public int getLimiteEmprestimos() {
        return 3;
    }

    @Override
    public int getPrazoEmprestimo() {
        return 15;
    }
}
