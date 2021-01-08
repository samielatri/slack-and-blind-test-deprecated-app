package service;

import database.DAO;
import database.DAOFactory;
import model.communication.Message;
import model.communication.Workspace;
import model.communication.WorkspaceChannel;
import model.user.Profile;
import model.user.User;

import java.sql.SQLException;

public abstract class AbstractServiceDAO {

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

    protected static User currentConnectedUser;
    protected static Profile currentConnectedProfile;
    protected static Workspace currentConnectedWorkspace;
    protected static WorkspaceChannel currentConnectedWorkspaceChannel;

}
