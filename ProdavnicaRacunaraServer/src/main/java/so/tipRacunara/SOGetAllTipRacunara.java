package so.tipRacunara;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za ucitavanje svih tipova racunara iz baze podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOGetAllTipRacunara extends SistemskeOperacije {

    /**
     * Lista svih tipova racunara izabranih iz baze podataka.
     */
    private ArrayList<TipRacunara> lista;

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * ucitavanje iz baze.
     * <p>
     * Ova metoda proverava da li je objekat instanca klase TipRacunara. Ako
     * nije, baca se izuzetak i ucitavanje se ne izvrsava.
     * </p>
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase TipRacunara
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof TipRacunara)) {
            throw new Exception("Prosledjeni objekat nije instanca klase TipRacunara!");
        }
    }

    /**
     * Izvrsava ucitavanje svih tipova racunara iz baze podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * select metodu iz klase DBBroker kako bi ucitala zapise iz baze podataka.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se ucitava
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za ucitavanje iz baze
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> tipoviRacunara = DBBroker.getInstance().select(ado);
        lista = (ArrayList<TipRacunara>) (ArrayList<?>) tipoviRacunara;
    }

    /**
     * Vraca listu svih tipova racunara ucitanih iz baze podataka.
     *
     * @return lista tipova racunara
     */
    public ArrayList<TipRacunara> getLista() {
        return lista;
    }
}
