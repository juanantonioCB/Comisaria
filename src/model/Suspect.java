package model;

import java.util.ArrayList;

/**
 * Clase sospechoso.
 * @author Juan Antonio
 */
public class Suspect {    
    
    private int id;
    private String Name;
    private String Surname1;
    private String Surname2;
    private String DNI;
    private ArrayList Residencies;
    private ArrayList licensePlates;
    private ArrayList emails;
    private ArrayList companions;
    private ArrayList phoneNumbers;
    private String records;
    private String facts;
    private ArrayList<byte[]> photos;

    public Suspect(int id,
            String name, 
            String s1, 
            String s2, 
            String dni, 
            ArrayList<String> licensePlates,
            ArrayList<String> residencies, 
            ArrayList<String> phoneNumbers,
            ArrayList<String> emails, 
            ArrayList<Suspect> companions,   
            String records, 
            String facts,
            ArrayList<byte[]> photo) {
        this.id=id;
        this.Name = name;
        this.Surname1 = s1;
        this.Surname2 = s2;
        this.DNI = dni;
        this.licensePlates=licensePlates;
        this.Residencies=residencies;
        this.emails=emails;
        this.companions=companions;
        this.phoneNumbers=phoneNumbers;
        this.records=records;
        this.facts=facts;
        this.photos=photo;

    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Surname1
     */
    public String getSurname1() {
        return Surname1;
    }

    /**
     * @param Surname1 the Surname1 to set
     */
    public void setSurname1(String Surname1) {
        this.Surname1 = Surname1;
    }

    /**
     * @return the Surname2
     */
    public String getSurname2() {
        return Surname2;
    }

    /**
     * @param Surname2 the Surname2 to set
     */
    public void setSurname2(String Surname2) {
        this.Surname2 = Surname2;
    }

    /**
     * @return the DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * @param DNI the DNI to set
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     * @return the Residencies
     */
    public ArrayList getResidencies() {
        return Residencies;
    }

    /**
     * @param Residencies the Residencies to set
     */
    public void setResidencies(ArrayList Residencies) {
        this.Residencies = Residencies;
    }

    /**
     * @return the licensePlates
     */
    public ArrayList getLicensePlates() {
        return licensePlates;
    }

    /**
     * @param licensePlates the licensePlates to set
     */
    public void setLicensePlates(ArrayList licensePlates) {
        this.licensePlates = licensePlates;
    }

    /**
     * @return the emails
     */
    public ArrayList getEmails() {
        return emails;
    }

    /**
     * @param emails the emails to set
     */
    public void setEmails(ArrayList emails) {
        this.emails = emails;
    }

    /**
     * @return the companions
     */
    public ArrayList getCompanions() {
        return companions;
    }

    /**
     * @param companions the companions to set
     */
    public void setCompanions(ArrayList companions) {
        this.companions = companions;
    }

    /**
     * @return the phoneNumbers
     */
    public ArrayList getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * @param phoneNumbers the phoneNumbers to set
     */
    public void setPhoneNumbers(ArrayList phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /**
     * @return the records
     */
    public String getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(String records) {
        this.records = records;
    }

    /**
     * @return the facts
     */
    public String getFacts() {
        return facts;
    }

    /**
     * @param facts the facts to set
     */
    public void setFacts(String facts) {
        this.facts = facts;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        return "-----------------\n"
                +this.id+"\n"
                +this.Name+"\n"
                +this.Surname1+"\n"
                +this.Surname2+"\n"
                +this.DNI+"\n"
                +this.licensePlates+"\n"
                +this.Residencies+"\n"
                +this.phoneNumbers+"\n"
                +this.emails.toString()+"\n"
                +this.companions+"\n"
                +this.records+"\n"
                +this.facts+"\n"
                +"FOTO ---"+this.photos;
    }

    /**
     * @return the photo
     */
    public ArrayList<byte[]> getPhoto() {
        return photos;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(ArrayList<byte[]> photos) {
        this.photos = photos;
    }
    
}