package controllerTest;

import controllers.AdminController;
import controllers.EventoController;
import controllers.GerenciadorCompraController;
import controllers.UsuarioController;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AdminRepository;
import repository.EventoRepository;
import repository.NotaFiscalRepository;
import repository.UserRepository;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.Assert.*;

public class UsuarioControllerTest {

    private UserRepository userRepository;
    private UsuarioController usuarioController;
    private NotaFiscalRepository notaFiscalRepository;
    private EventoController eventoController;
    private  EventoRepository eventoRepository;
    private  AdminController adminController;
    private AdminRepository adminRepository;
    private GerenciadorCompraController gerenciadorCompraController;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
        eventoRepository = new EventoRepository();
        adminRepository = new AdminRepository();
        notaFiscalRepository = new NotaFiscalRepository();
        eventoController = new EventoController(eventoRepository);
        usuarioController = new UsuarioController(userRepository, eventoController);

        adminController = new AdminController(eventoRepository,adminRepository);


        gerenciadorCompraController = new GerenciadorCompraController(eventoController, eventoRepository, userRepository, notaFiscalRepository);

        try {
            Files.createDirectories(Paths.get(UserRepository.diretorioBase));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterEach
    public void tearDown() {
        try {
            // Caminho do diretório onde os arquivos e pastas são salvos
            Path diretorio = Paths.get(UserRepository.diretorioBase);

            // Remove todos os arquivos e pastas dentro do diretório
            Files.walk(diretorio)
                    .sorted(Comparator.reverseOrder())  // Ordena em ordem reversa para excluir arquivos antes das pastas
                    .forEach(caminho -> {
                        try {
                            Files.delete(caminho);  // Exclui cada arquivo ou pasta
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void EditarUsuarioTest() throws FileNotFoundException {
        UsuarioComum usuario = usuarioController.cadastrarUsuario("login", "senha123467", "cpf", "email@gmail.com");
        Assertions.assertNotNull(usuario, "Usuário não foi cadastrado");

        usuarioController.editarUsuario("novoemail@gmail.com", "novasenha", usuario);
        UsuarioComum usuarioAtualizado = userRepository.buscarPorId(usuario);


        assertNotNull("Usuário atualizado não deve ser null", usuarioAtualizado);
        Assertions.assertEquals("novoemail@gmail.com", usuarioAtualizado.getEmail());
        Assertions.assertEquals("novasenha", usuarioAtualizado.getSenha());
    }



    @Test
    public void ListarIngressoCompradosUsuarioTest() throws FileNotFoundException, IllegalArgumentException {
        LocalDate data = LocalDate.now().plusDays(10);

        // cria o evento
        Adimin adimin =  adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento evento = adminController.cadastrarEvento(adimin,"Novo evento", "evento de ano novo", data, 1000);
        Assertions.assertNotNull(evento, "Evento não deve ser null");

        //cria o usuario
        UsuarioComum usuario = usuarioController.cadastrarUsuario("Meulogin", "senha123467", "cpf", "email@gmail.com");
        UsuarioComum usuarioEncontrado = userRepository.buscarPorId(usuario);
        Assertions.assertNotNull(usuarioEncontrado, "Usuário não foi cadastrado");
        CartaoCredito metodoPagamento = new CartaoCredito("11111111");

        // Realiza a venda do ingresso
        gerenciadorCompraController.comprarIngresso(usuarioEncontrado, evento, 2, metodoPagamento);

        Assertions.assertEquals(2, usuarioController.listarIngressosComprados(usuario).size());
    }



    @Test
    public  void ComentarEventoTest() throws FileNotFoundException , IllegalArgumentException{
        LocalDate data = LocalDate.now().plusDays(10);
        Adimin adimin = adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento evento = adminController.cadastrarEvento(adimin,"Novo evento", "evento de ano novo", data, 1000);

        UsuarioComum usuario = usuarioController.cadastrarUsuario("Meulogin", "senha123467", "cpf", "email@gmail.com");
        UsuarioComum usuarioEncontrado = userRepository.buscarPorId(usuario);
        CartaoCredito metodoPagamento = new CartaoCredito("11111111");

        // Realiza a venda do ingresso
        gerenciadorCompraController.comprarIngresso(usuarioEncontrado, evento, 2, metodoPagamento);

        // usuario realiza avaliação
        Avaliacao avaliacao = new Avaliacao(usuario,"evento muito bom",5);
        Evento eventoEncontrado = eventoRepository.buscarPorId(evento);

        usuarioController.comentarEvento(eventoEncontrado,usuarioEncontrado,avaliacao);

        Assertions.assertEquals("evento muito bom", eventoEncontrado.getAvalicao().getFirst().getComentario());
        Assertions.assertEquals(usuario, eventoEncontrado.getAvalicao().getFirst().getUsuario());

    }


    @Test
    public void tentarComentarSemComprarTest() throws FileNotFoundException, IllegalArgumentException {
        LocalDate data = LocalDate.now().plusDays(10);
        Adimin adimin = adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento evento = adminController.cadastrarEvento(adimin,"Evento sem login", "evento", data, 1000);

        UsuarioComum usuario = usuarioController.cadastrarUsuario("loginSemLogin", "senha123", "cpf", "email@gmail.com");
        Avaliacao avaliacao = new Avaliacao(usuario, "Não gostei", 2);

        // Tentativa de comentar
        Evento eventoEncontrado = eventoRepository.buscarPorId(evento);
        Assertions.assertFalse( usuarioController.comentarEvento(eventoEncontrado, usuario, avaliacao));
    }

}
