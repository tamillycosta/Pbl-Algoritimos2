package vendaingressos;
import java.util.Date;
import java.util.List;


public class Evento {
    private String nome;
    private String descricao;
    private Date data;
    private List<String> assentosDisponiveis;
    private boolean ativo;

    public Evento(String nome, String descricao, Date data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
    }

    public void adicionarAssento(String assento){
            if(isAtivo() && !assentosDisponiveis.contains(assento)){
                assentosDisponiveis.add(assento);
            }
    }

    public void removerAssento(String assento){
        if(isAtivo() && assentosDisponiveis.contains(assento)){
            assentosDisponiveis.remove(assento);
        }
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<String> getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(List<String> assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
