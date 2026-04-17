package so.tipRacunara;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 *
 * @author PAVLE
 */
public class SOAddTipRacunara extends SistemskeOperacije {

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

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().insert(ado);

    }

}
