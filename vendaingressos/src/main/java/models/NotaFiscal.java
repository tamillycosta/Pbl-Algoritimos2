package models;
import models.interfaces.MetodoPagamento;

import java.time.LocalDate;
import java.util.UUID;


import java.util.List;

public class NotaFiscal {
    private  String id;
    private double valor ;
    private LocalDate dataCompra;
    private List<Ingresso> ingressosComprados;
    private MetodoPagamento formaPagamento;
    private UsuarioComum comprador;

    /***
     *
     * @param valor
     * @param data
     * @param Ingressos
     * @param formaPagamento
     * @param comprador
     */
    public NotaFiscal(double valor, LocalDate data, List<Ingresso> Ingressos, MetodoPagamento formaPagamento, UsuarioComum comprador) {
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.dataCompra = data;
        this.ingressosComprados = Ingressos;
        this.formaPagamento = formaPagamento;
        this.comprador = comprador;
    }

    @Override
    public String toString() {
        return "NotaFiscal{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                ", data=" + dataCompra +
                ", ingressos=" + ingressosComprados +
                ", formaPagamento=" + formaPagamento +
                ", comprador=" + comprador +
                '}';
    }

    public String getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return dataCompra;
    }

    public List<Ingresso> getIngressos() {
        return ingressosComprados;
    }

    public MetodoPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public UsuarioComum getComprador() {
        return comprador;
    }
}
