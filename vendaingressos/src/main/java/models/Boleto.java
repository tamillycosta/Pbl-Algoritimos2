package models;

import models.abstratas.Pagamento;



public class Boleto extends Pagamento{
    private String codigoBoleto;

    public Boleto(double valor, String codigoBoleto) {
        super(valor);
        this.codigoBoleto = codigoBoleto;
    }

    public String getCodigoBoleto() {
        return codigoBoleto;
    }

    public boolean validarBoleto(){
        return this.codigoBoleto.length() >= 8;
    }

    @Override
    public boolean processarPagamento(double valorPagar) {
        if (valorPagar < this.getValor()){
            return false;
        }
        setValor(getValor() - valorPagar);
        return true;
    }


    @Override
    public boolean processarPagamento(double valorPagar, int parcelas) {
        return false;
    }
}
