/*******************************************************************************************
 Autor: Tamilly Costa Cerqueira
 Componente Curricular: EXA863 - MI - PROGRAMAÇÃO
 Concluído em: 07/09/22024
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

 ********************************************************************************************/
package models;
import  java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Evento {
    private  String id ;
    private List<Avaliacao> avalicoes;
    private String nome;
    private String descricao;
    private LocalDate data;
    private double capacidadeDisponivel;
    private final double capacidadeEvento;
    private boolean ativo;


    public Evento(String nome, String descricao, LocalDate data, double capacidadeEvento) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.ativo = verificaEvento();
        this.capacidadeEvento = capacidadeEvento;
        this.capacidadeDisponivel = capacidadeEvento;
        this.avalicoes = new ArrayList<Avaliacao>();
        // usar a quantidade de assentos
    }

    public static final LocalDate dataLimite = LocalDate.now();


    public boolean verificaEvento() {
        setAtivo(this.data.isBefore(dataLimite)  ? false : true);
        return this.ativo;
    }

    public  void desocuparAssento(){
        if (isAtivo() && capacidadeDisponivel < capacidadeEvento){
            this.capacidadeDisponivel ++;
        }
        else throw new IllegalStateException();
    }

    public void ocuparEspaco(){
        if (isAtivo() && capacidadeDisponivel > 0) {
            this.capacidadeDisponivel --;
        }
    }

    public List<Avaliacao> getAvalicao() {
        return avalicoes;
    }

    public LocalDate getData() {
        return data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCapacidadeEvento() {
        return capacidadeEvento;
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public double getCapacidadeDisponivel() {
        return capacidadeDisponivel;
    }

    public void setCapacidadeDisponivel(double capacidadeDisponivel) {
        this.capacidadeDisponivel = capacidadeDisponivel;
    }
}
