package api;

import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;

import database.DBManager;
import model.Client;
import model.ClientCard;
import model.ClientCondition;
import model.ClientInfo;
import model.Session;

public class ChartingInterface {
	private DBManager db;
	private ClientCard[] cardArray;
	private int currentCard;
	private Queue<ClientCard> callQueue;
	private ClientCard topCard;
	private Client currentClient;
	private ClientInfo currentClientInfo;
	private LinkedList<Session> clientSessions;
	private ListIterator<Session> sessionIterator;
	private Session currentSession;


	public ChartingInterface() throws Exception {
		db = new DBManager();
		refreshArray();
		callQueue = new LinkedList<ClientCard>();
		topCard = cardArray[0];
		getClient(1);
		clientSessions = db.getSessions(cardArray[0].getClientID());
		sessionIterator = clientSessions.listIterator();
	}
	
	////////////////// card array //////////////////
	public void refreshArray() throws Exception {
		cardArray = db.getCards();
		currentCard = 0;
	}
	
	public void firstCard() {
		currentCard = 0;
	}
	
	public void lastCard() {
		currentCard = cardArray.length - 1;
	}
	
	public void nextCard() {
		if (currentCard < cardArray.length - 1) {
			currentCard++;
		}
	}
	
	public void previousCard() {
		if (currentCard > 0) {
			currentCard--;
		}
	}
	
	public void sortByLastSession() {
		Arrays.sort(cardArray, ClientCard.LastSessionComparator);
	}

	public void sortByLastContact() {
		Arrays.sort(cardArray, ClientCard.LastContactComparator);
	}

	public void sortByTotalSession() {
		Arrays.sort(cardArray, ClientCard.TotalSessionsComparator);
	}

	public void sortByTotalPaid() {
		Arrays.sort(cardArray, ClientCard.TotalPaidComparator);
	}

	public int getCardClientID() {
		return cardArray[currentCard].getClientID();
	}
	
	public String getCardFName() {
		return cardArray[currentCard].getFName();
	}
	
	public String getCardLName() {
		return cardArray[currentCard].getLName();
	}
	
	public String getCardPhone() {
		return cardArray[currentCard].getPhone();
	}
	
	public String getCardEmail() {
		return cardArray[currentCard].getEmail();
	}
	
	public Date getCardLastSession() {
		return cardArray[currentCard].getLastSession();
	}
	
	public Date getCardLastContact() {
		return cardArray[currentCard].getLastContact();
	}
	
	public Date getCardDob() {
		return cardArray[currentCard].getDob();
	}
	
	public int getCardSessions() {
		return cardArray[currentCard].getSessions();
	}
	
	public int getCardPaid() {
		return cardArray[currentCard].getPaid();
	}
	
	public boolean getCardIgnore() {
		return cardArray[currentCard].getIgnore();
	}
	
	////////////////// call queue //////////////////
	public void getNewQueue() throws Exception {
		callQueue = db.getQueue();
		topCard = callQueue.poll();
	}

	public int getTopClientID() {
		if (topCard != null) {
			return topCard.getClientID();
		} else {
			return 0;
		}
	}
	
	public String getTopFName() {
		if (topCard != null) {
			return topCard.getFName();
		} else {
			return "";
		}

	}
	
	public String getTopLName() {
		if (topCard != null) {
			return topCard.getLName();
		} else {
			return "";
		}

	}
	
	public String getTopPhone() {
		if (topCard != null) {
			return topCard.getPhone();
		} else {
			return "";
		}

	}
	
	public String getTopEmail() {
		if (topCard != null) {
			return topCard.getEmail();
		} else {
			return "";
		}

	}
	
	public Date getTopLastSession() {
		if (topCard != null) {
			return topCard.getLastSession();
		} else {
			return Date.valueOf(LocalDate.now());
		}

	}
	
	public Date getTopLastContact() {
		if (topCard != null) {
			return topCard.getLastSession();
		} else {
			return Date.valueOf(LocalDate.now());
		}

	}
	
	public Date getTopDob() {
		if (topCard != null) {
			return topCard.getDob();
		} else {
			return Date.valueOf(LocalDate.now());
		}

	}
	
