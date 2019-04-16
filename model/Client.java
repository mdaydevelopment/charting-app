package model;
import java.sql.Date;

public class Client {
    private int clientID;
    private String fName;
    private String lName;
    private Date dob;
    private int referredBy;
    private Date lastContact;
    private boolean ignore;

    public Client() {
    	this.clientID = -1;
    }

    public Client(int id, String fn, String ln, Date dob, int rb) {
        this.clientID = id;
        this.fName = fn;
        this.lName = ln;
        this.dob = dob;
        this.referredBy = rb;
    }

    public Client(Date dob, String fn, String ln, int rb) {
        this.clientID = -1;
        this.dob = dob;
        this.fName = fn;
        this.lName = ln;
        this.referredBy = rb;
    }

    public Client(int id) {
        this.clientID = id;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int id) {
        this.clientID = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fn) {
        this.fName = fn;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String ln) {
        this.lName = ln;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(int rb) {
        this.referredBy = rb;
    }

    public Date getLastContact() {
        return lastContact;
    }

    public void setLastContact(Date lc) {
        this.lastContact = lc;
    }

    public boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(boolean i) {
        this.ignore = i;
    }

	@Override
	public String toString() {
		return "Client [clientID=" + clientID + ", fName=" + fName + ", lName=" + lName + ", dob=" + dob
				+ ", referredBy=" + referredBy + ", lastContact=" + lastContact + ", ignore=" + ignore + "]";
	}

}



