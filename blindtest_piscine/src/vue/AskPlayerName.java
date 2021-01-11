package vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskPlayerName {
    private JTextField namePlayer;
    private JPanel ask;
    private JButton OKButton;

    public AskPlayerName() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameP = namePlayer.getText();
                //add insert method for player TODO
            }
        });
    }
}
