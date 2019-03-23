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
    private Thread tcm;

    public Controler() {
        this.cm = new ClientManager(this);
        this.tcm = new Thread(this.cm);
        this.w = new Window(this);
    }

    public ClientManager getClientManager() {
        return this.cm;
    }

    public Window getWindow() {
        return this.w;
    }

    public void startClient (String serverAddress, String pseudo) {
        try {
            System.out.println("start client");
            this.cm.setConnexionInformations(serverAddress, pseudo);
            this.cm.startConnexion();
            this.cm.setConnected(true);
            this.tcm.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
