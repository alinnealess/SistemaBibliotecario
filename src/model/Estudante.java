package model;

import java.time.LocalDate;
import java.util.Date;

public class Estudante extends Usuario {
    private String curso;

    public Estudante() {
    }

    public Estudante(String nome, String cpf, String matricula, String dataNascimento, String curso) {
        super(nome, cpf, matricula, dataNascimento);
        this.curso = curso;
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
