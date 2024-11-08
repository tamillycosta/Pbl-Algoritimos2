package models;

import java.security.SecureRandom;
import java.util.Objects;


public class Adimin extends  Usuario{


    /**
     * Uma subclasse de {@link Usuario} que representa um usuário administrador. Esta classe fornece
     * funcionalidade adicional para gerar uma chave de segurança usando {@link SecureRandom}.
     */
    static final private SecureRandom chaveSegrança =  new SecureRandom();;

    /**
     *
     * @param login
     * @param senha
     * @param cpf
     * @param email
     */
    public Adimin(String login, String senha, String cpf, String email ) {
        super(login, senha, cpf, email);
        gerarChaveSeguranca();
    }


    /**
     * Gera uma chave de segurança aleatória no formato hexadecimal.
     *
     * @return uma string hexadecimal de 32 caracteres que representa a chave de segurança
     */
    public String gerarChaveSeguranca() {
        byte[] bytes = new byte[16]; // Gera 16 bytes aleatórios
        Adimin.chaveSegrança.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b)); // Converte cada byte em uma string hexadecimal
        }
        return sb.toString();
    }


    /**
     * Retorna a instância de {@link SecureRandom} usada para gerar a chave de segurança.
     *
     * @return a instância estática de {@code SecureRandom}
     */
    public SecureRandom getChaveSeguranca() {
        return Adimin.chaveSegrança;
    }
}
