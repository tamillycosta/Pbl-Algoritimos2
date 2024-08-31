package vendaingressos;

import java.util.Date;
import java.util.List;

public class Controller {

    private  List<Usuario> usuarios;
    private  List<Evento> eventos;

    public Usuario cadastrarUsuario(String login, String senha,String nome,  String cpf, String email, boolean admin){
        return  new Usuario(login,senha,nome,cpf,email, admin);

    }

    public Evento cadastrarEvento(Usuario usuario, String nome, String descricao, Date date){

    }


}
