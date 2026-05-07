package models;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import controller.ClientController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PAVLE
 */
public class TableModelRacuni extends AbstractTableModel implements Runnable {

    private ArrayList<Racun> lista;
    private String[] kolone = {"ID", "Datum i vreme", "Cena", "Administrator"};
    private String parametar = "";

    public TableModelRacuni() {
        try {
            lista = ClientController.getInstance().getAllRacun(null);
        } catch (Exception ex) {
            Logger.getLogger(TableModelRacuni.class.getName()).log(Level.SEVERE, null, ex);
            lista = new ArrayList<>();
        }
    }

    public TableModelRacuni(Administrator a) {
        try {
            lista = ClientController.getInstance().getAllRacun(a);
        } catch (Exception ex) {
            Logger.getLogger(TableModelRacuni.class.getName()).log(Level.SEVERE, null, ex);
            lista = new ArrayList<>();
        }
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int i) {
        return kolone[i];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Racun ra = lista.get(row);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        switch (column) {
            case 0:
                return ra.getRacunID();
            case 1:
                return sdf.format(ra.getDatumVreme());
            case 2:
                return ra.getCena() + "din";
            case 3:
                return ra.getAdministrator();

            default:
                return null;
        }
    }

    public Racun getSelectedRacun(int row) {
        return lista.get(row);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(10000);
                refreshTable();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TableModelRacuni.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllRacun(null);
            if (!parametar.equals("")) {
                ArrayList<Racun> novaLista = new ArrayList<>();
                for (Racun ra : lista) {
                    if (ra.getAdministrator().getIme().toLowerCase().contains(parametar.toLowerCase())
                            || ra.getAdministrator().getPrezime().toLowerCase().contains(parametar.toLowerCase())) {
                        novaLista.add(ra);
                    }
                }
                lista = novaLista;
            }

            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
