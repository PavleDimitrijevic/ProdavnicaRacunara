package so.tipRacunara;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import db.DBBroker;
import java.sql.SQLException;
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
public class SOAddTipRacunaraTest {
    
   private SOAddTipRacunara soAdd;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws Exception {
        soAdd = new SOAddTipRacunara();
        tip = new TipRacunara();
    }

    @AfterEach
    void tearDown() {
        soAdd = null;
        tip = null;
    }

    @Test
    @DisplayName("Uspesno dodavanje tipa racunara u bazu")
    void testExecuteSuccessful() throws SQLException {
        tip.setNaziv("TestTip" + System.currentTimeMillis());

        assertDoesNotThrow(() -> soAdd.validate(tip));
        assertDoesNotThrow(() -> soAdd.execute(tip));

        @SuppressWarnings("unchecked")
        ArrayList<TipRacunara> tipovi =
                (ArrayList<TipRacunara>) (ArrayList<?>) DBBroker.getInstance().select(tip);

        assertTrue(tipovi.stream().anyMatch(tr -> tr.getNaziv().equals(tip.getNaziv())));

        DBBroker.getInstance().delete(tip);
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soAdd.validate(new Administrator()));
    }

    @Test
    @DisplayName("Naziv nije jedinstven u bazi")
    void testValidationDuplicateNaziv() throws Exception {
        String naziv = "naziv" + System.currentTimeMillis();

        TipRacunara prvi = new TipRacunara();
        prvi.setNaziv(naziv);
        DBBroker.getInstance().insert(prvi);

        TipRacunara drugi = new TipRacunara();
        drugi.setNaziv(naziv);

        assertThrows(Exception.class, () -> soAdd.validate(drugi));

        DBBroker.getInstance().delete(prvi);
    }

    @Test
    @DisplayName("Naziv je jedinstven u bazi")
    void testValidationUniqueNaziv() {
        tip.setNaziv("TestNaziv" + System.currentTimeMillis());

        assertDoesNotThrow(() -> soAdd.validate(tip));
    }
    
}