	public int getTopSessions() {
		if (topCard != null) {
			return topCard.getSessions();
		} else {
			return 0;
		}

	}
	
	public int getTopPaid() {
		if (topCard != null) {
			return topCard.getPaid();
		} else {
			return 0;
		}

	}
	

	////////////////// client info //////////////////
	public void getClient(int cid) throws Exception {
		currentClient = db.getClient(cid);
		currentClientInfo = db.getClientInfo(cid);
		clientSessions = db.getSessions(cid);
		sessionIterator = clientSessions.listIterator();
		if (sessionIterator.hasNext()) {
			currentSession = sessionIterator.next();
		} else {
			currentSession = new Session();
			clientSessions.offerFirst(currentSession);
		}
	}

	public void getClientC() throws Exception {
		currentClient = db.getClient(cardArray[currentCard].getClientID());
		currentClientInfo = db.getClientInfo(currentClient.getClientID());
		clientSessions = db.getSessions(currentClient.getClientID());
		sessionIterator = clientSessions.listIterator();
		if (sessionIterator.hasNext()) {
			currentSession = sessionIterator.next();
		} else {
			currentSession = new Session();
			clientSessions.offerFirst(currentSession);
		}
	}

	public void getClientQ() throws Exception {
		if (topCard != null) {
			currentClient = db.getClient(topCard.getClientID());
			currentClientInfo = db.getClientInfo(topCard.getClientID());
			clientSessions = db.getSessions(topCard.getClientID());
			sessionIterator = clientSessions.listIterator();
			if (sessionIterator.hasNext()) {
				currentSession = sessionIterator.next();
			} else {
				currentSession = new Session();
				clientSessions.offerFirst(currentSession);
			}
		}
	}

	public void newClient() {
		currentClient = new Client();
		currentClientInfo = new ClientInfo();
		clientSessions = new LinkedList<Session>();
		currentSession = new Session();
		clientSessions.add(currentSession);
		sessionIterator = clientSessions.listIterator();
	}
	
	public void submitClient() throws Exception {
		if (currentClient.getClientID() == -1) {
			int newID = db.insertClient(currentClient);
			currentClient.setClientID(newID);
			currentClientInfo.setClientID(newID);
			newID = db.insertClientInfo(currentClientInfo);
			currentClientInfo.setCInfoID(newID);
		} else {
			db.replaceClient(currentClient);
			if (currentClientInfo.getCInfoID() == -1) {
				int newID = db.insertClientInfo(currentClientInfo);
				currentClientInfo.setCInfoID(newID);
			} else {
				db.insertClientInfo(currentClientInfo);
			}
		}
	}
	
	public int getCliClientID() {
		return currentClient.getClientID();
	}
	
	public String getCliFName() {
		return currentClient.getFName();
	}

	public void setCliFName(String fn) {
		currentClient.setFName(fn);
	}
	
	public String getCliLName() {
		return currentClient.getLName();
	}

	public void setCliLName(String ln) {
		currentClient.setLName(ln);
	}

	public void setCliDob(Date dob) {
		currentClient.setDob(dob);
	}
	
	public Date getCliDob() {
		return currentClient.getDob();
	}
	
	public int getCliReferredBy() {
		return currentClient.getReferredBy();
	}

	public void setCliReferredBy(int rb) {
		currentClient.setReferredBy(rb);
	}
	
	public Date getCliLastContact() {
		return currentClient.getLastContact();
	}

	public void setCliLastContact(Date lc) {
		currentClient.setLastContact(lc);
	}
	
	public boolean getCliIgnore() {
		return currentClient.getIgnore();
	}

	public void setCliIgnore(boolean i) {
		currentClient.setIgnore(i);
	}
	
	public Date getCliDate() {
		return currentClientInfo.getDate();
	}
	
	public String getCliStreet() {
		return currentClientInfo.getStreet();
	}

