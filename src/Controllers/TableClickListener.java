package Controllers;

import Models.Model;
import View.ClientPaiement;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TableClickListener implements MouseListener {
    private Model model;
    private ClientPaiement view;

    public TableClickListener(Model model, ClientPaiement view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int Row = view.getFactureTable().getSelectedRow();
        DefaultTableModel model1 = (DefaultTableModel) view.getFactureTable().getModel();
        String info = model1.getValueAt(Row, 0).toString();
            model.caddie(info);
            view.updateViewTable(model);

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Autres méthodes de l'interface MouseListener à implémenter
}

