package controller;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTable;
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
        guiViewSuspect.reloadButton.addActionListener(this);
        guiViewSuspect.deleteButton.addActionListener(this);
        guiViewSuspect.searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guiViewSuspect.searchTextField.getText().length() != 0) {
                    listSearchSuspect(guiViewSuspect.searchTextField.getText());
                }
            }
        });

        guiViewSuspect.tableSuspects.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // your valueChanged overridden method 
                    loadSuspect((int) guiViewSuspect.tableSuspects.getValueAt(row, 0));
                }
            }
        });

        consults = Consults.getConsults();
        completeTable(consults.getSuspects());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == guiViewSuspect.reloadButton) {
            completeTable(consults.getSuspects());

        }
        if (e.getSource() == guiViewSuspect.searchButton) {
            if (guiViewSuspect.searchTextField.getText().length() != 0) {
                listSearchSuspect(guiViewSuspect.searchTextField.getText());
            }
        }
        if (e.getSource() == guiViewSuspect.deleteButton) {
            if (guiViewSuspect.tableSuspects.getSelectedRow() != -1) {
                deleteSuspect((int) guiViewSuspect.tableSuspects.getValueAt(guiViewSuspect.tableSuspects.getSelectedRow(), 0));
            }
        }
    }

    private void deleteSuspect(int id) {
        consults.deleteSuspect(id);
        completeTable(consults.getSuspects());
    }

    private void completeTable(ArrayList<Suspect> s) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{
                    "ID", "Nombre", "Apellidos", "DNI"
                }, 0);
        guiViewSuspect.tableSuspects.setModel(model);
        if (s != null) {
            for (int i = 0; i < s.size(); i++) {
                model.addRow(new Object[]{s.get(i).getId(), s.get(i).getName(),
                    s.get(i).getSurname1() + " " + s.get(i).getSurname2(), s.get(i).getDNI()});
            }
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
        guiViewSuspect.emailsList.removeAll();

        if (s.getPhoto() != null) {
            Image img = new ImageIcon(s.getPhoto().get(0)).getImage().getScaledInstance(guiViewSuspect.imageLabel.getWidth(), guiViewSuspect.imageLabel.getHeight(), Image.SCALE_SMOOTH);
            guiViewSuspect.imageLabel.setIcon(new ImageIcon(img));
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
        if (s.getCompanions() != null) {
            for (int i = 0; i < s.getCompanions().size(); i++) {
                System.out.println(s.getCompanions().get(i));
                guiViewSuspect.companionsList.add(s.getCompanions().get(i).toString());
            }
        }
    }

    private void listSearchSuspect(String search) {
        ArrayList<Suspect> suspects = null;
        suspects = consults.searchSuspect(search);
        completeTable(suspects);
    }

    /**
     * @return the guiAddSuspect
     */
    public static GUIviewSuspect getGuiViewSuspect() {
        return guiViewSuspect;
    }

}
