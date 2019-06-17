package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
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
        relatedSuspects.loadRelatedSuspectsButton.addActionListener(this);
        relatedSuspects.reloadButton.addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == relatedSuspects.loadRelatedSuspectsButton) {
            loadRelatedSuspects((int) relatedSuspects.tableSuspects.getValueAt(relatedSuspects.tableSuspects.getSelectedRow(), 0));
        }
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
