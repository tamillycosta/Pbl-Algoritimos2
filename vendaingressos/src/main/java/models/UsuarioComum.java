    package models;

    import java.util.ArrayList;
    import java.util.List;

    public class UsuarioComum  extends Usuario{

        private List<Ingresso> ingressos;

        /***
         *
         * @param login
         * @param senha
         * @param cpf
         * @param email
         */
        public UsuarioComum(String login, String senha, String cpf, String email) {
            super(login, senha, cpf, email);
            this.ingressos = new ArrayList<>();
        }

        public List<Ingresso> getIngressos() {
            return ingressos;
        }

        public void setIngressos(List<Ingresso> ingressos) {
            this.ingressos = ingressos;
        }


    }
