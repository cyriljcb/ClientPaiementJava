package Classe;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RowHeightAdjuster {
    public static void adjustRowHeight(JTable table, int rowHeight) {
        table.setRowHeight(rowHeight);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                for (int row = 0; row < table.getRowCount(); row++) {
                    int rowHeight = table.getRowHeight(row);
                    table.setRowHeight(row, rowHeight);
                }
            }
        });
    }
}
