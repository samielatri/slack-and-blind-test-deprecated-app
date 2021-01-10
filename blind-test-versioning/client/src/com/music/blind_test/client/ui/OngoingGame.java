package com.music.blind_test.client.ui;

import com.music.blind_test.EndRoundData;
import com.music.blind_test.GameData;
import com.music.blind_test.Packet;
import com.music.blind_test.StartRoundData;
import com.music.blind_test.client.Client;
import com.music.blind_test.client.GameClient;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OngoingGame implements Client.PacketsListener {
    private static final int MAX_IMAGE_SIZE = 300;
    private final GameClient client;
    private final JFrame frame;
    private final DefaultListModel<String> playersModel = new DefaultListModel<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private JPanel rootPanel;
    private JButton leaveButton;
    private JLabel gameOwnerLabel;
    private JButton startGameButton;
    private JButton stopGameButton;
    private JList<String> playersList;
    private JPanel submissionPanel;
    private JLabel statusLabel;
    private JLabel imageLabel;
    private JTextField solutionField;
    private JButton submitButton;
    private GameData data;
    private int currentRoundId = -1;

    public OngoingGame(JFrame parent, GameClient client, String game) {
        this.client = client;
        frame = new JFrame(game + " - Music Blind Test");
        frame.setContentPane(rootPanel);
        frame.pack();

        gameOwnerLabel.setText(game);

        playersList.setModel(playersModel);

        try {
            data = client.gameData();
        } catch (GameClient.RequestException ex) {
            ex.printStackTrace();
            frame.setVisible(false);
            frame.dispose();
            return;
        }

        if (data.info.name.equals(client.username)) {
            if (data.playing) {
                startGameButton.setVisible(false);
                stopGameButton.setVisible(true);
            } else {
                startGameButton.setVisible(true);
                stopGameButton.setVisible(false);
            }
        } else {
            startGameButton.setVisible(false);
            stopGameButton.setVisible(false);
        }

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.setVisible(true);
            }
        });
        leaveButton.addActionListener(e -> {
            try {
                client.leaveGame();

                frame.setVisible(false);
                frame.dispose();
            } catch (GameClient.RequestException ex) {
                JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Failed leaving game!", JOptionPane.ERROR_MESSAGE);
            }
        });
        startGameButton.addActionListener(e -> {
            try {
                client.startGame();
                stopGameButton.setVisible(true);
                startGameButton.setVisible(false);
            } catch (GameClient.RequestException ex) {
                JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Failed starting game!", JOptionPane.ERROR_MESSAGE);
            }
        });
        stopGameButton.addActionListener(e -> {
            try {
                client.stopGame();
                stopGameButton.setVisible(false);
                startGameButton.setVisible(true);
            } catch (GameClient.RequestException ex) {
                JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Failed stopping game!", JOptionPane.ERROR_MESSAGE);
            }
        });
        submitButton.addActionListener(e -> {
            String solutionText = solutionField.getText();
            if (solutionText == null || solutionText.isEmpty() || currentRoundId == -1)
                return;

            submitSolution(solutionText);
            solutionField.setText("");
        });

        setPoints(data.points);
        statusLabel.setText("Wait for a new round to start...");
        submissionPanel.setVisible(false);
        imageLabel.setVisible(false);

        frame.setVisible(true);

        client.addPacketsListener(this);
    }

    public static BufferedImage scale(byte[] bytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        float ratio = (float) image.getHeight() / (float) image.getWidth();
        int dWidth, dHeight;
        if (image.getHeight() > image.getWidth()) {
            dHeight = MAX_IMAGE_SIZE;
            dWidth = (int) (dHeight / ratio);
        } else {
            dWidth = MAX_IMAGE_SIZE;
            dHeight = (int) (dWidth * ratio);
        }

        BufferedImage scaledImage = new BufferedImage(dWidth, dHeight, image.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, dWidth, dHeight, null);
        graphics2D.dispose();
        return scaledImage;
    }

    private void setPoints(Map<String, Integer> points) {
        playersModel.clear();
        playersModel.addAll(points.entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.toList()));
    }

    private void submitSolution(String solution) {
        if (currentRoundId == -1) return;

        try {
            client.submitRoundSolution(currentRoundId, solution);
        } catch (GameClient.RequestException ex) {
            ex.printStackTrace();
        }
    }

    private void loadEntry(byte[] entry, int delayMs) {
        submissionPanel.setVisible(false);

        switch (data.mode) {
            case SONG:
                statusLabel.setText("Song will start playing in " + (delayMs / 1000) + " seconds...");
                imageLabel.setVisible(false);

                Clip clip;
                try {
                    clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(entry)));
                    clip.addLineListener(new LineListener() {
                        @Override
                        public void update(LineEvent event) {
                            if (event.getType() == LineEvent.Type.STOP) {
                                clip.removeLineListener(this);
                                clip.close();
                            }
                        }
                    });
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                    ex.printStackTrace();
                    break;
                }

                scheduler.schedule(() -> {
                    statusLabel.setText("Type the solution and submit!!");
                    submissionPanel.setVisible(true);

                    clip.start();
                }, delayMs, TimeUnit.MILLISECONDS);
                break;
            case IMAGE:
                statusLabel.setText("Image will be shown in " + (delayMs / 1000) + " seconds...");

                try {
                    imageLabel.setIcon(new ImageIcon(scale(entry)));
                    imageLabel.setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                scheduler.schedule(() -> {
                    statusLabel.setText("Type the solution and submit!!");
                    imageLabel.setVisible(true);
                    submissionPanel.setVisible(true);
                }, delayMs, TimeUnit.MILLISECONDS);
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(data.mode));
        }
    }

    private void showWinner(String winner) {
        imageLabel.setVisible(false);
        submissionPanel.setVisible(false);

        if (winner == null)
            statusLabel.setText("Nobody won! A new round will start soon...");
        else
            statusLabel.setText(winner + " won! A new round will start soon...");
    }

    @Override
    public void onPacket(Packet.Type type, byte[] body) throws IOException, ClassNotFoundException {
        switch (type) {
            case EVENT_START_ROUND:
                StartRoundData startRoundData = StartRoundData.parse(body);
                currentRoundId = startRoundData.roundId;
                loadEntry(startRoundData.entry, startRoundData.delayMs);
                break;
            case EVENT_END_ROUND:
                EndRoundData endRoundData = EndRoundData.parse(body);
                setPoints(endRoundData.points);
                showWinner(endRoundData.roundWinner);
                break;
            case EVENT_STOP:
                startGameButton.setVisible(data.info.name.equals(client.username));
                stopGameButton.setVisible(false);
                statusLabel.setText("Game stopped.");
                imageLabel.setVisible(false);
                submissionPanel.setVisible(false);
                break;
        }
    }
}
