package so.tipRacunara;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za dodavanje novog tipa racunara u bazu podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOAddTipRacunara extends SistemskeOperacije {

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * dodavanje u bazu. Metoda proverava:
     * <ul>
     * <li>Da li je objekat instanca klase TipRacunara.</li>
     * <li>Da li vec postoji tip racunara sa istim nazivom u bazi.</li>
     * </ul>
     * Ako bilo koji od uslova nije ispunjen, baca se izuzetak.
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase TipRacunara
     * ili naziv vec postoji u bazi
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof TipRacunara)) {
            throw new Exception("Prosledjeni objekat nije instanca klase TipRacunara!");
        }

        TipRacunara tr = (TipRacunara) ado;

        ArrayList<TipRacunara> tipovi = (ArrayList<TipRacunara>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (TipRacunara tipRacunara : tipovi) {
            if (tipRacunara.getNaziv().equals(tr.getNaziv())) {
                throw new Exception("Ovaj tip racunara vec postoji!");
            }
        }

    }

    /**
     * Izvrsava dodavanje novog tipa racunara u bazu podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * insert metodu iz klase DBBroker kako bi upisala novi zapis u bazu
     * podataka.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se dodaje
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za dodavanje u bazu
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().insert(ado);

    }

}
