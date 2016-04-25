package players;

public abstract interface Player {

	public abstract String mName();
	public abstract int mCash();
	public abstract int mGetHandValue();
	public abstract int mHit();
	public abstract int mSeat();
	public abstract int mHands(int n);
	public abstract boolean insurance();
	public abstract boolean doubleDown();
	public abstract boolean split();
	
}
