package client.view.shape;

import java.awt.*;
import java.io.Serializable;

public class Circle extends Shape implements Serializable {

	private int r;
	private Color color;

	public Circle(int x, int y, int r, Color c) {
		super(x, y, c);
		this.r = r;
		this.color = c;
	}

	public int getR() {
		return r;
	}
}
