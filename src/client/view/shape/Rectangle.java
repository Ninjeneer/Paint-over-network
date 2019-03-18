package client.view.shape;

import java.awt.*;
import java.io.Serializable;

public class Rectangle extends Shape implements Serializable {

	private int diag;

	public Rectangle(int x, int y, Color c) {
		super(x, y);
	}
}
