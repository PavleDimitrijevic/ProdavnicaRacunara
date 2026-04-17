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
public class SOGetAllTipRacunara extends SistemskeOperacije {

    private ArrayList<TipRacunara> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof TipRacunara)) {
            throw new Exception("Prosledjeni objekat nije instanca klase TipRacunara!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> tipoviRacunara = DBBroker.getInstance().select(ado);
        lista = (ArrayList<TipRacunara>) (ArrayList<?>) tipoviRacunara;
    }

    public ArrayList<TipRacunara> getLista() {
        return lista;
    }
}
