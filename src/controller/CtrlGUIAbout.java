package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.GUIAbout;

/**
 *
 * @author Juan Antonio
 */
public class CtrlGUIAbout implements ActionListener {

    private static GUIAbout guiAbout;

    public CtrlGUIAbout() {
        guiAbout = new GUIAbout();
    }

    

    /**
     * @return the guiAddSuspect
     */
    public static GUIAbout getGuiAbout() {
        return guiAbout;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

}
