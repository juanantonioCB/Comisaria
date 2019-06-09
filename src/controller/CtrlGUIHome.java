package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Consults;
import model.Suspect;
import view.*;

/**
 *
 * @author Juan Antonio
 */
public class CtrlGUIHome implements ActionListener {

    GUIHome gui;
    GUIAddSuspect addSuspect;
    GUIHomePanel homePanel;
    GUIviewSuspect viewSuspect;
    Consults consults;
    Suspect suspect;

    public CtrlGUIHome() {
        this.gui = new GUIHome();
        this.gui.inicioButton.addActionListener(this);
        this.gui.addSuspect.addActionListener(this);
        this.gui.suspectsRelatedButton.addActionListener(this);
        this.gui.aboutButton.addActionListener(this);
        this.gui.viewSuspectsButton.addActionListener(this);
    }

    public void start() throws Exception {

        if (CtrlHomePanel.getGuiHomePanel() == null
                || CtrlAddSuspect.getGuiAddSuspect() == null
                || CtrlViewSuspect.getGuiViewSuspect() == null) {
            throw new Exception("Algun controlador no ha sido instanciado");

        } else {

            this.gui.setTitle("Comisaria 0.1");
            this.gui.setVisible(true);

            CtrlHomePanel.getGuiHomePanel().setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
            this.gui.contentPanel.removeAll();
            this.gui.contentPanel.add(CtrlHomePanel.getGuiHomePanel());
            this.gui.contentPanel.revalidate();
            this.gui.contentPanel.repaint();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.gui.inicioButton) {
            CtrlHomePanel.getGuiHomePanel().setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
            this.gui.contentPanel.removeAll();
            this.gui.contentPanel.add(CtrlHomePanel.getGuiHomePanel());
            this.gui.contentPanel.revalidate();
            this.gui.contentPanel.repaint();
        }

        if (e.getSource() == this.gui.viewSuspectsButton) {
            CtrlViewSuspect.getGuiViewSuspect().setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
            this.gui.contentPanel.removeAll();
            this.gui.contentPanel.add(CtrlViewSuspect.getGuiViewSuspect());
            this.gui.contentPanel.revalidate();
            this.gui.contentPanel.repaint();
        }

        if (e.getSource() == this.gui.addSuspect) {
            CtrlAddSuspect.getGuiAddSuspect().setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
            this.gui.contentPanel.removeAll();
            this.gui.contentPanel.add(CtrlAddSuspect.getGuiAddSuspect());
            this.gui.contentPanel.revalidate();
            this.gui.contentPanel.repaint();
        }

        if (e.getSource() == this.gui.aboutButton) {

        }

    }

}
