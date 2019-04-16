package Driver;
import java.sql.*;
import java.util.LinkedList;

import Database.DBManager;
import Model.Client;
import Model.Session;

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

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

