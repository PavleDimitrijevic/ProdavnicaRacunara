package so.tipRacunara;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import db.DBBroker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author PAVLE
 */
public class SODeleteTipRacunaraTest {

    private SODeleteTipRacunara soDelete;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws Exception {
        soDelete = new SODeleteTipRacunara();
        tip = new TipRacunara();
    }

    @AfterEach
    void tearDown() {
        soDelete = null;
        tip = null;
    }

    @Test
    @DisplayName("Uspesno brisanje tipa racunara")
    void testExecuteSuccessful() throws Exception {
        tip.setNaziv("TipZaBrisanje" + System.currentTimeMillis());
        DBBroker.getInstance().insert(tip);

        assertDoesNotThrow(() -> soDelete.validate(tip));
        assertDoesNotThrow(() -> soDelete.execute(tip));

        DBBroker.getInstance().delete(tip);
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soDelete.validate(new Administrator()));
    }

    @Test
    @DisplayName("Validacija ispravnog tipa")
    void testValidationValidTipRacunara() {
        assertDoesNotThrow(() -> soDelete.validate(tip));
    }

}
