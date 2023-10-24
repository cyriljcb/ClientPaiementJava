package Classe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ColumnWidthAdjuster {
    public static void adjustColumnWidth(JTable table, int column, int width) {
        TableColumn col = table.getColumnModel().getColumn(column);
        col.setPreferredWidth(width);
        col.setMaxWidth(width);
        col.setMinWidth(width);
    }
}
