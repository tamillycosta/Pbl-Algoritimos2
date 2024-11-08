package repositoryTest;

import models.Adimin;
import models.UsuarioComum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AdminRepository;
import repository.UserRepository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminRepositoryTest {

    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp(){
        adminRepository = new AdminRepository();
        try{
            Files.createDirectories(Paths.get(AdminRepository.diretorio));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            // Caminho do diretório onde os arquivos são salvos
            Path diretorio = Paths.get(AdminRepository.diretorio);


            Files.walk(diretorio)
                    .filter(Files::isRegularFile)
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
    public void salvarAdmin() throws IOException {
        Adimin adimin = new Adimin("meuLogin", "12345678",  "12345678", "meuemail@gmail.com");
        adminRepository.salvar(adimin);

        assertTrue(Files.exists(Paths.get(AdminRepository.diretorio  + adimin.getId() + ".json")), "Arquivo do usuário deve existir");
    }



}
