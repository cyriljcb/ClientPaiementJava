import Classe.Caddie;
import Classe.RendererImage;
import Classe.RowHeightAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class InterfaceCaddie extends JPanel {
    private JTable table1;

    public InterfaceCaddie(List<Caddie> caddies) {
        JFrame frame = new JFrame("Caddie");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("intitulé");
        model.addColumn("quantité");
        model.addColumn("image");

        table1 = new JTable(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Utilisez le RendererImage pour la colonne d'images
        table1.getColumnModel().getColumn(2).setCellRenderer(new RendererImage());

        for (Caddie caddie : caddies) {
            String imagePath = "src\\images\\"+caddie.getImage();
            Object[] rowData = {caddie.getIntitule(), caddie.getQuantite(),imagePath };
            model.addRow(rowData);
        }

        frame.add(new JScrollPane(table1));
        frame.pack();
        frame.setVisible(true);
        RowHeightAdjuster.adjustRowHeight(table1, 300);
    }
}
