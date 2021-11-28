package stdLib_presentation;

import edu.princeton.cs.introcs.StdDraw;

class SelectableButton extends Button{

	private boolean selected;
	private boolean showsFrame;
	
	SelectableButton(String title, double x, double y, Runnable action) {
		
		super(title, x, y, action);
		
		this.selected = false;
		this.showsFrame = false;
	}

	boolean isSelected() {
		return selected;
	}

	void setSelected(boolean isSelected) {
		this.selected = isSelected;
	}

	
	@Override
	void draw() {
		
		super.draw();
		
		if (selected) {
			drawFrame();
		}
	}
	
	@Override
	void exe() {
		
		selected = true;
		Runnable superAction = super.getAction();
		
		Thread th = new Thread() {

			public void run() {

				try {
					Thread.sleep(200);
					superAction.run();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
		};
		th.start();

		drawFrame();
	}
	
	void setFrame(boolean showsFrame) {
		
		this.showsFrame = showsFrame;
	}
	
	private void drawFrame() {
		
		if (showsFrame) {
			StdDraw.rectangle(super.getX(), super.getY(), super.getWidth() + 0.1, super.getHeight() + 0.1);
		}
	}
}
