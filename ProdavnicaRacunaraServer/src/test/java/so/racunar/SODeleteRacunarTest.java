package so.racunar;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Komponenta;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;

/**
 *
 * @author PAVLE
 */
class SODeleteRacunarTest {

    private SODeleteRacunar soDelete;
    private Racunar racunar;
    private TipRacunara tip;

    @BeforeEach
    public void setUp() throws Exception {
        soDelete = new SODeleteRacunar();

        tip = new TipRacunara();
        tip.setNaziv("TipRacunara" + System.currentTimeMillis());

        PreparedStatement psT = DBBroker.getInstance().insert(tip);
        ResultSet keysT = psT.getGeneratedKeys();
        if (keysT.next()) {
            tip.setTipRacunaraID(keysT.getLong(1));
        }

        racunar = new Racunar();
        racunar.setNaziv("TestDeleteRacunar" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(200000);
        racunar.setOpis("Test opis");
        racunar.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta();
        k1.setNaziv("K1" + System.currentTimeMillis());
        k1.setRb(1);

        Komponenta k2 = new Komponenta();
        k2.setNaziv("K2" + System.currentTimeMillis());
        k2.setRb(2);

        komponente.add(k1);
        komponente.add(k2);
        racunar.setKomponente(komponente);

        PreparedStatement ps = DBBroker.getInstance().insert(racunar);
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            racunar.setRacunarID(keys.getLong(1));
        }
    }

    @AfterEach
    void tearDown() {
        soDelete = null;
        racunar = null;
        tip = null;
    }

    @Test
    @DisplayName("Uspesno brisanje racunara")
    void testExecuteSuccessful() throws SQLException {
        assertDoesNotThrow(() -> soDelete.validate(racunar));
        assertDoesNotThrow(() -> soDelete.execute(racunar));

        Racunar proba = new Racunar();
        proba.setRacunarID(racunar.getRacunarID());

        @SuppressWarnings("unchecked")
        ArrayList<Racunar> lista
                = (ArrayList<Racunar>) (ArrayList<?>) DBBroker.getInstance().select(proba);

        assertFalse(lista.stream().anyMatch(r -> r.getRacunarID().equals(racunar.getRacunarID())));
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soDelete.validate(new Administrator()));
    }

    @Test
    public void testValidationValid() {
        assertDoesNotThrow(() -> soDelete.validate(racunar));
    }

}
