package client.view.shape;

import java.awt.*;
import java.io.Serializable;

public class Square extends Shape implements Serializable {

	private int diag;

	public Square(int x, int y, Color c) {
		super(x, y, c);
	}
}
