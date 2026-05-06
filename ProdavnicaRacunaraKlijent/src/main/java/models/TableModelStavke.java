package models;

import com.pavledimitrijevic.prodavnicaracunara.StavkaRacuna;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PAVLE
 */
public class TableModelStavke extends AbstractTableModel {

    private ArrayList<StavkaRacuna> lista;
    private String[] kolone = {"Rb", "Racunar", "Kolicina", "Cena"};
    private int rb = 0;

    public TableModelStavke() {
        lista = new ArrayList<>();
    }

    public TableModelStavke(ArrayList<StavkaRacuna> stavkeRacuna) {
        lista = stavkeRacuna;
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
        StavkaRacuna sr = lista.get(row);

        switch (column) {
            case 0:
                return sr.getRb();
            case 1:
                return sr.getRacunar().getNaziv();
            case 2:
                return sr.getKolicina() + "kom";
            case 3:
                return sr.getCena() + "din";

            default:
                return null;
        }
    }

    public void dodajStavku(StavkaRacuna sr) {

        for (StavkaRacuna stavkaRacuna : lista) {
            if (stavkaRacuna.getRacunar().getRacunarID().equals(sr.getRacunar().getRacunarID())) {
                stavkaRacuna.setKolicina(stavkaRacuna.getKolicina() + sr.getKolicina());
                stavkaRacuna.setCena(stavkaRacuna.getCena() + sr.getCena());
                fireTableDataChanged();
                return;
            }
        }

        rb = lista.size();
        sr.setRb(++rb);
        lista.add(sr);
        fireTableDataChanged();
    }

    public void obrisiStavku(int row) {
        lista.remove(row);

        int rb = 0;
        for (StavkaRacuna stavkaRacuna : lista) {
            stavkaRacuna.setRb(++rb);
        }

        fireTableDataChanged();
    }

    public ArrayList<StavkaRacuna> getLista() {
        return lista;
    }

    public double vratiCenuRacuna() {
        double cenaRacuna = 0;

        for (StavkaRacuna stavkaRacuna : lista) {
            cenaRacuna += stavkaRacuna.getCena();
        }

        return cenaRacuna;
    }

}
