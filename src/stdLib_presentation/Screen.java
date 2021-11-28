package stdLib_presentation;

import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.primitive.DoubleList;
import org.eclipse.collections.api.map.primitive.DoubleDoubleMap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.tuple.primitive.DoubleDoublePair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import app.IGameMaster;
import edu.princeton.cs.introcs.StdDraw;

class Screen implements IPresentation{
	
	private final Font BASIC;
	private final Font BIG;
	private final Font JOKER;
	private final Font SMALL;
	
	private ImmutableList<Button> menuButtons;
	private ImmutableList<Button> afterGameButtons;
	private ImmutableList<SelectableButton> settingButtons;
	
	private MutableListMultimap<Byte, DoubleDoublePair> drawOrders;
	private IntIterable columns;
	private double[] settings;
	
	private IGameMaster gameMaster;
	
	private static final double A = 0.35;
	private static final double B = 3.05;
	private static final double C = 0.8;
	private static final double D = 0.3;
	private static final double E = 0.0;
	private static final double F = 4.15;
	private static final double G = 7.5;
	
	Screen(IGameMaster gameMaster) {
		
		this.settings = new double[]{A, B, C, D, E, F, G};
		this.gameMaster = gameMaster;
		
//		this.menuButtons = Lists.immutable.of(new Button("Singleplayer", 2.0, 4.0, () -> gameMaster.startSingleplayer()),
//											  new Button("Settings", 8.0, 4.0, () -> gameMaster.goToSettings()),
//											  new Button("Stats", 5.0, 2.0, () -> gameMaster.showStats()));
		
		this.menuButtons = Lists.immutable.of(new Button("Singleplayer", 2.0, 4.0, () -> gameMaster.startSingleplayer()));
		
		this.afterGameButtons = Lists.immutable.of(new Button("Play again", 2.0, settings[6], () -> gameMaster.startSingleplayer()),
												   new Button("Back", 8.0, settings[6], () -> gameMaster.goToStartscreen()));
		
		this.settingButtons = Lists.immutable.of(new SelectableButton("Classic 7x6", 2.0, 6.0, () -> gameMaster.set(IGameMaster.STANDARD)),
												 new SelectableButton("Extended 7x6", 2.0, 5.0, () -> gameMaster.set(IGameMaster.EXTENDED)),
												 new SelectableButton("Back", 2.0, 9.0, () -> gameMaster.goToStartscreen()));
				
		this.drawOrders = Multimaps.mutable.list.empty();
		this.columns = IntInterval.zero();
		
		this.BASIC = new Font("Comic Sans MS", Font.BOLD, 42);
		this.BIG = new Font("Comic Sans MS", Font.BOLD, 82);
		this.JOKER = new Font("Jokerman", Font.BOLD, 220);
		this.SMALL = new Font("Lucida Sans", Font.BOLD, 15);
		
		StdDraw.setCanvasSize(1600, 1600);
		StdDraw.setXscale(0, 10);
		StdDraw.setYscale(0, 10);
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.enableDoubleBuffering();
	}
	
	@Override
	public void drawStartScreen() {
		
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setFont(JOKER);
		StdDraw.setPenColor(StdDraw.ORANGE);
		StdDraw.text(5, 8.5, "Connect 4");
		StdDraw.setFont(BASIC);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(5, 7, "created by Erik Merko");
		StdDraw.text(5, 5.5, "Options:");
		this.menuButtons.forEach(Button::draw);
		StdDraw.setFont(SMALL);
		StdDraw.text(0.5, 0.5, "Version 1.0");
		StdDraw.show();
		listen(this.menuButtons);
	}
	
	private void listen(ListIterable<? extends Button> buttons) {
						
		while (true) {

			if (StdDraw.isMousePressed()) {

				while (StdDraw.isMousePressed());
				
				if (buttons.anySatisfy(Button::isPressed)) {
					buttons.detect(Button::isPressed).exe();
					return;
				}
			}

		}
	}
	
