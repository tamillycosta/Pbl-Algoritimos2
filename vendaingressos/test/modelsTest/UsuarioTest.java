
import vendaingressos.Usuario;
import models.UsuarioComum;
import org.junit.Test;

import static org.junit.Assert.*;


public class UsuarioTest {

    @Test
    public void testCadastrarUsuario() {
        UsuarioComum usuario = new UsuarioComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com");

        assertNotNull(usuario);
        assertEquals("johndoe", usuario.getLogin());
        assertEquals("John Doe", usuario.getNome());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("john.doe@example.com", usuario.getEmail());
    }

    @Test
    public void testLogin() {
        UsuarioComum usuario = new UsuarioComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com");

        assertTrue(usuario.login("johndoe", "senha123"));
        assertFalse(usuario.login("johndoe", "senhaErrada"));
    }


    @Test
    public void testEmailValido() {
        UsuarioComum usuario = new UsuarioComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com");
        assertEquals("john.doe@example.com", usuario.getEmail());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testEmailInvalido() {
        new UsuarioComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe"); // Deve lançar exceção
    }


    @Test(expected = IllegalArgumentException.class)
    public  void testVerificarSenha(){
        UsuarioComum usuarioInvalidoSenha = new UsuarioComum("johndoe", "123", "John Doe", "12345678901", "john.doe@example.com");
        assertFalse(usuarioInvalidoSenha.getSenha().length() >= 6);

    }

    @Test
    public  void testeUsuarioDuplicado(){
        // Verificação de login único
        UsuarioComum usuario = new UsuarioComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com");
        UsuarioComum usuarioDuplicado = new UsuarioComum("johndoe", "senha456", "Jane Doe", "98765432109", "jane.doe@example.com");
        assertEquals(usuario.getLogin(), usuarioDuplicado.getLogin()); // Login já existe
    }

}
