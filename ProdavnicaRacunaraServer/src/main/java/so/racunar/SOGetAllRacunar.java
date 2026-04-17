package so.racunar;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Komponenta;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 *
 * @author PAVLE
 */
public class SOGetAllRacunar extends SistemskeOperacije {

    private ArrayList<Racunar> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Racunar)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Racunar!");
        }
    }

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

    public ArrayList<Racunar> getLista() {
        return lista;
    }
}
