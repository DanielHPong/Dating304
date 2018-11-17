package pkg304data;

public class Match {
	
	private int c1Id;
	private int c2Id;
	private boolean c1Active;
	private boolean c2Active;
	public Match(int c1Id, int c2Id, boolean c1Active, boolean c2Active) {
		this.c1Id = c1Id;
		this.c2Id = c2Id;
		this.c1Active = c1Active;
		this.c2Active = c2Active;
	}
	public int getC1Id() {
		return c1Id;
	}
	public int getC2Id() {
		return c2Id;
	}
	public boolean isC1Active() {
		return c1Active;
	}
	public boolean isC2Active() {
		return c2Active;
	}
	
}
