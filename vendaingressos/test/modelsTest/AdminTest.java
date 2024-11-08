package modelsTest;

import models.UsuarioComum;
import org.junit.Test;
import models.Adimin;

import static org.junit.Assert.*;

public class AdminTest {


    @Test
    public void testCadastrarUsuarioAdmin() {
        Adimin admin = new Adimin("admin", "senha123",  "00000000000", "admin@example.com");

        assertNotNull(admin);
        assertEquals("admin", admin.getLogin());
        assertEquals("00000000000", admin.getCpf());
        assertEquals("admin@example.com", admin.getEmail());
    }

    @Test
    public void testLoginAdminTest() {
        Adimin adm = new Adimin("johndoe", "senha123", "12345678901", "john.doe@example.com");
        assertTrue(adm.login("johndoe", "senha123"));
    }

    @Test
    public void testLoginAdminInvalidoTest() {
        Adimin adm = new Adimin("johndoe", "senha123",  "12345678901", "john.doe@example.com");

        assertFalse(adm.login("johndoe", "senhaErrada")); // Teste para login inválido
        assertFalse(adm.login("usuarioInvalido", "senha123")); // Teste com login inválido
    }


    @Test
    public void testEmailValido() {
        Adimin adm = new Adimin("johndoe", "senha123",  "12345678901", "john.doe@example.com");
        assertEquals("john.doe@example.com", adm.getEmail());
    }


    @Test(expected = IllegalArgumentException.class)
    public  void testVerificarSenha(){
        Adimin adm = new Adimin("johndoe", "23",  "12345678901", "john.doe@example.com");
        assertFalse(adm.getSenha().length() >= 6);

    }

}

