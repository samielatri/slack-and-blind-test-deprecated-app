package controller;

import model.Model;
import view.LoginFormEvent;
import view.LoginListener;
import view.MainFrame;

public class Controller implements LoginListener {
    private MainFrame mainFrame;
    private Model model;

    public Controller(MainFrame mainFrame, Model model) {
        this.mainFrame = mainFrame;
        this.model = model;
    }

    @Override
    public void loginPerformed(LoginFormEvent event) {
        System.out.println("Login event received: " + event.getName() + "; " + event.getPassword());

    }


}