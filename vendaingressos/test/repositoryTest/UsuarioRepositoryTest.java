    package repositoryTest;
    import models.Evento;
    import models.Ingresso;
    import models.UsuarioComum;
    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import repository.AdminRepository;
    import repository.UserRepository;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;


    import static org.junit.Assert.assertEquals;
    import static org.junit.Assert.assertNotEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;
    import static org.junit.jupiter.api.Assertions.assertTrue;

    public class UsuarioRepositoryTest {

        private UserRepository userRepository;


        @BeforeEach
        public void setUp() {
            userRepository = new UserRepository();  // Inicializa o repositório
            try {
                Files.createDirectories(Paths.get(UserRepository.diretorioBase));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @AfterEach
        public void tearDown() {
            try {
                // Caminho do diretório onde os arquivos são salvos
                Path diretorio = Paths.get(UserRepository.diretorioBase);

                // Remove todos os arquivos dentro do diretório
                Files.walk(diretorio)
                        .filter(Files::isRegularFile)  // Somente arquivos
                        .forEach(arquivo -> {
                            try {
                                Files.delete(arquivo);  // Exclui cada arquivo
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Test
        public void salvarUsuarios() throws IOException {

            UsuarioComum usuarioComum = new UsuarioComum("meuLogin", "12345678",  "12345678", "meuemail@gmail.com");
            userRepository.salvar(usuarioComum);

            assertTrue(Files.exists(Paths.get(UserRepository.diretorioBase + "meuLogin/" + usuarioComum.getId() + ".json")), "Arquivo do usuário deve existir");
        }


        @Test
        public  void  buscarUsuario(){
            UsuarioComum usuarioComum = new UsuarioComum("meuLogin", "12345678", "12345678", "meuemail@gmail.com");
            userRepository.salvar(usuarioComum);

            UsuarioComum encontrado = userRepository.buscarPorId(usuarioComum);
            assertNotNull(encontrado, "Usuário deve ser encontrado");
        }


        @Test
        public void buscarUsuarios(){
            UsuarioComum usuario1 = new UsuarioComum("login1", "12345678",  "12345678", "email1@gmail.com");
            UsuarioComum usuario2 = new UsuarioComum("login2", "12345678",  "12345678", "email2@gmail.com");

            userRepository.salvar(usuario1);
            userRepository.salvar(usuario2);

            // Verificar se os arquivos foram criados nas pastas corretas
            assertTrue(Files.exists(Paths.get(UserRepository.diretorioBase + "login1/" + usuario1.getId() + ".json")),
                    "Arquivo do usuário 1 deve existir na pasta login1");
            assertTrue(Files.exists(Paths.get(UserRepository.diretorioBase + "login2/" + usuario2.getId() + ".json")),
                    "Arquivo do usuário 2 deve existir na pasta login2");

            // verifica quantos usuarios tem na lista
            List<UsuarioComum> listaUsuarios = userRepository.buscarTodos();
            assertEquals(2, listaUsuarios.size());
        }


        @Test
        public  void editarUsarios() throws FileNotFoundException {
            UsuarioComum usuario1 = new UsuarioComum("login3", "12345678",  "12345678", "email1@gmail.com");

            userRepository.salvar(usuario1);

            usuario1.setSenha("8121334456");
            usuario1.setEmail("exemplo1345@gmail.com");
            userRepository.editarEntidade(usuario1);

            UsuarioComum encontrado = userRepository.buscarPorId(usuario1);
            assertNotEquals(encontrado.getSenha(), "12345678");
            assertNotEquals(encontrado.getEmail(), "email1@gmail.com");
        }


        @Test
        public void adicionarIngressoUsuario() throws FileNotFoundException {
            UsuarioComum usuario1 = new UsuarioComum("login3", "12345678",  "12345678", "email1@gmail.com");
            userRepository.salvar(usuario1);


            // determina data de crição evento
            LocalDate dataEvento = LocalDate.now().plusDays(5);


            // cria o evento
            Evento evento = new Evento("show da xuxa", "festa de fim de ano", dataEvento, 1000.0);
            Ingresso ingresso = new Ingresso(evento, 100);

            // cria ingressos comprados
            List<Ingresso> ingressos = new ArrayList<>();
            ingressos.add(ingresso);
            usuario1.setIngressos(ingressos);

            userRepository.editarEntidade(usuario1);

            UsuarioComum encontrado = userRepository.buscarPorId(usuario1);
            assertEquals(1,encontrado.getIngressos().size() );
        }

    }



