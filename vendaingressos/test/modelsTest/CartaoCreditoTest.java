
import models.CartaoCredito;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CartaoCreditoTest {

    @Test
    public void  validarPagamentoTest(){
        CartaoCredito cartaoCredito = new CartaoCredito(1000.0,"11111111");
        assertTrue(cartaoCredito.processarPagamento(100));
        assertEquals(900.0, cartaoCredito.getValor(), 0.01);
    }

    @Test
    public void validarPagamentoParcelasTest() {
        CartaoCredito cartaoCredito = new CartaoCredito(1000.0, "11111111");
        cartaoCredito.setParcelas(4);  // Define 4 parcelas
        cartaoCredito.setJuros(0.2);   // Define os juros
        System.out.println(cartaoCredito.fazerParcelamento(4));

        // Testa o pagamento de uma parcela de 300
        assertTrue(cartaoCredito.processarPagamento(300, 4));

        // Verifica se o valor restante no cartão foi atualizado corretamente
        assertEquals(700.0, cartaoCredito.getValor(), 0.01);
    }


    @Test
    public void validarCalculoJurosTest() {
        CartaoCredito cartaoCredito = new CartaoCredito(1000.0, "11111111");
        cartaoCredito.setTipoConta("Credito");
        cartaoCredito.setParcelas(5);

        // Testando o cálculo de juros
        double juros = cartaoCredito.calcularJuros(1000.0, 5);
        assertEquals(1002.0, juros, 0.01); // 0.2% de juros sobre 1000.0
    }


    @Test
    public void validarParcelamentoTest() {
        CartaoCredito cartaoCredito = new CartaoCredito(1000.0, "11111111");
        cartaoCredito.setParcelas(4);

        // Testando o parcelamento
        double valorParcelado = cartaoCredito.fazerParcelamento(4);
        assertEquals(250.5, valorParcelado, 0.01);
    }

}
