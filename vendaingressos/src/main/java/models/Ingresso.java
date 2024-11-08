package models; /*******************************************************************************************
 Autor: Tamilly Costa Cerqueira
 Componente Curricular: EXA863 - MI - PROGRAMAÇÃO
 Concluído em: 07/09/22024
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

 ********************************************************************************************/

import models.Evento;

import java.util.Objects;
import java.util.UUID;

public class Ingresso {

    private String id;
    private Evento evento ;
    private double preco;
    private boolean ativo;


    public Ingresso(Evento evento, double preco) {
        this.id = UUID.randomUUID().toString();
        this.evento = evento;
        this.preco = preco;
        this.ativo = true;
    }

    /**
     * Cancela o ingresso se o evento estiver ativo.
     *
     * @return true se o ingresso foi cancelado, false caso contrário.
     */

    public boolean cancelar() {
        if(this.evento.isAtivo()) {
            this.ativo = false;
            return true;
        }
        else return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingresso ingresso)) return false;
        return Double.compare(preco, ingresso.preco) == 0 && ativo == ingresso.ativo && Objects.equals(evento, ingresso.evento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evento, preco, ativo);
    }


    // gets e sets
    public Evento getEvento() {
        return evento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }


    public boolean isAtivo() {

        return ativo;
    }



}
