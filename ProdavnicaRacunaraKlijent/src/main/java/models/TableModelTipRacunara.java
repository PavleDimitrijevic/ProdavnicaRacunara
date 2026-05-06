package models;

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
public class TableModelTipRacunara extends AbstractTableModel implements Runnable {

    private ArrayList<TipRacunara> lista;
    private String[] kolone = {"ID", "Naziv"};
    private String parametar = "";

    public TableModelTipRacunara() {
        try {
            lista = ClientController.getInstance().getAllTipRacunara();
        } catch (Exception ex) {
            Logger.getLogger(TableModelTipRacunara.class.getName()).log(Level.SEVERE, null, ex);
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
        TipRacunara tr = lista.get(row);

        switch (column) {
            case 0:
                return tr.getTipRacunaraID();
            case 1:
                return tr.getNaziv();

            default:
                return null;
        }
    }

    public TipRacunara getSelectedTipRacunara(int row) {
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
            Logger.getLogger(TableModelTipRacunara.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllTipRacunara();
            if (!parametar.equals("")) {
                ArrayList<TipRacunara> novaLista = new ArrayList<>();
                for (TipRacunara tr : lista) {
                    if (tr.getNaziv().toLowerCase().contains(parametar.toLowerCase())) {
                        novaLista.add(tr);
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
