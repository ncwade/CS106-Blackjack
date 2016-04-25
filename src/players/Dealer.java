package players;

public class Dealer implements Player {

	@Override
	public String mName() {
		return "Dealer";
	}
	
	@Override
	public int mCash() {
		// A dealer is not going to be betting
		return 0;
	}
	
	@Override
	public int mGetHandValue() {
		// 
		return 0;
	}
	
	@Override
	public int mHit() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int mSeat() {
		// A dealer is always dealt last
		// Need to identify up to 7 seating locations + dealer
		// Have seat locations be boolean to see if they are occupied?
		return 0;
	}
	
	@Override
	public int mHands(int n) {
		// A dealer can only have 1 hand
		return 1;
	}

	@Override
	public boolean insurance() {
		// A dealer cannot buy insurance
		return false;
	}

	@Override
	public boolean doubleDown() {
		// A dealer cannot double down
		return false;
	}

	@Override
	public boolean split() {
		// A dealer cannot split cards
		return false;
	}
}