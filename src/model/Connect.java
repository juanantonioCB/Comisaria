package model;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Antonio
 */
public abstract class Connect {

    private String db = "comisaria";
    private String user = "sa";
    private String password = "";
    private String url = "jdbc:h2:tcp://localhost/~/" + db;
    private Connection con = null;

    protected Connection getConnect() {
        con=null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se ha podido establecer conexión con la base de datos");

            System.exit(0);
        }
        return con;
    }
    
    protected void disconnect(){
        try {
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

}
