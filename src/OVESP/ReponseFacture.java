package OVESP;

import Classe.Facture;

import java.util.List;

public class ReponseFacture implements Reponse{
    private List<Facture> facturelist;
    public static void afficherFactures(List<Facture> factures) {
        for (Facture facture : factures) {
            System.out.println("ID : " + facture.getId());
            System.out.println("ID du client : " + facture.getIdClient());
            System.out.println("Date : " + facture.getDate());
            System.out.println("Montant : " + facture.getMontant());
            System.out.println("Payée : " + facture.isPaye());
            System.out.println();
        }
    }

    ReponseFacture(List<Facture> facture) {
        facturelist = facture;
        System.out.println("allo");
        afficherFactures(facturelist);
    }
    public List<Facture> getFacture() {
        return facturelist;
    }
}
