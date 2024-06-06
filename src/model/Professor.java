package model;

import java.time.LocalDate;
import java.util.Date;

public class Professor  extends Usuario{
    private String departamento;

    public Professor() {
    }

    public Professor(String nome, String cpf, String matricula, String dataNascimento, String departamento) {
        super(nome, cpf, matricula, dataNascimento);
        this.departamento = departamento;
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
