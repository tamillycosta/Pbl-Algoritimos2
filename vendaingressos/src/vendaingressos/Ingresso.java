package vendaingressos;

import java.util.Objects;

public class Ingresso {

    private Evento evento ;
    private double preco;
    private String assento;
    private boolean ativo;

    public Ingresso(Evento evento, double preco, String assento) {
        this.evento = evento;
        this.preco = preco;
        this.assento = assento;
        this.ativo = true;
    }

    public Evento getEvento() {
        return evento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getAssento() {
        return assento;
    }

    public void setAssento(String assento) {
        this.assento = assento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean cancelar() {
        if(this.evento.isAtivo()) {
            this.ativo = false;
            return true;
        }
        else return false;
    }

    public boolean reativar(){
        if(this.evento.isAtivo() && !isAtivo()){
            this.ativo = true;
            return true;
        }
        else return  false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingresso ingresso)) return false;
        return Double.compare(preco, ingresso.preco) == 0 && ativo == ingresso.ativo && Objects.equals(evento, ingresso.evento) && Objects.equals(assento, ingresso.assento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evento, preco, assento, ativo);
    }
}
