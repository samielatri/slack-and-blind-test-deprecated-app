package vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends JFrame{
    private JButton back;
    private JPanel gameO;
    private JLabel titleGO;
    private JLabel finalscore;

    public void myUpdate(String score) {
        finalscore.setText(score);
    }
    public GameOver() {
        add(gameO);
        setSize(300,200);
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }

    public JLabel getTitleGO() {
        return titleGO;
    }

    public void setTitleGO(JLabel modi) {
        titleGO.setText(modi.getText());
    }
}
