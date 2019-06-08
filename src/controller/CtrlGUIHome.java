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

    GUI gui;
    GUIAddSuspect addSuspect;
    GUIHomePanel homePanel;
    GUIviewSuspect viewSuspect;
    Consults consults;
    Suspect suspect;

    public CtrlGUIHome() {
        this.gui = new GUI();
        this.addSuspect = new GUIAddSuspect();
        this.homePanel = new GUIHomePanel();
        this.viewSuspect = new GUIviewSuspect();
        this.consults = Consults.getConsults();

        this.gui.inicioButton.addActionListener(this);
        this.gui.addSuspect.addActionListener(this);
        this.gui.suspectsRelatedButton.addActionListener(this);
        this.gui.aboutButton.addActionListener(this);
        this.gui.viewSuspectsButton.addActionListener(this);
    }

    public void start() {
        this.gui.setTitle("Comisaria 0.1");
        this.gui.setVisible(true);
        this.homePanel.setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
        this.gui.contentPanel.removeAll();
        this.gui.contentPanel.add(this.homePanel);
        this.gui.contentPanel.revalidate();
        this.gui.contentPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.gui.inicioButton) {
            this.homePanel.setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
            this.gui.contentPanel.removeAll();
            this.gui.contentPanel.add(this.homePanel);
            this.gui.contentPanel.revalidate();
            this.gui.contentPanel.repaint();
        } 
        
        if(e.getSource()==this.gui.viewSuspectsButton){
            this.viewSuspect.setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
            this.gui.contentPanel.removeAll();
            this.gui.contentPanel.add(this.viewSuspect);
            this.gui.contentPanel.revalidate();
            this.gui.contentPanel.repaint();
        }
        
        if (e.getSource()==this.gui.addSuspect){
            this.addSuspect.setSize(this.gui.contentPanel.getWidth(), this.gui.getHeight());
            this.gui.contentPanel.removeAll();
            this.gui.contentPanel.add(this.addSuspect);
            this.gui.contentPanel.revalidate();
            this.gui.contentPanel.repaint();
        }
        
        if(e.getSource()==this.gui.aboutButton){
            
        }
        

    }
    


}
