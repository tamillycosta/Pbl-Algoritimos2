package repository;
import repository.abstrata.Repository;
import models.Adimin;

public class AdminRepository extends Repository <Adimin> {

    public static final String diretorio = "dataBase/admin/";

    public AdminRepository() {
        super(diretorio, Adimin.class);
    }

    @Override
    public String getId(Adimin adimin) {
        return  adimin.getId();
    }

}
