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


public class GerenciadorCompraControllerTest {

    private EventoController eventoController;
    private EventoRepository eventoRepository;
    private UserRepository userRepository;
    private GerenciadorCompraController gerenciadorCompraController;
    private  NotaFiscalRepository notaFiscalRepository;
    private UsuarioController usuarioController;
    private  AdminController adminController;
    private AdminRepository adminRepository;


    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
        eventoRepository = new EventoRepository();
        eventoController = new EventoController(eventoRepository);
        notaFiscalRepository = new NotaFiscalRepository();
        gerenciadorCompraController = new GerenciadorCompraController(eventoController, eventoRepository, userRepository, notaFiscalRepository);
        usuarioController = new UsuarioController(userRepository,eventoController);
        adminRepository = new AdminRepository();
        adminController = new AdminController(eventoRepository,adminRepository);

        try {
            Files.createDirectories(Paths.get(UserRepository.diretorioBase));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterEach
    public void tearDownUsuario() {
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


    @AfterEach
    public void tearDownEventos() {
        try {

            Path diretorio = Paths.get( EventoRepository.diretorio);

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
    public void venderIngressoTest() throws FileNotFoundException {
        LocalDate data = LocalDate.now().plusDays(10);
        Adimin adimin =  adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento evento = adminController.cadastrarEvento(adimin,"Novo evento", "evento de ano novo", data, 1000);

        UsuarioComum usuario = usuarioController.cadastrarUsuario("Meulogin", "senha123467", "cpf", "email@gmail.com");
        CartaoCredito metodoPagamento = new CartaoCredito("11111111");

        // Realiza a venda do ingresso
        NotaFiscal notaFiscal = gerenciadorCompraController.comprarIngresso(usuario, evento, 10, metodoPagamento);
        Assertions.assertNotNull(notaFiscal, "o usuario não recebeu a NotaFiscal da compra ");

        // verifica atualização do evento
        Evento eventoEncontrado = eventoRepository.buscarPorId(evento);
        Assertions.assertEquals(990.0, eventoEncontrado.getCapacidadeDisponivel());

        // verifica atualização do usuario
        UsuarioComum usuarioEncontrado = userRepository.buscarPorId(usuario);
        Assertions.assertEquals(10, usuarioEncontrado.getIngressos().size());
    }



    @Test
    public void UsuarioCancelaCompraTest() throws FileNotFoundException {
        LocalDate data = LocalDate.now().plusDays(10);
        Adimin adimin =  adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento evento = adminController.cadastrarEvento(adimin,"Novo evento", "evento de ano novo", data, 1000);

        UsuarioComum usuario = usuarioController.cadastrarUsuario("Meulogin", "senha123467", "cpf", "email@gmail.com");
        Boleto metodoPagamento = new Boleto("11111111");

        // Realiza a venda do ingresso
        NotaFiscal notaFiscal = gerenciadorCompraController.comprarIngresso(usuario, evento, 10, metodoPagamento);
        Ingresso ingressoDevolvido = usuario.getIngressos().getFirst();
        gerenciadorCompraController.cancelarCompra(usuario, ingressoDevolvido, notaFiscal);


        // Verifica a retirada do ingresso
        UsuarioComum usuarioEncontrado = userRepository.buscarPorId(usuario);
        Assertions.assertEquals(9, usuarioEncontrado.getIngressos().size());
        Assertions.assertFalse(usuarioEncontrado.getIngressos().contains(ingressoDevolvido));


        // Verifica o reposição de espaço do evento
        Evento eventoEncontrado = eventoRepository.buscarPorId(evento);
        Assertions.assertEquals(991, eventoEncontrado.getCapacidadeDisponivel());

    }


}
