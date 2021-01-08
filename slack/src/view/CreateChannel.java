package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateChannel extends JFrame {
    private JPanel createCH;
    private JTextField nameCH;
    private JButton CREATEchButton;

    public CreateChannel() {
        add(createCH);
        setSize(300,400);
        setTitle("Create your Channel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        CREATEchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: ajouter methode qui creer channel par l'user
            }
        });
        setVisible(true);
    }
}
