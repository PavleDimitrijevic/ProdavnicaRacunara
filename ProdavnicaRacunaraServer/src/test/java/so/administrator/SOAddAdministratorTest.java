package so.administrator;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
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
public class SOAddAdministratorTest {

    private SOAddAdministrator soAdd;
    private Administrator administrator;

    @BeforeEach
    void setUp() {
        soAdd = new SOAddAdministrator();
        administrator = new Administrator();
    }

    @AfterEach
    void tearDown() {
        soAdd = null;
        administrator = null;
    }

    @Test
    @DisplayName("Uspesno dodavanje administratora")
    void testExecuteSuccessful() throws Exception {
        administrator.setIme("TestAdd");
        administrator.setPrezime("Execute");
        administrator.setUsername("user" + System.currentTimeMillis()); // trenutno vreme se koristi da bi test
        // mogao da se pokrene vise puta bez da
        // se korisnik brise iz baze
        administrator.setPassword("test12345");

        assertDoesNotThrow(() -> soAdd.validate(administrator)); // prvo validate da ne bi bilo duplikata
        assertDoesNotThrow(() -> soAdd.execute(administrator)); // sada execute

        @SuppressWarnings("unchecked")
        ArrayList<Administrator> administratori = (ArrayList<Administrator>) (ArrayList<?>) DBBroker.getInstance()
                .select(administrator);
        assertTrue(administratori.stream().anyMatch(a -> a.getUsername().equals(administrator.getUsername()))); // provera
        // da li
        // je
        // stvarno
        // dodat

        DBBroker.getInstance().delete(administrator); // ciscenje baze da se ne bi prenatrpala podacima
    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(java.lang.Exception.class, () -> soAdd.validate(new Racunar()));
    }

    @Test
    @DisplayName("Username nije jedinstven u bazi")
    void testValidationDuplicateUsername() throws Exception {
        String username = "user" + System.currentTimeMillis();

        Administrator prvi = new Administrator();
        prvi.setIme("ImePrvi");
        prvi.setPrezime("PrezimePrvi");
        prvi.setUsername(username);
        prvi.setPassword("sifraPrvi123");
        DBBroker.getInstance().insert(prvi); // ubacujemo prvog u bazu

        Administrator duplikat = new Administrator();
        duplikat.setIme("ImeDuplikat");
        duplikat.setPrezime("PrezimeDuplikat");
        duplikat.setUsername(username);
        duplikat.setPassword("sifraDrugi123");

        assertThrows(java.lang.Exception.class, () -> soAdd.validate(duplikat)); // proveravamo da li username postoji u
        // bazi

        DBBroker.getInstance().delete(prvi);
    }

    @Test
    @DisplayName("Username je jedinstven u bazi")
    void testValidationUniqueUsername() {
        administrator.setIme("ImeValidation");
        administrator.setPrezime("PrezimeValidation");
        administrator.setUsername("usernameValidation" + System.currentTimeMillis());
        administrator.setPassword("sifraValidation123");

        assertDoesNotThrow(() -> soAdd.validate(administrator));
    }

}
