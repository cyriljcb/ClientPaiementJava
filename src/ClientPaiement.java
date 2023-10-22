import Classe.Facture;
import OVESP.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static java.lang.System.exit;

public class ClientPaiement extends JFrame {
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
    private Socket socket;

    private String login;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public void dialogueMessage( String message) {
        System.out.println("dialog");
        JOptionPane.showMessageDialog(this, message);
    }
    public static void afficherFactures(List<Facture> factures) {
        for (Facture facture : factures) {
            System.out.println("ID : " + facture.getId());
            System.out.println("ID du client : " + facture.getIdClient());
            System.out.println("Date : " + facture.getDate());
            System.out.println("Montant : " + facture.getMontant());
            System.out.println("Payée : " + facture.isPaye());
            System.out.println();
        }
    }

    public ClientPaiement(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idFacture");
        model.addColumn("date");
        model.addColumn("montant");
        model.addColumn("payé");

        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ipServeur = "0.0.0.0";
                int portServeur = 51000;
                try {
                    socket = new Socket(ipServeur,portServeur);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                String login1 = jTextFieldLogin.getText();
                String password = jTextFieldPassword.getText();
                System.out.println("login "+login1+" mdp "+password);
                try
                {

                    RequeteLogin requete = new RequeteLogin(login1,password);

                    oos.writeObject(requete);
                    System.out.println("est passé dans le login requete"+requete);
                    ReponseLogin reponse = (ReponseLogin) ois.readObject();
                    System.out.println("est passé dans le login après lecture");
                    if (reponse.isValide())
                    {
                        loginButton.setEnabled(false);
                        logoutButton.setEnabled(true);
                        dialogueMessage("connecté");
                        // jButtonCalcul.setEnabled(true);
                        // this.login = login;
                    }
                    else
                    {
                        //JOptionPane.showMessageDialog(this,"Erreur de login!","Erreur...",JOptionPane.ERROR_MESSAGE);
                        socket.close();

                    }
                }
                catch (IOException | ClassNotFoundException ex)
                {
                    //JOptionPane.showMessageDialog(this,"Problème de connexion!","Erreur...",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequeteLOGOUT requete = new RequeteLOGOUT(login);
                try {

                    System.out.println("la requete de logout :"+requete);
                    oos.writeObject(requete);
                    //Requete test = (Requete) ois.readObject();
                    //System.out.println("la requete de logout :"+test);


                    loginButton.setEnabled(true);
                    logoutButton.setEnabled(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //oos.close();
                //ois.close();
                // socket.close();

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
                    System.out.println("est passé dans le facture requete "+requete);
                    ReponseFacture reponse  = (ReponseFacture) ois.readObject();
                    System.out.println("est passé dans le facture après lecture");
                    for (Facture facture : reponse.getFacture()) {
                        Object[] rowData = {facture.getId(),facture.getDate(), facture.getMontant(), facture.isPaye()};
                        model.addRow(rowData);
                    }

                    //afficherFactures(reponse.getFacture());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maraicher en ligne");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ClientPaiement app = null;
            app = new ClientPaiement();

            frame.setSize(850, 250);  //onpeut mettre un listener
            //Nous demandons maintenant à notre objet de se positionner au centre
            frame.setLocationRelativeTo(null);
            frame.add(app.panel1);
            //frame.pack();
            frame.setVisible(true);
        });
    }
}
