package model;

import java.time.LocalDate;

public class Professores  extends Usuario{
    private String departamento;

    public Professores() {
    }

    public Professores(String nome, String cpf, String matricula, LocalDate dataNascimento) {
        super(nome, cpf, matricula, dataNascimento);
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
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
