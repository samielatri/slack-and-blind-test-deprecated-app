package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.user.UserServiceDAO;

public class CreateWorkspace extends JFrame{
    private JTextField wkName;
    private JPanel createWSPage;
    private JButton CREATEButton;
    private UserServiceDAO u=new UserServiceDAO();

    public CreateWorkspace() {
        add(createWSPage);
        setSize(300,400);
        setTitle("Create your Workspace");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameOfWorkspace=wkName.getText();
                u.createWs(nameOfWorkspace);// TODO: verifier la methode pour creer workspace par l'user
                dispose();
                new WelcomePage();
            }
        });
        setVisible(true);
    }
}
