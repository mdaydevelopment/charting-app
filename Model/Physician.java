package Model;


public class Physician {
    private int physicianID;
    private String fName;
    private String lName;
    private String phone;

    public Physician() {}

    public Physician(int pid, String fn, String ln, String po) {
        this.physicianID = pid;
        this.fName = fn;
        this.lName = ln;
        this.phone = po;
    }

    public Physician(String fn, String ln, String po) {
        this.fName = fn;
        this.lName = ln;
        this.phone = po;
    }

    public Physician(int pid) {
        this.physicianID = pid;
    }

    public int getPhysicianID() {
        return physicianID;
    }

    public void setPhysicianID(int pid) {
        this.physicianID = pid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String po) {
        this.phone = po;
    }
}

/*
            sql = "CREATE TABLE physician "
                        +"(physician_id         INTEGER           PRIMARY KEY, "
                        +"f_name                VARCHAR(35), "
                        +"l_name                VARCHAR(35)       NOT NULL, "
                        +"phone                 CHAR(10)"
 */
