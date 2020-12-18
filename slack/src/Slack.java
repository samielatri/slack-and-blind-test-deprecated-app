import controller.KeybordInput;
import controller.SlackSystem;
import model.group.Workspace;

import java.util.Scanner;

public class Slack {
    public static void main(String[] args) {
        SlackSystem slackSystem = new SlackSystem();
        Workspace selectedWs;
        KeybordInput keybordInput = new KeybordInput();
        Scanner buff;
        int choice, globalChoice=0;
        int wsChoice,chChoice;
        do {
            if (slackSystem.getConnectedUser() == null) {
                System.out.println("Enter your choice :");

                System.out.println("1- Login");
                System.out.println("2- Sign up");
                System.out.println("0- Exit");

                choice = keybordInput.readInt("choix");
                switch(choice){
                    case 1:

                        slackSystem.connection(keybordInput.readString("your email adress"),
                                keybordInput.readString("your passwword")
                               );
                        break;
                    case 2:
                        break;
                    case 0:
                        globalChoice = 1;
                        break;
                    default:
                        break;
                }

            } else {

                System.out.println("Enter your choice :");

                System.out.println("1- Log out");
                System.out.println("2- Edit my account");//mot de passe ...
                System.out.println("3- Create a workspace");
                System.out.println("4- Join a new workspace");
                System.out.println("5- select workspace");
                System.out.println("6- Delete a workspace");
                System.out.println("7- Edit a workspace");
                System.out.println("0- Exit");


                wsChoice = keybordInput.readInt("choix");

                switch (wsChoice) {
                    case 1:
                        break;
                    case 2:
                        slackSystem.getConnectedUser().editAccount();
                        break;
                    case 3:
                        slackSystem.getConnectedUser().creatWs();
                        break;
                    case 4:
                        slackSystem.getConnectedUser().joinWorkSpace();
                        break;
                    case 5:
                        selectedWs = slackSystem.getConnectedUser().selectWorkspace();


                        System.out.println("1- Create a channel");
                        System.out.println("2- Edit a channel");
                        System.out.println("3- Delete a channel");
                        System.out.println("4- join a channel");
                        System.out.println("5- Add a collaborator");
                        System.out.println("6- Send a message to a collabotator");
                        System.out.println("7- Send a message to a group of collabotators");
                        System.out.println("8- Delete a collaborator");
                        System.out.println("9- Visit a collaborator's profile");
                        System.out.println("10- Leave a channel");
                        System.out.println("0- Exit workspace");
                        chChoice = keybordInput.readInt("choix");
                        switch (chChoice) {
                            case 1:
                                selectedWs.createCh(slackSystem.getConnectedUser());
                                break;
                            case 2:
                                selectedWs.editChannel(slackSystem.getConnectedUser());
                                break;
                            case 3:
                                selectedWs.deleteCh(slackSystem.getConnectedUser());
                                break;
                            case 4:
                                selectedWs.joinChannel();
                                System.out.println("1- Send a message in the channel");//4 channel
                                System.out.println("2- Read a message in the channel");
                                System.out.println("3- Edit a message in the channel");
                                System.out.println("4- Delete a message from the channel");
                                System.out.println("0- Exit the channel");
                                chChoice = keybordInput.readInt("choix");
                                switch (chChoice) {
                                    case 1:

                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        System.out.println("Please enter a valid choice");
                                        break;
                                }
                                break;
                            case 5:
                                //slackSystem.getConnectedUser().addCollaborator();
                                break;
                            case 6:
                                //send message to a collab

                                break;
                            case 7:
                                break;
                            case 8:
                                break;
                            case 9:
                                break;
                            case 10:
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Please enter a valid choice");
                                break;
                        }

                        break;
                    case 6:
                        slackSystem.getConnectedUser().deleteWs();
                        break;
                    case 7:
                        selectedWs = slackSystem.getConnectedUser().selectWorkspace();
                        slackSystem.getConnectedUser().editWorkspace(selectedWs);
                        break;
                    case 0:
                        System.out.println("see you later !");
                        globalChoice = 1;
                        break;
                    default:
                        System.out.println("Please enter a valid choice");
                        break;

                }
            }
        }while(globalChoice == 0);
    }

    //implementer selectWorkspace
}

