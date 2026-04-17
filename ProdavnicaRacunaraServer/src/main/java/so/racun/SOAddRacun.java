package so.racun;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.StavkaRacuna;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import so.SistemskeOperacije;

/**
 *
 * @author PAVLE
 */
public class SOAddRacun extends SistemskeOperacije {

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
