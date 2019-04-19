package driver;

import api.ChartingInterface;
import database.DBManager;
import model.ClientCard;

public class TestDriver2 {

	public static void main(String[] args) {
        try {
			DBManager db = new DBManager();
			ClientCard[] cards = db.getCards();
			System.out.println(cards[0].toString());
			
			ChartingInterface infc = new ChartingInterface();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
