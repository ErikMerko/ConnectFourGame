package stdLib_presentation;

import edu.princeton.cs.introcs.StdDraw;

class Button {

	private String title;
	private double x;
	private double y;
	private double width;
	private double height;
	private Runnable action;
	
	
	Button(String title, double x, double y, Runnable action) {

		this.title = title;
		this.x = x;
		this.y = y;
		this.action = action;
		this.width = 1.0;
		this.height = 0.3;
	}


	void setWidth(double width) {
		this.width = width;
	}


	void setHeight(double height) {
		this.height = height;
	}
	
	double getX() {
		return x;
	}


	double getY() {
		return y;
	}

	void setY(double y) {
		this.y = y;
	}

	double getWidth() {
		return width;
	}


	double getHeight() {
		return height;
	}

	Runnable getAction() {
		return action;
	}
	
	void draw() {
		
		StdDraw.rectangle(x, y, width, height);
		StdDraw.text(x, y, title);
	}
	
	boolean isPressed() {
		
		return StdDraw.mouseX() >= x - width && StdDraw.mouseX() <= x + width && StdDraw.mouseY() >= y - height && StdDraw.mouseY() <= y + height;
	}

	void exe() {
		
		action.run();
	}
	
}
