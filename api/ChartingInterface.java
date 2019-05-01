package api;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import database.DBManager;
import model.Client;
import model.ClientCard;
import model.ClientCondition;
import model.ClientInfo;
import model.Session;

/**
 * @author Michael Day
 *
 */
public class ChartingInterface {
	private DBManager db;
	private ClientCard[] cardArray;
	private Queue<ClientCard> searchQueue;
	private ClientCard currentCard;
	private int currentCardIndex;
	private PriorityQueue<ClientCard> callQueue;
	private ClientCard topCard;
	private Client currentClient;
	private ClientInfo currentClientInfo;
	private LinkedList<Session> clientSessions;
	private ListIterator<Session> sessionIterator;
	private Session currentSession;

	/**
	 * Instantiates charting interface
	 * Database must first be created using MakeDB
	 * @throws Exception
	 */
	public ChartingInterface() throws Exception {
		db = new DBManager();
		System.out.println("Database manager loaded successfully");
		refreshArray();
		searchQueue = new LinkedList<ClientCard>();
		getNewQueue();
		getClient(1);
		clientSessions = db.getSessions(cardArray[0].getClientID());
		sessionIterator = clientSessions.listIterator();
	}

	////////////////// card array //////////////////
	/**
	 * Queries database to generate new client array
	 * @throws Exception
	 */
	public void refreshArray() throws Exception {
		cardArray = db.getCards();
		currentCardIndex = 0;
		currentCard = cardArray[0];
	}

	/**
	 * Searches clients for matching first name, last name, or phone number
	 * @param string to be matched
	 */
	public void searchCards(String s) {
		s = s.toLowerCase();
		for (ClientCard card : cardArray) {
			if (card.getFName().toLowerCase().equals(s) || card.getLName().toLowerCase().equals(s)
					|| card.getPhone().equals(s)) {
				searchQueue.add(card);
			}
		}
		if (!searchQueue.isEmpty()) {
			currentCard = searchQueue.remove();
		}
	}

	/**
	 * Searhes client array for matching client ID
	 * @param id to be matched
	 */
	public void jumpToID(int id) {
		for (ClientCard card : cardArray) {
			if (id == card.getClientID()) {
				currentCard = card;
				break;
			}
		}
	}

	/**
	 * Moves card view to next card in array 
	 */
	public void nextCard() {
		if (!searchQueue.isEmpty()) {
			currentCard = searchQueue.remove();
		} else if (currentCardIndex < cardArray.length - 1) {
			currentCardIndex++;
			currentCard = cardArray[currentCardIndex];
		}
	}

	/**
	 * Moves card view to previous card in array 
	 */
	public void previousCard() {
		if (currentCardIndex > 0) {
			currentCardIndex--;
			currentCard = cardArray[currentCardIndex];
		}
	}

	/**
	 * Moves card view to first card in array
	 */
	public void firstCard() {
		currentCardIndex = 0;
		currentCard = cardArray[0];
	}

	/**
	 * Moves card view to last card in array 
	 */
	public void lastCard() {
		currentCardIndex = cardArray.length - 1;
		currentCard = cardArray[currentCardIndex];
	}

	/**
	 * Sorts card view by last session starting with most recent
	 */
	public void sortByLastSession() {
		Arrays.sort(cardArray, ClientCard.LastSessionComparator);
		currentCardIndex = 0;
		currentCard = cardArray[0];
	}

	/**
	 * Sorts card view by last contact starting with most recent 
	 */
	public void sortByLastContact() {
		Arrays.sort(cardArray, ClientCard.LastContactComparator);
		currentCardIndex = 0;
		currentCard = cardArray[0];
	}

	/**
	 * Sorts card view by total sessions in descending order 
	 */
	public void sortByTotalSession() {
		Arrays.sort(cardArray, ClientCard.TotalSessionsComparator);
		System.out.println("sorting");
		currentCardIndex = 0;
		currentCard = cardArray[0];
	}

	/**
	 * Sorts card view by total paid in descending order 
	 */
	public void sortByTotalPaid() {
		Arrays.sort(cardArray, ClientCard.TotalPaidComparator);
		currentCardIndex = 0;
		currentCard = cardArray[0];
	}

	/**
	 * @return client id of current client in card view
	 */
	public int getCardClientID() {
		return currentCard.getClientID();
	}

	/**
	 * @return first name of current client in card view
	 */
	public String getCardFName() {
		return currentCard.getFName();
	}

