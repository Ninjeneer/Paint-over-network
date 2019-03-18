package client.view.shape;

import java.awt.*;
import java.io.Serializable;

public class Square extends Shape implements Serializable {

	private int size;

	public Square(int x, int y, int size, Color c) {
		super(x, y, c);

		this.size = size;
	}

	public int getSize() {
		return this.size;
	}
}
