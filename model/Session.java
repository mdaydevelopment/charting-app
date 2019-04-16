package model;

import java.sql.Date;

public class Session {
    private int sessionID;
    private int clientID;
    private Date date;
    private String time;
    private String complaint;
    private String treatment;
    private String notes;
    private int minutes;
    private int paid;

    public Session() {
    	this.sessionID = -1;
    }

    public Session(int cid) {
    	this.sessionID = -1;
        this.clientID = cid;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sid) {
        this.sessionID = sid;
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

    public String getTime() {
        return time;
    }

    public void setTime(String t) {
        this.time = t;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String c) {
        this.complaint = c;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String t) {
        this.treatment = t;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String n) {
        this.notes = n;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int m) {
        this.minutes = m;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int p) {
        this.paid = p;
    }

    @Override
    public String toString() {
        return time + " " + complaint + " " + treatment + " " + notes + " " + minutes + " " + paid;
    }

}
