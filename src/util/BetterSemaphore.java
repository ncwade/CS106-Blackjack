package util;

import java.util.concurrent.Semaphore;

public class BetterSemaphore extends Semaphore {
	private static final long serialVersionUID = 1998468105268891787L;

	public BetterSemaphore(int permits) {
		super(permits);
	}
	
	public void acquire() {
		try {
			super.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
