package models;
import java.util.UUID;


import java.util.Date;
import java.util.List;

public class NotaFiscal {
    private  String id;
    private int valor ;
    private Date data;
    private List<Integer> ingressos;
    private String formaPagamento;
    private int Idusuario;

    public NotaFiscal(int valor, Date data, List<Integer> idIngressos, String formaPagamento, int idusuario) {
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.data = data;
        this.ingressos = idIngressos;
        this.formaPagamento = formaPagamento;
        this.Idusuario = idusuario;
    }
}
