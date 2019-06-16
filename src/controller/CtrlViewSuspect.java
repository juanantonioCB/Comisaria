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
    private Suspect suspect = null;

    public CtrlViewSuspect() {
        guiViewSuspect = new GUIviewSuspect();
        guiViewSuspect.nextPhotoButton.addActionListener(this);
        guiViewSuspect.previousPhotoButton.addActionListener(this);
        guiViewSuspect.searchButton.addActionListener(this);
        guiViewSuspect.reloadButton.addActionListener(this);
        guiViewSuspect.deleteButton.addActionListener(this);
        guiViewSuspect.addCompanionButton.addActionListener(this);
        guiViewSuspect.deleteCompanionButton.addActionListener(this);
        guiViewSuspect.addEmailButton.addActionListener(this);
        guiViewSuspect.addLicensePlatesButton.addActionListener(this);
        guiViewSuspect.addPhoneNumberButton.addActionListener(this);
        guiViewSuspect.deleteCompanionButton.addActionListener(this);
        guiViewSuspect.deleteEmailButton.addActionListener(this);
        guiViewSuspect.deleteLicensePlatesButton.addActionListener(this);
        guiViewSuspect.deletePhoneNumberButton.addActionListener(this);
        guiViewSuspect.saveChangesButton.addActionListener(this);
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
        guiViewSuspect.currentPhoto.setVisible(false);
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
        if (e.getSource() == guiViewSuspect.nextPhotoButton) {
            showImage("next");
        }
        if (e.getSource() == guiViewSuspect.previousPhotoButton) {
            showImage("previous");
        }
        if (e.getSource() == guiViewSuspect.addLicensePlatesButton) {
            if (guiViewSuspect.licensePlatesTextField.getText().length() != 0) {
                guiViewSuspect.licensePlatesList.add(guiViewSuspect.licensePlatesTextField.getText());
                guiViewSuspect.licensePlatesTextField.setText(null);
            }
        }
        if (e.getSource() == guiViewSuspect.addEmailButton) {
            if (guiViewSuspect.emailsTextField.getText().length() != 0) {
                guiViewSuspect.emailsList.add(guiViewSuspect.emailsTextField.getText());
                guiViewSuspect.emailsTextField.setText(null);
            }
        }
        if (e.getSource() == guiViewSuspect.addPhoneNumberButton) {
            if (guiViewSuspect.phoneNumbersTextField.getText().length() != 0) {
                guiViewSuspect.phoneNumbersList.add(guiViewSuspect.phoneNumbersTextField.getText());
                guiViewSuspect.phoneNumbersTextField.setText(null);
            }
        }
        if (e.getSource() == guiViewSuspect.deleteLicensePlatesButton) {
            if (guiViewSuspect.licensePlatesList.getSelectedIndex() != -1) {
                guiViewSuspect.licensePlatesList.remove(guiViewSuspect.licensePlatesList.getSelectedIndex());
            }
        }
        if (e.getSource() == guiViewSuspect.deletePhoneNumberButton) {
            if (guiViewSuspect.phoneNumbersList.getSelectedIndex() != -1) {
                guiViewSuspect.phoneNumbersList.remove(guiViewSuspect.phoneNumbersList.getSelectedIndex());
            }
        }
        if (e.getSource() == guiViewSuspect.deleteEmailButton) {
            if (guiViewSuspect.emailsList.getSelectedIndex() != -1) {
                guiViewSuspect.emailsList.remove(guiViewSuspect.emailsList.getSelectedIndex());
            }
        }
        if (e.getSource() == guiViewSuspect.saveChangesButton) {
            saveChanges();
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
        suspect = consults.getSuspectFromBBDD(id);
        guiViewSuspect.currentSuspectLabel.setText(String.valueOf(id));
        suspect.setCompanions(consults.getCompanionsOfSuspect(id));
        suspect.setPhoto(consults.getPhotos(id));
        guiViewSuspect.phoneNumbersList.removeAll();
        guiViewSuspect.companionsList.removeAll();
        guiViewSuspect.licensePlatesList.removeAll();
        guiViewSuspect.recordsTextArea.setText(null);
        guiViewSuspect.factsTextArea.setText(null);
        guiViewSuspect.nameSuspectLabel.setText(suspect.getName() + " " + suspect.getSurname1() + " " + suspect.getSurname2());
        guiViewSuspect.companionsList.removeAll();
        guiViewSuspect.emailsList.removeAll();
        guiViewSuspect.imageLabel.setIcon(null);

        if (suspect.getPhoto() != null) {
            Image img = new ImageIcon(suspect.getPhoto().get(0)).getImage().getScaledInstance(guiViewSuspect.imageLabel.getWidth(), guiViewSuspect.imageLabel.getHeight(), Image.SCALE_SMOOTH);
            guiViewSuspect.imageLabel.setIcon(new ImageIcon(img));
            guiViewSuspect.currentPhoto.setText("0");
        }
        if (suspect.getLicensePlates() != null) {
            for (int i = 0; i < suspect.getLicensePlates().size(); i++) {
                guiViewSuspect.licensePlatesList.add((String) suspect.getLicensePlates().get(i));
            }
        }
        if (suspect.getPhoneNumbers() != null) {

            for (int i = 0; i < suspect.getPhoneNumbers().size(); i++) {
                guiViewSuspect.phoneNumbersList.add((String) suspect.getPhoneNumbers().get(i));
            }
        }
        if (suspect.getEmails() != null) {
            for (int i = 0; i < suspect.getEmails().size(); i++) {
                guiViewSuspect.emailsList.add((String) suspect.getEmails().get(i));
            }
        }
        if (suspect.getFacts() != null) {

            guiViewSuspect.factsTextArea.setText(suspect.getFacts());
        }
        if (suspect.getRecords() != null) {
            guiViewSuspect.recordsTextArea.setText(suspect.getRecords());
        }
        if (suspect.getCompanions() != null) {
            for (int i = 0; i < suspect.getCompanions().size(); i++) {
                System.out.println(suspect.getCompanions().get(i));
                guiViewSuspect.companionsList.add(suspect.getCompanions().get(i).toString());
            }
        }
    }

    private void showImage(String option) {
        if (suspect.getPhoto() != null) {
            int currentPhoto = Integer.parseInt(guiViewSuspect.currentPhoto.getText());
            switch (option) {
                case "next":
                    if (suspect.getPhoto().size() > currentPhoto + 1) {
                        byte[] foto = suspect.getPhoto().get(currentPhoto + 1);
                        Image img = new ImageIcon(foto).getImage().getScaledInstance(guiViewSuspect.imageLabel.getWidth(), guiViewSuspect.imageLabel.getHeight(), Image.SCALE_SMOOTH);
                        guiViewSuspect.imageLabel.setIcon(new ImageIcon(img));
                        guiViewSuspect.currentPhoto.setText(String.valueOf(currentPhoto + 1));
                    }
                    break;
                case "previous":
                    if ((currentPhoto - 1) > -1) {
                        byte[] foto = suspect.getPhoto().get(currentPhoto - 1);
                        Image img = new ImageIcon(foto).getImage().getScaledInstance(guiViewSuspect.imageLabel.getWidth(), guiViewSuspect.imageLabel.getHeight(), Image.SCALE_SMOOTH);
                        guiViewSuspect.imageLabel.setIcon(new ImageIcon(img));
                        guiViewSuspect.currentPhoto.setText(String.valueOf(currentPhoto - 1));
                    }
                    break;
                case "remove":
                    if (currentPhoto > -1) {
                        suspect.getPhoto().remove(currentPhoto);
                        guiViewSuspect.currentPhoto.setText("-1");
                        guiViewSuspect.imageLabel.setIcon(null);
                    }
                    break;
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

    private void saveChanges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
