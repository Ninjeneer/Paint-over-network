package client.view.shape;

import java.io.Serializable;

public abstract class Shape implements Serializable {

	private int x;
	private int y;

	public Shape(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
