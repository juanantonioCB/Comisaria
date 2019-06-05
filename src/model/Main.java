package model;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author Juan Antonio
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Consults c = Consults.getConsults();
        ArrayList<Suspect> s = new ArrayList<Suspect>();
            s = c.getSuspects();
            
            ArrayList<String> residencias = new ArrayList<>();
            residencias.add("residencie_1");
            residencias.add("residencie_2");
            ArrayList<String> telefonos = new ArrayList<>();
            telefonos.add("000000");
            telefonos.add("111111");
            
            
            File imgFile = new File("foto.jpg");
                FileInputStream fis = new FileInputStream(imgFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int i; (i = fis.read()) != -1;) {
                    bos.write(buf, 0, i);
                }
                byte[] imageBytes = bos.toByteArray();
                byte[] imageBytes1 = bos.toByteArray();
                
                if(imageBytes.equals(imageBytes1)){
                    System.out.println(true);
                }
                
                ArrayList<byte[]> fotos = new ArrayList<>();
                fotos.add(imageBytes);
                fotos.add(imageBytes1);
        if(imageBytes.equals(imageBytes1))
            System.out.println(true);

        
        
    }
}
