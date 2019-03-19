package client.view.shape;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {

	private int x;
	private int y;
	private Color c;

	public Shape(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() { return this.c; }

	@Override
	public boolean equals(Object o) {
		if (o instanceof Shape) {
			Shape s = (Shape)o;
			return this.x == s.x && this.y == s.y && this.c == s.c;
		} else
			return false;
	}
}
