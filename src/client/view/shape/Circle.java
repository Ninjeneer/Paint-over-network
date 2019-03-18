package client.view.shape;

import java.awt.*;

public class Circle extends Shape{

	private int r;
	private Color color;

	public Circle(int x, int y, int r, Color c) {
		super(x, y);
		this.r = r;
		this.color = c;
	}

	public int getR() {
		return r;
	}
}
