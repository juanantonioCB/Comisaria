package model;

import controller.CtrlAddSuspect;
import controller.CtrlGUIHome;
import controller.CtrlHomePanel;
import controller.CtrlViewSuspect;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Antonio
 */
public class Main {

    public static void main(String[] args) throws SQLException{
        /*CtrlGUIHome ctrlGUI = new CtrlGUIHome();
        CtrlAddSuspect ctrlAddSuspect = new CtrlAddSuspect();
        CtrlHomePanel ctrlHome = new CtrlHomePanel();
        CtrlViewSuspect ctrlViewSuspect = new CtrlViewSuspect();

        try {
            ctrlGUI.start();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        Consults consults = Consults.getConsults();
        System.out.println(consults.searchSuspect("raul").size());
                
        
        
        
        
        
    }
}