	@Override
	public void drawBoard(int size) {
		
		StdDraw.enableDoubleBuffering();
		switch (size) {
		
		case IGameMaster.STANDARD:
			this.settings = new double[]{A, B, C, D, E, F, G};
			break;
		
		case IGameMaster.EXTENDED:
			this.settings = new double[]{A, B+1.3, C-1.3, D-1.3, E, F+0.4, G+1.6};
			break;
		
		default:
			return;
		}
		
		this.afterGameButtons.forEach(b -> b.setY(settings[6]+0.5));
		drawOrders.clear();
		columns = IntInterval.oneTo(IGameMaster.getColumnCount());
		redrawBoard();
	}

	private void redrawBoard() {
		
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setPenColor(StdDraw.WHITE);
		
		int col;
		int row;
		for (col = 1; col <= IGameMaster.getColumnCount(); col++) {
			for (row = 1; row <= IGameMaster.getRowCount(); row++) {
				
				StdDraw.circle(col+settings[2], row+settings[2], settings[0]);
			}
		}
		
		drawOrders.forEachKeyMultiValues((k, v) -> {
			
			switch (k) {
			
			case IGameMaster.YELLOW:
				StdDraw.setPenColor(StdDraw.YELLOW);
				break;
				
			case IGameMaster.RED:
				StdDraw.setPenColor(StdDraw.RED);
				break;
			}
			
			v.forEach(e -> StdDraw.filledCircle(e.getOne()+settings[2], e.getTwo()+settings[2], settings[0]));
		});
		
	}
	
	@Override
	public void paintStone(byte player, int col, int row) {
				
		animateStone(player, col, row);
		drawOrders.put(player, PrimitiveTuples.pair(col+settings[4], row+settings[4]));
	}
		
	private void animateStone(byte player, int col, int row) {
		
		StdDraw.enableDoubleBuffering();
		
		for(double i = IGameMaster.getRowCount()+settings[2]; i > row+settings[3]; i--) {
			
			redrawBoard();
			switch (player) {
			case IGameMaster.YELLOW:
				StdDraw.setPenColor(StdDraw.YELLOW);
				break;
				
			case IGameMaster.RED: 	
				StdDraw.setPenColor(StdDraw.RED);
				break;
				
			default: return;	
			}
			StdDraw.filledCircle(col+settings[2], i, settings[0]);
			if (i < (row+settings[3]+1)) {
								
				drawOrders.get(IGameMaster.BLANK).forEach(e -> {
					StdDraw.setFont(BIG);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(e.getOne()+settings[2], e.getTwo()+settings[2], "X");
				});
			}
			
			StdDraw.pause(10);
			StdDraw.show();
		}

		StdDraw.disableDoubleBuffering();
		
	}

	@Override
	public void writeMessage(byte colour, String message) {
				
		switch (colour) {
		case IGameMaster.YELLOW: 
			StdDraw.setPenColor(StdDraw.YELLOW);
			break;
		
		case IGameMaster.RED:
			StdDraw.setPenColor(StdDraw.RED);
			break;
		
		case IGameMaster.BLANK:
			StdDraw.setPenColor(StdDraw.WHITE);
			break;
		
		default:
			return;
		}
		
		StdDraw.setFont(BASIC);
		StdDraw.text(5, settings[6], message);
		StdDraw.show();
				
	}

	@Override
	public int playersMove() {
				
		while (true) {
			
			if (StdDraw.isMousePressed()) {
				
				while (StdDraw.isMousePressed());
				
				try {
					return columns.select(c -> StdDraw.mouseX() >= (c+settings[2])-settings[0] && StdDraw.mouseX() <= (c+settings[2])+settings[0] && StdDraw.mouseY() >= 4.5-settings[1] && StdDraw.mouseY() <= settings[5]+settings[1]).min();
				} catch (NoSuchElementException e) {
					
				}
			}

		}
	}

	@Override
	public void markWinner(int col, int row) {
		
		drawOrders.put(IGameMaster.BLANK, PrimitiveTuples.pair((double) col, (double) row));
	}

	@Override
	public void end() {
		
		StdDraw.setPenColor(StdDraw.WHITE);
		this.afterGameButtons.forEach(Button::draw);
		listen(this.afterGameButtons);
	}
		
	@Override
	public void drawSettingsScreen() {
		
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setFont(BASIC);
		StdDraw.text(2, 6.75, "Board Size:");
		this.settingButtons.forEach(Button::draw);		
		StdDraw.show();
		listen(this.settingButtons);
		this.drawStartScreen();
//		drawSettingsScreen();
	}

}
