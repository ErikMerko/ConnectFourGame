package app;

public interface IGameMaster {
	
	public static final int STANDARD = 42;
	public static final int EXTENDED = 90;
	
	public static final int ENGLISH = 50;
	public static final int GERMAN = 51;
	
	public static final byte BLANK = 0;
	public static final byte YELLOW = 1;
	public static final byte RED = 2;
	
	public static byte opposeOf(byte player) {
		
		switch (player) {
		case YELLOW: return RED;
		case RED: return YELLOW;
		default: return BLANK;
		}
	}
	
	public static int getColumnCount() {
		
		return Property.getColumnCount();
	}
	
	public static int getRowCount() {
		
		return Property.getRowCount();
	}
	
	public abstract void startSingleplayer();
	
	public abstract void goToStartscreen();
	
	public abstract void set(int setting);
	
	public abstract void goToSettings();
	
	public abstract void showStats();
	
}
