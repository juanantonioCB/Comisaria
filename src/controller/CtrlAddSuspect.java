package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Consults;
import model.Suspect;
import view.GUIAddSuspect;

/**
 *
 * @author Juan Antonio
 */
public class CtrlAddSuspect implements ActionListener {

    private static GUIAddSuspect guiAddSuspect;
    private Consults consults = Consults.getConsults();
    private ArrayList<byte[]> photos = null;
    private ListIterator<byte[]> iterator = null;
    private ArrayList<Suspect> suspects = consults.getSuspects();
    private DefaultListModel listCompanionsAddModel = new DefaultListModel<>();
    private DefaultListModel listCompanionsModel = new DefaultListModel<>();

    public CtrlAddSuspect() {
        guiAddSuspect = new GUIAddSuspect();
        guiAddSuspect.addCompanionButton.addActionListener(this);
        guiAddSuspect.addEmailButton.addActionListener(this);
        guiAddSuspect.addLicensePlatesButton.addActionListener(this);
        guiAddSuspect.addPhoneNumberButton.addActionListener(this);
        guiAddSuspect.deletePhoneNumberButton.addActionListener(this);
        guiAddSuspect.addResidenciesButton.addActionListener(this);
        guiAddSuspect.deleteCompanionButton.addActionListener(this);
        guiAddSuspect.deleteEmailButton.addActionListener(this);
        guiAddSuspect.deleteLicensePlateButton.addActionListener(this);
        guiAddSuspect.nextPhotoButton.addActionListener(this);
        guiAddSuspect.previousPhotoButton.addActionListener(this);
        guiAddSuspect.saveButton.addActionListener(this);
        guiAddSuspect.addPhotoButton.addActionListener(this);
        guiAddSuspect.removePhotoButton.addActionListener(this);
        addSuspectsCompanion();
        guiAddSuspect.companionsListAdd.setModel(listCompanionsAddModel);
        guiAddSuspect.companionsList.setModel(listCompanionsModel);
        guiAddSuspect.deleteResidenciesButton.addActionListener(this);
        guiAddSuspect.currentPhoto.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == guiAddSuspect.saveButton) {
            saveSuspect();

        }
        if (e.getSource() == guiAddSuspect.addLicensePlatesButton) {
            if (!guiAddSuspect.licensePlatesTextField.getText().equals("")) {
                guiAddSuspect.licensePlatesList.add(guiAddSuspect.licensePlatesTextField.getText());
                guiAddSuspect.licensePlatesTextField.setText("");
            }
        }
        if (e.getSource() == guiAddSuspect.deleteLicensePlateButton) {
            if (guiAddSuspect.licensePlatesList.getSelectedIndex() != -1) {
                guiAddSuspect.licensePlatesList.remove(guiAddSuspect.licensePlatesList.getSelectedItem());
            }
        }

        if (e.getSource() == guiAddSuspect.addPhoneNumberButton) {
            if (!guiAddSuspect.phoneNumbersTextField.getText().equals("")) {
                guiAddSuspect.phoneNumbersList.add(guiAddSuspect.phoneNumbersTextField.getText());
                guiAddSuspect.phoneNumbersTextField.setText("");
            }
        }

        if (e.getSource() == guiAddSuspect.deletePhoneNumberButton) {
            if (guiAddSuspect.phoneNumbersList.getSelectedIndex() != -1) {
                guiAddSuspect.phoneNumbersList.remove(guiAddSuspect.phoneNumbersList.getSelectedItem());
            }
        }

        if (e.getSource() == guiAddSuspect.addEmailButton) {
            if (!guiAddSuspect.emailsTextField.getText().equals("")) {
                guiAddSuspect.emailList.add(guiAddSuspect.emailsTextField.getText());
                guiAddSuspect.emailsTextField.setText("");
            }
        }

        if (e.getSource() == guiAddSuspect.deleteEmailButton) {
            if (guiAddSuspect.emailList.getSelectedIndex() != -1) {
                guiAddSuspect.emailList.remove(guiAddSuspect.emailList.getSelectedItem());
            }
        }

