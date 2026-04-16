package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
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
public class StavkaRacunaTest {
    
    public StavkaRacunaTest() {
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
    public void testGetRacunar() {
        System.out.println("getRacunar");
        StavkaRacuna instance = new StavkaRacuna();
        Racunar expResult = null;
        Racunar result = instance.getRacunar();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetRacunar() {
        System.out.println("setRacunar");
        Racunar racunar = null;
        StavkaRacuna instance = new StavkaRacuna();
        instance.setRacunar(racunar);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRacun() {
        System.out.println("getRacun");
        StavkaRacuna instance = new StavkaRacuna();
        Racun expResult = null;
        Racun result = instance.getRacun();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetRacun() {
        System.out.println("setRacun");
        Racun racun = null;
        StavkaRacuna instance = new StavkaRacuna();
        instance.setRacun(racun);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRb() {
        System.out.println("getRb");
        StavkaRacuna instance = new StavkaRacuna();
        int expResult = 0;
        int result = instance.getRb();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetRb() {
        System.out.println("setRb");
        int rb = 0;
        StavkaRacuna instance = new StavkaRacuna();
        instance.setRb(rb);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetKolicina() {
        System.out.println("getKolicina");
        StavkaRacuna instance = new StavkaRacuna();
        int expResult = 0;
        int result = instance.getKolicina();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetKolicina() {
        System.out.println("setKolicina");
        int kolicina = 0;
        StavkaRacuna instance = new StavkaRacuna();
        instance.setKolicina(kolicina);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetCena() {
        System.out.println("getCena");
        StavkaRacuna instance = new StavkaRacuna();
        double expResult = 0.0;
        double result = instance.getCena();
        assertEquals(expResult, result, 0);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetCena() {
        System.out.println("setCena");
        double cena = 0.0;
        StavkaRacuna instance = new StavkaRacuna();
        instance.setCena(cena);
        fail("The test case is a prototype.");
    }

    @Test
    public void testNazivTabele() {
        System.out.println("nazivTabele");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.nazivTabele();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAlijas() {
        System.out.println("alijas");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.alijas();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testJoin() {
        System.out.println("join");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.join();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testVratiListu() throws Exception {
        System.out.println("vratiListu");
        ResultSet rs = null;
        StavkaRacuna instance = new StavkaRacuna();
        ArrayList<AbstractDomainObject> expResult = null;
        ArrayList<AbstractDomainObject> result = instance.vratiListu(rs);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testKoloneZaInsert() {
        System.out.println("koloneZaInsert");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.koloneZaInsert();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUslov() {
        System.out.println("uslov");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.uslov();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testVrednostiZaInsert() {
        System.out.println("vrednostiZaInsert");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.vrednostiZaInsert();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testVrednostiZaUpdate() {
        System.out.println("vrednostiZaUpdate");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.vrednostiZaUpdate();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUslovZaSelect() {
        System.out.println("uslovZaSelect");
        StavkaRacuna instance = new StavkaRacuna();
        String expResult = "";
        String result = instance.uslovZaSelect();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
