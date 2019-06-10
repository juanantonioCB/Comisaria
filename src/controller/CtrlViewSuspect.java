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
    private ArrayList<Suspect> suspects = null;

    public CtrlViewSuspect() {
        guiViewSuspect = new GUIviewSuspect();
        guiViewSuspect.nextPhotoButton.addActionListener(this);
        guiViewSuspect.previousPhotoButton.addActionListener(this);
        guiViewSuspect.searchButton.addActionListener(this);
        guiViewSuspect.loadButton.addActionListener(this);
        guiViewSuspect.reloadButton.addActionListener(this);
        consults = Consults.getConsults();
        completeTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiViewSuspect.loadButton) {
            if (guiViewSuspect.tableSuspects.getSelectedRow() != -1) {
                loadSuspect((int) guiViewSuspect.tableSuspects.getValueAt(guiViewSuspect.tableSuspects.getSelectedRow(), 0));
            }
        }
        if (e.getSource() == guiViewSuspect.reloadButton) {
            completeTable();
        }
    }

    private void completeTable() {
        suspects = consults.getSuspects();
        DefaultTableModel model = new DefaultTableModel(new Object[][]{
            {null, null, null, null},},
                new String[]{
                    "ID", "Nombre", "Apellidos", "DNI"
                });
        for (int i = 0; i < suspects.size() - 1; i++) {
            model.addRow(new Object[][]{{null, null, null, null}});

        }

        guiViewSuspect.tableSuspects.setModel(model);

        for (int x = 0; x < suspects.size(); x++) {
            guiViewSuspect.tableSuspects.setValueAt(suspects.get(x).getId(), x, 0);
            guiViewSuspect.tableSuspects.setValueAt(suspects.get(x).getName(), x, 1);
            guiViewSuspect.tableSuspects.setValueAt(suspects.get(x).getSurname1() + " " + suspects.get(x).getSurname2(), x, 2);
            guiViewSuspect.tableSuspects.setValueAt(suspects.get(x).getDNI(), x, 3);

        }
    }

    private void loadSuspect(int id) {
        Suspect s = consults.getSuspectFromBBDD(id);
        guiViewSuspect.phoneNumbersList.removeAll();
        guiViewSuspect.emailsList.removeAll();
        guiViewSuspect.licensePlatesList.removeAll();
        guiViewSuspect.recordsTextArea.setText(null);
        guiViewSuspect.factsTextArea.setText(null);
        guiViewSuspect.nameSuspectLabel.setText(s.getName() + " " + s.getSurname1() + " " + s.getSurname2());
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
    }

    /**
     * @return the guiAddSuspect
     */
    public static GUIviewSuspect getGuiViewSuspect() {
        return guiViewSuspect;
    }

}
