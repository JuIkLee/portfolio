package application;

public class NextThread extends Thread {
	NextController m_controller;
	public NextThread(NextController controller) {
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
