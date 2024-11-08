package repository;


import models.UsuarioComum;
import repository.abstrata.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class UserRepository  extends Repository<UsuarioComum> {

    public static final String diretorioBase = "dataBase/usuarios/";

    public UserRepository() {
        super(diretorioBase , UsuarioComum.class);
    }


    @Override
    public String getDiretorioEspecifico(UsuarioComum usuarioComum) {
        //  diretório é personalizado para cada usuário
        return diretorioBase + usuarioComum.getLogin() + "/";
    }


    @Override
    public String getId(UsuarioComum usuarioComum) {
        return usuarioComum.getId();
    }


    public boolean buscarLogin(String login) {
        Path caminhoDiretorioUsuario = Paths.get(diretorioBase + login);
        return Files.exists(caminhoDiretorioUsuario) && Files.isDirectory(caminhoDiretorioUsuario);
    }




}