package OVESP;

public class ReponseFacture implements Reponse{
    private boolean valide;

    ReponseFacture(boolean v) {
        valide = v;
    }
    public boolean isValide() {
        return valide;
    }
}
