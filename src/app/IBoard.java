package app;

import stdLib_presentation.IPresentation;

interface IBoard {

	public static final int WINCOUNT = 4;
	
	public abstract boolean canPlay (int column);
	
	public abstract int playColumn (int column);
		
	public abstract boolean checkWinner();
	
	public abstract int getMoveCount();
	
	public static IBoard create (IPresentation presentation) {
		
		return new ViewBoard(presentation);
	}

	public abstract IBoard getClone();
	
}
