package model;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Antonio
 */
public abstract class Connect {

    private String db = "test";
    private String user = "sa";
    private String password = "";
    private String url = "jdbc:h2:tcp://localhost/~/" + db;
    private Connection con = null;

    protected Connection getConnect() {
        con = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            start();
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se ha podido establecer conexión con la base de datos");
            System.exit(0);
        }
        return con;
    }

    /**
     * crea las tablas si no existen.
     */
    private void start() {
        String sql = "create table if not exists Sospechosos (\n"
                + "	id int auto_increment,\n"
                + "	nombre varchar(255),\n"
                + "	apellido1 varchar(255),\n"
                + "	apellido2 varchar(255),\n"
                + "	dni varchar(255),\n"
                + "	antecedentes varchar(255),\n"
                + "	hechos varchar(255),\n"
                + "	primary key(id)\n"
                + ");\n"
                + "create table if not exists Matriculas(\n"
                + "	id int auto_increment primary key,\n"
                + "	matricula varchar(255),\n"
                + "	idSospechoso int,\n"
                + "	foreign key (idSospechoso) references Sospechosos(id)\n"
                + "	ON DELETE CASCADE\n"
                + ");\n"
                + "\n"
                + "create table if not exists Fotos(\n"
                + "	id int auto_increment primary key,\n"
                + "	foto blob,\n"
                + "	idSospechoso int,\n"
                + "	foreign key (idSospechoso) references Sospechosos(id)\n"
                + "	ON DELETE CASCADE\n"
                + ");\n"
                + "create table if not exists Residencias(\n"
                + "	id int auto_increment primary key,\n"
                + "	idSospechoso int,\n"
                + "	residencia varchar(255),\n"
                + "	foreign key (idSospechoso) references Sospechosos(id)\n"
                + "	ON DELETE CASCADE\n"
                + ");\n"
                + "create table if not exists Telefonos(\n"
                + "	id int auto_increment primary key,\n"
                + "	idSospechoso int,\n"
                + "	telefono varchar(255),\n"
                + "	foreign key (idSospechoso) references Sospechosos(id)\n"
                + "	ON DELETE CASCADE\n"
                + ");\n"
                + "create table if not exists Acompañantes(\n"
                + "	id1 int,\n"
                + "	id2 int,\n"
                + "	primary key(id1,id2),\n"
                + "	foreign key (id1) references Sospechosos(id),\n"
                + "	foreign key (id2) references Sospechosos(id)\n"
                + "	ON DELETE CASCADE\n"
                + ");\n"
                + "create table if not exists emails(\n"
                + "	id int auto_increment primary key,\n"
                + "	idSospechoso int,\n"
                + "	email varchar(255),\n"
                + "	foreign key (idSospechoso) references Sospechosos(id)\n"
                + "	ON DELETE CASCADE\n"
                + ");";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void disconnect() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

}
