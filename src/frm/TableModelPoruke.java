/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import domain.Poruka;

/**
 *
 * @author Dragana Stefanovic
 */
public class TableModelPoruke extends AbstractTableModel {
     private List<Poruka> porukeZaPrikaz = new ArrayList<>();
     private List<Poruka> svePoruke = new ArrayList<>();
     private List<Poruka> poslednje3poruke=new ArrayList<>();
    
    private String[] koloneSaVremenom = { "Od korisnika", "Za korisnika/Za sve", "Poruka", "Vreme" };
    
    TableModelPoruke(){
        setPorukeZaPrikaz(poslednje3poruke);}

    public List<Poruka> getPoslednje3poruke() {
        return poslednje3poruke;
    }

    public List<Poruka> getSvePoruke() {
        return svePoruke;
    }
    
    public void setPorukeZaPrikaz(List<Poruka> porukeZaPrikaz) {
        this.porukeZaPrikaz = porukeZaPrikaz;
    }

    @Override
    public int getRowCount() {
        return porukeZaPrikaz.size();
    }

    @Override
    public int getColumnCount() {
       // return showTime ? koloneSaVremenom.length : kolone.length;
       return koloneSaVremenom.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Poruka poruka = porukeZaPrikaz.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return poruka.getOdKorisnika().getEmail();
            case 1:
                return poruka.getKaKorisniku() == null ? "Za sve" : poruka.getKaKorisniku().getEmail();
            case 2:
                return vratiSkracenuPoruku(poruka.getPoruka());
            case 3:
                return vratiVreme(poruka.getVreme());
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return koloneSaVremenom[column];
    }

    private String vratiSkracenuPoruku(String poruka) {
        if (poruka.length() > 20) {
            return poruka.substring(0, 20) + "...";
        }
        return poruka;
    }

    public void addPoruka(Poruka poruka) {
        if (poslednje3poruke.size() == 3) {
            Poruka najranijaPoruka = poslednje3poruke.get(0);
            poslednje3poruke.remove(0);
           
        }
        poslednje3poruke.add(poruka);
        svePoruke.add(poruka);
        fireTableDataChanged();
    }

    public Poruka getPoruka(int rowIndex) {
        return porukeZaPrikaz.get(rowIndex);
    }

    

    private String vratiVreme(LocalDateTime vreme) {
        return vreme.getHour() + ":" + vreme.getMinute() + ":" + vreme.getSecond();
    }

    
}
