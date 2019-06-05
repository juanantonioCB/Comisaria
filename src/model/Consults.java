package model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;



/**
 *
 * @author Juan Antonio
 */
public class Consults extends Connect {
    
    private static Consults consults=null;
    
    private Consults(){
    }
    
    public static Consults getConsults(){
        if(consults==null){
            consults = new Consults();
        }
        return consults;
    }

    public void insertSuspect(Suspect s) {
        Connection con = getConnect();
        String sql = "INSERT INTO Sospechosos (nombre, apellido1, apellido2, dni"
                + ", antecedentes, hechos) values (?,?,?,?,?,?)";
        String sqlLicensePlates = "INSERT INTO matriculas (matricula, idSospechoso) values (?,?)";
        String sqlResidencies = "INSERT INTO residencies (residencie, idSospechoso) values (?,?)";
        String sqlEmails = "INSERT INTO emails (email, idSospechoso) values (?,?)";
        String sqlPhoneNumbers = "INSERT INTO telefonos (telefono, idSospechoso) values (?,?)";
        String sqlPhotos = "INSERT INTO fotos (foto, idSospechoso) values (?,?)";
        int last_inserted_id = -1;

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, s.getName());
            ps.setString(2, s.getSurname1());
            ps.setString(3, s.getSurname2());
            ps.setString(4, s.getDNI());
            ps.setString(5, s.getRecords());
            ps.setString(6, s.getFacts());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                last_inserted_id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        // COMPROBAMOS SI HAY MATRICULAS Y LAS INTRODUCIMOS
        if (s.getLicensePlates() != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sqlLicensePlates);
                for (int i = 0; i < s.getLicensePlates().size(); i++) {
                    ps.setString(1, (String) s.getLicensePlates().get(i));
                    ps.setInt(2, last_inserted_id);
                    ps.execute();
                    
                }
            } catch (SQLException ex) {
                System.err.print(ex);
            }
        }
        //RESIDENCIAS
        if (s.getResidencies() != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sqlLicensePlates);
                for (int i = 0; i < s.getLicensePlates().size(); i++) {
                    ps.setString(1, (String) s.getLicensePlates().get(i));
                    ps.setInt(2, last_inserted_id);
                    ps.execute();
                }
            } catch (SQLException ex) {
                System.err.print(ex);
            }
        }
        //EMAILS
        if (s.getEmails() != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sqlEmails);
                for (int i = 0; i < s.getEmails().size(); i++) {
                    ps.setString(1, (String) s.getEmails().get(i));
                    ps.setInt(2, last_inserted_id);
                    ps.execute();
                }
            } catch (SQLException ex) {
                System.err.print(ex);
            }
        }
        //TELEFONOS
        if (s.getPhoneNumbers() != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sqlEmails);
                for (int i = 0; i < s.getPhoneNumbers().size(); i++) {
                    ps.setString(1, (String) s.getPhoneNumbers().get(i));
                    ps.setInt(2, last_inserted_id);
                    ps.execute();
                }
            } catch (SQLException ex) {
                System.err.print(ex);
            }
        }
        
        //FOTOS
        if(s.getPhoto()!=null){
            try {
                PreparedStatement ps = con.prepareStatement(sqlPhotos);
                for(int i = 0;i<s.getPhoto().size();i++){
                    ps.setBytes(1, s.getPhoto().get(i));
                    ps.setInt(2, last_inserted_id);
                    ps.execute();
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
        super.disconnect();
        
    }
    /**
     * Relaciona compañeros con sospechosos
     * @param idSuspect id del sospechoso
     * @param idCompanion id del acompañante que vamos a agregar al sospechoso
     */
    public void addCompanion(int idSuspect, int idCompanion){
        try {
            Connection con = getConnect();
            String sql = "INSERT INTO acompañantes (id1, id2) values (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idSuspect);
            ps.setInt(2, idCompanion);
            ps.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        super.disconnect();
    }
    
    public void deleteSuspect(int id){
        try {
            Connection con = getConnect();
            String sql = "DELETE FROM Sospechosos where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        super.disconnect();
    }
    
    public ArrayList<Suspect> getSuspects(){
        try {
            ArrayList<Suspect> suspects = new ArrayList<>();
            String SqlQuery = "SELECT s.id, s.nombre, s.apellido1, \n" +
                    "s.apellido2, s.dni, s.antecedentes, s.hechos,\n" +
                    "m.matricula, r.residencia, t.telefono, \n" +
                    "e.email, f.foto FROM sospechosos AS s LEFT JOIN emails AS e \n" +
                    "ON e.idSospechoso=s.id LEFT JOIN telefonos AS t \n" +
                    "ON t.idSospechoso=s.id LEFT JOIN residencias AS r \n" +
                    "ON r.idSospechoso=s.id LEFT JOIN matriculas AS m \n" +
                    "ON m.idSospechoso=s.id LEFT JOIN fotos as f\n" +
                    "ON f.idSospechoso=s.id";
            Connection con = getConnect();
            Statement conexion = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            ResultSet rs = conexion.executeQuery(SqlQuery);
            
            //ITERAMOS UNA VEZ
            HashSet<String> correos = new HashSet<>();
            HashSet<String> matriculas = new HashSet<>();
            HashSet<String> residencias = new HashSet<>();
            HashSet<String> telefonos = new HashSet<>();
            HashSet<byte[]> fotos = new HashSet<>();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                do {
                    if (rs.getInt("id") != id) {
                        
                        rs.previous();
                        
                        //SACAMOS LA FOTO Y LA CONVERTIMOS A UN ARRAY DE BYTES
                        
                        Blob blob = rs.getBlob("foto");
                        
                        byte[] blobAsBytes = null;
                        if (blob != null) {
                            int blobLength = (int) blob.length();
                            blobAsBytes = blob.getBytes(1, blobLength);
                            //release the blob and free up memory. (since JDBC 4.0)
                            blob.free();
                            fotos.add(blobAsBytes);
                            
                        }
                        Suspect nuevo = new Suspect(rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("dni"),
                                new ArrayList<>(matriculas), new ArrayList<>(residencias),
                                new ArrayList<>(telefonos),
                                new ArrayList<>(correos),
                                null,
                                rs.getString("antecedentes"), rs.getString("hechos"),
                                new ArrayList<>(fotos)
                        );
                        suspects.add(nuevo);
                        rs.next();
                        id = rs.getInt("id");
                        correos = new HashSet<>();
                        matriculas = new HashSet<>();
                        telefonos = new HashSet<>();
                        residencias = new HashSet<>();
                        rs.previous();
                    } else {
                        //hay que recoorrer las filas pertenecientes al mismo sujeto creando los arraylist correspondientes.
                        
                        correos.add(rs.getString("email"));
                        matriculas.add(rs.getString("matricula"));
                        telefonos.add(rs.getString("telefono"));
                        residencias.add(rs.getString("residencia"));
                    }
                } while (rs.next());
            }
            //insert last one
            rs.previous();
            Blob blob = rs.getBlob("foto");
            byte[] blobAsBytes = null;
            if (blob != null) {
                int blobLength = (int) blob.length();
                blobAsBytes = blob.getBytes(1, blobLength);
                //release the blob and free up memory. (since JDBC 4.0)
                blob.free();
                fotos.add(blobAsBytes);
            }
            
            Suspect nuevo = new Suspect(rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("dni"),
                    new ArrayList<>(matriculas), new ArrayList<>(residencias),
                    new ArrayList<>(telefonos),
                    new ArrayList<>(correos),
                    null,
                    rs.getString("antecedentes"), rs.getString("hechos"),
                    new ArrayList<>(fotos)
            );
            suspects.add(nuevo);
            
            if (suspects.size() == 0) {
                return null;
            } else {
                return suspects;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    
}
