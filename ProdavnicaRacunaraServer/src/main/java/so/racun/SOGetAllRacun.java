package so.racun;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.StavkaRacuna;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za ucitavanje svih racuna iz baze podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOGetAllRacun extends SistemskeOperacije {

    /**
     * Lista svih racuna izabranih iz baze.
     */
    private ArrayList<Racun> lista;

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * ucitavanje iz baze.
     * <p>
     * Ova metoda proverava da li je objekat instanca klase Racun. Ako nije,
     * baca se izuzetak i ucitavanje se ne izvrsava.
     * </p>
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Racun
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Racun)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Racun!");
        }
    }

    /**
     * Izvrsava ucitavanje svih racuna iz baze podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * select metodu iz klase DBBroker kako bi ucitala zapis iz baze podataka.
     * Ucitava racune i stavke svih racuna.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se ucitava
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za ucitavanje iz baze
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> racuni = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Racun>) (ArrayList<?>) racuni;

        for (Racun racun : lista) {

            StavkaRacuna stavkaRacuna = new StavkaRacuna();
            stavkaRacuna.setRacun(racun); //stavka racuna se vezuje za racun na kom je for each u tom momentu

            ArrayList<StavkaRacuna> stavkeRacuna
                    = (ArrayList<StavkaRacuna>) (ArrayList<?>) DBBroker.getInstance().select(stavkaRacuna); //dbb dobija upit za select iz klase stavkaracuna

            racun.setStavkeRacuna(stavkeRacuna); //trenutni racun iz for each dobija stavke racuna koje su vracene iz select upita
        }
    }

    /**
     * Vraca listu svih racuna i njihovih stavki ucitanih iz baze podataka.
     *
     * @return lista racuna
     */
    public ArrayList<Racun> getLista() {
        return lista;
    }

}
