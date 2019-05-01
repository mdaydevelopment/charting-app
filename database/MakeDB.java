package database;
import java.sql.*;

public class MakeDB {
    public static void main( String args[] ) {
        Connection c = null;
        Statement stmt = null;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            sql = "CREATE TABLE client "
                        +"(client_id            INTEGER           PRIMARY KEY, "
                        +"f_name                VARCHAR(35)       NOT NULL, "
                        +"l_name                VARCHAR(35)       NOT NULL, "
                        +"dob                   DATE, "
                        +"referred_by           INT, "
                        +"last_contact          DATE, "
                        +"ignore                INT, "
                        +"FOREIGN KEY (referred_by) REFERENCES client (client_id)"
                        +");";
            stmt.executeUpdate(sql);
            System.out.println("Client table added.");

            sql = "CREATE TABLE physician "
                        +"(physician_id         INTEGER           PRIMARY KEY, "
                        +"f_name                VARCHAR(35), "
                        +"l_name                VARCHAR(35)       NOT NULL, "
                        +"phone                 CHAR(10)"
                        +");";
            stmt.executeUpdate(sql);
            System.out.println("Physician table added.");

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
            stmt.executeUpdate(sql);
            System.out.println("Client Info table added.");

            sql = "CREATE TABLE session "
                        +"(session_id           INTEGER           PRIMARY KEY, "
                        +"client_id             INT               NOT NULL, "
                        +"date                  DATE              NOT NULL, "
                        +"time                  CHAR(4), "
                        +"complaint             TEXT, "
                        +"treatment             TEXT, "
                        +"notes                 TEXT, "
                        +"minutes               INT, "
                        +"paid                  INT, "
                        +"FOREIGN KEY (client_id) REFERENCES client (client_id)"
                        +");";
            stmt.executeUpdate(sql);
            System.out.println("Session table added.");

            sql = "CREATE TABLE condition "
                        +"(condition_id         INTEGER           PRIMARY KEY, "
                        +"condition             VARCHAR(35)"
                        +");";
            stmt.executeUpdate(sql);
            System.out.println("Condition table added.");

            sql = "CREATE TABLE client_condition "
                        +"(cinfo_id             INT REFERENCES client_info (cinfo_id), "
                        +"condition_id          INT REFERENCES condition (condition_id), "
                        +"condition_desc        TEXT, "
                        +"PRIMARY KEY (cinfo_id, condition_id)"
                        +");";
            stmt.executeUpdate(sql);
            System.out.println("Client/Condition table added.");

            sql = "INSERT INTO condition (condition) VALUES ('Other');"
                 +"INSERT INTO condition (condition) VALUES ('Diabetes');"
                 +"INSERT INTO condition (condition) VALUES ('Headaches');"
                 +"INSERT INTO condition (condition) VALUES ('Pregnant');"
                 +"INSERT INTO condition (condition) VALUES ('Arthritis');"
                 +"INSERT INTO condition (condition) VALUES ('High Blood Pressure');"
                 +"INSERT INTO condition (condition) VALUES ('Epilepsy');"
                 +"INSERT INTO condition (condition) VALUES ('Seizures');"
                 +"INSERT INTO condition (condition) VALUES ('Joint Swelling');"
                 +"INSERT INTO condition (condition) VALUES ('Varicose Veins');"
                 +"INSERT INTO condition (condition) VALUES ('Bruise Easily');"
                 +"INSERT INTO condition (condition) VALUES ('Cardiac Problems');"
                 +"INSERT INTO condition (condition) VALUES ('Circulatory Problems');"
                 +"INSERT INTO condition (condition) VALUES ('Back Pain');"
                 +"INSERT INTO condition (condition) VALUES ('Numbness');"
                 +"INSERT INTO condition (condition) VALUES ('Stabbing Pains');"
                 +"INSERT INTO condition (condition) VALUES ('Sensitive to Touch');"
                 +"INSERT INTO condition (condition) VALUES ('Osteoporosis');"
                 +"INSERT INTO condition (condition) VALUES ('Tension Soreness');"
                 +"INSERT INTO condition (condition) VALUES ('Stress');";
            stmt.executeUpdate(sql);
            System.out.println("Condition table populated.");
            
            sql = "INSERT INTO client (f_name, l_name, dob, referred_by, last_contact, ignore) "
            	+ "VALUES ('YourName', 'Here', 0, NULL, 0, 0); "
            	+ "INSERT INTO client_info (client_id, date) "
            	+ "VALUES (1, 0);"
            	+ "INSERT INTO session (client_id, date)"
            	+ "VALUES (1, 0)";
            stmt.executeUpdate(sql);
            System.out.println("Seed data added.");

            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }
}
