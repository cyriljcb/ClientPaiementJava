package Controllers;

import Models.Model;
import View.ClientPaiement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoginController implements ActionListener {
    private Model model;
    private ClientPaiement view;

    public LoginController(Model model, ClientPaiement view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nom = view.getNomTextfield();
        String mdp = view.getMdpTextfield();
        boolean v = model.login(nom, mdp,false);
        if(v)
            view.updateView(model);
        else
            view.updateViewMessage(model);

    }
}
