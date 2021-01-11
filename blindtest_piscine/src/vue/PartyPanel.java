package vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PartyPanel extends JFrame{
    private JPanel choice;
    private JPanel choiceVue;
    private JButton joinAPartyButton;
    private JButton newPartyButton;

    public PartyPanel() {
        add(choice);
        setSize(500,400);
        setTitle("Create Or Join");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
        joinAPartyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JoinParty();
            }
        });
        newPartyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateParty();
            }
        });
    }
}