	public void setCliStreet(String st) {
		currentClientInfo.setStreet(st);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliCity() {
		return currentClientInfo.getCity();
	}

	public void setCliCity(String c) {
		currentClientInfo.setCity(c);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliState() {
		return currentClientInfo.getState();
	}

	public void setCliState(String s) {
		currentClientInfo.setState(s);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliZip() {
		return currentClientInfo.getZip();
	}

	public void setCliZip(String z) {
		currentClientInfo.setZip(z);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliPhone() {
		return currentClientInfo.getPhone();
	}

	public void setCliPhone(String p) {
		currentClientInfo.setPhone(p);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliEmail() {
		return currentClientInfo.getEmail();
	}

	public void setCliEmail(String e) {
		currentClientInfo.setEmail(e);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliOccupation() {
		return currentClientInfo.getOccupation();
	}

	public void setCliOccupation(String o) {
		currentClientInfo.setOccupation(o);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public int getCliPhysicianID() {
		return currentClientInfo.getPhysicianID();
	}

	public void setCliPhysicianID(int pid) {
		currentClientInfo.setPhysicianID(pid);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliAcdntSgrs() {
		return currentClientInfo.getAcdntSgrs();
	}

	public void setCliAcdntSgrs(String as) {
		currentClientInfo.setAcdntSgrs(as);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	public String getCliAllergies() {
		return currentClientInfo.getAllergies();
	}

	public void setCliAllergies(String a) {
		currentClientInfo.setAllergies(a);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public String getCliRepRisk() {
		return currentClientInfo.getRepRisk();
	}

	public void setCliRepRisk(String rr) {
		currentClientInfo.setRepRisk(rr);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	public Map<Integer, String> getCliConds() {
		Map<Integer, String> condMap = new HashMap<Integer, String>();
		ArrayList<ClientCondition> condArray = currentClientInfo.getConditions();
		for (ClientCondition cond : condArray) {
			condMap.put(cond.getConditionID(), cond.getConditionDesc());
		}
		return condMap;
	}

	public void setConds(Map<Integer, String> cm) {
		ArrayList<ClientCondition>  ccArr = new ArrayList<ClientCondition>();
		for (Map.Entry<Integer, String> entry : cm.entrySet()) {
			ccArr.add(new ClientCondition(entry.getKey(), entry.getValue()));
		}
		currentClientInfo.setConditions(ccArr);
		if (currentClientInfo.getCInfoID() != 0) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	////////////////// session //////////////////
	public void newSession() {
		currentSession = new Session();
		clientSessions.offerFirst(currentSession);
		sessionIterator = clientSessions.listIterator();
	}
	
	public void submitSession() throws Exception {
		if (currentSession.getSessionID() == -1) {
			int newID = db.insertSession(currentSession);
			currentSession.setSessionID(newID);
		} else {
			db.replaceSession(currentSession);
		}
	}
	
	public void nextSession() {
		if (sessionIterator.hasNext()) {
			currentSession = sessionIterator.next();
		}
	}
	
	public void previousSession() {
		if (sessionIterator.hasPrevious()) {
			currentSession = sessionIterator.previous();
		}
	}
	
	public void firstSession() {
		currentSession = clientSessions.getFirst();
	}
	
	public void lastSession() {
		currentSession = clientSessions.getLast();
	}
	
	public Date getSesDate() {
		return currentSession.getDate();
	}
	
	public void setSesDate(Date d) {
		currentSession.setDate(d);
	}
	
	public String getSesTime() {
		return currentSession.getTime();
	}
	
	public void setSesTime(String t) {
		currentSession.setTime(t);
	}
	
	public String getSesComplaint() {
		return currentSession.getComplaint();
	}
	
	public void setSesComplaint(String c) {
		currentSession.setComplaint(c);
	}
	
	public String getSesTreatment() {
		return currentSession.getTreatment();
	}
	
	public void setSesTreatment(String t) {
		currentSession.setTreatment(t);
	}
	
	public String getSesNotes() {
		return currentSession.getNotes();
	}
	
	public void setSesNotes(String n) {
		currentSession.setNotes(n);
	}
	
	public int getSesMinutes() {
		return currentSession.getMinutes();
	}
	
	public void setSesMinutes(int m) {
		currentSession.setMinutes(m);
	}
	
	public int getSesPaid() {
		return currentSession.getPaid();
	}
	
	public void setSesPaid(int p) {
		currentSession.setPaid(p);
	}
	
	public void close() throws Exception {
		db.close();
	}
	
}
