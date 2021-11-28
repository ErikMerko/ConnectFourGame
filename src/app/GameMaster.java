package app;

import java.util.Random;

import stdLib_presentation.IPresentation;

public class GameMaster implements IGameMaster{
			
	private IPresentation presentation;
	private byte currentPlayer;
	private Random random;
	private Audio audio;
	
	public GameMaster() {
		
		random = new Random();
		audio = new Audio();
		presentation = IPresentation.create(this);
		presentation.drawStartScreen();
	}
		
	@Override
	public void startSingleplayer() {
		
		audio.sound(Audio.PRESSED);
		int move = 0;
		presentation.drawBoard(Property.getSize());
		if (random.nextBoolean()) {

			currentPlayer = YELLOW;
			presentation.writeMessage(YELLOW, "You begin!");

		} else {
			currentPlayer = RED;
			presentation.writeMessage(RED, "AI begins!");
		}
		
		IBoard playingBoard = IBoard.create(presentation);
		AI brain = new AI(playingBoard);
		audio.sound(Audio.MATCH);

		while (!playingBoard.checkWinner() && playingBoard.getMoveCount() < Property.getColumnCount() * Property.getRowCount()) {

			if (currentPlayer == YELLOW) {

				move = presentation.playersMove();

			} else {

				move = brain.think();
			}

			if (playingBoard.canPlay(move)) {
				
				presentation.paintStone(currentPlayer, move, playingBoard.playColumn(move));

				if (currentPlayer == YELLOW) {
					audio.sound(Audio.PLAYER);
				} else {
					audio.sound(Audio.AI);
				}

				currentPlayer = IGameMaster.opposeOf(currentPlayer);
			}
		}
		
		if (playingBoard.getMoveCount() == Property.getColumnCount() * Property.getRowCount() && !playingBoard.checkWinner()) {
			
			presentation.writeMessage(BLANK, "Draw!");
			audio.sound(Audio.DRAW);
		} else {
			
			switch (IGameMaster.opposeOf(currentPlayer)) {
			
			case YELLOW:
				presentation.writeMessage(YELLOW, "You won!");
				audio.sound(Audio.VICTORY);
				break;

			case RED:
				presentation.writeMessage(RED, "You lost!");
				audio.sound(Audio.FAILURE);
				break;

			}
		}
		presentation.end();
				
	}

	@Override
	public void goToSettings() {

		audio.sound(Audio.PRESSED);
		presentation.drawSettingsScreen();
	}

	@Override
	public void showStats() {
		
		presentation.drawStartScreen();
	}
	
	@Override
	public void goToStartscreen() {
		
		audio.sound(Audio.PRESSED);
		presentation.drawStartScreen();
	}
	
	@Override
	public void set(int setting) {
		
		audio.sound(Audio.PRESSED);
		
		switch (setting) {
		
		case STANDARD:
			
			Property.setBoardSize(STANDARD);
			break;
			
		case EXTENDED:
			Property.setBoardSize(EXTENDED);
			break;
		}
		
		
	}
	
}
