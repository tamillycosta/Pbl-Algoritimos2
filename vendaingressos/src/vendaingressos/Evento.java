package vendaingressos;
import java.util.ArrayList;
import java.util.Calendar;
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
        this.ativo = verificaEvento();
        this.assentosDisponiveis = new ArrayList<>();
    }

    public static final Date dataLimite;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);  // Define a DATA_LIMITE para 1 de janeiro de 2024
        dataLimite = calendar.getTime();
    }


    public boolean verificaEvento(){
        if(this.data.before(dataLimite)){
            setAtivo(false);

        }
        else{setAtivo(true);}

        return this.ativo;
    }

    public List<String> adicionarAssento(String assento){
            if(this.ativo){
                setAssentosDisponiveis(assento);
            }
            return getAssentosDisponiveis();
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

    public void setAssentosDisponiveis(String assentosDisponiveis) {
        this.assentosDisponiveis.add(assentosDisponiveis) ;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
