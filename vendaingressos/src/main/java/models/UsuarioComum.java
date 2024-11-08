package models;

import vendaingressos.Ingresso;

import java.util.ArrayList;
import java.util.List;

public class UsuarioComum  extends Usuario{

    private List<Ingresso> ingressos;

    public UsuarioComum(String login, String senha, String nome, String cpf, String email) {
        super(login, senha, nome, cpf, email);
        this.ingressos = new ArrayList<>();
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    public UsuarioComum alterarPerfil(String login, String senha, String nome, String email){
        // no momento deixa assim, mas depois usar classes de validação de dados
        setEmail(email);
        setLogin(login);
        setNome(nome);
        setSenha(senha);

        return  this;
    }


}
