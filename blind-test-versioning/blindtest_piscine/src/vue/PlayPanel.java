package vue;

import database.Questions;
import database.Reponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
//import java.util.Timer;
import javax.swing.Timer;

public class PlayPanel extends JFrame {
    private JPanel panel1;
    private JTextField answered;
    private JButton submitButton;
    private JLabel scorelab;
    private JLabel qField;
    private JLabel picsong;
    private JLabel countT;

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
    public PlayPanel() throws SQLException {
        second=50;
        add(panel1);
        setSize(500,400);
        setTitle("Play");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initData();
        update();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answerEnter= answered.getText();
                if(r.dbRep(quizzQ[numQuestion][0],answerEnter)){
                    System.out.println("je verifie");
                    score++;
                    System.out.println("correct");
                }else{
                    gameOver();
                    System.out.println("problem...");//ajouter box game over
                    /*else{
                    wrong.setIcon(new ImageIcon(getClass().getResource("../Webp.net-resizeimage.png")));
                }*/
                }
                numQuestion++;

                update();

            }
        });
        setVisible(true);
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
            System.out.println("une image");
            picsong.setIcon(new ImageIcon((getClass().getResource("../"+pathPic))));
        }
        timer=countdown();
        timer.start();
    }

    public void update(){
        countT.setText("");
        answered.setText("");
        picsong.setText("");
        //add question
        initquest=quizzQ[numQuestion][0];
        qField.setText(initquest);
        picsong.setIcon(null);
        picsong.revalidate();
        //add album pic
        pathPic=q.picQuestion(initquest);
        System.out.println(pathPic);
        //picsong.setIcon(new ImageIcon("../"+pathPic));
        System.out.println("image suiv");
        if(pathPic!=""){
            System.out.println("image suiv");
            //picsong.setIcon(new ImageIcon("../"+pathPic));
            picsong.setIcon(new ImageIcon((getClass().getResource("../"+pathPic))));
        }
        //add score
        scorelab.setText(""+score);

        //add countdown timer
        timer=countdown();
        timer.stop();
        System.out.println("timer updated");
        //timer.start();
        if (timer.isRunning()){
            timer.stop();
            System.out.println("timer stop");
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
        GameOver g=new GameOver();
        String finalscore= scorelab.getText();
        g.myUpdate(finalscore);
        g.setVisible(true);
        dispose();
    }
}
