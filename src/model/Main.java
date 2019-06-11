package model;

import controller.CtrlAddSuspect;
import controller.CtrlGUIHome;
import controller.CtrlHomePanel;
import controller.CtrlViewSuspect;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.GUILoad;

/**
 *
 * @author Juan Antonio
 */
public class Main {

    public static void main(String[] args) throws SQLException{
        GUILoad load = new GUILoad();
        load.setVisible(true);
        load.jProgressBar1.setValue(10);
        
        CtrlGUIHome ctrlGUI = new CtrlGUIHome();
        load.jProgressBar1.setValue(30);
        CtrlAddSuspect ctrlAddSuspect = new CtrlAddSuspect();
        load.jProgressBar1.setValue(60);
        CtrlHomePanel ctrlHome = new CtrlHomePanel();
        load.jProgressBar1.setValue(80);
        CtrlViewSuspect ctrlViewSuspect = new CtrlViewSuspect();
        load.jProgressBar1.setValue(100);
        load.setVisible(false);

        try {
            ctrlGUI.start();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Consults consults = Consults.getConsults();

        
        
        
        
        
        
    }
}
