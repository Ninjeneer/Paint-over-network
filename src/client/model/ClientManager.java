package client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import client.controler.Controler;
import client.view.Window;
import client.view.shape.Shape;
import utils.Affichage;
import utils.Serializer;

public class ClientManager extends Thread {

    private String serverAddress;
    private String pseudo;
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected;

    private Window view;

    private Controler ctrl;

    /**
     * Crée un client manager
     *
     * @param ctrl controleur
     */
    public ClientManager(Controler ctrl) {
        this.ctrl = ctrl;
        this.connected = false;
    }

    /**
     * Établi la connexion avec le serveur cible
     *
     * @throws NumberFormatException
     * @throws UnknownHostException
     * @throws IOException
     */
    public void startConnexion() throws NumberFormatException, UnknownHostException, IOException {
        this.s = new Socket(this.serverAddress.split(":")[0], Integer.parseInt(this.serverAddress.split(":")[1]));
        this.out = new PrintWriter(s.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        // envoi des données de connexion
        this.out.println(this.pseudo);
    }

    /**
     * Envoie un message au serveur
     *
     * @param o message
     */
    public void sendMessage(Object o) {
        try {
            this.out.println(Serializer.serialize(o));
        } catch (Exception e) {
        }
    }

    /**
     * Commence la récupération asynchrone des messages
     */
    public void run() {
        try {
            Object reponse;
            while (true) {
                reponse = Serializer.deserialize(in.readLine());

                // récéption d'un message simple
                if (reponse instanceof String) {
                    String message = (String) reponse;

                    // suppression des caractères de format propres à la console
                    message = message.replace(Affichage.bold, "");
                    message = message.replace(Affichage.italic, "");
                    message = message.replace(Affichage.reset, "");
                    for (String color : Affichage.colors)
                        message = message.replace(color, "");

                    for (String line : message.split("\n"))
                        this.ctrl.getWindow().newMessage(line);
                } else if (reponse instanceof ArrayList) {
                    this.ctrl.getWindow().getDrawZone().setContent((ArrayList<Shape>) reponse);
                    this.ctrl.getWindow().getDrawZone().repaint();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Défini les informations relatives à la connexion
     *
     * @param serverAddress adresse IP du serveur
     * @param pseudo        pseudo du client
     */
    public void setConnexionInformations(String serverAddress, String pseudo) {
        this.serverAddress = serverAddress;
        this.pseudo = pseudo;
    }

    /**
     * Retourne le pseudo du client
     *
     * @return pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Défini si le client est connecté
     *
     * @param b vrai si connecté
     */
    public void setConnected(boolean b) {
        this.connected = b;
    }

}
