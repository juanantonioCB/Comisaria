package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.GUIHomePanel;

/**
 *
 * @author Juan Antonio
 */
public class CtrlHomePanel implements ActionListener {

    private static GUIHomePanel guiHomePanel;

    public CtrlHomePanel() {
        guiHomePanel = new GUIHomePanel();
    }

    

    /**
     * @return the guiAddSuspect
     */
    public static GUIHomePanel getGuiHomePanel() {
        return guiHomePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

}
