package modelsTest;

import models.CartaoCredito;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CartaoCreditoTest {

    @Test
    public void  validarPagamentoTest(){
        CartaoCredito cartaoCredito = new CartaoCredito("11111111");
        cartaoCredito.setValorPagar(1000.0);
        assertTrue(cartaoCredito.processarPagamento(100));
        assertEquals(900.0, cartaoCredito.getValorPagar(), 0.01);
    }

    @Test
    public void validarPagamentoParceladoTest() {
        CartaoCredito cartaoCredito = new CartaoCredito( "11111111");
        cartaoCredito.setParcelas(4);  // Define 4 parcelas
        cartaoCredito.setJuros(0.2);   // Define os juros

        // Testa o pagamento de uma parcela de 300
        cartaoCredito.setValorPagar(1000.0);
        assertTrue(cartaoCredito.processarPagamentoParcelado(300, 4));

        // Verifica se o valor restante no cartão foi atualizado corretamente
        assertEquals(700.0, cartaoCredito.getValorPagar(), 0.01);
    }


    @Test
    public void processarPagamanetoRegular(){
        CartaoCredito cartaoCredito = new CartaoCredito( "11111111");
        cartaoCredito.setValorPagar(1000.0);
        assertTrue(cartaoCredito.processarPagamento(400.0));

        assertEquals(600.0 ,cartaoCredito.getValorPagar(), 0.01);
    }



    @Test
    public void validarCalculoJurosTest() {
        CartaoCredito cartaoCredito = new CartaoCredito( "11111111");
        cartaoCredito.setTipoConta("Credito");
        cartaoCredito.setParcelas(5);
        cartaoCredito.setValorPagar(1000.0);
        // Testando o cálculo de juros
        double juros = cartaoCredito.calcularJuros(5);
        assertEquals(1002.0, juros, 0.01); // 0.2% de juros sobre 1000.0
    }


    @Test
    public void validarParcelamentoTest() {
        CartaoCredito cartaoCredito = new CartaoCredito( "11111111");
        cartaoCredito.setParcelas(4);
        cartaoCredito.setValorPagar(1000.0);

        // Testando o parcelamento
        double valorParcelado = cartaoCredito.fazerParcelamento(4);
        assertEquals(250.5, valorParcelado, 0.01);
    }

}
