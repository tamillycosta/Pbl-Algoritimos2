package models;

public class Avaliacao {
    private Usuario usuario;
    private String comentario;
    private  int estrelas;


    /***
     *
     * @param usuario
     * @param comentario
     * @param estrelas
     */
    public Avaliacao(Usuario usuario, String comentario, int estrelas) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.estrelas = setEstrelas(estrelas);
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

    /***
     * Define o comentário associado. O comentário não pode ser nulo ou vazio.
     *
     * @param comentario o comentário a ser definido
     * @throws IllegalArgumentException se o comentário for nulo ou vazio
     */

    public void setComentario(String comentario) {
        if (comentario == null || comentario.isEmpty()) {
            throw new IllegalArgumentException("Comentário não pode ser vazio.");
        }
        this.comentario = comentario;
    }

    public int getEstrelas() {
        return estrelas;
    }

    /***
     * Define a quantidade de estrelas, limitando o valor a no máximo 5.
     *
     * @param estrelas a quantidade de estrelas a ser definida
     * @return o valor de estrelas definido
     */
    public int setEstrelas(int estrelas) {
        if(estrelas <= 5){
            this.estrelas = estrelas;
        }
        return estrelas;
    }


}
