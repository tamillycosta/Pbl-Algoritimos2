package repositoryTest;

import models.Avaliacao;
import models.Evento;
import models.UsuarioComum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EventoRepository;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class EventoRepositoryTest {

    private EventoRepository eventoRepository;


    @BeforeEach
    public void setUp() {
        this.eventoRepository = new EventoRepository();
        System.out.println("Instância de eventoRepository: " + eventoRepository);
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
    public void salvarEventoTest() {
        LocalDate data = LocalDate.now().plusDays(10);
        Evento evento = new Evento("show da xuxa", "festa de fim de ano", data, 1000.0);

        eventoRepository.salvar(evento);

        Path caminhoArquivo = Paths.get(EventoRepository.diretorio + evento.getData() + "/" + evento.getId() + ".json");
        assertTrue(Files.exists(caminhoArquivo), "Arquivo do evento deve existir");

    }


    @Test
    public void listarEventoTest(){
        LocalDate data1 = LocalDate.now().plusDays(12);
        LocalDate data2 = LocalDate.now().plusDays(11);

        Evento evento1 = new Evento("show do rock", "Rock in Rio", data1, 1000.0);
        Evento evento2 = new  Evento("Show de pop", "Rock in Rio", data2, 1000.0);

        eventoRepository.salvar(evento1);
        eventoRepository.salvar(evento2);

        assertTrue(Files.exists(Paths.get(EventoRepository.diretorio + evento1.getData() + "/" + evento1.getId() + ".json")),"Arquivo do evento1 deve existir");
        assertTrue(Files.exists(Paths.get(EventoRepository.diretorio + evento2.getData() + "/" + evento2.getId() + ".json")),"Arquivo do evento2 deve existir");


        List<Evento> listaEvento = eventoRepository.buscarTodos();
        assertEquals(2, listaEvento.size());
    }



    @Test
    public void EditarDisponivelEventoTest() throws FileNotFoundException {
        LocalDate data = LocalDate.now().plusDays(10);
        Evento evento = new Evento("show da xuxa", "festa de fim de ano", data, 1000.0);

        eventoRepository.salvar(evento);
        evento.setAtivo(false);

        eventoRepository.editarEntidade(evento);

        Evento eventoEcontrado = eventoRepository.buscarPorId(evento);
        assertEquals(false, eventoEcontrado.isAtivo());

    }



    @Test
    public  void adicionarAvaliacaoEventoTest() throws FileNotFoundException {
        LocalDate data = LocalDate.now().minusDays(10);
        Evento evento = new Evento("show da xuxa", "festa de fim de ano", data, 1000.0);
        UsuarioComum usuarioComum = new UsuarioComum("meuLogin", "12345678", "12345678", "meuemail@gmail.com");

        eventoRepository.salvar(evento);

        Avaliacao avaliacao = new Avaliacao(usuarioComum,"que show da xuxa foi esse?",10);
        Avaliacao avaliacao2 = new Avaliacao(usuarioComum,"adorei a festa",5);

        List<Avaliacao>  avaliacaos = new ArrayList<Avaliacao>();
        avaliacaos.add(avaliacao);
        avaliacaos.add(avaliacao2);

        evento.setAvalicoes(avaliacaos);
        eventoRepository.editarEntidade(evento);

        Evento eventoCarregado = eventoRepository.buscarPorId(evento);

        assertEquals(2,evento.getAvalicao().size());
    }

}

