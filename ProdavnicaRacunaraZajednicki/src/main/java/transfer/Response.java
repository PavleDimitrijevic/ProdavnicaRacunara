package transfer;

import java.io.Serializable;
import transfer.util.ResponseStatus;

/**
 * Klasa koja predstavlja odgovor servera na zahtev klijenta.
 * <p>
 * Odgovor moze sadrzati podatke, poruku o gresci (ako je doslo do greske) i
 * status odgovora (uspeh ili greska). Implementira Serializable radi slanja
 * preko mreze.
 *
 * @author PAVLE
 */
public class Response implements Serializable {

    /**
     * Podaci koje server vraca klijentu.
     */
    private Object data;

    /**
     * Poruka o gresci ako je do nje doslo/
     */
    private Exception exc;

    /**
     * Status odgovora (uspesan ili neuspesan).
     */
    private ResponseStatus responseStatus;

    /**
     * Inicijalizuje objekat klase Response sa atributima koji imaju default
     * vrednosti.
     */
    public Response() {
    }

    /**
     * Inicijalizuje objekat klase Response sa svim parametrima.
     *
     * @param data Podaci koje server vraca klijentu
     * @param errorMessage Poruka o gresci ako je do nje doslo
     * @param responseStatus Status odgovora (SUCCESS / ERROR)
     */
    public Response(Object data, Exception exc, ResponseStatus responseStatus) {
        this.data = data;
        this.exc = exc;
        this.responseStatus = responseStatus;
    }

    /**
     * Vraca podatke koje je server poslao.
     *
     * @return objekat sa podacima
     */
    public Object getData() {
        return data;
    }

    /**
     * Postavlja podatke koje server salje.
     *
     * @param data Objekat sa podacima
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Vraca gresku ako je do nje doslo.
     *
     * @return greska
     */
    public Exception getException() {
        return exc;
    }

    /**
     * Postavlja gresku.
     *
     * @param exc Greska
     */
    public void setException(Exception exc) {
        this.exc = exc;
    }

    /**
     * Vraca status odgovora.
     *
     * @return SUCCESS ili ERROR
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Postavlja status odgovora.
     *
     * @param responseStatus SUCCESS ili ERROR
     */
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}
