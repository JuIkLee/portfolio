package application;

public class CardGameThread extends Thread {

	RootController m_controller;
	public CardGameThread(RootController controller) {
		super();
		m_controller = controller;
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_controller.checkMatch();
		}
	}
}
