package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.GUIAddResidencie;

/**
 *
 * @author Juan Antonio
 */
public class CtrlGUIAddResidencie implements ActionListener {

    private static GUIAddResidencie guiAddResidencie;

    public CtrlGUIAddResidencie() {
        guiAddResidencie = new GUIAddResidencie();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    /**
     * @return the guiAddSuspect
     */
    public static GUIAddResidencie getGuiViewSuspect() {
        
        return guiAddResidencie;
    }

}
