package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.GUIviewSuspect;

/**
 *
 * @author Juan Antonio
 */
public class CtrlViewSuspect implements ActionListener {

    private static GUIviewSuspect guiViewSuspect;

    public CtrlViewSuspect() {
        guiViewSuspect = new GUIviewSuspect();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    /**
     * @return the guiAddSuspect
     */
    public static GUIviewSuspect getGuiViewSuspect() {
        return guiViewSuspect;
    }

}
