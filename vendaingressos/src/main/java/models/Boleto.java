package models;

import models.interfaces.MetodoPagamento;


public class Boleto implements MetodoPagamento{
    private String codigoBoleto;
    private  double valorPagar;

    /***
     *
     * @param codigoBoleto
     */
    public Boleto( String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }


    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    public String getCodigoBoleto() {
        return codigoBoleto;
    }

    public boolean validar(){
        return this.codigoBoleto.length() >= 8;
    }

    /***
     * Processa o pagamento do valor devido. Se o valor a ser pago for menor que o valor devido,
     * o pagamento não é processado e o método retorna {@code false}. Caso contrário, o valor
     * devido é atualizado e o pagamento é considerado bem-sucedido.
     *
     * @param valorPagar o valor a ser pago
     * @return {@code true} se o pagamento for processado com sucesso, caso contrário, {@code false}
     */
    @Override
    public boolean processarPagamento(double valorPagar) {
        if (valorPagar < this.getValorPagar()){
            return false;
        }
        setValorPagar(getValorPagar() - valorPagar);
        return true;
    }


}
