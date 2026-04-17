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
 *
 * @author PAVLE
 */
public class SOAddRacunar extends SistemskeOperacije {

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