	/**
	 * @return last name of current client in card view
	 */
	public String getCardLName() {
		return currentCard.getLName();
	}

	/**
	 * @return phone number of current client in card view
	 */
	public String getCardPhone() {
		return currentCard.getPhone();
	}

	/**
	 * @return email of current client in card view
	 */
	public String getCardEmail() {
		return currentCard.getEmail();
	}

	/**
	 * @return date of last session of current client in card view
	 */
	public Date getCardLastSession() {
		return currentCard.getLastSession();
	}

	/**
	 * @return date of last contact of current client in card view
	 */
	public Date getCardLastContact() {
		return currentCard.getLastContact();
	}

	/**
	 * @return date of birth of current client in card view
	 */
	public Date getCardDob() {
		if (currentCard.getDob() != null) {
			return currentCard.getDob();
		} else {
			return Date.valueOf(LocalDate.now());
		}
	}

	/**
	 * @return total sessions of current client in card view
	 */
	public int getCardSessions() {
		return currentCard.getSessions();
	}

	/**
	 * @return total paid of current client in card view
	 */
	public int getCardPaid() {
		return currentCard.getPaid();
	}

	/**
	 * @return ignore status of current client in card view
	 */
	public boolean getCardIgnore() {
		return currentCard.getIgnore();
	}

	////////////////// call queue //////////////////
	/**
	 * Generates a new call queue 
	 */
	public void getNewQueue() throws Exception {
		refreshArray();
		callQueue = new PriorityQueue<ClientCard>();
		ZoneId z = ZoneId.of("America/Chicago");
		LocalDate todayCentral = LocalDate.now(z);
		for (int i = cardArray.length - 1; i >= 0; i--) {
			if (!cardArray[i].getIgnore()) {
				if (cardArray[i].getLastContact() != null && cardArray[i].getLastSession() != null) {
					if (cardArray[i].getLastContact().compareTo(cardArray[i].getLastSession()) <= 0
						&& cardArray[i].getLastSession().compareTo(Date.valueOf(todayCentral.minusDays(3))) < 0) {
						callQueue.add(cardArray[i]);
					}
				}
			}
		}
		topCard = callQueue.poll();
	}

	/**
	 * Skips current client in call view
	 */
	public void skipTop() {
		topCard = callQueue.poll();
	}

	/**
	 * Mark current client in call view as contacted
	 * @throws Exception
	 */
	public void contactTop() throws Exception {
		db.contactClient(topCard.getClientID());
		topCard = callQueue.poll();
	}

	/**
	 * Skips current client in call view and sets ignore
	 * @throws Exception
	 */
	public void ignoreTop() throws Exception {
		db.ignoreClient(topCard.getClientID());
		topCard = callQueue.poll();
	}

	/**
	 * @return client ID of current client in call view
	 */
	public int getTopClientID() {
		if (topCard != null) {
			return topCard.getClientID();
		} else {
			return 0;
		}
	}

	/**
	 * @return first name of current client in call view
	 */
	public String getTopFName() {
		if (topCard != null) {
			return topCard.getFName();
		} else {
			return "";
		}

	}

	/**
	 * @return last name of current client in call view
	 */
	public String getTopLName() {
		if (topCard != null) {
			return topCard.getLName();
		} else {
			return "";
		}

	}

	/**
	 * @return phone number of current client in call view
	 */
	public String getTopPhone() {
		if (topCard != null) {
			return topCard.getPhone();
		} else {
			return "";
		}

	}

	/**
	 * @return email of current client in call view
	 */
	public String getTopEmail() {
		if (topCard != null) {
			return topCard.getEmail();
		} else {
			return "";
		}

	}

	/**
	 * @return date of last session of current client in call view
	 */
	public Date getTopLastSession() {
		if (topCard != null) {
			return topCard.getLastSession();
		} else {
			return Date.valueOf(LocalDate.now());
		}

	}

	/**
	 * @return date of last contact of current client in call view
	 */
	public Date getTopLastContact() {
		if (topCard != null) {
			return topCard.getLastContact();
		} else {
			return Date.valueOf(LocalDate.now());
		}

	}

	/**
	 * @return date of birth of current client in call view
	 */
	public Date getTopDob() {
		if (topCard != null) {
			if (topCard.getDob() != null) {
				return topCard.getDob();
			} else {
				return Date.valueOf(LocalDate.now());
			}
		} else {
			return Date.valueOf(LocalDate.now());
		}

	}

