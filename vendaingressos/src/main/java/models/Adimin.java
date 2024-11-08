package models;

import java.security.SecureRandom;
import java.util.Objects;

public class Adimin extends  Usuario{

    static final private SecureRandom chaveSegrança =  new SecureRandom();;

    public Adimin(String login, String senha, String nome, String cpf, String email ) {
        super(login, senha, nome, cpf, email);
        gerarChaveSeguranca();
    }

    public boolean login(String login, String senha, SecureRandom chaveSegrança){
        return Objects.equals(getLogin(), login) && Objects.equals(getSenha(), senha);
    }


    public String gerarChaveSeguranca() {
        byte[] bytes = new byte[16]; // Gera 16 bytes aleatórios
        Adimin.chaveSegrança.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b)); // Converte cada byte em uma string hexadecimal
        }
        return sb.toString();
    }

    public SecureRandom getChaveSeguranca() {
        return Adimin.chaveSegrança;
    }
}
