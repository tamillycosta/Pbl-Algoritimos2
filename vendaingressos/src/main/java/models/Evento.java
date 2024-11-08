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
    private final double valorIngresso;
    private boolean ativo;

    /***
     *
     * @param nome
     * @param descricao
     * @param data
     * @param capacidadeEvento
     */
    public Evento(String nome, String descricao, LocalDate data, double  capacidadeEvento) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.ativo = verificaEvento();
        this.capacidadeEvento = capacidadeEvento;
        this.capacidadeDisponivel = capacidadeEvento;
        this.avalicoes = new ArrayList<>();
        this.valorIngresso = 50;
        // usar a quantidade de assentos
    }

    public static final LocalDate dataLimite = LocalDate.now();


    /**
     * Verifica o status do evento com base na data atual em relação à data limite.
     * Se a data do evento for anterior à data limite, o evento é desativado.
     *
     * @return {@code true} se o evento ainda estiver ativo, {@code false} caso contrário
     */
    public boolean verificaEvento() {
        setAtivo(this.data.isBefore(dataLimite)  ? false : true);
        return this.ativo;
    }


    /**
     * Libera um assento no evento, aumentando a capacidade disponível.
     * Só pode desocupar um assento se o evento estiver ativo e ainda houver capacidade para liberar.
     *
     * @throws IllegalStateException se o evento não estiver ativo ou a capacidade disponível for maior ou igual à capacidade total do evento
     */
    public  void desocuparAssento(){
        if (isAtivo() && capacidadeDisponivel < capacidadeEvento){
            this.capacidadeDisponivel ++;
        }
        else throw new IllegalStateException();
    }


    /**
     * Ocupa um espaço no evento, diminuindo a capacidade disponível.
     * O espaço só pode ser ocupado se o evento estiver ativo e houver assentos disponíveis.
     */
    public void ocuparEspaco(){
        if (isAtivo() && capacidadeDisponivel > 0) {
            this.capacidadeDisponivel --;
        }
    }


    /**
     * Calcula o preço do ingresso com base na capacidade disponível do evento.
     * O preço é aumentado conforme a ocupação do evento, com um aumento máximo de até 50%.
     *
     * @return o preço do ingresso ajustado de acordo com a capacidade disponível
     */
    // Método para calcular o preço do ingresso com base na capacidade disponível
    public double calcularValorIngresso() {
        double ocupacao = (capacidadeEvento - capacidadeDisponivel) / capacidadeEvento;
        double aumentoPorOcupacao = ocupacao * 0.5;  // Aumenta em até 50% conforme a ocupação

        return valorIngresso + (valorIngresso * aumentoPorOcupacao);
    }


    public void setAvalicoes(List<Avaliacao> avalicoes) {
        this.avalicoes = avalicoes;
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

    public void setData(LocalDate data) {
        this.data = data;
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

    public String getId() {
        return this.id;
    }
}
