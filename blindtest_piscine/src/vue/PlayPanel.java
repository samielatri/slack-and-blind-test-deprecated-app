package vue;

import database.Questions;
import database.Reponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.Timer;

public class PlayPanel extends JFrame {
    private JPanel panel1;
    private JTextField answered;
    private JButton submitButton;
    private JLabel scorelab;
    private JLabel qField;
    private JLabel picsong;
    private JLabel countT;
    private JButton quitButton;

    String [] [] quizzQ= new String[10][6];
    String initquest="";
    String pathPic="";
    int numQuestion=0;
    int score=0;
    Questions q=new Questions();
    Reponse r= new Reponse();
    Timer timer;
    int second, minute;
    String ddSecond, ddMinute;
    DecimalFormat dFormat= new DecimalFormat("00");
    GameOver g=new GameOver();

    public PlayPanel() throws SQLException {
        second=50;
        add(panel1);
        setSize(500,400);
        setTitle("Play");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initData();
        update();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answerEnter= answered.getText();
                if(r.dbRep(quizzQ[numQuestion][0],answerEnter)){
                    score++;
                    System.out.println("correct");
                }else{
                    gameOver();
                }
                numQuestion++;
                timer.stop();
                update();

            }
        });
        setVisible(true);
        //bouton quitter
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel endGame= new JLabel("YOU QUIT THE GAME");
                g.setTitleGO(endGame);
                gameOver();
            }
        });
    }

    public void initData() throws SQLException {
        Questions q=new Questions();
        ArrayList<String> listQu= q.bddselect();
        for (int i=0; i<listQu.size();i++){
            quizzQ[i][0]=listQu.get(i);
        }
        initquest=quizzQ[numQuestion][0];
        pathPic=q.picQuestion(initquest);
        if(pathPic!=""){
            picsong.setIcon(new ImageIcon((getClass().getResource("../"+pathPic))));
        }
    }

    public void update(){
        countT.setText("");
        answered.setText("");
        picsong.setText("");
        //add score
        scorelab.setText(""+score);
        //add countdown timer
        timer=countdown();
        timer.restart();
        //add question
        initquest=quizzQ[numQuestion][0];
        if (initquest==null){
            JLabel endGame= new JLabel("FINISH !!");
            g.setTitleGO(endGame);
            gameOver();
        }
        qField.setText(initquest);
        picsong.setIcon(null);
        picsong.revalidate();
        //add album pic
        pathPic=q.picQuestion(initquest);
        if(pathPic!=""){
            picsong.setIcon(new ImageIcon((getClass().getResource("../"+pathPic))));
        }


    }


    public Timer countdown(){
        /*timer=new Timer(1000,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                second--;
                ddSecond = dFormat.format(second);
                //ddMinute= dFormat.format(minute);
                //countT.setText(ddMinute+":"+ddSecond);
                countT.setText("00 : "+ddSecond);

                if(second==-1){
                    second=00;
                    //minute--;
                    ddSecond = dFormat.format(second);
                    //ddMinute= dFormat.format(minute);
                    //countT.setText(ddMinute+":"+ddSecond);
                    timer.stop();
                    countT.setText("00 : "+ddSecond);
                }
            }
        });*/
        ActionListener act1= new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                second--;
                ddSecond = dFormat.format(second);
                //ddMinute= dFormat.format(minute);
                //countT.setText(ddMinute+":"+ddSecond);
                countT.setText("00 : "+ddSecond);

                if(second==-1){
                    second=00;
                    //minute--;
                    ddSecond = dFormat.format(second);
                    //ddMinute= dFormat.format(minute);
                    //countT.setText(ddMinute+":"+ddSecond);
                    timer.stop();
                    gameOver();
                }
            }
        };
        return new Timer(1000,act1);
    }


    public void gameOver(){
        String finalscore= scorelab.getText();
        g.myUpdate(finalscore);
        g.setVisible(true);
        dispose();
    }
}
