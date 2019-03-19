package client.view;


import client.controler.Controler;
import client.model.DrawTool;
import client.view.shape.Circle;
import client.view.shape.Shape;
import client.view.shape.Square;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class DrawZone extends Canvas implements MouseListener, MouseMotionListener {

    private Controler ctrl;
    private List<Shape> content;
    private List<Shape> userContent;
    private int posX;
    private int posY;

    public DrawZone(Controler ctrl) {
        super();
        this.ctrl = ctrl;

        this.content = new ArrayList<>();
        this.userContent = new ArrayList<>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
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
        this.posX = e.getX();
        this.posY = e.getY();
        drawShape(e);
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

    /**
     * Actualise le contenu du canvas
     * @param c liste de formes
     */
    public void setContent(ArrayList<Shape> c) {
        this.content = c;
        this.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (distance(this.posX, this.posY, mouseEvent.getX(), mouseEvent.getY()) > this.ctrl.getDrawSize() / 4) {
            drawShape(mouseEvent);
            this.posX = mouseEvent.getX();
            this.posY = mouseEvent.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    /**
     * Dessine une forme sur le canvas et l'envoie à tous les clients
     * @param e position
     */
    private void drawShape(MouseEvent e) {
        Shape shape = null;

        if (this.ctrl.getTool() == DrawTool.SQUARE)
            shape = new Square(e.getX() - this.ctrl.getDrawSize() / 2, e.getY() - this.ctrl.getDrawSize() / 2, this.ctrl.getDrawSize(), this.ctrl.getDrawColor());
        else if (this.ctrl.getTool() == DrawTool.CIRCLE)
            shape = new Circle(e.getX() - this.ctrl.getDrawSize() / 2, e.getY() - this.ctrl.getDrawSize() / 2, this.ctrl.getDrawSize(), this.ctrl.getDrawColor());

        this.content.add(shape); //ajout de la forme au canvas
        this.userContent.add(shape); //ajout de la forme dans l'historique des dessins
        this.repaint(); //refresh le canvas
        this.ctrl.getClientManager().sendMessage(this.content); //envoie la mise à jour à tous les clients
    }

    /**
     * Calcul la distance entre deux points
     * @param x abscisse 1
     * @param y ordonnée 1
     * @param x1 abscisse 2
     * @param y1 ordonnée 2
     * @return distance entre x et y
     */
    private int distance(int x, int y, int x1, int y1) {
        return (int) (Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)));
    }

    /**
     * Annule la dernière opération de l'utilisateur
     */
    public void undo() {
        if (this.userContent.size() > 0) {
            Shape toRemove = this.userContent.remove(this.userContent.size()-1);

            for (Shape s : this.content) {
                if (s.equals(toRemove)) {
                    this.content.remove(s);
                    break;
                }
            }

            this.ctrl.getClientManager().sendMessage(this.content); //envoie la mise à jour à tous les clients
        }

        this.repaint();
    }
}
