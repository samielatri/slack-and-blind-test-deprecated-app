package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileUser extends JFrame {
    private JPanel profileUser;
    private JButton OKButton;
    private JLabel nomUser;
    private JLabel prenomUser;

    public ProfileUser() {
        add(profileUser);
        setSize(300,400);
        setTitle("Profil User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //TODO: ajouter fonction qui affiche les info de l'user
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
}
