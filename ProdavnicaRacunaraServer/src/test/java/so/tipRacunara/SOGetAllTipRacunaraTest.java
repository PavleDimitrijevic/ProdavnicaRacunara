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
public class SOGetAllTipRacunaraTest {

    private SOGetAllTipRacunara soGetAll;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws Exception {
        soGetAll = new SOGetAllTipRacunara();
        tip = new TipRacunara();
    }

    @AfterEach
    void tearDown() throws Exception {
        soGetAll = null;
        tip = null;
    }

    @Test
    @DisplayName("Uspesno vracanje svih tipova racunara iz baze")
    void testExecuteSuccessful() throws SQLException {
        tip.setNaziv("TipZaVracanje" + System.currentTimeMillis());
        DBBroker.getInstance().insert(tip);

        assertDoesNotThrow(() -> soGetAll.validate(tip));
        assertDoesNotThrow(() -> soGetAll.execute(tip));

        ArrayList<TipRacunara> lista = soGetAll.getLista();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(tr -> tr.getNaziv().equals(tip.getNaziv())));

        DBBroker.getInstance().delete(tip);
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soGetAll.validate(new Administrator()));
    }

    @Test
    @DisplayName("Validacija ispravnog objekta")
    void testValidationValidObject() {
        tip.setNaziv("TipTest" + System.currentTimeMillis());

        assertDoesNotThrow(() -> soGetAll.validate(tip));
    }

}
