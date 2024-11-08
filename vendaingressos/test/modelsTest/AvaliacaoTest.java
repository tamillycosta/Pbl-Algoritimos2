package modelsTest;

import models.Avaliacao;
import models.Usuario;
import models.UsuarioComum;
import org.junit.Test;

import static org.junit.Assert.*;


public class AvaliacaoTest {
    @Test
    public void  CriaComentarioTest() {
        UsuarioComum usuario = new UsuarioComum("JohnDoe","12345678", "111111111", "meuEmail@gmail.com");
        Avaliacao avaliacao = new Avaliacao(usuario, "Ótimo evento!", 5);

        assertEquals(usuario, avaliacao.getUsuario());
        assertEquals("Ótimo evento!", avaliacao.getComentario());
        assertEquals(5, avaliacao.getEstrelas());
    }


    @Test
   public void ComentarioNullTest() {
        UsuarioComum usuario = new UsuarioComum("JohnDoe","12345678", "111111111", "meuEmail@gmail.com");
        Avaliacao avaliacao = new Avaliacao(usuario, "Bom evento!", 3);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            avaliacao.setComentario(null);
        });
        assertEquals("Comentário não pode ser vazio.", exception.getMessage());
    }


    @Test
    public void EstrelasInvalidoTest() {
        UsuarioComum usuario = new UsuarioComum("JohnDoe","12345678", "111111111", "meuEmail@gmail.com");
        Avaliacao avaliacao = new Avaliacao(usuario, "Bom evento!", 3);


        avaliacao.setEstrelas(6);
        assertEquals(3, avaliacao.getEstrelas());
    }
}
