package so.racun;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.StavkaRacuna;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 *
 * @author PAVLE
 */
public class SOGetAllRacun extends SistemskeOperacije {

    private ArrayList<Racun> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Racun)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Racun!");
        }
    }

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

    public ArrayList<Racun> getLista() {
        return lista;
    }

}
