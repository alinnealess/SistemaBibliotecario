package dao;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDAO {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimosAtivos;
    private static BancoDAO instance;

    private BancoDAO() {
        livros = new ArrayList<>();
        usuarios = new ArrayList<>();
        emprestimosAtivos = new ArrayList<>();
        carregarDados();
    }

    public static BancoDAO getInstance() {
        if (instance == null) {
            instance = new BancoDAO();
        }
        return instance;
    }

    // Métodos para adicionar livros, usuários e empréstimos
    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        salvarDados();
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        salvarDados();
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        emprestimosAtivos.add(emprestimo);
        salvarDados();
    }

    // Métodos para remover livros, usuários e empréstimos
    public void removerLivro(Livro livro) {
        livros.remove(livro);
        salvarDados();
    }

    public void removerUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        salvarDados();
    }

    public void removerEmprestimo(Emprestimo emprestimo) {
        emprestimosAtivos.remove(emprestimo);
        salvarDados();
    }

    // Métodos para buscar livros, usuários e empréstimos
    public List<Livro> buscarLivros() {
        return new ArrayList<>(livros);
    }

    public List<Usuario> buscarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Emprestimo> buscarEmprestimosAtivos() {
        return new ArrayList<>(emprestimosAtivos);
    }

    // Método para salvar dados em arquivos binários
    private void salvarDados() {
        try (ObjectOutputStream oosLivros = new ObjectOutputStream(new FileOutputStream("livros.bin"));
             ObjectOutputStream oosUsuarios = new ObjectOutputStream(new FileOutputStream("usuarios.bin"));
             ObjectOutputStream oosEmprestimos = new ObjectOutputStream(new FileOutputStream("emprestimos.bin"))) {

            oosLivros.writeObject(livros);
            oosUsuarios.writeObject(usuarios);
            oosEmprestimos.writeObject(emprestimosAtivos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar dados de arquivos binários
    private void carregarDados() {
        try (ObjectInputStream oisLivros = new ObjectInputStream(new FileInputStream("livros.bin"));
             ObjectInputStream oisUsuarios = new ObjectInputStream(new FileInputStream("usuarios.bin"));
             ObjectInputStream oisEmprestimos = new ObjectInputStream(new FileInputStream("emprestimos.bin"))) {

            livros = (ArrayList<Livro>) oisLivros.readObject();
            usuarios = (ArrayList<Usuario>) oisUsuarios.readObject();
            emprestimosAtivos = (ArrayList<Emprestimo>) oisEmprestimos.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Se os arquivos não existirem ou estiverem vazios, inicializar listas vazias
            livros = new ArrayList<>();
            usuarios = new ArrayList<>();
            emprestimosAtivos = new ArrayList<>();
        }
    }
}
