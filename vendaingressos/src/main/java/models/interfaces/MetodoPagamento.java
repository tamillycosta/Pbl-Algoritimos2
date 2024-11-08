package models.abstratas;



public abstract class MetodoPagamento {



    public abstract boolean processarPagamento(double valor);

    public abstract boolean processarPagamento(double valorPagar, int parcelas);

    
}
