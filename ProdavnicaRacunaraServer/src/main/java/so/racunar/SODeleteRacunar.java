package so.racunar;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za brisanje racunara iz baze podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SODeleteRacunar extends SistemskeOperacije {

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi brisanje
     * iz baze.
     * <p>
     * Ova metoda proverava da li je objekat instanca klase Racunar. Ako nije,
     * baca se izuzetak i brisanje se ne izvrsava.
     * </p>
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Racunar
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Racunar)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Racunar!");
        }
    }

    /**
     * Izvrsava brisanje racunara iz baze podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Metoda koristi delete
     * metodu iz klase DBBroker kako bi uklonila zapis iz baze podataka koji
     * odgovara prosledjenom objektu klase Racunar.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se brise
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * operacije za brisanje iz baze
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().delete(ado);
    }
}
