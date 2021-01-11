package com.music.blind_test.client.ui;

import com.music.blind_test.CreateGameData;
import com.music.blind_test.GameData;
import com.music.blind_test.client.GameClient;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class CreateGame extends JDialog {
    private JPanel rootPanel;
    private JButton buttonOk;
    private JButton buttonCancel;
    private JCheckBox practiceEnabled;
    private JList<String> categoriesList;
    private JRadioButton imageModeRadioButton;
    private JRadioButton songModeRadioButton;
    private JFormattedTextField maxRoundsField;

    public CreateGame(GameClient client) {
        setContentPane(rootPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        buttonCancel.addActionListener(e -> dispose());

        imageModeRadioButton.addActionListener(e -> songModeRadioButton.setSelected(false));
        songModeRadioButton.addActionListener(e -> imageModeRadioButton.setSelected(false));

        DefaultListModel<String> categoriesModel = new DefaultListModel<>();
        categoriesList.setModel(categoriesModel);

        try {
            for (String category : client.getCategories())
                categoriesModel.addElement(category);
        } catch (GameClient.RequestException ex) {
            ex.printStackTrace();
        }
    }

    public void setOkListener(Consumer<CreateGameData> listener) {
        buttonOk.addActionListener(e -> {
            GameData.Mode mode;
            if (imageModeRadioButton.isSelected()) mode = GameData.Mode.IMAGE;
            else mode = GameData.Mode.SONG;

            List<String> categories = categoriesList.getSelectedValuesList();
            if (categories.isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Select at least one category.", "Invalid options", JOptionPane.WARNING_MESSAGE);
                return;
            }

            listener.accept(new CreateGameData(practiceEnabled.isSelected(), mode, categories, Integer.parseInt(maxRoundsField.getText())));
            dispose();
        });
    }

    private void createUIComponents() {
        maxRoundsField = new JFormattedTextField(NumberFormat.getNumberInstance());
    }
}
