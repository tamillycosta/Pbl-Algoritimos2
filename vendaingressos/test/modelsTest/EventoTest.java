
import java.time.LocalDate;
import models.Evento;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class EventoTest {

    @Test
    public void testCriarEvento() {
        LocalDate data = LocalDate.now().plusDays(10);

        Evento evento = new Evento("Show de Rock","um show de Rock",data,1.000);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("um show de Rock", evento.getDescricao());
        assertEquals(data, evento.getData());
    }

    @Test
    public void LiberarAssento(){
        LocalDate data = LocalDate.now().plusDays(10);

        Evento evento = new Evento("Show de Rock","um show de Rock",data,1000.0);
        evento.ocuparEspaco();
        evento.desocuparAssento();

        assertTrue(evento.getCapacidadeDisponivel() == 1000.0);
    }


    @Test(expected = IllegalStateException.class)
    public void testDesocuparAssentoSemOcupar() {
        LocalDate data = LocalDate.now().plusDays(10);
        Evento evento = new Evento("Show de Rock", "um show de Rock", data, 1.000);

        evento.desocuparAssento();
    }


    @Test
    public  void OcuparAssento(){
        LocalDate data = LocalDate.now().plusDays(10);

        Evento evento = new Evento("Show de Rock","um show de Rock",data,1000.0);
        evento.ocuparEspaco();

        assertTrue(evento.getCapacidadeDisponivel() == 999.0);

    }


    @Test
    public void testEventoAtivo() {
        // insere uma data daqui a 10 dias
        LocalDate data = LocalDate.now().plusDays(10);

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data, 800);
        assertTrue(evento.isAtivo());
    }


    @Test
    public void testEventoInativo() {
        // insere uma data a 10 dias atras
        LocalDate data = LocalDate.now().minusDays(10);
        Evento evento = new Evento("Show de Rock", "Banda XYZ", data, 800);
        assertFalse(evento.isAtivo());
    }

}