package API;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import Database.DBManager;
import Model.Client;
import Model.ClientCard;
import Model.ClientInfo;
import Model.Session;

public class ChartingInterface {
	private DBManager db;
	// card array
	private ClientCard[] cardArray;
	private int currentCard;
	// call queue
	private Queue<ClientCard> callQueue;
	private ClientCard topCard;
	// client view
	private Client currentClient;
	private ClientInfo currentClientInfo;
	// session
	private LinkedList<Session> clientSessions;
	private ListIterator<Session> sessionIterator;

	public ChartingInterface() throws Exception {
		db = new DBManager();
		refreshArray();
		callQueue = new LinkedList<ClientCard>();
		topCard = cardArray[0];
		currentClient = db.getClient(cardArray[0].getClientID());
		currentClientInfo = db.getClientInfo(cardArray[0].getClientID());
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

	public int getCurrentClientID() {
		return cardArray[currentCard].getClientID();
	}
	
	public String getCurrentFName() {
		return cardArray[currentCard].getFName();
	}
	
	public String getCurrentLName() {
		return cardArray[currentCard].getLName();
	}
	
	public String getCurrentPhone() {
		return cardArray[currentCard].getPhone();
	}
	
	public String getCurrentEmail() {
		return cardArray[currentCard].getEmail();
	}
	
	public Date getCurrentLastSession() {
		return cardArray[currentCard].getLastSession();
	}
	
	public Date getCurrentLastContact() {
		return cardArray[currentCard].getLastContact();
	}
	
	public Date getCurrentDob() {
		return cardArray[currentCard].getDob();
	}
	
	public int getCurrentSessions() {
		return cardArray[currentCard].getSessions();
	}
	
	public int getCurrentPaid() {
		return cardArray[currentCard].getPaid();
	}
	
	public boolean getCurrentIgnore() {
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
	

}
