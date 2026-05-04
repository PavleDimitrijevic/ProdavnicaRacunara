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
class SOAddRacunarTest {

    private SOAddRacunar soAdd;
    private Racunar racunar;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws SQLException {
        soAdd = new SOAddRacunar();

        tip = new TipRacunara();
        tip.setNaziv("TipRacunara" + System.currentTimeMillis());

        PreparedStatement ps = DBBroker.getInstance().insert(tip);
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            tip.setTipRacunaraID(rs.getLong(1));
        }

        racunar = new Racunar();
        racunar.setTipRacunara(tip);
    }

    @AfterEach
    void tearDown() throws SQLException {
        soAdd = null;
        racunar = null;
        tip = null;
    }

    @Test
    @DisplayName("Uspesno dodavanje racunara")
    void testExecuteSuccessful() throws SQLException {
        String naziv = "nazivAddRacunar" + System.currentTimeMillis();

        racunar.setNaziv(naziv);
        racunar.setCenaPoKomadu(150000);
        racunar.setOpis("Opis test racunara");
        racunar.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta();
        k1.setNaziv("Komponenta1" + System.currentTimeMillis());
        k1.setRb(1);

        Komponenta k2 = new Komponenta();
        k2.setNaziv("Komponenta2" + System.currentTimeMillis());
        k2.setRb(2);

        komponente.add(k1);
        komponente.add(k2);
        racunar.setKomponente(komponente);

        assertDoesNotThrow(() -> soAdd.validate(racunar));
        assertDoesNotThrow(() -> soAdd.execute(racunar));

        Racunar proba = new Racunar();
        proba.setNaziv(naziv);

        @SuppressWarnings("unchecked")
        ArrayList<Racunar> lista
                = (ArrayList<Racunar>) (ArrayList<?>) DBBroker.getInstance().select(proba);

        assertTrue(lista.stream().anyMatch(r -> r.getNaziv().equals(racunar.getNaziv())));

        Komponenta probeK = new Komponenta();
        probeK.setRacunar(racunar);

        @SuppressWarnings("unchecked")
        ArrayList<Komponenta> kompIzBaze
                = (ArrayList<Komponenta>) (ArrayList<?>) DBBroker.getInstance().select(probeK);

        assertNotNull(kompIzBaze);
        assertTrue(kompIzBaze.size() >= 2, "Ocekivano najmanje 2 komponente u bazi za taj racunar");
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soAdd.validate(new Administrator()));
    }

    @Test
    @DisplayName("Cena manja od 100000")
    void testValidationInvalidCena100000() {
        racunar.setNaziv("nazivTest" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(50000);
        racunar.setOpis("Opis test racunara");
        racunar.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta();
        k1.setNaziv("K1");
        k1.setRb(1);

        Komponenta k2 = new Komponenta();
        k2.setNaziv("K2");
        k2.setRb(2);

        komponente.add(k1);
        komponente.add(k2);
        racunar.setKomponente(komponente);

        assertThrows(Exception.class, () -> soAdd.validate(racunar));
    }

    @Test
    @DisplayName("Cena veca od 500000")
    void testValidationInvalidCena500000() {
        racunar.setNaziv("nazivTest" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(600000);
        racunar.setOpis("Opis test racunara");
        racunar.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta();
        k1.setNaziv("K1");
        k1.setRb(1);

        Komponenta k2 = new Komponenta();
        k2.setNaziv("K2");
        k2.setRb(2);

        komponente.add(k1);
        komponente.add(k2);
        racunar.setKomponente(komponente);

        assertThrows(Exception.class, () -> soAdd.validate(racunar));
    }

    @Test
    @DisplayName("Nedovoljan broj komponenti")
    void testValidationNedovoljnoKomponenti() {
        racunar.setNaziv("nazivTest" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(200000);
        racunar.setOpis("Opis test racunara");
        racunar.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta();
        k1.setNaziv("Komponenta A");
        k1.setRb(1);

        komponente.add(k1);
        racunar.setKomponente(komponente);

        assertThrows(Exception.class, () -> soAdd.validate(racunar));
    }

    @Test
    @DisplayName("Naziv nije jedinstven")
    void testValidationDuplicateNaziv() throws SQLException {
        String naziv = "nazivTest" + System.currentTimeMillis();

        Racunar r1 = new Racunar();
        r1.setNaziv(naziv);
        r1.setCenaPoKomadu(200000);
        r1.setOpis("Opis prvog racunara");
        r1.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();

        Komponenta k1 = new Komponenta();
        k1.setRb(1);
        k1.setNaziv("K1" + System.currentTimeMillis());

        Komponenta k2 = new Komponenta();
        k2.setNaziv("K2" + System.currentTimeMillis());
        k2.setRb(2);

        komponente.add(k1);
        komponente.add(k2);
        r1.setKomponente(komponente);

        DBBroker.getInstance().insert(r1);

        Racunar duplikat = new Racunar();
        duplikat.setNaziv(naziv);
        duplikat.setCenaPoKomadu(300000);
        duplikat.setOpis("Opis duplikata");
        duplikat.setTipRacunara(tip);

        ArrayList<Komponenta> komponente2 = new ArrayList<>();

        Komponenta k21 = new Komponenta();
        k21.setNaziv("K21" + System.currentTimeMillis());
        k21.setRb(21);

        Komponenta k22 = new Komponenta();
        k22.setNaziv("K22" + System.currentTimeMillis());
        k22.setRb(22);

        komponente2.add(k21);
        komponente2.add(k22);
        duplikat.setKomponente(komponente2);

        assertThrows(Exception.class, () -> soAdd.validate(duplikat));
    }

}
