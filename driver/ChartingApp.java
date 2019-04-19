package driver;

import view.ChartingFrame;

public class ChartingApp {

	public static void main(String[] args) {
		try {
			ChartingFrame frame = new ChartingFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
