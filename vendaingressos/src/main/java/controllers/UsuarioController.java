package controllers;
import models.*;
import repository.UserRepository;
import models.UsuarioComum;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;


public class UsuarioController {

    private final UserRepository userRepository;
    private final EventoController eventoController;

    /***
     *
     * @param userRepository
     * @param eventoController
     */
    public UsuarioController(UserRepository userRepository, EventoController eventoController) {
        this.userRepository = userRepository;
        this.eventoController = eventoController;
    }


    /**
     * Cadastra um novo usuário comum no sistema.
     * Verifica se o e-mail é válido e se já existe um usuário com o mesmo login no repositório.
     * Se essas condições forem satisfeitas, o novo usuário é salvo no repositório.
     *
     * @param login o login do novo usuário
     * @param senha a senha do novo usuário
     * @param cpf o CPF do novo usuário
     * @param email o e-mail do novo usuário
     * @return o objeto {@link UsuarioComum} recém-criado
     * @throws IllegalArgumentException se o e-mail for inválido ou se já houver um usuário com o mesmo login
     */
    public UsuarioComum cadastrarUsuario(String login, String senha, String cpf, String email){
        UsuarioComum usuario = new UsuarioComum(login,senha,cpf,email);
       if (!UsuarioComum.isEmailValido(email)  || userRepository.buscarPorId(usuario) != null){
           throw new IllegalArgumentException("Já existe um usuario com esse login.");
       }
       userRepository.salvar(usuario);
       return  usuario;
    }


    /**
     * Edita os dados de um usuário comum existente.
     * Verifica se o login do usuário existe no repositório e, em seguida, permite alterar o e-mail e a senha.
     *
     * @param email o novo e-mail do usuário, se for informado
     * @param senha a nova senha do usuário, se for informada
     * @param usuarioComum o objeto {@link UsuarioComum} a ser editado
     * @return o objeto {@link UsuarioComum} atualizado
     * @throws FileNotFoundException se o login do usuário não for encontrado
     */
    public UsuarioComum editarUsuario(String email, String senha, UsuarioComum usuarioComum) throws FileNotFoundException {
        if (userRepository.buscarLogin(usuarioComum.getLogin())) {
            usuarioComum.login(usuarioComum.getLogin(), usuarioComum.getSenha());

            if (!email.isEmpty()) {
                usuarioComum.setEmail(email);
            }
            if (!senha.isEmpty()) {
                usuarioComum.setSenha(senha);
            }
        }
        userRepository.editarEntidade(usuarioComum);
        return usuarioComum;
    }


    /**
     * Lista os ingressos comprados por um usuário comum.
     * Busca o usuário no repositório e retorna a lista de ingressos associados a ele.
     *
     * @param usuario o objeto {@link UsuarioComum} cujo histórico de ingressos será consultado
     * @return uma lista de objetos {@link Ingresso} comprados pelo usuário
     */
    public  List<Ingresso> listarIngressosComprados(UsuarioComum usuario){
        UsuarioComum usuarioEncontrado = userRepository.buscarPorId(usuario);
        return  usuarioEncontrado.getIngressos();
    }


    /**
     * Permite que um usuário comente um evento após verificar a compra de ingresso para o evento.
     * Se o usuário comprou ingresso para o evento, o comentário é registrado.
     *
     * @param evento o objeto {@link Evento} que será comentado
     * @param usuario o objeto {@link UsuarioComum} que deseja comentar o evento
     * @param avaliacao a avaliação feita pelo usuário sobre o evento
     * @return {@code true} se o comentário foi registrado com sucesso, {@code false} caso contrário
     * @throws FileNotFoundException se o login do usuário não for encontrado
     */
    public boolean comentarEvento(Evento evento, UsuarioComum usuario, Avaliacao avaliacao) throws FileNotFoundException {
       if(userRepository.buscarLogin(usuario.getLogin())){
          for (Ingresso ingresso : usuario.getIngressos()) {
             if(Objects.equals(ingresso.getEvento().getNome(), evento.getNome())){
                eventoController.receberComentariosEvento(avaliacao, evento);
                return  true;
             }
          }
       }
    return  false;
    }

}