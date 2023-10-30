package Controllers;

import Models.Model;
import View.ClientPaiement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreerController implements ActionListener {
    private Model model;
    private ClientPaiement view;

    public CreerController(Model model, ClientPaiement view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String nom = view.getNomTextfield();
        String mdp = view.getMdpTextfield();
        boolean v = model.login(nom, mdp,true);
        if(v)
            view.updateView(model);

    }
}
