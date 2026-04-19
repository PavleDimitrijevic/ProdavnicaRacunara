package so.racun;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Komponenta;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import com.pavledimitrijevic.prodavnicaracunara.StavkaRacuna;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import db.DBBroker;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author PAVLE
 */
public class SOAddRacunTest {

    private SOAddRacun soAdd;
    private Racun racun;
    private Administrator administrator;
    private Racunar racunar;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws Exception {
        soAdd = new SOAddRacun();

        tip = new TipRacunara();
        tip.setNaziv("testNaziv" + System.currentTimeMillis());
        PreparedStatement psT = DBBroker.getInstance().insert(tip); // dodavanje u bazu uz uzimanje ID-a
        ResultSet keysT = psT.getGeneratedKeys();
        if (keysT.next()) {
            tip.setTipRacunaraID(keysT.getLong(1));
        }

        racunar = new Racunar();
        racunar.setNaziv("TestRacunar" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(150000);
        racunar.setOpis("Opis racunara");
        racunar.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();
        Komponenta k1 = new Komponenta();
        k1.setRb(1);
        k1.setNaziv("K1" + System.currentTimeMillis());
        k1.setRacunar(racunar);

        Komponenta k2 = new Komponenta();
        k2.setRb(2);
        k2.setNaziv("K2" + System.currentTimeMillis());
        k2.setRacunar(racunar);

        komponente.add(k1);
        komponente.add(k2);
        racunar.setKomponente(komponente);

        // ubacivanje racunara u bazu
        PreparedStatement ps = DBBroker.getInstance().insert(racunar);
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            racunar.setRacunarID(keys.getLong(1));
        }

        DBBroker.getInstance().insert(k1);
        DBBroker.getInstance().insert(k2);

        administrator = new Administrator();
        administrator.setIme("AdminAdd" + System.currentTimeMillis());
        administrator.setPrezime("Test");
        administrator.setUsername("user" + System.currentTimeMillis());
        administrator.setPassword("test12345");
        PreparedStatement psA = DBBroker.getInstance().insert(administrator);
        ResultSet keysA = psA.getGeneratedKeys();
        if (keysA.next()) {
            administrator.setAdministratorID(keysA.getLong(1));
        }

        racun = new Racun();
        racun.setDatumVreme(new Date());
        racun.setCena(300000);
        racun.setAdministrator(administrator);

        StavkaRacuna stavka = new StavkaRacuna();
        stavka.setRb(1);
        stavka.setRacunar(racunar);
        stavka.setKolicina(2);
        stavka.setCena(150000);
        stavka.setRacun(racun);

        ArrayList<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavka);
        racun.setStavkeRacuna(stavke);
    }

    @AfterEach
    void tearDown() throws SQLException {
        soAdd = null;
        racun = null;
        racunar = null;
        tip = null;
        administrator = null;
    }

    @Test
    @DisplayName("Uspesno dodavanje racuna")
    void testExecuteSuccessful() throws SQLException {
        assertDoesNotThrow(() -> soAdd.validate(racun));
        assertDoesNotThrow(() -> soAdd.execute(racun));

        // provera da li je racun dodat u bazu
        Racun proba = new Racun();
        proba.setRacunID(racun.getRacunID());

        @SuppressWarnings("unchecked")
        ArrayList<Racun> lista = (ArrayList<Racun>) (ArrayList<?>) DBBroker.getInstance().select(proba);
        assertTrue(lista.stream().anyMatch(r -> r.getRacunID().equals(racun.getRacunID())));

        // provera da li racun ima bar jednu stavku
        StavkaRacuna probaStavka = new StavkaRacuna();
        probaStavka.setRacun(racun);

        @SuppressWarnings("unchecked")
        ArrayList<StavkaRacuna> stavkeIzBaze = (ArrayList<StavkaRacuna>) (ArrayList<?>) DBBroker.getInstance()
                .select(probaStavka);

        assertTrue(stavkeIzBaze.size() >= 1, "Ocekivano bar jedna stavka u bazi za taj racun");
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soAdd.validate(new Administrator()));
    }

    @Test
    @DisplayName("Racun bez stavki racuna")
    void testValidationNoStavke() {
        racun.setStavkeRacuna(new ArrayList<>());
        assertThrows(Exception.class, () -> soAdd.validate(racun));
    }

    @Test
    void testValidateSuccessful() {
        assertDoesNotThrow(() -> soAdd.validate(racun));
    }
}
