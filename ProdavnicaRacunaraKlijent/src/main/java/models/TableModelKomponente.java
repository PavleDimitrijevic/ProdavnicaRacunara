package models;

import com.pavledimitrijevic.prodavnicaracunara.Komponenta;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PAVLE
 */
public class TableModelKomponente extends AbstractTableModel {

    private ArrayList<Komponenta> lista;
    private String[] kolone = {"Rb", "Komponenta"};
    private int rb = 0;

    public TableModelKomponente() {
        lista = new ArrayList<>();
    }

    public TableModelKomponente(ArrayList<Komponenta> komponente) {
        lista = komponente;
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
        Komponenta k = lista.get(row);

        switch (column) {
            case 0:
                return k.getRb();
            case 1:
                return k.getNaziv();

            default:
                return null;
        }
    }

    public void dodajKomponentu(Komponenta k) {
        rb = lista.size();
        k.setRb(++rb);
        lista.add(k);
        fireTableDataChanged();
    }

    public void obrisiKomponentu(int row) {
        lista.remove(row);

        int rb = 0;
        for (Komponenta komponenta : lista) {
            komponenta.setRb(++rb);
        }

        fireTableDataChanged();
    }

    public ArrayList<Komponenta> getLista() {
        return lista;
    }

    public boolean postojiKomponenta(Komponenta k) {
        for (Komponenta komponenta : lista) {
            if (komponenta.getNaziv().equals(k.getNaziv())) {
                return true;
            }
        }
        return false;
    }

}
