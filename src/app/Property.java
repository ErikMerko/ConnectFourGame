package app;


class Property{
	
	private static int currentSize = IGameMaster.STANDARD;
	private static int columns = 7;
	private static int rows = 6;
	
			
	static void setBoardSize(int size) {
		
		switch (size) {
		
		case IGameMaster.STANDARD:
			currentSize = IGameMaster.STANDARD;
			columns = 7;
			rows = 6;
			break;
		
		case IGameMaster.EXTENDED:
			currentSize = IGameMaster.EXTENDED;
			columns = 10;
			rows = 9;
			break;
			
		default: return;
		}
		
	}
	
	static int getSize() {
		
		return currentSize;
	}
	
	static int getColumnCount() {
		return columns;
	}

	static int getRowCount() {
		return rows;
	}
	
//	static int getBoardCount() {
//		
//		return columns*rows;
//	}
	
}
