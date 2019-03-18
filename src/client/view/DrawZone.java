package client.view;


import client.controler.Controler;
import client.model.DrawTool;
import client.view.shape.Circle;
import client.view.shape.Shape;
import client.view.shape.Square;

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
            g.setColor(s.getColor());

            if (s instanceof Circle)
                g.fillArc(s.getX(), s.getY(), ((Circle) s).getR(), ((Circle) s).getR(), 0, 360);
            else if (s instanceof Square)
                g.fillRect(s.getX(), s.getY(), ((Square) s).getSize(), ((Square) s).getSize());
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Shape shape = null;

        if (this.ctrl.getTool() == DrawTool.SQUARE)
            shape = new Square(e.getX() - this.ctrl.getDrawSize() / 2, e.getY() - this.ctrl.getDrawSize() / 2, this.ctrl.getDrawSize(), this.ctrl.getDrawColor());
        else if (this.ctrl.getTool() == DrawTool.CIRCLE)
            shape = new Circle(e.getX() - this.ctrl.getDrawSize() / 2, e.getY() - this.ctrl.getDrawSize() / 2, this.ctrl.getDrawSize(), this.ctrl.getDrawColor());

        this.content.add(shape); //ajout de la forme au canvas
        this.repaint(); //refresh le canvas
        this.ctrl.getClientManager().sendMessage(this.content); //envoie la mise à jour à tous les clients
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
