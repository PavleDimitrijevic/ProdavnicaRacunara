package so.racun;

import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author PAVLE
 */
public class SOGetAllRacunTest {
    
    public SOGetAllRacunTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        AbstractDomainObject ado = null;
        SOGetAllRacun instance = new SOGetAllRacun();
        instance.validate(ado);
        fail("The test case is a prototype.");
    }

    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        AbstractDomainObject ado = null;
        SOGetAllRacun instance = new SOGetAllRacun();
        instance.execute(ado);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetLista() {
        System.out.println("getLista");
        SOGetAllRacun instance = new SOGetAllRacun();
        ArrayList<Racun> expResult = null;
        ArrayList<Racun> result = instance.getLista();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
