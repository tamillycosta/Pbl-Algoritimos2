package repository;

import models.NotaFiscal;
import repository.abstrata.Repository;

public class NotaFiscalRepository extends Repository<NotaFiscal> {

    public  static final String diretorio = "dataBase/usuarios/";

    public NotaFiscalRepository( ) {
        super(diretorio, NotaFiscal.class);
    }

    @Override
    public String getId(NotaFiscal entidade) {
        return entidade.getId();
    }

    @Override
    public String getDiretorioEspecifico(NotaFiscal entidade) {
        return  diretorio + entidade.getComprador().getLogin() +  "/";
    }

}
