package so.racunar;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Komponenta;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za dodavanje novog racunara u bazu podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOAddRacunar extends SistemskeOperacije {

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * dodavanje u bazu. Metoda proverava:
     * <ul>
     * <li>Da li je objekat instanca klase Racunar.</li>
     * <li>Da li je cena racunara u rasponu od 100000 do 500000 dinara.</li>
     * <li>Da li racunar ima bar dve komponente.</li>
     * <li>Da li vec postoji racunar sa istim nazivom u bazi.</li>
     * </ul>
     * Ako bilo koji od uslova nije ispunjen, baca se izuzetak.
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Racunar, cena
     * nije validna, racunar nema dovoljno komponenti ili naziv vec postoji u
     * bazi
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

        if (r.getKomponente().size() < 2) {
            throw new Exception("Racunar mora da ima barem dve komponente!");
        }

        ArrayList<Racunar> racunari = (ArrayList<Racunar>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Racunar racunar : racunari) {
            if (racunar.getNaziv().equals(r.getNaziv())) {
                throw new Exception("Ovaj naziv racunara vec postoji!");
            }
        }

    }

    /**
     * Izvrsava dodavanje novog racunara u bazu podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * insert metodu iz klase DBBroker kako bi upisala novi zapis u bazu
     * podataka. Ubacuje racunar, postavlja generisani ID i ubacuje sve povezane
     * komponente.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se dodaje
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za dodavanje u bazu
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {

        PreparedStatement ps = DBBroker.getInstance().insert(ado); //racunar je autoinc

        //ovde vracam kljuceve svakog racunara
        ResultSet tableKeys = ps.getGeneratedKeys();
        tableKeys.next();
        Long racunarID = tableKeys.getLong(1);

        //id je bio null - to sam radio u btndodajracunar, ovim ga setujem
        Racunar r = (Racunar) ado;
        r.setRacunarID(racunarID);

        //dodajem komponentu za svaki racunar
        for (Komponenta komponenta : r.getKomponente()) {
            komponenta.setRacunar(r); //racunar sam stavio na null u btndodajkomponentu, pa moram prvo da je setujem ovde
            DBBroker.getInstance().insert(komponenta);
        }

    }
}
