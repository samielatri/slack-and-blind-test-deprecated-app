package test.client;

import old.Client1;

import javax.swing.JFrame;

/**
 *
 */
public class ClientTest {
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        Client1 charlie;
        charlie = new Client1("127.0.0.1"); // localhost for now
        charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        charlie.startRunning();
    }
}
