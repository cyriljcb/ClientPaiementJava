package OVESP;
import java.net.Socket;

public interface Protocole {
    String getNom();
    ReponseLogin TraiteRequete(Requete requete, Socket socket) throws FinConnexionException;

}
