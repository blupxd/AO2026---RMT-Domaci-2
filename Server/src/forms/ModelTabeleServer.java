/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forms;

import domain.Prijava;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author matij
 */
public class ModelTabeleServer extends AbstractTableModel {

    private List<Prijava> listaPrijava = new ArrayList<>();
    private final String[] kolone = {"Volonter", "JMBG", "Datum", "Smena", "Pozicija", "Status"};

    public ModelTabeleServer(List<Prijava> listaPrijava) {
        this.listaPrijava = listaPrijava;
    }

    @Override
    public int getRowCount() {
        return listaPrijava.size();
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
        Prijava p = listaPrijava.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        switch (columnIndex) {
            case 0:
                return p.getKorisnik().getIme() + " " + p.getKorisnik().getPrezime();
            case 1:
                return p.getKorisnik().getJmbg();
            case 2:
                return sdf.format(p.getDatumVolontiranja());
            case 3:
                return p.getSmena();
            case 4:
                return p.getPozicija();
            case 5:
                return p.getStatus();
            default:
                return "N/A";
        }
    }

    public void refreshData(List<Prijava> novaLista) {
        this.listaPrijava = novaLista;
        fireTableDataChanged();
    }
}
