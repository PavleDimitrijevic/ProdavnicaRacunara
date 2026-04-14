package transfer.util;

/**
 *
 * @author PAVLE
 */
public interface Operation {

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;

    public static final int ADD_ADMINISTRATOR = 2;
    public static final int DELETE_ADMINISTRATOR = 3;
    public static final int UPDATE_ADMINISTRATOR = 4;
    public static final int GET_ALL_ADMINISTRATOR = 5;

    public static final int ADD_RACUNAR = 6;
    public static final int UPDATE_RACUNAR = 7;
    public static final int DELETE_RACUNAR = 8;
    public static final int GET_ALL_RACUNAR = 9;

    public static final int ADD_RACUN = 10;
    public static final int GET_ALL_RACUN = 11;

    public static final int ADD_TIP_RACUNARA = 12;
    public static final int DELETE_TIP_RACUNARA = 13;
    public static final int UPDATE_TIP_RACUNARA = 14;
    public static final int GET_ALL_TIP_RACUNARA = 15;

}
