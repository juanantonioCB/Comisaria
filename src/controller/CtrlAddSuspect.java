package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Consults;
import model.Suspect;
import view.GUIAddResidencie;
import view.GUIAddSuspect;

/**
 *
 * @author Juan Antonio
 */
public class CtrlAddSuspect implements ActionListener {

    private static GUIAddSuspect guiAddSuspect;
    private Consults consults = Consults.getConsults();
    private ArrayList<byte[]> photos = null;
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
        addSuspectsCompanion();
        guiAddSuspect.companionsListAdd.setModel(listCompanionsAddModel);
        guiAddSuspect.companionsList.setModel(listCompanionsModel);
        guiAddSuspect.deleteResidenciesButton.addActionListener(this);

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
            if(guiAddSuspect.residencieTextField.getText().length()>0){
                guiAddSuspect.residenciesList.add(guiAddSuspect.residencieTextField.getText());
                guiAddSuspect.residencieTextField.setText(null);
            }
        }
        if(e.getSource()==guiAddSuspect.deleteResidenciesButton){
            if(guiAddSuspect.residenciesList.getSelectedIndex()!=-1){ 
                guiAddSuspect.residenciesList.remove(guiAddSuspect.residenciesList.getSelectedIndex());
            }
        }
    }

    private void showImage(String option) {
        if (photos != null) {
            ListIterator<byte[]> iterator = photos.listIterator();
            switch (option) {
                case "next":
                    if (iterator.hasNext()) {

                        ByteArrayInputStream bais = new ByteArrayInputStream(photos.get(0));
                        try {
                            BufferedImage image = ImageIO.read(bais);
                            new ImageIcon(image);
                        } catch (IOException ex) {
                            Logger.getLogger(CtrlAddSuspect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    break;
                case "previous":
                    if (iterator.hasPrevious()) {
                        iterator.previous();
                        System.out.println(iterator);
                    }
            }
        }

    }

    private void loadImage() {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif"));
        fileChooser.setDialogTitle("Busca una foto");
        if (fileChooser.showOpenDialog(guiAddSuspect) == JFileChooser.APPROVE_OPTION) {
            File image = new File(fileChooser.getSelectedFile().getAbsolutePath());
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(image);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CtrlAddSuspect.class.getName()).log(Level.SEVERE, null, ex);
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int i; (i = fis.read()) != -1;) {
                    bos.write(buf, 0, i);
                }
            } catch (IOException ex) {
                Logger.getLogger(CtrlAddSuspect.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] imageBytes = bos.toByteArray();
            photos.add(imageBytes);
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
            consults.insertSuspect(suspect);

            JOptionPane.showMessageDialog(null, "Sospechoso guardado correctamente");
            clearForms();
            suspects = consults.getSuspects();
            addSuspectsCompanion();
        } else {
            JOptionPane.showMessageDialog(null, "Falta algún campo por completar");
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
