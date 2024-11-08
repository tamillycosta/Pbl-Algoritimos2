package modelsTest;

import models.Boleto;
import org.junit.Test;

import static org.junit.Assert.*;


public class BoletoTest {

    @Test public  void  processarPagamentoTest(){

        Boleto boleto = new Boleto("12345678");
        boleto.setValorPagar(1000.0);

        assertTrue(boleto.processarPagamento(1000.0));
        assertEquals(0.0, boleto.getValorPagar(), 0.01);
    }


    @Test
    public void pagamentoInvalidoTest() {
        Boleto boleto = new Boleto("12345678");
        boleto.setValorPagar(1000.0);

        assertFalse(boleto.processarPagamento(500.0));
        assertEquals(1000.0, boleto.getValorPagar(), 0.01);
    }

    @Test
    public void validarBoletoTest() {
        Boleto boleto = new Boleto( "12345678");
        boleto.setValorPagar(1000.0);
        assertTrue(boleto.validar());
    }

}
