package model;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

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
            System.err.println(ex);
        } catch (SQLException ex) {
            System.err.println(ex);
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