        if (e.getSource() == guiAddSuspect.addPhotoButton) {
            loadImage();
        }

        if (e.getSource() == guiAddSuspect.nextPhotoButton) {
            showImage("next");
        }

        if (e.getSource() == guiAddSuspect.previousPhotoButton) {
            showImage("previous");
        }

        if (e.getSource() == guiAddSuspect.removePhotoButton) {
            showImage("remove");
        }

        if (e.getSource() == guiAddSuspect.addCompanionButton) {
            if (guiAddSuspect.companionsListAdd.getSelectedIndex() != -1) {
                listCompanionsModel.addElement(listCompanionsAddModel.getElementAt(guiAddSuspect.companionsListAdd.getSelectedIndex()));
                listCompanionsAddModel.remove(guiAddSuspect.companionsListAdd.getSelectedIndex());
            }
        }

        if (e.getSource() == guiAddSuspect.deleteCompanionButton) {
            if (guiAddSuspect.companionsList.getSelectedIndex() != -1) {
                listCompanionsAddModel.addElement(listCompanionsModel.getElementAt(guiAddSuspect.companionsList.getSelectedIndex()));
                listCompanionsModel.removeElementAt(guiAddSuspect.companionsList.getSelectedIndex());
            }
        }
        if (e.getSource() == guiAddSuspect.addResidenciesButton) {
            if (guiAddSuspect.residencieTextField.getText().length() > 0) {
                guiAddSuspect.residenciesList.add(guiAddSuspect.residencieTextField.getText());
                guiAddSuspect.residencieTextField.setText(null);
            }
        }
        if (e.getSource() == guiAddSuspect.deleteResidenciesButton) {
            if (guiAddSuspect.residenciesList.getSelectedIndex() != -1) {
                guiAddSuspect.residenciesList.remove(guiAddSuspect.residenciesList.getSelectedIndex());
            }
        }
    }

    private void showImage(String option) {
        if (photos != null) {
            int currentPhoto = Integer.parseInt(guiAddSuspect.currentPhoto.getText());
            switch (option) {
                case "next":
                    if (photos.size() > currentPhoto + 1) {
                        byte[] foto = photos.get(currentPhoto + 1);
                        Image img = new ImageIcon(foto).getImage().getScaledInstance(guiAddSuspect.imageLabel.getWidth(), guiAddSuspect.imageLabel.getHeight(), Image.SCALE_SMOOTH);
                        guiAddSuspect.imageLabel.setIcon(new ImageIcon(img));
                        guiAddSuspect.currentPhoto.setText(String.valueOf(currentPhoto + 1));
                    }
                    break;
                case "previous":
                    if ((currentPhoto - 1) > -1) {
                        byte[] foto = photos.get(currentPhoto - 1);
                        Image img = new ImageIcon(foto).getImage().getScaledInstance(guiAddSuspect.imageLabel.getWidth(), guiAddSuspect.imageLabel.getHeight(), Image.SCALE_SMOOTH);
                        guiAddSuspect.imageLabel.setIcon(new ImageIcon(img));
                        guiAddSuspect.currentPhoto.setText(String.valueOf(currentPhoto - 1));
                    }
                    break;
                case "remove":
                    if (currentPhoto > -1) {
                        photos.remove(currentPhoto);
                        guiAddSuspect.currentPhoto.setText("-1");
                        guiAddSuspect.imageLabel.setIcon(null);
                    }
                    break;
            }
        }

    }

    private void loadImage() {
        if (photos == null) {
            photos = new ArrayList<>();
            iterator = photos.listIterator();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif"));
        fileChooser.setDialogTitle("Busca una foto");
        if (fileChooser.showOpenDialog(guiAddSuspect) == JFileChooser.APPROVE_OPTION) {
            File image = new File(fileChooser.getSelectedFile().getAbsolutePath());
            byte[] fileContent;
            try {
                fileContent = Files.readAllBytes(image.toPath());
                photos.add(fileContent);
                Image img = new ImageIcon(fileContent).getImage().getScaledInstance(guiAddSuspect.imageLabel.getWidth(), guiAddSuspect.imageLabel.getHeight(), Image.SCALE_SMOOTH);
                guiAddSuspect.imageLabel.setIcon(new ImageIcon(img));
                guiAddSuspect.currentPhoto.setText(String.valueOf(photos.size() - 1));

            } catch (IOException ex) {
                Logger.getLogger(CtrlAddSuspect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void saveSuspect() {
        if (guiAddSuspect.nameTextField.getText().length() > 3
                && guiAddSuspect.surname1TextField.getText().length() > 3
                && guiAddSuspect.surname2TextField.getText().length() > 3
                && checkDNI(guiAddSuspect.dniTextField.getText())) {
            String facts = null;
            String records = null;
            if (guiAddSuspect.recordsTextArea.getText().length() != 0) {
                records = guiAddSuspect.recordsTextArea.getText();
            }
            if (guiAddSuspect.factsTextArea.getText().length() != 0) {
                facts = guiAddSuspect.factsTextArea.getText();
            }

            ArrayList<Suspect> companions = null;
            if (listCompanionsModel.toArray().length != 0) {
                companions = new ArrayList(Arrays.asList(listCompanionsModel.toArray()));
            }

            Suspect suspect = new Suspect(-1,
                    guiAddSuspect.nameTextField.getText(),
                    guiAddSuspect.surname1TextField.getText(),
                    guiAddSuspect.surname2TextField.getText(),
                    guiAddSuspect.dniTextField.getText(),
                    new ArrayList<String>(Arrays.asList(guiAddSuspect.licensePlatesList.getItems())),
                    new ArrayList<String>(Arrays.asList(guiAddSuspect.residenciesList.getItems())),
                    new ArrayList<String>(Arrays.asList(guiAddSuspect.phoneNumbersList.getItems())),
                    new ArrayList<String>(Arrays.asList(guiAddSuspect.emailList.getItems())),
                    companions,
                    records,
                    facts,
                    photos);
            consults.insertSuspect(suspect, null);

            JOptionPane.showMessageDialog(null, "Sospechoso guardado correctamente");
            clearForms();
            suspects = consults.getSuspects();
            addSuspectsCompanion();
        } else {
            JOptionPane.showMessageDialog(null, "Algún campo está incompleto");
        }
    }

    private void clearForms() {
        guiAddSuspect.nameTextField.setText(null);
        guiAddSuspect.surname1TextField.setText(null);
        guiAddSuspect.surname2TextField.setText(null);
        guiAddSuspect.dniTextField.setText(null);
        guiAddSuspect.birthdateTextField.setText(null);
        guiAddSuspect.factsTextArea.setText(null);
        guiAddSuspect.recordsTextArea.setText(null);
        guiAddSuspect.residenciesList.removeAll();
        guiAddSuspect.licensePlatesList.removeAll();
        guiAddSuspect.phoneNumbersList.removeAll();
        guiAddSuspect.emailList.removeAll();
        guiAddSuspect.companionsList.removeAll();
        listCompanionsAddModel.removeAllElements();
        listCompanionsModel.removeAllElements();
        photos = null;
    }

    private void addSuspectsCompanion() {
        if (suspects != null) {
            for (int i = 0; i < suspects.size(); i++) {
                listCompanionsAddModel.addElement(suspects.get(i));
            }
        }
    }

    private boolean checkDNI(String dni) {
        Pattern dniExpresion = Pattern.compile("^(\\d{8})([A-Z|a-z])$");
        Pattern nieExpresion = Pattern.compile("^[XYZ]\\d{7,8}[A-Z]$");
        Matcher dniM = dniExpresion.matcher(dni);
        Matcher nieM = dniExpresion.matcher(dni);
        if (dniM.matches() || nieM.matches()) {
            System.out.println(true);
            return true;
        } else {
            System.out.println(false);
            return false;
        }
    }

    /**
     * @return the guiAddSuspect
     */
    public static GUIAddSuspect getGuiAddSuspect() {
        return guiAddSuspect;
    }

}
