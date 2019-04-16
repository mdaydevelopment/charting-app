package Model;

import java.sql.Date;
import java.util.Comparator;

public class ClientCard {
    private int clientID;
    private String fName;
    private String lName;
    private String phone;
    private String email;
    private Date lastSession;
    private Date lastContact;
    private Date dob;
    private int sessions;
    private int paid;
    private boolean ignore;
    

    public ClientCard() {}

    public ClientCard(int cid) {
        this.clientID = cid;
    }

    public static Comparator<ClientCard> LastSessionComparator =
    	new Comparator<ClientCard>() {
    	@Override
    	public int compare(ClientCard c1, ClientCard c2) {
    		return (c1.getLastSession().compareTo(c2.getLastSession()));
    	}
    };
    public static Comparator<ClientCard> LastContactComparator =
    	new Comparator<ClientCard>() {
    	@Override
    	public int compare(ClientCard c1, ClientCard c2) {
    		return (c1.getLastContact().compareTo(c2.getLastContact()));
    	}
    };
    public static Comparator<ClientCard> TotalSessionsComparator =
    	new Comparator<ClientCard>() {
    	@Override
    	public int compare(ClientCard c1, ClientCard c2) {
    		return (c1.getSessions() - c2.getSessions());
    	}
    };
    public static Comparator<ClientCard> TotalPaidComparator =
    	new Comparator<ClientCard>() {
    	@Override
    	public int compare(ClientCard c1, ClientCard c2) {
    		return (c1.getPaid() - c2.getPaid());
    	}
    };

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int id) {
        this.clientID = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String f) {
        this.fName = f;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String l) {
        this.lName = l;
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

    public Date getLastSession() {
        return lastSession;
    }

    public void setLastSession(Date d) {
        this.lastSession = d;
    }

    public Date getLastContact() {
        return lastContact;
    }

    public void setLastContact(Date d) {
        this.lastContact = d;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date d) {
        this.dob = d;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int s) {
        this.sessions = s;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int p) {
        this.paid = p;
    }

    public boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(boolean i) {
        this.ignore = i;
    }

	@Override
	public String toString() {
		return "ClientCard [clientID=" + clientID + ", fName=" + fName + ", lName=" + lName + ", phone=" + phone
				+ ", email=" + email + ", lastSession=" + lastSession + ", lastContact=" + lastContact + ", dob=" + dob
				+ ", sessions=" + sessions + ", paid=" + paid + ", ignore=" + ignore + "]";
	}


}
