package controllerTest;

import controllers.AdminController;

import models.Adimin;
import models.Evento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AdminRepository;
import repository.EventoRepository;
import repository.UserRepository;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;

public class AdminControllerTest {
    private EventoRepository eventoRepository;
    private AdminRepository adminRepository;
    private AdminController adminController;

    public AdminControllerTest() {
        this.eventoRepository = new EventoRepository();
        this.adminRepository = new AdminRepository();
        this.adminController = new AdminController(eventoRepository, adminRepository);
    }

    @BeforeEach
    public void setUp() {
        this.eventoRepository = new EventoRepository();
        this.adminRepository = new AdminRepository();
        try {
            Files.createDirectories(Paths.get(AdminRepository.diretorio));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterEach
    public void tearDown() {
        try {
            // Caminho do diretório onde os arquivos e pastas são salvos
            Path diretorio = Paths.get(AdminRepository.diretorio);

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
        try {

            Path diretorio = Paths.get(EventoRepository.diretorio);
            Files.walk(diretorio)
                    .sorted(Comparator.reverseOrder())
                    .forEach(caminho -> {
                        try {
                            Files.delete(caminho);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void cadastrarEvento() throws IllegalArgumentException {
        LocalDate data = LocalDate.now().plusDays(5);
        Adimin adimin = adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento eventoCadastrado = adminController.cadastrarEvento(adimin, "Evento de natal", "Festa natalina", data, 1000);

        Assertions.assertNotNull(eventoCadastrado, "O evento não deve ser nulo após o cadastro.");
        Assertions.assertEquals("Evento de natal", eventoCadastrado.getNome(), "O nome do evento deve ser o esperado.");
        Assertions.assertEquals(data, eventoCadastrado.getData(), "A data do evento deve ser a esperada.");
        Assertions.assertEquals(1000, eventoCadastrado.getCapacidadeDisponivel(), "A capacidade do evento deve ser a esperada.");
    }


    @Test
    public void desativarEventoComSucessoTest() throws FileNotFoundException {
        LocalDate data = LocalDate.now().plusDays(5);
        Adimin adimin = adminController.cadastrarAdmim("funcionario", "senha1345", "09809810", "meuEmail@gmail.com");
        Evento eventoCadastrado = adminController.cadastrarEvento(adimin, "Evento de natal", "Festa natalina", data, 1000);

        Evento eventoDesativado = adminController.desativarEvento(eventoCadastrado);

        // Verifica se o evento foi desativado corretamente
        Assertions.assertNotNull(eventoDesativado, "O evento não deve ser nulo após a desativação.");
        Assertions.assertFalse(eventoDesativado.isAtivo(), "O evento deve estar desativado.");
    }



}
