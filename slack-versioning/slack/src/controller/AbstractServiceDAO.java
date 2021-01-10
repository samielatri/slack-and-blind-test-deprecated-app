package controller;

import database.DAO;
import database.DAOFactory;
import model.SlackSystem;
import model.communication.Message;
import model.communication.Workspace;
import model.communication.WorkspaceChannel;
import model.user.Profile;
import model.user.User;

import java.sql.SQLException;

public abstract class AbstractServiceDAO {

    protected static SlackSystem slackSystem = new SlackSystem();

    protected static DAO<User> DAOUser;

    static {
        try {
            DAOUser = DAOFactory.user();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected static DAO<WorkspaceChannel> DAOChannel;

    static {
        try {
            DAOChannel = DAOFactory.workspaceChannel();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected static DAO<Workspace> DAOWorkspace;

    static {
        try {
            DAOWorkspace = DAOFactory.workspace();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected static DAO<Profile> DAOProfile;

    static {
        try {
            DAOProfile = DAOFactory.profile();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected static DAO<Message> DAOMessageDirect;

    static {
        try {
            DAOMessageDirect = DAOFactory.messageDirect();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected static DAO<Message> DAOMessageChannel;

    static {
        try {
            DAOMessageChannel = DAOFactory.messageChannel();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    private static DAO<User> getDAOUser() {
        return DAOUser;
    }

    private static void setDAOUser(DAO<User> DAOUser) {
        AbstractServiceDAO.DAOUser = DAOUser;
    }

    private static DAO<WorkspaceChannel> getDAOChannel() {
        return DAOChannel;
    }

    private static void setDAOChannel(DAO<WorkspaceChannel> DAOChannel) {
        AbstractServiceDAO.DAOChannel = DAOChannel;
    }

    private static DAO<Workspace> getDAOWorkspace() {
        return DAOWorkspace;
    }

    private static void setDAOWorkspace(DAO<Workspace> DAOWorkspace) {
        AbstractServiceDAO.DAOWorkspace = DAOWorkspace;
    }

    private static DAO<Profile> getDAOProfile() {
        return DAOProfile;
    }

    private static void setDAOProfile(DAO<Profile> DAOProfile) {
        AbstractServiceDAO.DAOProfile = DAOProfile;
    }

    private static DAO<Message> getDAOMessageDirect() {
        return DAOMessageDirect;
    }

    private static void setDAOMessageDirect(DAO<Message> DAOMessageDirect) {
        AbstractServiceDAO.DAOMessageDirect = DAOMessageDirect;
    }

    private static DAO<Message> getDAOMessageChannel() {
        return DAOMessageChannel;
    }

    private static void setDAOMessageChannel(DAO<Message> DAOMessageChannel) {
        AbstractServiceDAO.DAOMessageChannel = DAOMessageChannel;
    }
}
