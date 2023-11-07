package Controllers;

import Models.Model;
import View.ClientPaiement;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayerController implements ActionListener {
    private Model model;
    private ClientPaiement view;

    public PayerController(Model model, ClientPaiement view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        int Row = view.getFactureTable().getSelectedRow();
        DefaultTableModel model1 = (DefaultTableModel) view.getFactureTable().getModel();
        String info = model1.getValueAt(Row, 0).toString();
        boolean paye = (boolean) model1.getValueAt(Row, 3);
        String nom = view.getNomVisa();
        String numVisa = view.getNumVisa();
        if(!paye)
        {
            if(model.payer(nom,numVisa,Row,info))
                view.updateViewPayer(model);
            else
                view.updateViewMessage(model);
        }
        else
        {
            view.updateViewMessage("la facture n'est plus a payer");
        }

    }
}
