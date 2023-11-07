package VESPAP;

import java.net.Socket;
import java.util.HashMap;

public class VESPAP implements Protocole {
    private HashMap<String, String> passwords;
    private HashMap<String, Socket> clientsConnectes;
    private HashMap<Integer,String> idClientConnectes;

    private Logger logger;

    public VESPAP(Logger log) {
        passwords = new HashMap<>();
        passwords.put("wagner", "abcd");
        passwords.put("charlet", "1234");
        passwords.put("calmant", "azerty");

        logger = log;

        clientsConnectes = new HashMap<>();
        idClientConnectes= new HashMap();
    }

    @Override
    public String getNom() {
        return "VESPAP";
    }

    @Override
    public synchronized ReponseLogin TraiteRequete(Requete requete, Socket socket) throws FinConnexionException {
        if (requete instanceof RequeteLogin) return TraiteRequeteLOGIN((RequeteLogin) requete, socket);
        if (requete instanceof RequeteLOGOUT) TraiteRequeteLOGOUT((RequeteLOGOUT) requete);
        if (requete instanceof RequetClientId) TraiteRequetClientId((RequetClientId)requete);
        return null;
    }

    private synchronized ReponseLogin TraiteRequeteLOGIN(RequeteLogin requete, Socket socket) throws FinConnexionException {
        logger.Trace("RequeteLOGIN reçue de " + requete.getLogin());
        String password = passwords.get(requete.getLogin());
        if (password != null) if (password.equals(requete.getPassword())) {
            String ipPortClient = socket.getInetAddress().getHostAddress() + "/" + socket.getPort();
            logger.Trace(requete.getLogin() + " correctement loggé de " + ipPortClient);
            clientsConnectes.put(requete.getLogin(), socket);
            return new ReponseLogin(true);
        }
        logger.Trace(requete.getLogin() + " --> erreur de login");
        throw new FinConnexionException(new ReponseLogin(false));
    }

    private synchronized void TraiteRequeteLOGOUT(RequeteLOGOUT requete) throws FinConnexionException {
        logger.Trace("RequeteLOGOUT reçue de " + requete.getLogin());
        clientsConnectes.remove(requete.getLogin());
        logger.Trace(requete.getLogin() + " correctement déloggé");
        throw new FinConnexionException(null);
    }
    private synchronized void TraiteRequetClientId(RequetClientId requete) throws FinConnexionException{
        int id = Integer.parseInt(idClientConnectes.get(requete.getID()));


    }

}