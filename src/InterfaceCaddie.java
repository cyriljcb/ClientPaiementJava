import Classe.Caddie;
import Classe.RendererImage;
import Classe.RowHeightAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class InterfaceCaddie extends JPanel {
    private JTable table1;
    private DefaultTableModel model;
    private JFrame caddieFrame;  // Garder une référence au JFrame de l'interface caddie

    public InterfaceCaddie() {

        model = new DefaultTableModel();
        model.addColumn("intitulé");
        model.addColumn("quantité");
        model.addColumn("image");

        table1 = new JTable(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table1.getColumnModel().getColumn(2).setCellRenderer(new RendererImage());

        caddieFrame = new JFrame("Caddie");
        caddieFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        caddieFrame.setSize(400, 300);
        caddieFrame.add(new JScrollPane(table1));
        caddieFrame.pack();

        RowHeightAdjuster.adjustRowHeight(table1, 200);
        Font customFont = new Font("Arial", Font.PLAIN, 28);

        table1.setFont(customFont);
        Font columnHeaderFont = new Font("Arial", Font.BOLD, 18);  // Ajustez la police, le style et la taille ici

        // Obtenez l'en-tête de la JTable
        JTableHeader tableHeader = table1.getTableHeader();

        // Appliquez la police personnalisée aux noms de colonnes
        tableHeader.setFont(columnHeaderFont);
    }

    // Méthode pour mettre à jour les données de la JTable
    public void updateCaddie(List<Caddie> caddies) {
        // Supprimez toutes les lignes actuelles
        model.setRowCount(0);

        // Ajoutez les nouvelles données
        for (Caddie caddie : caddies) {
            String imagePath = "src\\images\\" + caddie.getImage();
            Object[] rowData = {caddie.getIntitule(), caddie.getQuantite(), imagePath};
            model.addRow(rowData);
        }

        // Actualisez l'affichage
        model.fireTableDataChanged();
        caddieFrame.setVisible(true);
    }
}

