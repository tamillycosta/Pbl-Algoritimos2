import models.Boleto;
import org.junit.Test;

import static org.junit.Assert.*;


public class BoletoTest {

    @Test public  void  processarPagamentoTest(){

        Boleto boleto = new Boleto(1000.0,"12345678");
        
        assertTrue(boleto.processarPagamento(1000.0));
        assertEquals(0.0, boleto.getValor(), 0.01);
    }


    @Test
    public void pagamentoInvalidoTest() {
        Boleto boleto = new Boleto(1000.0, "12345678");

        assertFalse(boleto.processarPagamento(500.0));
        assertEquals(1000.0, boleto.getValor(), 0.01);
    }

    @Test
    public void validarBoletoTest() {
        Boleto boleto = new Boleto(1000.0, "12345678");
        assertTrue(boleto.validarBoleto());
    }

}
