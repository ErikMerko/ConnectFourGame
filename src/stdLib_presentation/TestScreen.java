package stdLib_presentation;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import app.IGameMaster;

class TestScreen implements IPresentation{

	private final Font BASIC;
	private final Font BIG;
	private final Font JOKER;
	private final Font SMALL;
	
	private Map<String, Entry<Double, Double>> menuButtons;
	private Map<String, Entry<Double, Double>> afterGameButtons;
	private Map<String, Entry<Double, Double>> settingButtons;
	
	private Map<Byte, List<Entry<Double, Double>>> drawOrders;
	private List<Integer> columns;
	
	private double A = 0.35;
	private double B = 3.05;
	private double C = 0.8;
	private double D = 0.3;
	private double E = 0.0;
	private double F = 4.15;
	private double G = 7.5;
	
	TestScreen(IGameMaster gameMaster) {
		
		this.BASIC = new Font("Comic Sans MS", Font.BOLD, 42);
		this.BIG = new Font("Comic Sans MS", Font.BOLD, 82);
		this.JOKER = new Font("Jokerman", Font.BOLD, 220);
		this.SMALL = new Font("Lucida Sans", Font.BOLD, 15);
		this.menuButtons = new HashMap<>();
		this.afterGameButtons = new HashMap<>();
		this.settingButtons = new HashMap<>();
		this.drawOrders = new HashMap<>();
		this.columns = new ArrayList<>();
	}
	
	@Override
	public void drawStartScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawBoard(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeMessage(byte colour, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintStone(byte player, int col, int row) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSettingsScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int playersMove() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void markWinner(int col, int row) {
		// TODO Auto-generated method stub
		
	}

}
