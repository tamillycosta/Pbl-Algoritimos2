

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import models.Evento;
import models.Ingresso;


public class IngressoTest {

    @Test
    public void testCriarIngresso() {
        LocalDate data = LocalDate.now().plusDays(10);

        Evento evento = new models.Evento("Show de Rock","um show de Rock",data,1.000);
        Ingresso ingresso = new Ingresso(evento,100.0);

        assertNotNull(ingresso);
        assertEquals(evento, ingresso.getEvento());
         assertEquals(100.0, ingresso.getPreco(), 0.0001);
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void cancelarIngresso(){
        LocalDate data = LocalDate.now().plusDays(10);

        Evento evento = new Evento("Show de Rock","um show de Rock",data,1.000);
        Ingresso ingresso = new Ingresso(evento,100.0);

        assertTrue(ingresso.cancelar());
        assertFalse(ingresso.isAtivo());

    }

    @Test
    public void cancelarIngressoEventoPassadp(){
        LocalDate data = LocalDate.now().minusDays(10);

        Evento evento = new Evento("Show de Rock","um show de Rock",data,1.000);
        Ingresso ingresso = new Ingresso(evento,100.0);

        assertFalse(ingresso.cancelar());
        assertFalse(evento.isAtivo());

    }



}
