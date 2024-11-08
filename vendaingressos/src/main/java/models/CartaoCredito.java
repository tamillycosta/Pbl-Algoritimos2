package models;

import models.abstratas.Pagamento;

public class CartaoCredito extends Pagamento {

    private double juros;
    private String numeroCartao;
    private String tipoConta;
    private  int parcelas;

    public CartaoCredito(double valor, String numeroCartao) {
        super(valor);
        this.juros = 0.2;
        this.numeroCartao = numeroCartao;
        this.parcelas = 0;
    }

    private boolean validarCartao(){
        return  numeroCartao != null && numeroCartao.length() == 8;
    }

    public double calcularJuros(double valor, int parcelas) {
        if (parcelas > 3) {
            return valor * (1 + (getJuros() / 100));
        } else {
            return valor;  // Sem juros se as parcelas forem <= 3
        }
    }

    public double fazerParcelamento(int parcelas) {
        double valorComJuros = calcularJuros(getValor(), parcelas);
        return valorComJuros / parcelas;
    }


    // aplicando sobrecarga
    @Override
    public boolean processarPagamento(double valorPagar) {
        if(validarCartao()){
            setValor(getValor() - valorPagar );
            return  true;
        }
        return false;
    }


    @Override

    public boolean processarPagamento(double valorPagar, int parcelas) {
        if (validarCartao()) {
            double valorParcelado = fazerParcelamento(parcelas);  // Obtem o valor da parcela
            if (parcelas > 0 && valorPagar >= valorParcelado) {   // Valor pago deve ser <= valor da parcela
                this.parcelas -= 1;
                setValor(getValor() - valorPagar);  // Atualiza o valor restante
                return true;
            }
        }
        return false;
    }



    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public double getJuros() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros = juros;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }
}
