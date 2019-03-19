package client.view.shape;

import java.awt.*;

public class Triangle extends Shape {

    private int size;

    public Triangle(int x, int y, int size, Color c) {
        super(x, y, c);
        this.size = size;
    }

    public Polygon getPolygon() {
        Polygon p = new Polygon();

        p.addPoint(this.getX(), this.getY() - size / 2);
        p.addPoint(this.getX() - size / 2, this.getY() + size / 2);
        p.addPoint(this.getX() + size / 2, this.getY() + size / 2);

        return p;
    }

}
