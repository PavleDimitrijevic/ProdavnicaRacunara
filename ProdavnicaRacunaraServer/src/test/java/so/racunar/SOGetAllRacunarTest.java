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
class SOGetAllRacunarTest {

    private SOGetAllRacunar soGetAll;
    private Racunar racunar;
    private TipRacunara tip;
    private ArrayList<Komponenta> komponente;

    @BeforeEach
    void setUp() throws Exception {
        soGetAll = new SOGetAllRacunar();

        tip = new TipRacunara();
        tip.setNaziv("TestGetAllTip" + System.currentTimeMillis());

        PreparedStatement psT = DBBroker.getInstance().insert(tip);
        ResultSet keysT = psT.getGeneratedKeys();
        if (keysT.next()) {
            tip.setTipRacunaraID(keysT.getLong(1));
        }

        racunar = new Racunar();
        racunar.setNaziv("TestRacunar" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(200000);
        racunar.setOpis("Opis test racunara");
        racunar.setTipRacunara(tip);

        komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta();
        k1.setNaziv("Procesor" + System.currentTimeMillis());
        k1.setRb(1);
        k1.setRacunar(racunar);
        komponente.add(k1);

        Komponenta k2 = new Komponenta();
        k2.setNaziv("RAM" + System.currentTimeMillis());
        k2.setRb(2);
        k2.setRacunar(racunar);
        komponente.add(k2);

        racunar.setKomponente(komponente);

        PreparedStatement psR = DBBroker.getInstance().insert(racunar);
        ResultSet keysR = psR.getGeneratedKeys();
        if (keysR.next()) {
            racunar.setRacunarID(keysR.getLong(1));
        }

        DBBroker.getInstance().insert(k1);
        DBBroker.getInstance().insert(k2);
    }

    @AfterEach
    void tearDown() throws SQLException {
        tip = null;
        racunar = null;
        soGetAll = null;
        komponente = null;
    }

    @Test
    @DisplayName("Uspesno vracanje svih racunara iz baze")
    void testExecuteSuccessful() throws SQLException {
        assertDoesNotThrow(() -> soGetAll.validate(racunar));
        assertDoesNotThrow(() -> soGetAll.execute(racunar));

        ArrayList<Racunar> lista = soGetAll.getLista();

        assertNotNull(lista, "Lista racunara ne sme biti null");

        Racunar ucitanTestRacunar = lista.stream()
                .filter(r -> r.getRacunarID().equals(racunar.getRacunarID()))
                .findFirst()
                .orElse(null);

        assertNotNull(ucitanTestRacunar, "Test racunar koji smo ubacili mora biti pronadjen.");
        assertNotNull(ucitanTestRacunar.getKomponente(), "Lista komponenti za test racunar ne sme biti null.");
        assertEquals(2, ucitanTestRacunar.getKomponente().size(), "Test racunar treba da ima 2 komponente.");
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soGetAll.validate(new Administrator()));
    }

    @Test
    @DisplayName("Validacija ispravnog objekta")
    void testValidationValidObject() {
        assertDoesNotThrow(() -> soGetAll.validate(racunar));
    }

}
