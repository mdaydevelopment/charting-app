package database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import model.Client;
import model.ClientCard;
import model.ClientCondition;
import model.ClientInfo;
import model.Physician;
import model.Session;

public class DBManager {
    private Connection c;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String sql;

    public DBManager() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
        } catch (Exception e) {
            throw e;
        }
    }

    // this is what happens when you don't use a real datetime
    public ClientCard[] getCards() throws Exception {
        sql = "SELECT COUNT(*) FROM client";
        rs = stmt.executeQuery(sql);
        ClientCard[] cards = new ClientCard[rs.getInt(1)];
        sql = "SELECT c.client_id, c.f_name, c.l_name, ci.phone, "
				+ "ci.email, s.lasts, c.last_contact, c.dob, "
				+ "s.totals, s.totalp, c.ignore "
			+ "FROM client c "
			+ "LEFT JOIN "
				+ "(SELECT cis.client_id, cis.phone, cis.email "
				+ "FROM client_info cis "
				+ "JOIN "
					+ "(SELECT mid.client_id, MAX(mid.cinfo_id) maxid "
					+ "FROM client_info mid "
					+ "JOIN "
						+ "(SELECT client_id, MAX(date) maxdate "
						+ "FROM client_info "
						+ "GROUP BY client_id) mdt "
					+ "ON mdt.client_id = mid.client_id "
					+ "WHERE mid.date = mdt.maxdate "
					+ "GROUP BY mid.client_id) mid "
				+ "ON cis.client_id = mid.client_id "
				+ "WHERE cis.cinfo_id = mid.maxid) ci "
			+ "ON c.client_id = ci.client_id "
			+ "LEFT JOIN "
				+ "(SELECT client_id, COUNT(*) totals, "
					+ "SUM(paid) totalp, MAX(date) lasts "
				+ "FROM session "
				+ "GROUP BY client_id) s "
			+ "ON c.client_id = s.client_id "
			+ "ORDER BY c.client_id ";
        rs = stmt.executeQuery(sql);
        for (int i = 0; i < cards.length; i++) {
			rs.next();
			cards[i] = new ClientCard(rs.getInt(1));
            cards[i].setFName(rs.getString(2));
            cards[i].setLName(rs.getString(3));
            cards[i].setPhone(rs.getString(4));
            cards[i].setEmail(rs.getString(5));
            cards[i].setLastSession(rs.getDate(6));
            cards[i].setLastContact(rs.getDate(7));
            cards[i].setDob(rs.getDate(8));
            cards[i].setSessions(rs.getInt(9));
            cards[i].setPaid(rs.getInt(10));
            if (rs.getInt(11) == 1) {
                cards[i].setIgnore(true);
            } else {
                cards[i].setIgnore(false);
            }
		}
        return cards;
    }

    public int insertClient(Client cl) throws Exception {
        sql = "INSERT INTO client "
            +"(f_name, l_name, dob, referred_by, last_contact, ignore) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setString(1, cl.getFName());
        pstmt.setString(2, cl.getLName());
        pstmt.setDate(3, cl.getDob());
        if (cl.getReferredBy() != 0) {
            pstmt.setInt(4, cl.getReferredBy());
        } else {
            pstmt.setNull(4, Types.INTEGER);
        }
        if (cl.getLastContact() != null) {
            pstmt.setDate(5, cl.getLastContact());
        } else {
            pstmt.setDate(5, Date.valueOf(LocalDate.now()));
        }
        if (cl.getIgnore()) {
            pstmt.setInt(6, 1);
        } else {
            pstmt.setInt(6, 0);
        }
        pstmt.executeUpdate();
        return getLast();
    }

    public void replaceClient(Client cl) throws Exception {
        sql = "REPLACE INTO client "
            +"(client_id, f_name, l_name, dob, referred_by, last_contact, ignore) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, cl.getClientID());
        pstmt.setString(2, cl.getFName());
        pstmt.setString(3, cl.getLName());
        pstmt.setDate(4, cl.getDob());
        if (cl.getReferredBy() != -1) {
            pstmt.setInt(5, cl.getReferredBy());
        } else {
            pstmt.setNull(5, Types.INTEGER);
        }
        if (cl.getLastContact() != null) {
            pstmt.setDate(6, cl.getLastContact());
        } else {
            pstmt.setDate(6, Date.valueOf(LocalDate.now()));
        }
        if (cl.getIgnore()) {
            pstmt.setInt(7, 1);
        } else {
            pstmt.setInt(7, 0);
        }
        pstmt.executeUpdate();
    }
    
    public Client getClient(int id) throws Exception {
        sql = "SELECT * FROM client WHERE client_id = " + id;
        pstmt = c.prepareStatement(sql);
        rs = pstmt.executeQuery();
        Client nc = new Client(id);
        nc.setFName(rs.getString("f_name"));
        nc.setLName(rs.getString("l_name"));
        nc.setDob(rs.getDate("dob"));
        if (rs.getInt("referred_by") == 0) {
        	nc.setReferredBy(-1);
        } else {
        	nc.setReferredBy(rs.getInt("referred_by"));
        }
        nc.setLastContact(rs.getDate("last_contact"));
        if (rs.getInt("ignore") == 1) {
            nc.setIgnore(true);
        } else {
            nc.setIgnore(false);
        }
        return nc;
    }

    public void contactClient(int id) throws Exception {
        sql = "UPDATE client SET last_contact = ? "
            + "WHERE client_id = ?";
        pstmt = c.prepareStatement(sql);
        pstmt.setDate(1, Date.valueOf(LocalDate.now()));
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }

    public void ignoreClient(int id) throws Exception {
        sql = "UPDATE client SET ignorore = 1 "
            + "WHERE client_id = ?";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public void unignoreClient(int id) throws Exception {
        sql = "UPDATE client SET ignorore = 0 "
            + "WHERE client_id = ?";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public int insertPhysician(Physician p) throws Exception {
        sql = "INSERT INTO physician "
            +"(f_name, l_name, phone) "
            + "VALUES (?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setString(1, p.getFName());
        pstmt.setString(2, p.getLName());
        pstmt.setString(3, p.getPhone());
        pstmt.executeUpdate();
        return getLast();
    }

    public void replacePhysician(Physician p) throws Exception {
        sql = "REPLACE INTO physician "
            +"(physician_id, f_name, l_name, phone) "
            + "VALUES (?, ?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, p.getPhysicianID());
        pstmt.setString(2, p.getFName());
        pstmt.setString(3, p.getLName());
        pstmt.setString(4, p.getPhone());
        pstmt.executeUpdate();
    }

    public Physician getPysician(int id) throws Exception {
        sql = "SELECT * FROM physician WHERE physician_id = " + id;
        pstmt = c.prepareStatement(sql);
        rs = pstmt.executeQuery();
        Physician np = new Physician(id);
        np.setFName(rs.getString("f_name"));
        np.setLName(rs.getString("l_name"));
        np.setPhone(rs.getString("phone"));
        return np;
    }

    public int insertClientInfo(ClientInfo ci) throws Exception {
        sql = "INSERT INTO client_info "
            +"(client_id, date, street, city, state, zip, phone, email, occupation, "
            +"physician_id, accidents_surgeries, allergies, repetitive_risk) "
            +"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, ci.getClientID());
        if (ci.getDate() != null) {
            pstmt.setDate(2, ci.getDate());
        } else {
            pstmt.setDate(2, Date.valueOf(LocalDate.now()));
        }
        pstmt.setString(3, ci.getStreet());
        pstmt.setString(4, ci.getCity());
        pstmt.setString(5, ci.getState());
        pstmt.setString(6, ci.getZip());
        pstmt.setString(7, ci.getPhone());
        pstmt.setString(8, ci.getEmail());
        pstmt.setString(9, ci.getOccupation());
        if (ci.getPhysicianID() != -1) {
        	pstmt.setInt(10, ci.getPhysicianID());
        } else {
        	pstmt.setNull(10, Types.INTEGER);
        }
        pstmt.setString(11, ci.getAcdntSgrs());
        pstmt.setString(12, ci.getAllergies());
        pstmt.setString(13, ci.getRepRisk());
        pstmt.executeUpdate();
        int key = getLast();
        ArrayList<ClientCondition> conds = ci.getConditions();
        for (ClientCondition cond : conds) {
            sql = "INSERT INTO client_condition "
                +"(cinfo_id, condition_id, condition_desc) "
                +"VALUES (?, ?, ?)";
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, key);
            pstmt.setInt(2, cond.getConditionID());
            pstmt.setString(3, cond.getConditionDesc());
            pstmt.executeUpdate();
        }
        return getLast();
    }

    public void replaceClientInfo(ClientInfo ci) throws Exception {
        sql = "INSERT INTO client_info "
            +"(cinfo_id, client_id, date, street, city, state, zip, phone, email, "
            +"occupation, physician_id, accidents_surgeries, allergies, repetitive_risk) "
            +"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, ci.getCInfoID());
        pstmt.setInt(2, ci.getClientID());
        if (ci.getDate() != null) {
            pstmt.setDate(3, ci.getDate());
        } else {
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
        }
        pstmt.setString(4, ci.getStreet());
        pstmt.setString(5, ci.getCity());
        pstmt.setString(6, ci.getState());
        pstmt.setString(7, ci.getZip());
        pstmt.setString(8, ci.getPhone());
        pstmt.setString(9, ci.getEmail());
        pstmt.setString(10, ci.getOccupation());
        if (ci.getPhysicianID() != -1) {
        	pstmt.setInt(11, ci.getPhysicianID());
        } else {
        	pstmt.setNull(11, Types.INTEGER);
        }
        pstmt.setString(12, ci.getAcdntSgrs());
        pstmt.setString(13, ci.getAllergies());
        pstmt.setString(14, ci.getRepRisk());
        pstmt.executeUpdate();
        int key = ci.getCInfoID();
        ArrayList<ClientCondition> conds = ci.getConditions();
        for (ClientCondition cond : conds) {
            sql = "INSERT INTO client_condition "
                +"(cinfo_id, condition_id, condition_desc) "
                +"VALUES (?, ?, ?)";
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, key);
            pstmt.setInt(2, cond.getConditionID());
            pstmt.setString(3, cond.getConditionDesc());
            pstmt.executeUpdate();
        }
    }

    public ClientInfo getClientInfo(int cid) throws Exception {
        ClientInfo cinfo;
        sql = "SELECT cinfo_id, date, street, city, state, zip, phone, email, "
            + "occupation, physician_id, accidents_surgeries, allergies, repetitive_risk "
            + "FROM client_info "
            + "WHERE client_id = " + cid
            + " AND cinfo_id = ("
            	+ "SELECT MAX(cinfo_id) "
            	+ "FROM client_info "
            	+ "WHERE client_id = " + cid
            	+ " AND date = ("
					+ "SELECT MAX(date) "
					+ "FROM client_info "
					+ "WHERE client_id = " + cid
					+ ")"
                + ")";
        rs = stmt.executeQuery(sql);
        int infoID = rs.getInt("cinfo_id");
        cinfo = new ClientInfo(infoID);
        cinfo.setClientID(cid);
        cinfo.setDate(rs.getDate("date"));
        cinfo.setStreet(rs.getString("street"));
        cinfo.setCity(rs.getString("city"));
        cinfo.setState(rs.getString("state"));
        cinfo.setZip(rs.getString("zip"));
        cinfo.setPhone(rs.getString("phone"));
        cinfo.setEmail(rs.getString("email"));
        cinfo.setOccupation(rs.getString("occupation"));
        cinfo.setPhysicianID(rs.getInt("physician_id"));
        cinfo.setAcdntSgrs(rs.getString("accidents_surgeries"));
        cinfo.setAllergies(rs.getString("allergies"));
        cinfo.setRepRisk(rs.getString("repetitive_risk"));
        ArrayList<ClientCondition> conds = new ArrayList<ClientCondition>();
        ClientCondition ncc;
        sql = "SELECT condition_id, condition_desc "
            + "FROM client_condition "
            + "WHERE cinfo_id = " + infoID;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ncc = new ClientCondition();
            ncc.setConditionID(rs.getInt("condition_id"));
            ncc.setConditionDesc(rs.getString("condition_desc"));
            conds.add(ncc);
        }
        cinfo.setConditions(conds);
        return cinfo;
    }

    public int insertSession(Session s) throws Exception {
        sql = "INSERT INTO session "
            +"(client_id, date, time, complaint, treatment, notes, minutes, paid) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, s.getClientID());
        if (s.getDate() != null) {
            pstmt.setDate(2, s.getDate());
        } else {
            pstmt.setDate(2, Date.valueOf(LocalDate.now()));
        }
        pstmt.setString(3, s.getTime());
        pstmt.setString(4, s.getComplaint());
        pstmt.setString(5, s.getTreatment());
        pstmt.setString(6, s.getNotes());
        pstmt.setInt(7, s.getMinutes());
        pstmt.setInt(8, s.getPaid());
        pstmt.executeUpdate();
        return getLast();
    }

    public void replaceSession(Session s) throws Exception {
        sql = "REPLACE INTO session "
            +"(session_id, client_id, date, time, "
            + "complaint, treatment, notes, minutes, paid) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, s.getSessionID());
        pstmt.setInt(2, s.getClientID());
        if (s.getDate() != null) {
            pstmt.setDate(3, s.getDate());
        } else {
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
        }
        pstmt.setString(4, s.getTime());
        pstmt.setString(5, s.getComplaint());
        pstmt.setString(6, s.getTreatment());
        pstmt.setString(7, s.getNotes());
        pstmt.setInt(8, s.getMinutes());
        pstmt.setInt(9, s.getPaid());
        pstmt.executeUpdate();
    }

    public LinkedList<Session> getSessions(int cid) throws Exception {
        LinkedList<Session> list = new LinkedList<Session>();
        Session ns;
        sql = "SELECT date, time, complaint, treatment, notes, minutes, paid "
            + "FROM session "
            + "WHERE client_id = " + cid
            + " ORDER BY date";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ns = new Session(cid);
            ns.setDate(rs.getDate("date"));
            ns.setTime(rs.getString("time"));
            ns.setComplaint(rs.getString("complaint"));
            ns.setTreatment(rs.getString("treatment"));
            ns.setNotes(rs.getString("notes"));
            ns.setMinutes(rs.getInt("minutes"));
            ns.setPaid(rs.getInt("paid"));
            list.offerFirst(ns);
        }
        return list;
    }

    public Session getLastSession(int cid) throws Exception {
        Session ns;
        sql = "SELECT date, time, complaint, treatment, notes, minutes, paid "
            + "FROM session "
            + "WHERE client_id = " + cid
            + " AND date = ("
                        + "SELECT MAX(date) "
                        + "FROM session "
                        + "WHERE client_id = " + cid
                        + ") ";
        rs = stmt.executeQuery(sql);
        ns = new Session(cid);
        ns.setDate(rs.getDate("date"));
        ns.setTime(rs.getString("time"));
        ns.setComplaint(rs.getString("complaint"));
        ns.setTreatment(rs.getString("treatment"));
        ns.setNotes(rs.getString("notes"));
        ns.setMinutes(rs.getInt("minutes"));
        ns.setPaid(rs.getInt("paid"));
        return ns;
    }

    public String[] getConditionTable() throws Exception {
    	sql = "SELECT MAX(condition_id) FROM condition";
    	rs = stmt.executeQuery(sql);
    	String[] table = new String[rs.getInt(1) + 1];
    	sql = "SELECT condition_id, condition FROM condition";
    	rs = stmt.executeQuery(sql);
    	while (rs.next()) {
    		table[rs.getInt("condition_id")] = rs.getString("condition");
    	}
    	return table;
    }
    
    public void insertCondition(String c) throws Exception {
    	sql = "INSERT INTO condition VALUES " + c;
    }

    public int getLast() throws Exception {
        sql = "SELECT last_insert_rowid()";
        rs = stmt.executeQuery(sql);
        return rs.getInt(1);
    }

    public void close() throws Exception {
        pstmt.close();
        stmt.close();
        c.close();
    }

}


