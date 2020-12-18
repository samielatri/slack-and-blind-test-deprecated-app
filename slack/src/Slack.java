import controller.*;

import java.util.Scanner;

public class Slack {
    public static void main(String[] args) {
        SlackSystem slackSystem = new SlackSystem();
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


                choice = keybordInput.readInt("choix");

                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("1- Create a channel");
                        System.out.println("2- Edit a channel");
                        System.out.println("3- Delete a channel");
                        System.out.println("4- join a channel");
                        System.out.println("5- Add a collaborator");
                        System.out.println("6- Send a message to a collabotator");
                        System.out.println("7- Send a message to a group of collabotators");
                        System.out.println("8- Delete a collaborator");
                        System.out.println("9- Visit a collaborator's profile");
                        System.out.println("0- Exit workspace");
                        wsChoice = keybordInput.readInt("choix");
                        switch (wsChoice) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                System.out.println("1- Send a message in the channel");//4 channel
                                System.out.println("2- Edit a message in the channel");
                                System.out.println("3- Delete a message from the channel");
                                System.out.println("0- Exit the channel");
                                chChoice = keybordInput.readInt("choix");
                                switch (chChoice) {
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                break;
                            case 9:
                                break;
                            case 0:
                                break;
                            default:
                                break;
                        }

                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 0:
                        globalChoice = 1;
                        break;
                    default:
                        break;

                }
            }
        }while(globalChoice == 0);
    }


}
