package models;

import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import controller.ClientController;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PAVLE
 */
public class TableModelRacunari extends AbstractTableModel implements Runnable {

    private ArrayList<Racunar> lista;
    private String[] kolone = {"ID", "Tip racunara", "Naziv", "Cena po komadu"};
    private String parametar = "";

    public TableModelRacunari() {
        try {
            lista = ClientController.getInstance().getAllRacunar(null);
        } catch (Exception ex) {
            Logger.getLogger(TableModelRacunari.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TableModelRacunari(TipRacunara tr) {
        try {
            lista = ClientController.getInstance().getAllRacunar(tr);
        } catch (Exception ex) {
            Logger.getLogger(TableModelRacunari.class.getName()).log(Level.SEVERE, null, ex);
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
        Racunar r = lista.get(row);

        switch (column) {
            case 0:
                return r.getRacunarID();
            case 1:
                return r.getTipRacunara();
            case 2:
                return r.getNaziv();
            case 3:
                return r.getCenaPoKomadu() + "din";

            default:
                return null;
        }
    }

    public Racunar getSelectedRacunar(int row) {
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
            Logger.getLogger(TableModelRacunari.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllRacunar(null);
            if (!parametar.equals("")) {
                ArrayList<Racunar> novaLista = new ArrayList<>();
                for (Racunar r : lista) {
                    if (r.getNaziv().toLowerCase().contains(parametar.toLowerCase())) {
                        novaLista.add(r);
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
