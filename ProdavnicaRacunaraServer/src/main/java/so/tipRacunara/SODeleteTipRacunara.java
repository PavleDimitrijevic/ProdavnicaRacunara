package so.tipRacunara;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import so.SistemskeOperacije;

/**
 *
 * @author PAVLE
 */
public class SODeleteTipRacunara extends SistemskeOperacije {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof TipRacunara)) {
            throw new Exception("Prosledjeni objekat nije instanca klase TipRacunara!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().delete(ado);
    }

}
