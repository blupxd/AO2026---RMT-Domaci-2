/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forms;

import domain.Prijava;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author matij
 */
public class ModelTabeleKlijent extends AbstractTableModel {

    private List<Prijava> lista;
    private final String[] kolone = {"ID", "Datum Volontiranja", "Smena", "Pozicija", "STATUS"};

    public ModelTabeleKlijent(List<Prijava> lista) {
        this.lista = lista;
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
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Prijava p = lista.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        switch (columnIndex) {
            case 0:
                return p.getId().substring(0, 5) + "..."; // Skraćen ID
            case 1:
                return sdf.format(p.getDatumVolontiranja());
            case 2:
                return p.getSmena();
            case 3:
                return p.getPozicija();
            case 4:
                return izracunajStatus(p);
            default:
                return "N/A";
        }
    }

    public Prijava getPrijavaAt(int row) {
        return lista.get(row);
    }

    private String izracunajStatus(Prijava p) {
        Date danas = new Date();
        Date datumVol = p.getDatumVolontiranja();

        long razlikaUms = datumVol.getTime() - danas.getTime();
        long satiDoSmene = TimeUnit.MILLISECONDS.toHours(razlikaUms);

        if (razlikaUms < 0) {
            return "ZAVRŠENA";
        } else if (satiDoSmene < 24) {
            return "ZAKLJUČANA";
        } else {
            return "U OBRADI";
        }
    }
}
