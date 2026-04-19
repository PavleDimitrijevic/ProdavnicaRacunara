package so.racunar;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za izmenu postojeceg racunara u bazi podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOUpdateRacunar extends SistemskeOperacije {

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi izmena u
     * bazi. Metoda proverava:
     * <ul>
     * <li>Da li je objekat instanca klase Racunar.</li>
     * <li>Da li vec postoji racunar sa istim nazivom u bazi.</li>
     * <li>Da li je cena u rasponu od 100000 do 500000 dinara.</li>
     * </ul>
     * Ako bilo koji od uslova nije ispunjen, baca se izuzetak.
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Racunar, cena
     * nije validna ili naziv vec postoji u bazi
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Racunar)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Racunar!");
        }

        Racunar r = (Racunar) ado;

        if (r.getCenaPoKomadu() < 100000 || r.getCenaPoKomadu() > 500000) {
            throw new Exception("Cena mora da bude izmedju 100000din i 500000din!");
        }

        ArrayList<Racunar> racunari = (ArrayList<Racunar>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Racunar racunar : racunari) {
            if (!racunar.getRacunarID().equals(r.getRacunarID())) {
                if (racunar.getNaziv().equals(r.getNaziv())) {
                    throw new Exception("Ovaj naziv racunara vec postoji!");
                }
            }
        }

    }

    /**
     * Izvrsava izmenu racunara u bazi podataka.
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
