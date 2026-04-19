package so.racun;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.StavkaRacuna;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za dodavanje novog racuna u bazu podataka.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode
 * validate() i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOAddRacun extends SistemskeOperacije {

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * dodavanje u bazu. Metoda proverava:
     * <ul>
     * <li>Da li je objekat instanca klase Racun.</li>
     * <li>Da li racun ima bar jednu stavku.</li>
     * </ul>
     * Ako bilo koji od uslova nije ispunjen, baca se izuzetak.
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Racun ili ako
     * nema stavki na racunu
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Racun)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Racun!");
        }

        Racun ra = (Racun) ado;

        if (ra.getStavkeRacuna().isEmpty()) {
            throw new Exception("Racun mora da ima bar jednu stavku!");
        }
    }

    /**
     * Izvrsava dodavanje novog racuna u bazu podataka.
     * <p>
     * Poziva se samo ako je validacija uspesno prosla. Implementacija koristi
     * insert metodu iz klase DBBroker kako bi upisala novi zapis u bazu
     * podataka. Ubacuje racun, postavlja generisani ID i ubacuje sve povezane
     * stavke racuna.
     * </p>
     *
     * @see DBBroker
     * @param ado Domen objekat koji se dodaje
     * @throws java.lang.Exception ako dodje do greske tokom izvrsavanja SQL
     * komande za dodavanje u bazu
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {

        PreparedStatement ps = DBBroker.getInstance().insert(ado);

        ResultSet tableKeys = ps.getGeneratedKeys();
        tableKeys.next();
        Long racunID = tableKeys.getLong(1);

        Racun ra = (Racun) ado;
        ra.setRacunID(racunID);

        for (StavkaRacuna stavkaRacuna : ra.getStavkeRacuna()) {
            stavkaRacuna.setRacun(ra);
            DBBroker.getInstance().insert(stavkaRacuna);
        }
    }

}
