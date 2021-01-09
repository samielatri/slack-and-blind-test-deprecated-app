package application;

import controller.Controller;
import model.Model;
import view.MainFrame;

import javax.swing.SwingUtilities;

public class Application {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::runApp);
    }

    public static void runApp() {
        Model model = new Model();
        MainFrame mainFrame = new MainFrame(model);

        Controller controller = new Controller(mainFrame, model);

        mainFrame.setLoginListener(null); // !
    }

}