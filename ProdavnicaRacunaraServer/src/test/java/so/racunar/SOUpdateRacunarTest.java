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
class SOUpdateRacunarTest {

    private SOUpdateRacunar soUpdate;
    private Racunar racunar;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws SQLException {
        soUpdate = new SOUpdateRacunar();

        tip = new TipRacunara();
        tip.setNaziv("TipRacunara" + System.currentTimeMillis());

        PreparedStatement psT = DBBroker.getInstance().insert(tip);
        ResultSet keysT = psT.getGeneratedKeys();
        if (keysT.next()) {
            tip.setTipRacunaraID(keysT.getLong(1));
        }

        racunar = new Racunar();
        racunar.setNaziv("TestUpdateRacunar" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(200000);
        racunar.setOpis("Originalni opis");
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
        soUpdate = null;
        racunar = null;
        tip = null;
    }

    @Test
    @DisplayName("Uspesna izmena racunara")
    void testExecuteSuccessful() throws SQLException {
        racunar.setCenaPoKomadu(250000);
        racunar.setOpis("Izmenjen opis");

        assertDoesNotThrow(() -> soUpdate.validate(racunar));
        assertDoesNotThrow(() -> soUpdate.execute(racunar));

        Racunar proba = new Racunar();
        proba.setRacunarID(racunar.getRacunarID());

        @SuppressWarnings("unchecked")
        ArrayList<Racunar> lista
                = (ArrayList<Racunar>) (ArrayList<?>) DBBroker.getInstance().select(proba);

        assertTrue(
                lista.stream()
                        .anyMatch(r -> r.getRacunarID().equals(racunar.getRacunarID())
                        && r.getOpis().equals("Izmenjen opis")
                        && r.getCenaPoKomadu() == 250000),
                "Izmena racunara nije upisana u bazu!");
    }

    @Test
    @DisplayName("Pogresan tip objekta")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soUpdate.validate(new Administrator()));
    }

    @Test
    @DisplayName("Uspesna validacija")
    void testValidateValidType() {
        assertDoesNotThrow(() -> soUpdate.validate(racunar));
    }

    @Test
    @DisplayName("Naziv racunara nije jedinstven")
    void testValidationDuplicateNaziv() throws Exception {
        String naziv = "TestNaziv" + System.currentTimeMillis();

        Racunar r1 = new Racunar();
        r1.setNaziv(naziv);
        r1.setCenaPoKomadu(200000);
        r1.setOpis("Prvi racunar");
        r1.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta(r1, 1, "Procesor");
        Komponenta k2 = new Komponenta(r1, 2, "RAM");

        komponente.add(k1);
        komponente.add(k2);
        r1.setKomponente(komponente);

        PreparedStatement ps1 = DBBroker.getInstance().insert(r1);
        ResultSet keys1 = ps1.getGeneratedKeys();
        if (keys1.next()) {
            r1.setRacunarID(keys1.getLong(1));
        }

        Racunar r2 = new Racunar();
        r2.setNaziv("NazivDrugog" + System.currentTimeMillis());
        r2.setCenaPoKomadu(300000);
        r2.setOpis("Drugi racunar");
        r2.setTipRacunara(tip);

        ArrayList<Komponenta> komponente2 = new ArrayList<>();

        Komponenta k21 = new Komponenta(r2, 1, "Graficka");
        Komponenta k22 = new Komponenta(r2, 2, "SSD");

        komponente2.add(k21);
        komponente2.add(k22);
        r2.setKomponente(komponente2);

        PreparedStatement ps2 = DBBroker.getInstance().insert(r2);
        ResultSet keys2 = ps2.getGeneratedKeys();
        if (keys2.next()) {
            r2.setRacunarID(keys2.getLong(1));
        }

        r2.setNaziv(r1.getNaziv());

        assertThrows(Exception.class, () -> soUpdate.validate(r2));

        DBBroker.getInstance().delete(r1);
        DBBroker.getInstance().delete(r2);
    }

    @Test
    @DisplayName("Cena je preniska")
    void testValidateCenaPreniska() {
        racunar.setCenaPoKomadu(50000);
        assertThrows(Exception.class, () -> soUpdate.validate(racunar));
    }

    @Test
    @DisplayName("Cena je previsoka")
    void testValidateCenaPrevisoka() {
        racunar.setCenaPoKomadu(600000);
        assertThrows(Exception.class, () -> soUpdate.validate(racunar));
    }
}
