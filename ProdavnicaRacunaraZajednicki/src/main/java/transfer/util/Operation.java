package transfer.util;

/**
 * Interfejs koji definise sve operacije koje klijent moze da zahteva od
 * servera.
 * <p>
 * Svaka operacija je predstavljena celobrojnim kodom (int konstanta). Ovi
 * kodovi se koriste u Request klasi kako bi server znao koju operaciju treba da
 * izvrsi.
 *
 * @author PAVLE
 */
public interface Operation {

    /**
     * Operacija prijavljivanja korisnika na sistem.
     */
    public static final int LOGIN = 0;

    /**
     * Operacija odjavljivanja korisnika sa sistema.
     */
    public static final int LOGOUT = 1;

    /**
     * Operacija dodavanja novog administratora.
     */
    public static final int ADD_ADMINISTRATOR = 2;

    /**
     * Operacija brisanja administratora.
     */
    public static final int DELETE_ADMINISTRATOR = 3;

    /**
     * Operacija azuriranja podataka o administratoru.
     */
    public static final int UPDATE_ADMINISTRATOR = 4;

    /**
     * Operacija vracanja svih administratora.
     */
    public static final int GET_ALL_ADMINISTRATOR = 5;

    /**
     * Operacija dodavanja novog racunara.
     */
    public static final int ADD_RACUNAR = 6;

    /**
     * Operacija azuriranja racunara.
     */
    public static final int UPDATE_RACUNAR = 7;

    /**
     * Operacija brisanja racunara.
     */
    public static final int DELETE_RACUNAR = 8;

    /**
     * Operacija vracanja svih racunara.
     */
    public static final int GET_ALL_RACUNAR = 9;

    /**
     * Operacija dodavanja racuna.
     */
    public static final int ADD_RACUN = 10;

    /**
     * Operacija vracanja svih racuna.
     */
    public static final int GET_ALL_RACUN = 11;

    /**
     * Operacija dodavanja tipa racunara.
     */
    public static final int ADD_TIP_RACUNARA = 12;

    /**
     * Operacija brisanja tipa racunara.
     */
    public static final int DELETE_TIP_RACUNARA = 13;

    /**
     * Operacija azuriranja tipa racunara.
     */
    public static final int UPDATE_TIP_RACUNARA = 14;

    /**
     * Operacija vracanja svih tipova racunara.
     */
    public static final int GET_ALL_TIP_RACUNARA = 15;

}
