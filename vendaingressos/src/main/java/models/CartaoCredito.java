package models;

import models.interfaces.MetodoPagamento;

public class CartaoCredito implements MetodoPagamento {

    private double juros;
    private String numeroCartao;
    private String tipoConta;
    private  int parcelas;
    private double valorPagar;

    /***
     *
     * @param numeroCartao
     */
    public CartaoCredito(String numeroCartao) {

        this.juros = 0.2;
        this.numeroCartao = numeroCartao;
        this.parcelas = 0;
        this.valorPagar = 0;
    }

    /**
     * Valida o número do cartão. O número do cartão é considerado válido se não for nulo e tiver 8 caracteres.
     *
     * @return {@code true} se o número do cartão for válido, caso contrário, {@code false}
     */
    public boolean validar(){
        return  numeroCartao != null && numeroCartao.length() == 8;
    }


    /**
     * Calcula o valor total com juros para o pagamento parcelado. Se o número de parcelas for maior que 3,
     * os juros são aplicados ao valor a ser pago.
     *
     * @param parcelas o número de parcelas
     * @return o valor total com ou sem juros, dependendo do número de parcelas
     */
    public double calcularJuros(int parcelas) {
        if (parcelas > 3) {
            return getValorPagar() * (1 + (getJuros() / 100));
        } else {
            return  getValorPagar();  // Sem juros se as parcelas forem <= 3
        }
    }

    /**
     * Realiza o parcelamento do valor a ser pago. Calcula o valor de cada parcela, considerando
     * os juros aplicáveis.
     *
     * @param parcelas o número de parcelas
     * @return o valor de cada parcela
     */
    public double fazerParcelamento(int parcelas ) {
        double valorComJuros = calcularJuros( parcelas);
        return valorComJuros / parcelas;
    }


    /**
     * Processa o pagamento à vista. Se o número do cartão for válido, o valor a ser pago é subtraído
     * do valor total devido.
     *
     * @param valorPagar o valor a ser pago
     * @return {@code true} se o pagamento for processado com sucesso, caso contrário, {@code false}
     */
    public boolean processarPagamento(double valorPagar) {
        if(validar()){
            setValorPagar(getValorPagar() - valorPagar );
            return  true;
        }
        return false;
    }


    /***
     * Processa o pagamento parcelado. Valida o cartão e, se o valor a ser pago for suficiente para cobrir
     * o valor de cada parcela, o número de parcelas restantes é atualizado, e o valor total devido é ajustado.
     *
     * @param valorPagar o valor a ser pago
     * @param parcelas o número de parcelas
     * @return {@code true} se o pagamento for processado com sucesso, caso contrário, {@code false}
     */
    public boolean processarPagamentoParcelado(double valorPagar, int parcelas) {
        if (validar()) {
            double valorParcelado = fazerParcelamento(parcelas);  // Obtem o valor da parcela
            if (parcelas > 0 && valorPagar >= valorParcelado) {   // Valor pago deve ser <= valor da parcela
                this.parcelas -= 1;
                setValorPagar(getValorPagar() - valorPagar);  // Atualiza o valor restante
                return true;
            }
        }
        return false;
    }


    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
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
