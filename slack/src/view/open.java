package view;

import javax.swing.*;

public class open {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*HomePage h=new HomePage();
                h.setVisible(true);
                SignUpPage su=new SignUpPage();
                su.setVisible(true);*/
                new HomePage();

            }
        });

    }
}
