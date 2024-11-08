package repositoryTest;

import models.*;
import models.interfaces.MetodoPagamento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repository.NotaFiscalRepository;
import repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotaFiscalTest {

    private NotaFiscalRepository notaFiscalRepository ;

    @BeforeEach
    public void setUp() {
        notaFiscalRepository = new NotaFiscalRepository();  // Inicializa o repositório
        try {
            Files.createDirectories(Paths.get(NotaFiscalRepository.diretorio));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterEach
    public void tearDown() {
        try {

            Path diretorio = Paths.get(NotaFiscalRepository.diretorio);

            Files.walk(diretorio)
                    .sorted(Comparator.reverseOrder())
                    .forEach(arquivoOuPasta -> {
                        try {
                            Files.delete(arquivoOuPasta);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Test
    public  void criarNotaFiscalTest(){
        // determina data de compra do ingresso
        LocalDate dataCompra = LocalDate.now();

        // determina data de crição evento
        LocalDate dataEvento = LocalDate.now().plusDays(5);

        // cria o evento
        Evento evento = new Evento("show da xuxa", "festa de fim de ano", dataEvento, 1000.0);
        Ingresso ingresso = new Ingresso(evento, 100);

        // cria ingressos comprados
        List<Ingresso> ingressos = new ArrayList<>();
        ingressos.add(ingresso);

        // Cria usuario comprador e forma de pagamento
        UsuarioComum usuarioComum = new UsuarioComum("meuLogin", "12345678",  "12345678", "meuemail@gmail.com");
        CartaoCredito formaPagamento = new CartaoCredito( "11111111");
        formaPagamento.setValorPagar(1000.0);
        // salvando o usuario
        UserRepository userRepository = new UserRepository();
        userRepository.salvar(usuarioComum);

        NotaFiscal notaFiscal = new NotaFiscal(100,dataCompra,ingressos,formaPagamento, usuarioComum);
        notaFiscalRepository.salvar(notaFiscal);

        Path caminhoArquivo = Paths.get(NotaFiscalRepository.diretorio +  usuarioComum.getLogin() + "/" +  notaFiscal.getId() + ".json");
        assertTrue(Files.exists(caminhoArquivo), "Arquivo da nota deve existir");
    }
}
