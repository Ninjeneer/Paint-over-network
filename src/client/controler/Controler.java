package client.controler;

import client.model.ClientManager;
import client.model.DrawTool;
import client.view.Window;

import java.awt.*;

public class Controler {

    private Window w;
    private ClientManager cm;

    private DrawTool drawTool = DrawTool.CIRCLE;
    private Color drawColor = null;
    private int drawSize;

    public Controler() {
        this.cm = new ClientManager(this);
        this.w = new Window(this);
    }

    public ClientManager getClientManager() {
        return this.cm;
    }

    public Window getWindow() {
        return this.w;
    }

    public void setTool(DrawTool d) {
        this.drawTool = d;
    }

    public DrawTool getTool() {
        return this.drawTool;
    }

    public Color getDrawColor() {
        return this.drawColor;
    }

    public void setDrawColor(Color c) {
        this.drawColor = c;
    }

    public int getDrawSize() {
        return this.drawSize;
    }

    public void setDrawSize(int size) {
        this.drawSize = size;
    }

    public static void main(String[] args) {
        new Controler();
    }
}
