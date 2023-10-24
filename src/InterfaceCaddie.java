import Classe.Caddie;
import Classe.ColumnWidthAdjuster;
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
        model.addColumn("image");
        model.addColumn("intitulé");
        model.addColumn("quantité");

        table1 = new JTable(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table1.getColumnModel().getColumn(0).setCellRenderer(new RendererImage());

        caddieFrame = new JFrame("Caddie");
        caddieFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        caddieFrame.setSize(500, 200);
        caddieFrame.add(new JScrollPane(table1));
        caddieFrame.pack();

        // Ajustz la largeur de chaque colonne
        ColumnWidthAdjuster.adjustColumnWidth(table1, 0, 200);
        ColumnWidthAdjuster.adjustColumnWidth(table1, 1, 150);
        ColumnWidthAdjuster.adjustColumnWidth(table1, 2, 100);

        RowHeightAdjuster.adjustRowHeight(table1, 200);
        Font customFont = new Font("Arial", Font.PLAIN, 28);

        table1.setFont(customFont);
        Font columnHeaderFont = new Font("Arial", Font.BOLD, 18);
        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setFont(columnHeaderFont);
    }


    // Méthode pour mettre à jour les données de la JTable
    public void updateCaddie(List<Caddie> caddies) {
        // Supprimez toutes les lignes actuelles
        model.setRowCount(0);

        // Ajoutez les nouvelles données
        for (Caddie caddie : caddies) {
            String imagePath = "src\\images\\" + caddie.getImage();
            Object[] rowData = {imagePath, caddie.getIntitule(), caddie.getQuantite()};  // Assurez-vous que la première colonne correspond à "image"
            model.addRow(rowData);
        }

        // Actualisez l'affichage
        model.fireTableDataChanged();
        caddieFrame.setVisible(true);
    }
}
