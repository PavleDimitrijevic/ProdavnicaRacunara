package so.racunar;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Komponenta;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za ucitavanje svih racunara iz baze podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOGetAllRacunar extends SistemskeOperacije {

    /**
     * Lista svih racunara izabranih iz baze podataka.
     */
    private ArrayList<Racunar> lista;

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * ucitavanje iz baze.
     * <p>
     * Ova metoda proverava da li je objekat instanca klase Racunar. Ako nije,
     * baca se izuzetak i ucitavanje se ne izvrsava.
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
     * Izvrsava ucitavanje svih racunara iz baze podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * select metodu iz klase DBBroker kako bi ucitala zapise iz baze podataka.
     * Ucitava racunare i komponente svih racunara.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se ucitava
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za ucitavanje iz baze
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {

        ArrayList<AbstractDomainObject> racunari = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Racunar>) (ArrayList<?>) racunari;

        //racunar ima slab objekat komponenta, tako da sada moram da vratim i komponente za vracene racunare
        for (Racunar racunar : lista) {
            Komponenta komponenta = new Komponenta();
            komponenta.setRacunar(racunar); //for each ce da prodje kroz listu racunara i da setuje na trenutno na koji je dosao

            ArrayList<Komponenta> komponente
                    = (ArrayList<Komponenta>) (ArrayList<?>) DBBroker.getInstance().select(komponenta); //vraca komponente za taj racunar koji je trenutni
            //u klasi Komponenta je prosledjen uslov za select koji ce DBB da iskoristi

            racunar.setKomponente(komponente); //trenutan racunar dobija komponente vracene selectom
        }

    }

    /**
     * Vraca listu svih racunara i njihovih komponenti ucitanih iz baze
     * podataka.
     *
     * @return lista racunara
     */
    public ArrayList<Racunar> getLista() {
        return lista;
    }
}
