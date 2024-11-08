package models;
import java.util.UUID;

import java.util.Objects;

public class  Usuario {
    private String id;
    private  String nome;
    private String login;
    private String senha;
    private String cpf;
    private  String email;

    public Usuario(String login, String senha,String nome,  String cpf, String email) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        setSenha(senha);
        this.login = login;
        this.cpf = cpf;
        setEmail(email);
    }

    public boolean login(String login, String senha ){
        return Objects.equals(getLogin(), login) && Objects.equals(getSenha(), senha);
    }

    private boolean isEmailValido(String email) {
        return email.contains("@") && email.contains(".");
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        if (!(senha.length() >= 6)){
            throw new IllegalArgumentException();
        }
        this.senha = senha;
    }

    public void setEmail(String email) {
        if(!isEmailValido(email)){
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }
    public  String getCpf(){
        return this.cpf;
    }
}

