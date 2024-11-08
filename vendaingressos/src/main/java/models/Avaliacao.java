package models;

public class Avaliacao {
    private Usuario usuario;
    private String comentario;
    private  int estrelas;


    public Avaliacao(Usuario usuario, String comentario, int estrelas) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.estrelas = estrelas;
    }


    @Override
    public String toString() {
        return "Avaliacao{" +
                "usuario=" + usuario +
                ", comentario='" + comentario + '\'' +
                ", estrelas=" + estrelas +
                '}';
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        if (comentario == null || comentario.isEmpty()) {
            throw new IllegalArgumentException("Comentário não pode ser vazio.");
        }
        this.comentario = comentario;
    }

    public int getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(int estrelas) {
        this.estrelas = estrelas;
    }
}
