package stdLib_presentation;

import app.IGameMaster;

public interface IPresentation {
		
	public static IPresentation create(IGameMaster gameMaster) {
		
		return new Screen(gameMaster);
	}
	
	public abstract void drawStartScreen();
	
	public abstract void drawBoard(int size);
	
	public abstract void end();
	
	public abstract void writeMessage(byte colour, String message);
	
	public abstract void paintStone(byte player, int col, int row);
	
	public abstract void drawSettingsScreen();
	
	public abstract int playersMove();
	
	public abstract void markWinner(int col, int row);
	
}	