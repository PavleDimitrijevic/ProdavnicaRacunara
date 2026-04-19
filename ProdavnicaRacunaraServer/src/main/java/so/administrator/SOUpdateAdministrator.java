package so.administrator;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za izmenu postojeceg administratora u bazi podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOUpdateAdministrator extends SistemskeOperacije {

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * dodavanje u bazu. Metoda proverava:
     * <ul>
     * <li>Da li je objekat instanca klase Administrator.</li>
     * <li>Da li vec postoji administrator sa istim korisnickim imenom
     * (username) u bazi.</li>
     * </ul>
     * Ako bilo koji od uslova nije ispunjen, baca se izuzetak.
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Administrator
     * ili ako vec postoji username u bazi
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Administrator)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Administrator!");
        }

        Administrator a = (Administrator) ado;

        ArrayList<Administrator> administratori = (ArrayList<Administrator>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Administrator administrator : administratori) {
            if (!administrator.getAdministratorID().equals(a.getAdministratorID())) {
                if (administrator.getUsername().equals(a.getUsername())) {
                    throw new Exception("Korisnicko ime mora biti jedinstveno!");
                }
            }
        }

    }

    /**
     * Izvrsava izmenu administratora u bazi podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * update metodu iz klase DBBroker kako bi izmenila zapis u bazi podataka.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se menja
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za izmenu u bazi
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().update(ado);
    }
}
