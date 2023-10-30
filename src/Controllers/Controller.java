package Controllers;

import Models.Model;
import View.ClientPaiement;

public class Controller {
    private Model model;
    private ClientPaiement view;

    public Controller(Model model, ClientPaiement view) {
        this.model = model;
        this.view = view;
        view.addLoginListener(new LoginController(model, view));
        view.addLogoutListener(new LogoutController(model,view));
        view.addRechercherListener(new RechercherController(model,view));
        view.addCreerListener(new CreerController(model,view));
        view.addPayerListener(new PayerController(model,view));
        TableClickListener tableClickListener = new TableClickListener(model, view);
        view.addFactureClickListener(tableClickListener);
    }
}
