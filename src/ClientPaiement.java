import Classe.Caddie;
import Classe.Employe;
import Classe.Facture;
import OVESP.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.List;
import java.util.Properties;

public class ClientPaiement extends JFrame implements KeyListener {
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

    private Socket socket;

    private String login;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean isInterfaceOpen = false;
    InterfaceCaddie interfaceCaddie = new InterfaceCaddie();
    private boolean isInitialized = false;
    private void initializeObjectStreams() {
        if (!isInitialized) {
            Properties properties = new Properties();
            FileInputStream input = null;
            int port = 0;
            String ipServeur;
            try {
                input = new FileInputStream("C:\\Users\\ateli\\OneDrive\\Documents\\cours_superieur\\B3\\RTI\\labo\\partie paiement\\ClientPaiement\\src\\config.properties");
                properties.load(input);
                port = Integer.parseInt(properties.getProperty("PORT_PAIEMENT"));
                ipServeur = properties.getProperty(("IP_SERVEUR"));
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        dialogueMessage("erreur de lecture dans le fichier de conf");
                    }
                }
            }
            try {
                socket = new Socket(ipServeur, port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                isInitialized = true; // Marquer comme initialisé
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("touche : " + key);
        if (key == KeyEvent.VK_MINUS) {  // Touche "-"
//            initializeObjectStreams();
//            System.out.println("Touche '-' appuyée.");
//
//
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
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Rendre toutes les cellules non modifiables
                return false;
            }
        };
        model.addColumn("idFacture");
        model.addColumn("date");
        model.addColumn("montant");
        model.addColumn("payé");
        rechercherButton.setEnabled(false);
        logoutButton.setEnabled(false);
        payerButton.setEnabled(false);

        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                initializeObjectStreams();
                String login1 = jTextFieldLogin.getText();
                String password = jTextFieldPassword.getText();
                System.out.println("login " + login1 + " mdp " + password);
                try {

                    RequeteLogin requete = new RequeteLogin(login1, password,false);

                    oos.writeObject(requete);
                    System.out.println("est passé dans le login requete" + requete);
                    ReponseLogin reponse = (ReponseLogin) ois.readObject();
                    System.out.println("est passé dans le login après lecture");
                    if (reponse.isValide()) {
                        loginButton.setEnabled(false);
                        logoutButton.setEnabled(true);
                        rechercherButton.setEnabled(true);

                        dialogueMessage("connecté");
                    } else {
                       dialogueMessage("probleme lors de la tentative de connection");
                        socket.close();

                    }
                } catch (IOException | ClassNotFoundException ex) {
                    //JOptionPane.showMessageDialog(this,"Problème de connexion!","Erreur...",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequeteLOGOUT requete = new RequeteLOGOUT(login);
                try {

                    System.out.println("la requete de logout :" + requete);
                    oos.writeObject(requete);
                    socket.close();
                    oos.close();
                    ois.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                isInitialized=false;
                loginButton.setEnabled(true);
                logoutButton.setEnabled(false);
                rechercherButton.setEnabled(false);
                payerButton.setEnabled(false);
                jTextFieldLogin.setText("");
                jTextFieldPassword.setText("");
                numClient.setText("");
                model.setRowCount(0);


            }
        });
        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCli = numClient.getText();
                System.out.println(idCli);
                RequeteFacture requete = new RequeteFacture(idCli);
                model.setRowCount(0);
                try {
                    oos.writeObject(requete);
                    System.out.println("est passé dans le facture requete " + requete);
                    ReponseFacture reponse = (ReponseFacture) ois.readObject();
                    System.out.println("est passé dans le facture après lecture");
                    for (Facture facture : reponse.getFacture()) {
                        Object[] rowData = {facture.getId(), facture.getDate(), facture.getMontant(), facture.isPaye()};
                        model.addRow(rowData);
                    }
                    //afficherFactures(reponse.getFacture());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        payerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Row = table1.getSelectedRow();
                if (Row != -1) {
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    int columnCount = model.getColumnCount();

                    String info = model.getValueAt(Row, 0).toString();
                    System.out.println("idFacture  : " + info);
                    RequetePayeFacture requete = new RequetePayeFacture(info, numClient.getText(), nomVisa.getText(), NumVisa.getText());
                    try {
                        oos.writeObject(requete);
                        ReponsePayeFacture reponse1 = (ReponsePayeFacture) ois.readObject();
                        System.out.println("est passé dans le Payefacture requete " + requete);
                        if (reponse1.getPaye()) {

                            model.setRowCount(0);
                            RequeteFacture requete1 = new RequeteFacture(numClient.getText());
                            oos.writeObject(requete1);
                            ReponseFacture reponse = (ReponseFacture) ois.readObject();
                            System.out.println("est passé dans le Payefacture après lecture");
                            for (Facture facture : reponse.getFacture()) {
                                Object[] rowData = {facture.getId(), facture.getDate(), facture.getMontant(), facture.isPaye()};
                                model.addRow(rowData);
                            }
                            dialogueMessage("Facture payée");
                        } else
                            dialogueMessage("Probleme lors du paiement de la facture");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                    payerButton.setEnabled(true);
                    int selectedRow = table1.getSelectedRow();
                    String info = model.getValueAt(selectedRow, 0).toString();
                    RequeteCaddie requete = new RequeteCaddie(info);
                    System.out.println("Est passé dans le caddie requete " + requete);

                    try {
                        oos.writeObject(requete);
                        ReponseCaddie reponse1 = (ReponseCaddie) ois.readObject();

                        System.out.println("Affichage des résultats : ");
                        interfaceCaddie.updateCaddie(reponse1.getCaddieList());

                        if (!isInterfaceOpen) {
                           interfaceCaddie.setVisible(true);
                            isInterfaceOpen = true;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        creerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    initializeObjectStreams();
                    System.out.println("login : "+jTextFieldLogin.getText()+" mdp : "+jTextFieldPassword.getText());
                    RequeteLogin requete = new RequeteLogin(jTextFieldLogin.getText(), jTextFieldPassword.getText(), true);

                    oos.writeObject(requete);
                    System.out.println("est passé dans le login requete" + requete);
                    ReponseLogin reponse = (ReponseLogin) ois.readObject();
                    System.out.println("est passé dans le login après lecture");
                    if (reponse.isValide()) {
                        loginButton.setEnabled(false);
                        logoutButton.setEnabled(true);
                        dialogueMessage("connecté");
                        // jButtonCalcul.setEnabled(true);
                        // this.login = login;
                    } else {
                        //JOptionPane.showMessageDialog(this,"Erreur de login!","Erreur...",JOptionPane.ERROR_MESSAGE);
                        socket.close();

                    }
                } catch (IOException | ClassNotFoundException ex) {
                    //JOptionPane.showMessageDialog(this,"Problème de connexion!","Erreur...",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientPaiement app = new ClientPaiement();
            JFrame frame = new JFrame("Maraicher en ligne");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(app.panel1);
            frame.setSize(850, 250);
            frame.setLocationRelativeTo(null);

            // Ajoutez ces lignes pour activer le focus clavier
            frame.addKeyListener(app);
            frame.setFocusable(true);
            frame.requestFocusInWindow();

            frame.setVisible(true);
        });
    }
}
