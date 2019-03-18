package client.view;


import client.controler.Controler;
import client.model.ClientManager;
import client.view.shape.Circle;
import client.view.shape.Shape;
import client.view.shape.Rectangle;
import utils.Serializer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class DrawZone extends Canvas implements MouseListener {

	private Controler ctrl;
	private List<client.view.shape.Shape> content;

	public DrawZone(Controler ctrl) {
		super();
		this.ctrl = ctrl;

		this.content = new ArrayList<>();
		this.addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		for (Shape s : content) {
			if (s instanceof Circle)
				g.fillArc(s.getX(), s.getY(), ((Circle) s).getR(), ((Circle) s).getR(), 0, 360);
			else if (s instanceof Rectangle)
				g.fillRect(s.getX(), s.getY(), 150, 75);
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {

		System.out.println("click");

		//Circle c = new Circle(e.getX(), e.getY(), 10, Color.BLACK);
		Rectangle r = new Rectangle(e.getX(), e.getY(), Color.BLACK);
		this.content.add(r);
		this.repaint();
		this.ctrl.getClientManager().sendMessage(Serializer.serialize(this.content));
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void setContent(ArrayList<Shape> c) {
		this.content = c;
		this.repaint();
	}
}
