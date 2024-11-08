package models.interfaces;

public interface MetodoPagamento {


    void setValorPagar(double valorPagar);

    boolean validar();

    boolean processarPagamento(double valor);




}
