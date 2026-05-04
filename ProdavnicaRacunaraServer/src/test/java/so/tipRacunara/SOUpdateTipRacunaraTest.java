package so.tipRacunara;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import db.DBBroker;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author PAVLE
 */
public class SOUpdateTipRacunaraTest {

    private SOUpdateTipRacunara soUpdate;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws Exception {
        soUpdate = new SOUpdateTipRacunara();
        tip = new TipRacunara();
    }

    @AfterEach
    void tearDown() throws Exception {
        soUpdate = null;
        tip = null;
    }

    @Test
    @DisplayName("Uspesno azuriranje tipa racunara")
    void testExecuteSuccessful() throws Exception {
        tip.setNaziv("Naziv1" + System.currentTimeMillis());

        DBBroker.getInstance().insert(tip);

        @SuppressWarnings("unchecked")
        ArrayList<TipRacunara> tipovi
                = (ArrayList<TipRacunara>) (ArrayList<?>) DBBroker.getInstance().select(tip);

        TipRacunara ubacen = tipovi.stream()
                .filter(tr -> tr.getNaziv().equals(tip.getNaziv()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tip racunara nije pronadjen nakon inserta!"));

        String noviNaziv = "noviNaziv" + System.currentTimeMillis();
        ubacen.setNaziv(noviNaziv);

        assertDoesNotThrow(() -> soUpdate.validate(ubacen));
        assertDoesNotThrow(() -> soUpdate.execute(ubacen));

        @SuppressWarnings("unchecked")
        ArrayList<TipRacunara> lista
                = (ArrayList<TipRacunara>) (ArrayList<?>) DBBroker.getInstance().select(new TipRacunara());

        assertTrue(
                lista.stream()
                        .anyMatch(tr -> tr.getTipRacunaraID().equals(ubacen.getTipRacunaraID())
                        && tr.getNaziv().equals(noviNaziv)),
                "Tip racunara nije azuriran sa novim nazivom u bazi!");

        DBBroker.getInstance().delete(ubacen);
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soUpdate.validate(new Administrator()));
    }

    @Test
    @DisplayName("Naziv vec postoji za drugi tip racunara")
    void testValidationDuplicateNaziv() throws Exception {
        String naziv = "testNaziv" + System.currentTimeMillis();

        TipRacunara prviTip = new TipRacunara();
        prviTip.setNaziv(naziv);
        DBBroker.getInstance().insert(prviTip);

        TipRacunara drugiTip = new TipRacunara();
        drugiTip.setNaziv("drugiNaziv" + System.currentTimeMillis());
        DBBroker.getInstance().insert(drugiTip);

        @SuppressWarnings("unchecked")
        ArrayList<TipRacunara> tipovi
                = (ArrayList<TipRacunara>) (ArrayList<?>) DBBroker.getInstance().select(new TipRacunara());

        TipRacunara prviIzBaze = tipovi.stream()
                .filter(tr -> tr.getNaziv().equals(prviTip.getNaziv()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Prvi tip racunara nije pronadjen!"));

        TipRacunara drugiIzBaze = tipovi.stream()
                .filter(tr -> tr.getNaziv().equals(drugiTip.getNaziv()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Drugi tip racunara nije pronadjen!"));

        drugiIzBaze.setNaziv(prviIzBaze.getNaziv());

        assertThrows(Exception.class, () -> soUpdate.validate(drugiIzBaze));

        DBBroker.getInstance().delete(prviIzBaze);
        DBBroker.getInstance().delete(drugiIzBaze);
    }
}
