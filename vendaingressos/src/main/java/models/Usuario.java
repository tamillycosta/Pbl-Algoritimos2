package models;
import java.util.UUID;

import java.util.Objects;

public class  Usuario {
    private String id;
    private String login;
    private String senha;
    private String cpf;
    private  String email;

    /**
     *
     * @param login
     * @param senha
     * @param cpf
     * @param email
     */
    public Usuario(String login, String senha, String cpf, String email) {
        this.id = UUID.randomUUID().toString();

        setSenha(senha);
        this.login = login;
        this.cpf = cpf;
        setEmail(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(login, usuario.login) && Objects.equals(cpf, usuario.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, cpf);
    }


    /**
     * Realiza o login comparando o login e a senha fornecidos com os valores armazenados.
     *
     * @param login o login fornecido
     * @param senha a senha fornecida
     * @return {@code true} se o login e a senha coincidirem, caso contrário, {@code false}
     */
    public boolean login(String login, String senha ){
        return Objects.equals(getLogin(), login) && Objects.equals(getSenha(), senha);
    }


    /**
     * Verifica se o e-mail fornecido é válido. Um e-mail é considerado válido se contiver "@" e ".".
     *
     * @param email o e-mail a ser verificado
     * @return {@code true} se o e-mail for válido, caso contrário, {@code false}
     */
    public static boolean isEmailValido(String email) {
        return email.contains("@") && email.contains(".");
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Define a senha do usuário. A senha deve ter no mínimo 6 caracteres.
     *
     * @param senha a nova senha
     * @throws IllegalArgumentException se a senha tiver menos de 6 caracteres
     */
    public void setSenha(String senha) {
        if (!(senha.length() >= 6)){
            throw new IllegalArgumentException();
        }
        this.senha = senha;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        if(!isEmailValido(email)){
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
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

    public String getId() {
        return id;
    }

}

