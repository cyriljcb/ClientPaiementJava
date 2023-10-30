package Controllers;

import Models.Model;
import View.ClientPaiement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutController implements ActionListener {
    private Model model;
    private ClientPaiement view;

    public LogoutController(Model model, ClientPaiement view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        model.fermerApp();
        view.updateViewLogout(model);
    }
}
