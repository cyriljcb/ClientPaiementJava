import Controllers.Controller;
import Models.Model;
import View.ClientPaiement;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        ClientPaiement view = new ClientPaiement();
        Controller controller = new Controller(model, view);
        JFrame frame = new JFrame("Maraicher en ligne");
        frame.add(view.getPanel_principal());
        frame.pack();
        frame.setSize(850, 250);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(view);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        view.setMainWindow(frame);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.fermerApp();
                exit(0);
            }
        });
        frame.setVisible(true);
    }
}