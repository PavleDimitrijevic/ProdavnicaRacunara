package transfer;

import java.io.Serializable;

/**
 * * Klasa koja predstavlja zahtev koji klijent salje serveru.
 * <p>
 * Svaki zahtev sadrzi operaciju koja se izvrsava i podatke potrebne za tu
 * operaciju. Implementira Serializable radi slanja preko mreze.
 *
 * @author PAVLE
 */
public class Request implements Serializable {

    /**
     * Operacija koja se zahteva od servera kao int
     */
    private int operation;

    /**
     * Podaci koji se salju serveru (entitet ili dodatni parametri).
     */
    private Object data;

    /**
     * Inicijalizuje objekat klase Request sa atributima koji imaju default
     * vrednosti.
     */
    public Request() {
    }

    /**
     * Inicijalizuje objekat klase Request sa svim parametrima.
     *
     * @param operation Operacija koja se izvrsava
     * @param data Podaci potrebni za operaciju
     */
    public Request(int operation, Object data) {
        this.operation = operation;
        this.data = data;
    }

    /**
     * Vraca podatke iz zahteva.
     *
     * @return objekat sa podacima
     */
    public Object getData() {
        return data;
    }

    /**
     * Vraca broj operacije.
     *
     * @return broj operacije
     */
    public int getOperation() {
        return operation;
    }

    /**
     * Postavlja podatke za zahtev.
     *
     * @param data objekat sa podacima
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Postavlja operaciju koja se zahteva.
     *
     * @param operation broj operacije
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }
}
