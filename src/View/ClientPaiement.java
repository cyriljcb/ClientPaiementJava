package View;

import Classe.*;
import Controllers.TableClickListener;
import Models.Model;
import Models.ModelObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class ClientPaiement extends JFrame implements KeyListener, ModelObserver {
    private JPanel panel1;
    private JFormattedTextField jTextFieldLogin;
    private JFormattedTextField jTextFieldPassword;
    private JButton loginButton;
    private JButton logoutButton;
    private JFormattedTextField numClient;
    private JTable table1;
    private JFormattedTextField nomVisa;
    private JButton rechercherButton;
    private JFormattedTextField NumVisa;
    private JButton payerButton;
    private JButton creerButton;
    private JTable table2;
    private JScrollPane JScrollPane2;
    private JScrollPane JScrollPane1;
    private JLabel JLabelPanier;
    private DefaultTableModel tabModel ;
    private DefaultTableModel tabModel1;
    private JFrame mainWindow;
    private boolean testTable = false;
    int maxLength = 20;
    private int desiredFrameWidth = 850;

    public void addFactureClickListener(MouseListener listener) {

        table1.addMouseListener(listener);
    }
    public boolean isTestTable()
    {
        return testTable;

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("touche : " + key);
        if (key == KeyEvent.VK_MINUS) {  // Touche "-"
            creerButton.setVisible(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ne rien faire (corps vide) ou lancer une exception non prise en charge
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Code à exécuter lorsque qu'une touche est relâchée
    }
    public void dialogueMessage(String message) {
        System.out.println("dialog");
        JOptionPane.showMessageDialog(this, message);
    }

    public ClientPaiement() {
        creerButton.setVisible(false);
        tabModel = new DefaultTableModel();
        tabModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Rendre toutes les cellules non modifiables
                return false;
            }
        };

        tabModel.addColumn("idFacture");
        tabModel.addColumn("date");
        tabModel.addColumn("montant");
        tabModel.addColumn("payé");
        table1.setModel(tabModel);
        tabModel.setRowCount(10);

        tabModel1 = new DefaultTableModel();
        tabModel1.addColumn("image");
        tabModel1.addColumn("intitulé");
        tabModel1.addColumn("quantité");
        table2.setModel(tabModel1);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.getColumnModel().getColumn(0).setCellRenderer(new RendererImage());
        ColumnWidthAdjuster.adjustColumnWidth(table2, 0, 200);
        ColumnWidthAdjuster.adjustColumnWidth(table2, 1, 150);
        ColumnWidthAdjuster.adjustColumnWidth(table2, 2, 100);

        RowHeightAdjuster.adjustRowHeight(table2, 200);
        Font customFont = new Font("Arial", Font.PLAIN, 28);

        table2.setFont(customFont);
        Font columnHeaderFont = new Font("Arial", Font.BOLD, 18);
        JTableHeader tableHeader = table2.getTableHeader();
        tableHeader.setFont(columnHeaderFont);
        JScrollPane2.setVisible(false);
        JLabelPanier.setVisible(false);
        rechercherButton.setEnabled(false);
        logoutButton.setEnabled(false);
        payerButton.setEnabled(false);
        table2.setEnabled(false);
        numClient.setEnabled(false);
        nomVisa.setEnabled(false);
        NumVisa.setEnabled(false);

        AbstractDocument document = (AbstractDocument) jTextFieldLogin.getDocument();
        document.setDocumentFilter(new MaxLengthDocumentFilter(maxLength));

        AbstractDocument document1 = (AbstractDocument) jTextFieldPassword.getDocument();
        document1.setDocumentFilter(new MaxLengthDocumentFilter(maxLength));
        AbstractDocument document2 = (AbstractDocument) numClient.getDocument();
        document2.setDocumentFilter(new MaxLengthDocumentFilter(maxLength));
        AbstractDocument document3 = (AbstractDocument) nomVisa.getDocument();
        document3.setDocumentFilter(new MaxLengthDocumentFilter(maxLength));
        AbstractDocument document4 = (AbstractDocument) NumVisa.getDocument();
        document4.setDocumentFilter(new MaxLengthDocumentFilter(maxLength));

        panel1.setSize(1000,1000);

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    public String getNomTextfield()
    {
        return jTextFieldLogin.getText();
    }
    public String getMdpTextfield()
    {
        return jTextFieldPassword.getText();
    }
    public String getNumClient()
    {
        return numClient.getText();
    }
    public JTable getFactureTable() {
        return table1;
    }
    public String getNomVisa()
    {
        return nomVisa.getText();
    }
    public String getNumVisa()
    {
        return NumVisa.getText();
    }
    public Component getPanel_principal() {
        return panel1;
    }
    public void setMainWindow(JFrame frame) {
        this.mainWindow = frame;
    }

    @Override
    public void updateView(Model model) {

        dialogueMessage(model.getMessage());
        loginButton.setEnabled(false);
        creerButton.setEnabled(false);
        jTextFieldLogin.setEnabled(false);
        jTextFieldPassword.setEnabled(false);
        logoutButton.setEnabled(true);
        rechercherButton.setEnabled(true);
        numClient.setEnabled(true);
    }
    @Override
    public void updateViewRechercher(Model model) {

        JScrollPane2.setVisible(false);
        JLabelPanier.setVisible(false);
        testTable = false;
        mainWindow.setSize(desiredFrameWidth, 250);
        tabModel.setRowCount(0);
        testTable = true;

        for (Facture facture : model.getFactures()) {
            Object[] rowData = {facture.getId(), facture.getDate(), facture.getMontant(), facture.isPaye()};
            tabModel.addRow(rowData);
        }
    }
    @Override
    public void updateViewMessage(Model model) {

        dialogueMessage(model.getMessage());

    }
    @Override
    public void updateViewPayer(Model model) {
        dialogueMessage(model.getMessage());
        tabModel.setRowCount(0);
        for (Facture facture : model.getFactures()) {
            Object[] rowData = {facture.getId(), facture.getDate(), facture.getMontant(), facture.isPaye()};
            tabModel.addRow(rowData);
        }
        nomVisa.setText("");
        NumVisa.setText("");
    }
    public void updateViewTable(Model model) {
        mainWindow.setSize(desiredFrameWidth, 500);
        payerButton.setEnabled(true);
        JScrollPane2.setVisible(true);
        JLabelPanier.setVisible(true);
        nomVisa.setEnabled(true);
        NumVisa.setEnabled(true);
        tabModel1.setRowCount(0);
        for (Caddie caddie : model.getCaddie1()) {
            String imagePath = "src\\images\\" + caddie.getImage();
            Object[] rowData = {imagePath, caddie.getIntitule(), caddie.getQuantite()};
            tabModel1.addRow(rowData);
        }
        tabModel1.fireTableDataChanged();

        pack(); // Réorganiser la fenêtre principale pour prendre en compte les modifications de la JTable
    }
    public void updateViewMessage(String msg)
    {
        dialogueMessage(msg);
    }


    @Override
    public void updateViewLogout(Model model) {
        loginButton.setEnabled(true);
        logoutButton.setEnabled(false);
        rechercherButton.setEnabled(false);
        payerButton.setEnabled(false);
        jTextFieldLogin.setText("");
        jTextFieldPassword.setText("");
        numClient.setText("");
        nomVisa.setText("");
        NumVisa.setText("");
        tabModel.setRowCount(0);
        tabModel1.setRowCount(0);
        JScrollPane2.setVisible(false);
        JLabelPanier.setVisible(false);
        testTable = false;
        mainWindow.setSize(desiredFrameWidth, 250);
        jTextFieldLogin.setEnabled(true);
        jTextFieldPassword.setEnabled(true);
        numClient.setEnabled(false);
    }
    public void addLoginListener(ActionListener listener){
        loginButton.addActionListener(listener);
    }
    public void addLogoutListener(ActionListener l) {
        logoutButton.addActionListener(l);
    }
    public void addRechercherListener(ActionListener l) {
        rechercherButton.addActionListener(l);
    }
    public void addCreerListener(ActionListener l) {
        creerButton.addActionListener(l);
    }
    public void addPayerListener(ActionListener l) {
        payerButton.addActionListener(l);
    }



}
