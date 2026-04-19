package so.administrator;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za ucitavanje svih administratora iz baze podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode validate()
 * i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOGetAllAdministrator extends SistemskeOperacije {

    /**
     * Lista svih administratora izabranih iz baze podataka.
     */
    private ArrayList<Administrator> lista;

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * ucitavanje iz baze.
     * <p>
     * Ova metoda proverava da li je objekat instanca klase Administrator. Ako
     * nije, baca se izuzetak i ucitavanje se ne izvrsava.
     * </p>
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Administrator
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Administrator)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Administrator!");
        }
    }

    /**
     * Izvrsava ucitavanje svih administratora iz baze podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * select metodu iz klase DBBroker kako bi ucitala zapis iz baze podataka.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se ucitava
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za ucitavanje iz baze
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> administratori = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Administrator>) (ArrayList<?>) administratori;
    }

    /**
     * Vraca listu svih administratora ucitanih iz baze podataka.
     *
     * @return lista administratora
     */
    public ArrayList<Administrator> getLista() {
        return lista;
    }
}
