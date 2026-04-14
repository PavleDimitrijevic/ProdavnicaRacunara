package transfer.util;

/**
 * Enumeracija koja predstavlja status odgovora servera.
 * <p>
 * Koristi se u klasi Response kako bi se oznacilo da li je zahtev uspesno
 * izvrsen ili je doslo do greske.
 *
 * @author PAVLE
 */
public enum ResponseStatus {
    /**
     * Oznaka da je operacija uspesno izvrsena.
     */
    Success,
    /**
     * Oznaka da je doslo do greske pri izvrsavanju operacije.
     */
    Error
}
