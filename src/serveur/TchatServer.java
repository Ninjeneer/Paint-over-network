package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import client.view.shape.Shape;
import commandes.ChangerCouleur;
import commandes.Commande;
import commandes.Coucou;
import commandes.Help;
import commandes.MP;
import commandes.Me;
import commandes.Nick;
import commandes.Quit;
import commandes.Who;
import commandes.Wizz;
import commandes.admin.*;
import utils.Affichage;

public class TchatServer {

    private ServerSocket ss;
    private ArrayList<GerantDeClient> clientList;
    private HashMap<String, Commande> commandListe;

    private ArrayList<Shape> drawZoneSave;

    /**
     * Crée un serveur de tchat
     */
    public TchatServer(int port) {

        try {
            // création du serveur
            System.out.println("Création du tchat..");
            this.ss = new ServerSocket(port);
            System.out.println("Tchat créé sur le port " + port + " !");
        } catch (Exception e) {
            System.out.println("Impossible de créer le serveur");
            e.printStackTrace();
        }

        this.clientList = new ArrayList<>();
        this.commandListe = new HashMap<>();
        this.drawZoneSave = new ArrayList<>();

        // ajout des commandes
        addCommand("coucou", new Coucou());
        addCommand("mp", new MP());
        addCommand("help", new Help());
        addCommand("nick", new Nick());
        addCommand("quit", new Quit());
        addCommand("me", new Me());
        addCommand("who", new Who());

        // commandes admin
        addCommand("kick", new Kick());
        addCommand("adminlogin", new AdminLogin());
        addCommand("adminlogoff", new AdminLogoff());
        addCommand("mute", new Mute());
        addCommand("unmute", new UnMute());
        addCommand("say", new Say());
        addCommand("adminhelp", new AdminHelp());
        addCommand("getinfo", new GetInfo());
        addCommand("kickall", new KickAll());
        addCommand("muteall", new MuteAll());
        addCommand("unmuteall", new UnMuteAll());
        addCommand("blockcommand", new BlockCommand());
        addCommand("unblockcommand", new UnBlockCommand());
        addCommand("cleardraw", new ClearDraw());

        while (true) {
            // attente du client
            Socket s = null;
            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(s);

            // création du client
            GerantDeClient gdc = new GerantDeClient(this, s);
            Thread tgdc = new Thread(gdc);
            tgdc.start();
        }
    }

    /**
     * Envoie un message à tous les clients
     *
     * @param sender client émetteur
     * @param o      message envoyé
     */
    public void sendMessage(GerantDeClient sender, Object o, boolean objectMode) {
        // gestion des commandes
        String s = "";
        if (!objectMode)
            s = o.toString();

        //Si c'est une commande
        if (s.startsWith("/")) {
            String trigger;

            //Si elle contient des arguments
            if (s.contains(" "))
                trigger = s.substring(1, s.indexOf(" "));
            else
                trigger = s.substring(1);

            // si la commande n'existe pas
            if (!this.commandListe.containsKey(trigger)) {
                sender.showMessage(Affichage.bold + "ERREUR : cette commande n'existe pas" + Affichage.reset);
                return;
            }

            // si l'utilisateur n'a pas le droit d'utiliser des commandes
            if (!sender.isComandAllowed()) {
                sender.showMessage(Affichage.red + "ERREUR : vos commandes ont été bloquées par un administrateur !"
                        + Affichage.reset);
                return;
            }

            // si la commande est mal utilisée
            if (!this.commandListe.get(trigger).onCommand(this, sender, s.split(" ")) && sender.isComandAllowed())
                sender.showMessage(Affichage.bold + this.commandListe.get(trigger).getError() + Affichage.reset);

        } else {
            // sauvegarde de la zone de dessin
            if (objectMode)
                this.drawZoneSave = (ArrayList<Shape>)o;

            // envoi du message
            for (GerantDeClient gdc : this.clientList) {
                if (gdc != sender && !sender.isMuted() && sender.isAlive())
                    if (!objectMode)
                        gdc.showMessage(sender.getCouleur() + sender.getPseudo() + ": " + "\033[0m" + s);
                    else
                        gdc.showMessage(o);
            }
        }
    }

    /**
     * Envoie une notification à tous les clients
     *
     * @param sender
     * @param s
     */
    public void sendNotification(GerantDeClient sender, String s) {
        for (GerantDeClient gdc : this.clientList) {
            if (gdc != sender)
                gdc.showMessage(s);
        }
    }

    /**
     * Ajoute le client à la liste de clients
     *
     * @param gdc Client
     */
    public void addGerantDeClient(GerantDeClient gdc) {
        sendNotification(gdc, ">>> " + gdc.getPseudo() + "(" + gdc.getSocket().getInetAddress()
                + ") vient de rejoindre le serveur :D");
        gdc.showMessage(Affichage.bold + "Tapez /help [commande] afin d'obtenir de l'aide" + Affichage.reset);
        this.clientList.add(gdc);
    }

    /**
     * Supprime le client de la liste des clients
     *
     * @param gdc
     */
    public void delGerantDeClient(GerantDeClient gdc) {
        sendNotification(gdc, "<<< " + gdc.getPseudo() + " vient de quitter le serveur :(");
        this.clientList.remove(gdc);
    }

    /**
     * Ajoute une commande au serveur
     *
     * @param trigger commande utilisateur
     * @param cmd     commande déclenchée
     */
    private void addCommand(String trigger, Commande cmd) {
        this.commandListe.put(trigger, cmd);
    }

    /**
     * Retourne la liste des clients
     *
     * @return liste de clients
     */
    public ArrayList<GerantDeClient> getClientList() {
        return this.clientList;
    }

    /**
     * Retourne la liste des commandes
     *
     * @return liste des commandes
     */
    public HashMap<String, Commande> getCommandeList() {
        return this.commandListe;
    }

    public ArrayList<Shape> getDrawZoneSave() {
        return this.drawZoneSave;
    }

    public void resetDrawZoneSave() {
        this.drawZoneSave = new ArrayList<>();
    }

    public static void main(String[] args) {

        int port;
        if (args.length == 0)
            port = 8003; //port par défaut
        else
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                port = 8003;
            }


        new TchatServer(port);
    }
}