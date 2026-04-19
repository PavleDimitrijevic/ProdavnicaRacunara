package so.racun;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Komponenta;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import com.pavledimitrijevic.prodavnicaracunara.StavkaRacuna;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;

class SOGetAllRacunTest {

    private SOGetAllRacun soGetAll;
    private Racun racun;
    private Racunar racunar;
    private Administrator administrator;
    private TipRacunara tip;

    @BeforeEach
    void setUp() throws Exception {
        soGetAll = new SOGetAllRacun();

        tip = new TipRacunara();
        tip.setNaziv("TipTest" + System.currentTimeMillis());
        PreparedStatement psT = DBBroker.getInstance().insert(tip);
        ResultSet keysT = psT.getGeneratedKeys();
        if (keysT.next()) {
            tip.setTipRacunaraID(keysT.getLong(1));
        }

        racunar = new Racunar();
        racunar.setNaziv("TestRacunar" + System.currentTimeMillis());
        racunar.setCenaPoKomadu(150000);
        racunar.setOpis("Opis test racunara");
        racunar.setTipRacunara(tip);

        ArrayList<Komponenta> komponente = new ArrayList<>();
        Komponenta k1 = new Komponenta();
        k1.setRb(1);
        k1.setNaziv("Procesor" + System.currentTimeMillis());
        k1.setRacunar(racunar);

        Komponenta k2 = new Komponenta();
        k2.setRb(2);
        k2.setNaziv("RAM" + System.currentTimeMillis());
        k2.setRacunar(racunar);

        komponente.add(k1);
        komponente.add(k2);
        racunar.setKomponente(komponente);

        PreparedStatement psP = DBBroker.getInstance().insert(racunar);
        ResultSet keysP = psP.getGeneratedKeys();
        if (keysP.next()) {
            racunar.setRacunarID(keysP.getLong(1));
        }

        DBBroker.getInstance().insert(k1);
        DBBroker.getInstance().insert(k2);

        administrator = new Administrator();
        administrator.setIme("AdminGetAll" + System.currentTimeMillis());
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

        PreparedStatement psR = DBBroker.getInstance().insert(racun);
        ResultSet keysR = psR.getGeneratedKeys();
        if (keysR.next()) {
            racun.setRacunID(keysR.getLong(1));
        }

        StavkaRacuna stavka = new StavkaRacuna();
        stavka.setRb(1);
        stavka.setRacunar(racunar);
        stavka.setKolicina(2);
        stavka.setCena(150000);
        stavka.setRacun(racun);

        DBBroker.getInstance().insert(stavka);

        ArrayList<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavka);
        racun.setStavkeRacuna(stavke);
    }

    @AfterEach
    void tearDown() {
        soGetAll = null;
        racun = null;
        racunar = null;
        tip = null;
        administrator = null;
    }

    @Test
    @DisplayName("Uspesno vracanje svih racuna iz baze")
    void testExecuteSuccessful() throws Exception {
        assertDoesNotThrow(() -> soGetAll.validate(racun));
        assertDoesNotThrow(() -> soGetAll.execute(racun));

        ArrayList<Racun> lista = soGetAll.getLista();
        assertNotNull(lista, "Lista racuna ne sme biti null");
        assertTrue(lista.size() > 0, "Ocekivano je da postoji bar jedan racun u bazi");

        boolean sviImajuStavke = lista.stream()
                .allMatch(r -> r.getStavkeRacuna() != null && !r.getStavkeRacuna().isEmpty());
        assertTrue(sviImajuStavke, "Svi racuni moraju imati bar jednu stavku");
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(Exception.class, () -> soGetAll.validate(new Administrator()));
    }

    @Test
    @DisplayName("Validacija ispravnog objekta")
    void testValidationValidObject() {
        assertDoesNotThrow(() -> soGetAll.validate(racun));
    }
}
