package view;

import controller.user.ProfileServiceDAO;
import database.SQLProfileDAO;
import model.SlackSystem;
import model.user.Profile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileUser extends JFrame {
    private JPanel profileUser;
    private JButton OKButton;
    private JLabel nomUser;
    private ProfileServiceDAO ps = new ProfileServiceDAO();
    private ArrayList<Profile> plist;
    private SlackSystem system = new SlackSystem();
    private SQLProfileDAO sp= new SQLProfileDAO();
    private ChatPage ch = new ChatPage(system.getCurrentConnectedWorkspaceChannel().getName());
    private Profile vp=new Profile(system.getCurrentConnectedWorkspace().getId(),system.getCurrentConnectedUser().getId());

    public ProfileUser() throws SQLException {
        add(profileUser);
        setSize(300,400);
        setTitle("Profil User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nomUser.setText(null);

        //affiche les info du profile TODO
        plist = (ArrayList<Profile>) sp.selectAll();
        for(int i = 0; i<plist.size(); i++){
            if (plist.get(i).getUsername().equals(ch.getProfileName())){
                vp=ps.visitProfile(plist.get(i).getId());
                nomUser.setText(""+vp.getCompleteName());
            }
            else {
                JOptionPane.showMessageDialog(profileUser,"Profile no defined","LEGO Slack warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
