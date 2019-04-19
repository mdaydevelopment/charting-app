package driver;
import java.sql.*;
import java.util.LinkedList;

import database.DBManager;
import model.Client;
import model.ClientInfo;
import model.Session;

public class TestDriver {
    public static void main(String[] args) {
        try {
            DBManager db = new DBManager();

            Client client1 = new Client();

            client1.setFName("Michael");
            client1.setLName("Day");

            db.insertClient(client1);

            Client client2 = db.getClient(1);

            System.out.println(client2.toString());

            Session session1 = new Session(1);
            session1.setComplaint("This is a complaint");

            db.insertSession(session1);

            Session session2 = db.getLastSession(1);

            System.out.println(session2.toString());

            LinkedList<Session> list = db.getSessions(1);

            session2 = list.getFirst();

            System.out.println(session2.toString());
            
            ClientInfo cinfo1 = new ClientInfo();
            cinfo1.setClientID(1);
            cinfo1.setStreet("123 Cool Street");
            cinfo1.setCity("Asgaard");
            cinfo1.setState("Fluid");
            cinfo1.setZip("99999");
            cinfo1.setPhone("555-666-7777");
            cinfo1.setEmail("mr.amazing@mdaydevelopment.com");
            cinfo1.setOccupation("Intern with ?");
            cinfo1.setAcdntSgrs("Arrow to knee");
            cinfo1.setAllergies("Crowds");
            cinfo1.setRepRisk("Awesome overload");
            
            db.insertClientInfo(cinfo1);
            ClientInfo cinfo2 = db.getClientInfo(1);
            System.out.println(cinfo2.toString());

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

