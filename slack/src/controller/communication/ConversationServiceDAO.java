package controller.communication;

import controller.AbstractServiceDAO;
import model.communication.Conversation;
import model.communication.Message;

import java.util.Date;
import java.util.Scanner;

public class ConversationServiceDAO extends AbstractServiceDAO {

    public Conversation createConversation(){
        Conversation conversation;
        String name;
        Scanner buffer;
        Date date = new Date();
        buffer = new Scanner(System.in);
        name = buffer.nextLine();
        conversation = new Conversation(name,date);
        return conversation;
    }

    /******************************************************

    public Message sendConversationMsg(Conversation conversation){
        Message message;
        String content;
        Scanner buffer;
        Date date = new Date();

        System.out.println("Enter the content of your message");
        buffer = new Scanner(System.in);
        content = buffer.next();
        message = new Message(connectedProfile,content);
        message.setCreatedAt(date);
        message.setIdConversation(conversation.getId());
        return DAOMessageDirect.insert(message);
    }
    public Message editConversationMsg(Message msg){
        Message message;
        String newContent;
        Scanner buffer;
        Date date = new Date();
        System.out.println("Enter the new content of your message");
        buffer = new Scanner(System.in);
        newContent = buffer.next();
        msg.setCreatedAt(date);

        msg.setContent(newContent);
        message = DAOMessageDirect.update(msg);
        System.out.println("your content has been changed successfully");
        return message;
    }




    **************************/






}