	/**
	 * @return total sessions of current client in call view
	 */
	public int getTopSessions() {
		if (topCard != null) {
			return topCard.getSessions();
		} else {
			return 0;
		}

	}

	/**
	 * @return total paid of current client in call view
	 */
	public int getTopPaid() {
		if (topCard != null) {
			return topCard.getPaid();
		} else {
			return 0;
		}

	}

	////////////////// client info //////////////////
	/**
	 * Generates client view
	 * @param client ID to be viewed
	 * @throws Exception
	 */
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

	/**
	 * Generated client view for current client in card view
	 * @throws Exception
	 */
	public void getClientC() throws Exception {
		currentClient = db.getClient(currentCard.getClientID());
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

	/**
	 * Generates client view for current client in call view
	 * @throws Exception
	 */
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

	/**
	 * Generates new client view to be added to database 
	 */
	public void newClient() {
		currentClient = new Client();
		currentClientInfo = new ClientInfo();
		clientSessions = new LinkedList<Session>();
		currentSession = new Session();
		clientSessions.add(currentSession);
		sessionIterator = clientSessions.listIterator();
	}

	/**
	 * Submits current client view to database
	 * Updates client fields and creates new client_info record
	 * @throws Exception
	 */
	public void submitClient() throws Exception {
		if (currentClient.getClientID() == -1) {
			int newID = db.insertClient(currentClient);
			currentClient.setClientID(newID);
			currentClientInfo.setClientID(newID);
			newID = db.insertClientInfo(currentClientInfo);
			currentClientInfo.setCInfoID(newID);
		} else {
			db.replaceClient(currentClient);
			int newID = db.insertClientInfo(currentClientInfo);
			currentClientInfo.setCInfoID(newID);
		}
	}

	/**
	 * @return client ID of current client in client view
	 */
	public int getCliClientID() {
		return currentClient.getClientID();
	}

	/**
	 * @return first name of current client in client view
	 */
	public String getCliFName() {
		return currentClient.getFName();
	}

	/**
	 * @param first name for current client in client view
	 */
	public void setCliFName(String fn) {
		currentClient.setFName(fn);
	}

	/**
	 * @return last name for current client in client view
	 */
	public String getCliLName() {
		return currentClient.getLName();
	}

	/**
	 * @param last name for current client in client view
	 */
	public void setCliLName(String ln) {
		currentClient.setLName(ln);
	}

	/**
	 * @param date of birth for current client in client view
	 */
	public void setCliDob(Date dob) {
		currentClient.setDob(dob);
	}

	/**
	 * @return date of birth for current client in client view
	 */
	public Date getCliDob() {
		return currentClient.getDob();
	}

	/**
	 * @return client ID for referring client of current client in client view
	 */
	public int getCliReferredBy() {
		return currentClient.getReferredBy();
	}

	/**
	 * @param client ID for referring client of current client in client view
	 */
	public void setCliReferredBy(int rb) {
		currentClient.setReferredBy(rb);
	}

	/**
	 * @return date of last contact for current client in client view
	 */
	public Date getCliLastContact() {
		return currentClient.getLastContact();
	}

	/**
	 * @param date of last contact for current client in client view
	 */
	public void setCliLastContact(Date lc) {
		currentClient.setLastContact(lc);
	}

	/**
	 * @return ignore status for current client in client view
	 */
	public boolean getCliIgnore() {
		return currentClient.getIgnore();
	}

	/**
	 * @param ignore status for current client in client view
	 */
	public void setCliIgnore(boolean i) {
		currentClient.setIgnore(i);
	}

	/**
	 * @return date of most recent client information form for current client in client view
	 */
	public Date getCliDate() {
		return currentClientInfo.getDate();
	}

	/**
	 * @return street address for current client in client view
	 */
	public String getCliStreet() {
		return currentClientInfo.getStreet();
	}

	/**
	 * @param street address for current client in client view
	 */
	public void setCliStreet(String st) {
		currentClientInfo.setStreet(st);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return city for current client in client view
	 */
	public String getCliCity() {
		return currentClientInfo.getCity();
	}

	/**
	 * @param city for current client in client view
	 */
	public void setCliCity(String c) {
		currentClientInfo.setCity(c);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return state for current client in client view
	 */
	public String getCliState() {
		return currentClientInfo.getState();
	}

	/**
	 * @param state for current client in client view
	 */
	public void setCliState(String s) {
		currentClientInfo.setState(s);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return zip code for current client in client view
	 */
	public String getCliZip() {
		return currentClientInfo.getZip();
	}

	/**
	 * @param zip code for current client in client view
	 */
	public void setCliZip(String z) {
		currentClientInfo.setZip(z);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return phone number for current client in client view
	 */
	public String getCliPhone() {
		return currentClientInfo.getPhone();
	}

	/**
	 * @param phone number for current client in client view
	 */
	public void setCliPhone(String p) {
		currentClientInfo.setPhone(p);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return email for current client in client view
	 */
	public String getCliEmail() {
		return currentClientInfo.getEmail();
	}

	/**
	 * @param email for current client in client view
	 */
	public void setCliEmail(String e) {
		currentClientInfo.setEmail(e);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return occupation for current client in client view
	 */
	public String getCliOccupation() {
		return currentClientInfo.getOccupation();
	}

	/**
	 * @param occupaiton for current client in client view
	 */
	public void setCliOccupation(String o) {
		currentClientInfo.setOccupation(o);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return physician ID for current client in client view
	 */
	public int getCliPhysicianID() {
		return currentClientInfo.getPhysicianID();
	}

	/**
	 * @param physician ID for current client in client view
	 */
	public void setCliPhysicianID(int pid) {
		currentClientInfo.setPhysicianID(pid);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return accidents and surgeries for current client in client view
	 */
	public String getCliAcdntSgrs() {
		return currentClientInfo.getAcdntSgrs();
	}

	/**
	 * @param accidents and surgeries for current client in client view
	 */
	public void setCliAcdntSgrs(String as) {
		currentClientInfo.setAcdntSgrs(as);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return allergies for current client in client view
	 */
	public String getCliAllergies() {
		return currentClientInfo.getAllergies();
	}

	/**
	 * @param allergies for current client in client view
	 */
	public void setCliAllergies(String a) {
		currentClientInfo.setAllergies(a);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return repetitive injury risk for current client in client view
	 */
	public String getCliRepRisk() {
		return currentClientInfo.getRepRisk();
	}

	/**
	 * @param repetitive injury risk for current client in client view
	 */
	public void setCliRepRisk(String rr) {
		currentClientInfo.setRepRisk(rr);
		if (currentClientInfo.getCInfoID() != -1) {
			currentClientInfo.setCInfoID(0);
		}
	}

	/**
	 * @return string/string map of conditions and notes for current client in client view
	 * @throws Exception
	 */
	public Map<String, String> getCliConds() throws Exception {
		Map<String, String> condMap = new HashMap<String, String>();
		ArrayList<ClientCondition> condArray = currentClientInfo.getConditions();
		String[] table = db.getConditionTable();
		for (ClientCondition cond : condArray) {
			condMap.put(table[cond.getConditionID()], cond.getConditionDesc());
		}
		return condMap;
	}

	/**
	 * @param string/string map of conditions and notes for current client in client view
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public void setConds(Map<String, String> cm) throws Exception, NullPointerException {
		ArrayList<ClientCondition> ccArr = new ArrayList<ClientCondition>();
		String[] table = db.getConditionTable();
		int cID;
		for (Map.Entry<String, String> entry : cm.entrySet()) {
			cID = 0;
			String cName = entry.getKey();
			for (int i = 1; i < table.length; i++) {
				if (cName.toLowerCase().equals(table[i].toLowerCase())) {
					cID = i;
					break;
				}
			}
			if (cID == 0) {
				throw new NullPointerException("Condition not in list");
			}
			ccArr.add(new ClientCondition(cID, entry.getValue()));
		}
		currentClientInfo.setConditions(ccArr);
		if (currentClientInfo.getCInfoID() != 0) {
			currentClientInfo.setCInfoID(0);
		}
	}
	
	/**
	 * @param condition to be added to current client in client view
	 * @param description of condition for current client in client view
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public void addCondition(String c, String d) throws Exception, NullPointerException {
		String[] table = db.getConditionTable();
		int cID = 0;
		for (int i = 1; i < table.length; i++) {
			if (c.toLowerCase().equals(table[i].toLowerCase())) {
				cID = i;
				break;
			}
		}
		if (cID == 0) {
			throw new NullPointerException("Condition not in list");
		}
		currentClientInfo.addCondition(cID, d);
	}
	
	/**
	 * @param condition to be removed from current client in client view
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public void removeCondition(String c) throws Exception, NullPointerException {
		String[] table = db.getConditionTable();
		int cID = 0;
		for (int i = 1; i < table.length; i++) {
			if (c.toLowerCase().equals(table[i].toLowerCase())) {
				cID = i;
				break;
			}
		}
		if (cID == 0) {
			throw new NullPointerException("Condition not in list");
		}
		ClientCondition thisCondition = new ClientCondition(cID);
		ArrayList<ClientCondition> currentConditions = currentClientInfo.getConditions();
		currentConditions.remove(thisCondition);
	}
	
	/**
	 * @return string array of currently tracked conditions indexed by condition_id
	 * @throws Exception
	 */
	public String[] getConditionTable() throws Exception {
		return db.getConditionTable();
	}
	
	/**
	 * @param condition to be added to list of tracked conditions
	 * @throws Exception
	 */
	public void addTrackedCondition(String c) throws Exception {
		db.insertCondition(c);
	}

	////////////////// session //////////////////
	/**
	 * Generates blank session to be added to session list
	 */
	public void newSession() {
		currentSession = new Session();
		currentSession.setDate(Date.valueOf(LocalDate.now()));
		currentSession.setClientID(currentClient.getClientID());
		clientSessions.offerFirst(currentSession);
		sessionIterator = clientSessions.listIterator();
	}

	/**
	 * Submits current session to be added to or updated in database
	 * @throws Exception
	 */
	public void submitSession() throws Exception {
		if (currentSession.getSessionID() == -1) {
			int newID = db.insertSession(currentSession);
			currentSession.setSessionID(newID);
			sessionIterator = clientSessions.listIterator();
			currentSession = sessionIterator.next();
		} else {
			db.replaceSession(currentSession);
		}
	}

	/**
	 * Moves view to previous session
	 */
	public void previousSession() {
		if (sessionIterator.hasNext()) {
			currentSession = sessionIterator.next();
		}
	}

	/**
	 * Moves view to next most recent session
	 */
	public void nextSession() {
		if (sessionIterator.hasPrevious()) {
			currentSession = sessionIterator.previous();
		}
	}

	/**
	 * Moves view to most recent session
	 */
	public void lastSession() {
		sessionIterator = clientSessions.listIterator();
		if (sessionIterator.hasNext()) {
			currentSession = sessionIterator.next();
		}
	}

	/**
	 * Moves view to first session
	 */
	public void firstSession() {
		sessionIterator = clientSessions.listIterator(clientSessions.size() -1);
		if (sessionIterator.hasNext()) {
			currentSession = sessionIterator.next();
		}
	}

	/**
	 * @return date of current session in view
	 */
	public Date getSesDate() {
		return currentSession.getDate();
	}

	/**
	 * @param date of current session in view
	 */
	public void setSesDate(Date d) {
		currentSession.setDate(d);
	}

	/**
	 * @return time of current session in view
	 */
	public String getSesTime() {
		return currentSession.getTime();
	}

	/**
	 * @param time of current session in view
	 */
	public void setSesTime(String t) {
		currentSession.setTime(t);
	}

	/**
	 * @return client complaint/ailment for current session in view
	 */
	public String getSesComplaint() {
		return currentSession.getComplaint();
	}

	/**
	 * @param client complaint/ailment for current session in view
	 */
	public void setSesComplaint(String c) {
		currentSession.setComplaint(c);
	}

	/**
	 * @return treatments used for current session in view
	 */
	public String getSesTreatment() {
		return currentSession.getTreatment();
	}

	/**
	 * @param treatments used for current session in view
	 */
	public void setSesTreatment(String t) {
		currentSession.setTreatment(t);
	}

	/**
	 * @return other notes for current session in view
	 */
	public String getSesNotes() {
		return currentSession.getNotes();
	}

	/**
	 * @param other notes for current session in view
	 */
	public void setSesNotes(String n) {
		currentSession.setNotes(n);
	}

	/**
	 * @return minutes for current session in view
	 */
	public int getSesMinutes() {
		return currentSession.getMinutes();
	}

	/**
	 * @param minutes for current session in view
	 */
	public void setSesMinutes(int m) {
		currentSession.setMinutes(m);
	}

	/**
	 * @return amount paid for current session in view
	 */
	public int getSesPaid() {
		return currentSession.getPaid();
	}

	/**
	 * @param amoutn paid for current session in view
	 */
	public void setSesPaid(int p) {
		currentSession.setPaid(p);
	}

	/**
	 * Closes all connections
	 * @throws Exception
	 */
	public void close() throws Exception {
		db.close();
	}

}
