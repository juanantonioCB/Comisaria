package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Consults;
import model.Suspect;
import view.GUIviewSuspect;

/**
 *
 * @author Juan Antonio
 */
public class CtrlViewSuspect implements ActionListener {

    private static GUIviewSuspect guiViewSuspect;
    private Consults consults = null;


    public CtrlViewSuspect() {
        guiViewSuspect = new GUIviewSuspect();
        guiViewSuspect.nextPhotoButton.addActionListener(this);
        guiViewSuspect.previousPhotoButton.addActionListener(this);
        guiViewSuspect.searchButton.addActionListener(this);
        guiViewSuspect.loadButton.addActionListener(this);
        guiViewSuspect.reloadButton.addActionListener(this);
        consults = Consults.getConsults();
        completeTable(consults.getSuspects());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiViewSuspect.loadButton) {
            if (guiViewSuspect.tableSuspects.getSelectedRow() != -1) {
                loadSuspect((int) guiViewSuspect.tableSuspects.getValueAt(guiViewSuspect.tableSuspects.getSelectedRow(), 0));
            }
        }
        if (e.getSource() == guiViewSuspect.reloadButton) {
            completeTable(consults.getSuspects());
            
        }
        if(e.getSource()==guiViewSuspect.searchButton){
            if(guiViewSuspect.searchTextField.getText().length()!=0){
                listSearchSuspect(guiViewSuspect.searchTextField.getText());
            }
        }
    }

    private void completeTable(ArrayList<Suspect> s) {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{
            {null, null, null, null},},
                new String[]{
                    "ID", "Nombre", "Apellidos", "DNI"
                });
        for (int i = 0; i < s.size() - 1; i++) {
            model.addRow(new Object[][]{{null, null, null, null}});

        }

        guiViewSuspect.tableSuspects.setModel(model);

        for (int x = 0; x < s.size(); x++) {
            guiViewSuspect.tableSuspects.setValueAt(s.get(x).getId(), x, 0);
            guiViewSuspect.tableSuspects.setValueAt(s.get(x).getName(), x, 1);
            guiViewSuspect.tableSuspects.setValueAt(s.get(x).getSurname1() + " " + s.get(x).getSurname2(), x, 2);
            guiViewSuspect.tableSuspects.setValueAt(s.get(x).getDNI(), x, 3);

        }
    }

    private void loadSuspect(int id) {
        Suspect s = consults.getSuspectFromBBDD(id);
        s.setCompanions(consults.getCompanionsOfSuspect(id));
        s.setPhoto(consults.getPhotos(id));
        guiViewSuspect.phoneNumbersList.removeAll();
        guiViewSuspect.companionsList.removeAll();
        guiViewSuspect.licensePlatesList.removeAll();
        guiViewSuspect.recordsTextArea.setText(null);
        guiViewSuspect.factsTextArea.setText(null);
        guiViewSuspect.nameSuspectLabel.setText(s.getName() + " " + s.getSurname1() + " " + s.getSurname2());
        guiViewSuspect.companionsList.removeAll();
        
        if (s.getPhoto() != null) {
            for (int i = 0; i < s.getPhoto().size(); i++) {
                System.out.println(s.getPhoto().size());
            }
        }
        if (s.getLicensePlates() != null) {
            for (int i = 0; i < s.getLicensePlates().size(); i++) {
                guiViewSuspect.licensePlatesList.add((String) s.getLicensePlates().get(i));
            }
        }
        if (s.getPhoneNumbers() != null) {

            for (int i = 0; i < s.getPhoneNumbers().size(); i++) {
                guiViewSuspect.phoneNumbersList.add((String) s.getPhoneNumbers().get(i));
            }
        }
        if (s.getEmails() != null) {
            for (int i = 0; i < s.getEmails().size(); i++) {
                guiViewSuspect.emailsList.add((String) s.getEmails().get(i));
            }
        }
        if (s.getFacts() != null) {

            guiViewSuspect.factsTextArea.setText(s.getFacts());
        }
        if (s.getRecords() != null) {
            guiViewSuspect.recordsTextArea.setText(s.getRecords());
        }
        if(s.getCompanions()!=null){
            for(int i=0;i<s.getCompanions().size();i++){
                System.out.println(s.getCompanions().get(i));
                guiViewSuspect.companionsList.add(s.getCompanions().get(i).toString());
            }
        }
    }

    private void listSearchSuspect(String search) {
        ArrayList<Suspect> suspects = null;
        suspects=consults.searchSuspect(search);
        completeTable(suspects);
    }
    
    /**
     * @return the guiAddSuspect
     */
    public static GUIviewSuspect getGuiViewSuspect() {
        return guiViewSuspect;
    }

    

}
