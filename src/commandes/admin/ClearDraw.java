package commandes.admin;

import commandes.Commande;
import serveur.GerantDeClient;
import serveur.TchatServer;

public class ClearDraw implements Commande {
    @Override
    public boolean onCommand(TchatServer ts, GerantDeClient sender, String[] args) {
        if (sender.isAdmin()) {
            ts.resetDrawZoneSave();
            sender.showMessage(ts.getDrawZoneSave());
            ts.sendMessage(sender, ts.getDrawZoneSave(), true);
            ts.sendNotification(sender, "Un administrateur à nettoyeé la zone de dessin.");
            sender.showMessage("Vous avez nettoyé la zone de dessin.");

            return true;
        }

        return false;
    }

    @Override
    public boolean isDisplayable() {
        return false;
    }

    @Override
    public String getError() {
        return null;
    }

    @Override
    public String getDescription() {
        return "nettoie la zone de dessin";
    }
}
