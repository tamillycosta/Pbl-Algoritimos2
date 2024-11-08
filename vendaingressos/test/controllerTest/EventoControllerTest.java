package controllerTest;
import controllers.AdminController;
import controllers.EventoController;
import models.Adimin;
import models.Evento;
import models.Ingresso;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AdminRepository;
import repository.EventoRepository;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;


public class EventoControllerTest {

    EventoRepository eventoRepository;
    AdminController adminController;
    AdminRepository adminRepository;
    EventoController eventoController;

    @BeforeEach
    public void setUp() {

        eventoRepository = new EventoRepository();
        adminRepository = new AdminRepository();
        adminController = new AdminController(eventoRepository,adminRepository);
        eventoController = new EventoController(eventoRepository);

        try {
            Files.createDirectories(Paths.get(EventoRepository.diretorio));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
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
    public  void gerarIngressoEventoTest() throws FileNotFoundException {
        LocalDate data = LocalDate.now().plusDays(5);
        Adimin adimin = adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento eventoCadastrado = adminController.cadastrarEvento(adimin, "Evento de natal", "Festa natalina", data, 1000);
        List<Ingresso> ingressos =  eventoController.gerarIngressos(5, eventoCadastrado);

        Evento eventoEcontrado = eventoRepository.buscarPorId(eventoCadastrado);
       Assertions.assertEquals(5, ingressos.size());
       Assertions.assertEquals(995, eventoEcontrado.getCapacidadeDisponivel());
    }

    @Test
    public  void gerarIngressoEventoCheioTest() throws FileNotFoundException {
        LocalDate data = LocalDate.now().plusDays(5);
        Adimin adimin = adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento eventoCadastrado = adminController.cadastrarEvento(adimin, "Evento de natal", "Festa natalina", data, 1000);
        eventoCadastrado.setCapacidadeDisponivel(0);
        eventoRepository.editarEntidade(eventoCadastrado);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
           eventoController.gerarIngressos(5,eventoCadastrado);
        });

        Assertions.assertEquals("O evento esta cheio.", exception.getMessage(), "A mensagem da exceção deve indicar que o evento está cheio.");
    }


    @Test
    public void  listarEventosDisponiveisTest() {
        LocalDate dataValida = LocalDate.now().plusDays(5);

        Adimin adimin = adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
          adminController.cadastrarEvento(adimin, "Evento de natal", "Festa natalina", dataValida, 1000);
          adminController.cadastrarEvento(adimin, "Evento de Ano Novo", "Festa de Ano Novo",dataValida, 500);

        List<Evento> eventos = eventoController.listarEventosDisponiveis();
        Assertions.assertEquals(2, eventos.size());
    }


}
