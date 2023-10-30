package Controllers;

import Models.Model;
import View.ClientPaiement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RechercherController implements ActionListener {
    private Model model;
    private ClientPaiement view;

    public RechercherController(Model model, ClientPaiement view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(model.Rechercher(view.getNumClient()))
            view.updateViewRechercher(model);
        else
            view.updateViewMessage(model);

    }
}