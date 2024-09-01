package vendaingressos;

import java.util.*;

public class Controller {

    private Map<String, Evento> eventos;
    private List<Evento> listaEventos ;

    public Controller() {
        this.eventos = new HashMap<>();
        this.listaEventos = new ArrayList<>();
    }

    // Data limite a partir da qual eventos podem ser cadastrados
    private static final Date DATA_LIMITE = Evento.dataLimite;


    public Usuario cadastrarUsuario(String login, String senha,String nome,  String cpf, String email, boolean admin){
        return  new Usuario(login,senha,nome,cpf,email, admin);

    }

    public Evento cadastrarEvento(Usuario usuario, String nome, String descricao, Date date){
        if (date.before(DATA_LIMITE)) {
            throw new IllegalArgumentException("A data do evento deve ser a partir de " + DATA_LIMITE + ".");
        }
        if (!usuario.isAdmin()) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        eventos.put(nome, new Evento(nome, descricao, date));
        Evento evento =  new Evento(nome, descricao, date);
        listaEventos.add(evento);
        return  evento;

    }

    public void adicionarAssentoEvento(String nomeEvento, String assento){
        Evento evento = eventos.get(nomeEvento);
        if(evento == null){
            throw new IllegalArgumentException("Evento não encontrado.");
        }

        evento.adicionarAssento(assento);
    }

    public Ingresso comprarIngresso(Usuario usuario, String nomeEvento, String assento){
        Evento evento = eventos.get(nomeEvento);
        if(!evento.getAssentosDisponiveis().contains(assento)){
           return null;
        }
        evento.removerAssento(assento);
        Ingresso ingresso = new Ingresso(evento,0,assento);
        usuario.setIngressos(ingresso);
        return  ingresso;
    }

   public boolean cancelarCompra(Usuario usuario, Ingresso ingresso){
        if(!usuario.getIngressos().contains(ingresso)){
          return  false;
        }
       ingresso.cancelar();
       usuario.getIngressos().remove(ingresso);
        return true;
   }

   public List<Evento> listarEventosDisponiveis(){
       return this.listaEventos;
   }

   public List<Ingresso> listarIngressosComprados(Usuario usuario){
       return usuario.getIngressos();
   }

}
