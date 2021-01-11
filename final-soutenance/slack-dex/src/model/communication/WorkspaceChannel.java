package model.communication;

import model.HasId;
import model.user.Profile;
import tool.ListManipulator;

import java.util.ArrayList;

public class WorkspaceChannel implements HasId {

    /* attributes */

    // id
    private String name; // name of the workspace channel

    
    // belongs_to relation type
    private String WorkspaceId; // the id of the workspace that the WorkspaceChannel belongs to

    // characteristics
    private boolean isPrivate; // true if private, if not (public)

    /* constructors */
    public WorkspaceChannel(String name) {
        this.name = name;
        
    }

    /* accessors */
    @Override
    public String getId() {
        return name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkspaceId() {
        return WorkspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        WorkspaceId = workspaceId;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }



    //@Override
    /*public String toString() {
        return "WorkspaceChannel{" +
                "name='" + name + '\'' +
                ", conversation=" + conversation +
                ", members=" + members +
                ", admins=" + admins +
                ", WorkspaceId='" + WorkspaceId + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkspaceChannel that = (WorkspaceChannel) o;

        return name.equals(that.name);
    }*/

}
