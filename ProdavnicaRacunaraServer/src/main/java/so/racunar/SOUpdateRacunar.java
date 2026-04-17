package so.racunar;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 *
 * @author PAVLE
 */
public class SOUpdateRacunar extends SistemskeOperacije {

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

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().update(ado);
    }

}
