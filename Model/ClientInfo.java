package Model;
/**
 * client info class for charting app
 * @author: Michael Day
 */

import java.util.ArrayList;
import java.sql.Date;

public class ClientInfo {
    private int cInfoID;
    private int clientID;
    private Date date;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;
    private String occupation;
    private int physicianID;
    private String acdntSgrs;
    private String allergies;
    private String repRisk;
    private ArrayList<ClientCondition> conds;

    public ClientInfo() {
        conds = new ArrayList<ClientCondition>();
    }

    public ClientInfo(int iid) {
        this.cInfoID = iid;
        conds = new ArrayList<ClientCondition>();
    }

    public int getCInfoID() {
        return cInfoID;
    }

    public void setCInfoID(int iid) {
        this.cInfoID = iid;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int cid) {
        this.clientID = cid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date d) {
        this.date = d;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String s) {
        this.street = s;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String c) {
        this.city = c;
    }

    public String getState() {
        return state;
    }

    public void setState(String s) {
        this.state = s;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String z) {
        this.zip = z;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String p) {
        this.phone = p;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String o) {
        this.occupation = o;
    }

    public int getPhysicianID() {
        return physicianID;
    }

    public void setPhysicianID(int p) {
        this.physicianID = p;
    }

    public String getAcdntSgrs() {
        return acdntSgrs;
    }

    public void setAcdntSgrs(String as) {
        this.acdntSgrs = as;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String a) {
        this.allergies = a;
    }

    public String getRepRisk() {
        return repRisk;
    }

    public void setRepRisk(String rr) {
        this.repRisk = rr;
    }

    public ArrayList<ClientCondition> getConditions() {
        return conds;
    }

    public void setConditions(ArrayList<ClientCondition> c) {
        this.conds = c;
    }

    public void addCondition(ClientCondition c) {
        conds.add(c);
    }

    public void addCondition(int c, String d) {
        ClientCondition cc = new ClientCondition(c, d);
        conds.add(cc);
    }

	@Override
	public String toString() {
		return "ClientInfo [cInfoID=" + cInfoID + ", clientID=" + clientID + ", date=" + date + ", street=" + street
				+ ", city=" + city + ", state=" + state + ", zip=" + zip + ", phone=" + phone + ", email=" + email
				+ ", occupation=" + occupation + ", physicianID=" + physicianID + ", acdntSgrs=" + acdntSgrs
				+ ", allergies=" + allergies + ", repRisk=" + repRisk + ", conds=" + conds + "]";
	}

}

/*
            sql = "CREATE TABLE client_info "
                        +"(cinfo_id             INTEGER           PRIMARY KEY, "
                        +"client_id             INT               NOT NULL, "
                        +"date                  DATE              NOT NULL, "
                        +"street                VARCHAR(200), "
                        +"city                  VARCHAR(100), "
                        +"state                 CHAR(2), "
                        +"zip                   CHAR(5), "
                        +"phone                 CHAR(10), "
                        +"email                 VARCHAR(254), "
                        +"occupation            VARCHAR(75), "
                        +"physician_id          INT, "
                        +"accidents_surgeries   TEXT, "
                        +"allergies             TEXT, "
                        +"repetitive_risk       TEXT, "
                        +"FOREIGN KEY (client_id) REFERENCES client (client_id), "
                        +"FOREIGN KEY (physician_id) REFERENCES physician (physician_id)"
                        +");";
*/
