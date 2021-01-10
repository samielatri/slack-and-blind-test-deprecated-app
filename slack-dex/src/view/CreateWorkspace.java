package view;

import controller.communication.WorkspaceServiceDAO;
import database.SQLWorkspaceDAO;
import model.SlackSystem;
import model.communication.Workspace;
//import view.WelcomePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class CreateWorkspace extends JFrame{
    private JTextField wkName;
    private JPanel createWSPage;
    private JButton CREATEButton;
    private SQLWorkspaceDAO ws= new SQLWorkspaceDAO();
    private Workspace wp;
    private SlackSystem system = new SlackSystem();
    private WorkspaceServiceDAO workspaceServiceDAO = new WorkspaceServiceDAO();

    public CreateWorkspace() throws SQLException {
        add(createWSPage);
        setSize(300,400);
        setTitle("Create your Workspace");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //creation du workspace TODO
        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameOfWorkspace=wkName.getText();
                try {
                    wp=workspaceServiceDAO.createWs(nameOfWorkspace);
                    if(wp==null){
                        JOptionPane.showMessageDialog(createWSPage,"This name already exist please enter another one","LEGO Slack warning",JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                JOptionPane.showMessageDialog(createWSPage,"Your workspace was successfully created");
                dispose();
                //new WelcomePage();
            }
        });
        setVisible(true);
    }

    //TODO: VERIFIER L'ENSEMBLE SI TOUT EST CORDA
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
