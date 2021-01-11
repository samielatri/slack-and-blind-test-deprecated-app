package view;

import controller.user.ProfileServiceDAO;
import controller.user.UserService;
import model.SlackSystem;
import model.user.Profile;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class ProfileCreation extends JFrame {
    private JPanel createProfile;
    private JTextField completeName;
    private JTextField phoneNumber;
    private JTextField username;
    private JButton CREATEButton;
    private JTextField acp;
    private JButton createPButton;

    public ProfileCreation(SlackSystem slackSystem) throws SQLException {
        add(createProfile);
        setSize(600,600);
        setTitle("Create your profile");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        ProfileServiceDAO profileServiceDAO = new ProfileServiceDAO();

        setVisible(true);
        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Profile profile;
                String usernameE= username.getText();
                String completeNameE= completeName.getText();
                String phoneNumberE = phoneNumber.getText();
                String actualPositionWork= acp.getText();
                dispose();
                profile= profileServiceDAO.createProfile(slackSystem.getCurrentConnectedUser().getId(),slackSystem.getCurrentConnectedWorkspace().getId());
                profile.setUsername(usernameE);
                profile.setCompleteName(completeNameE);
                profile.setPhoneNumber(phoneNumberE);
                profile.setActualWorkPosition(actualPositionWork);
                try {
                    new ChannelList(slackSystem);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        setVisible(true);
    }
    //TODO: FAIRE UNE GROSSE VERIFICATION
}
