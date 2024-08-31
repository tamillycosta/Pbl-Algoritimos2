package vendaingressos;

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
        this.ativo = false;
        return  isAtivo();
    }

    public boolean reativar() {
        this.ativo = true;
        return  isAtivo();
    }
}
