package vendaingressos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private  String nome;
    private String login;
    private String senha;
    private String cpf;
    private  String email;
    private boolean admin;

    private List<Ingresso> ingressos;

    public Usuario(String login, String senha,String nome,  String cpf, String email, boolean admin) {
        this.nome = nome;
        this.senha = senha;
        this.login = login;
        this.cpf = cpf;
        this.email = email;
        this.admin = admin;
        this.ingressos = new ArrayList<>();
    }

    @Override
    // sobescrita do metodo equals para se enquandar ao teste de UsuarioDuplicado
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Usuario usuario = (Usuario) obj;

        // Comparação dos atributos que definem igualdade
        return  login.equals(usuario.login) &&
                nome.equals(usuario.nome) &&
                cpf.equals(usuario.cpf) &&
                email.equals(usuario.email) &&
                admin == usuario.admin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, nome, cpf, email, admin);
    }

    public boolean login(String login, String senha ){
        return Objects.equals(getLogin(), login) && Objects.equals(getSenha(), senha);
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public void setIngressos(Ingresso ingressos) {
        this.ingressos.add(ingressos) ;
    }
}
