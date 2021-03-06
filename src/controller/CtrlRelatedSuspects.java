package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Consults;
import model.Suspect;
import view.GUIRelatedSuspects;

/**
 *
 * @author Juan Antonio
 */
public class CtrlRelatedSuspects implements ActionListener {
    
    private static GUIRelatedSuspects relatedSuspects;
    private DefaultListModel listSuspectsRelatedModel = new DefaultListModel<>();
    private Consults consults = Consults.getConsults();
    
    public CtrlRelatedSuspects() {
        relatedSuspects = new GUIRelatedSuspects();
        completeTable(consults.getSuspects());
        relatedSuspects.relatedSuspectsList.setModel(listSuspectsRelatedModel);
        relatedSuspects.reloadButton.addActionListener(this);
        relatedSuspects.tableSuspects.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // your valueChanged overridden method 
                    System.out.println((int) relatedSuspects.tableSuspects.getValueAt(row, 0));
                    loadRelatedSuspects((int) relatedSuspects.tableSuspects.getValueAt(row, 0));
                }
            }
        });
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==relatedSuspects.reloadButton){
            completeTable(consults.getSuspects());
        }
    }

    /**
     * @return the guiAddSuspect
     */
    public static GUIRelatedSuspects getGuiRelatedSuspects() {
        return relatedSuspects;
    }
    
    /**
     * se completa la tabla con el id, nombre, apellidos y dni de los sospechosos.
     * @param s ArrayList con los sospechosos
     */
    
    private void completeTable(ArrayList<Suspect> s) {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{
            {null, null, null, null},},
                new String[]{
                    "ID", "Nombre", "Apellidos", "DNI"
                });
        relatedSuspects.tableSuspects.setModel(model);
        if (s != null) {
            for (int i = 0; i < s.size() - 1; i++) {
                model.addRow(new Object[][]{{null, null, null, null}});
            }
            for (int x = 0; x < s.size(); x++) {
                relatedSuspects.tableSuspects.setValueAt(s.get(x).getId(), x, 0);
                relatedSuspects.tableSuspects.setValueAt(s.get(x).getName(), x, 1);
                relatedSuspects.tableSuspects.setValueAt(s.get(x).getSurname1() + " " + s.get(x).getSurname2(), x, 2);
                relatedSuspects.tableSuspects.setValueAt(s.get(x).getDNI(), x, 3);
            }
        }
    }
    
    /**
     * carga en un jlist los sospechosos relacionados con el id que se pasa
     * @param id id del sospechoso a buscar relacionados
     */
    
    private void loadRelatedSuspects(int id) {
        listSuspectsRelatedModel.removeAllElements();
        ArrayList<Suspect> s = consults.getRelatedSuspects(id);
        if (s != null) {
            for (int i = 0; i < s.size(); i++) {
                if(s.get(i).getId()!=id){
                    listSuspectsRelatedModel.addElement(s.get(i));
                }      
            }
        }
    }
}
