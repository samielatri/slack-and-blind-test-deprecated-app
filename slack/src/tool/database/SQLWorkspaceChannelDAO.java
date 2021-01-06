package tool.database;

import model.group.WorkspaceChannel;

import java.sql.*;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public class SQLWorkspaceChannelDAO extends AbstractSQLDAO<WorkspaceChannel> {
	Connection conn = ConnectionBuilder.createConnection();
	Statement state = conn.createStatement();
	ResultSet res=null;

	public SQLWorkspaceChannelDAO() throws SQLException {
	}

	@Override
	protected WorkspaceChannel create(ResultSet rs) {
		return null;
	}

	@Override
	protected String getTableName() {
		return null;
	}

	@Override
	public WorkspaceChannel insert(WorkspaceChannel obj) { //add a workspace
		String wcDB="";
		try{
			res=state.executeQuery("SELECT nameWC FROM workspacechannel");
			while(res.next()){
				wcDB=res.getString("nameWC");
				if(obj.getName().equals(wcDB)){
					System.out.println("This workspace is already in this channel");
				}
			}
			String sql= "INSERT INTO workspacechannel (nameWK, nameWC) VALUES (?,?)";
			PreparedStatement pstate= conn.prepareStatement(sql);
			//pstate.setString(1,obj.g);
			pstate.setString(2,obj.getName());
			res=pstate.executeQuery();
			System.out.println("Channel created !");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return new WorkspaceChannel(obj.getName());
	}

	@Override
	public void delete(WorkspaceChannel obj) { //delete the channel
		try{
			String sql= "DELETE FROM workspacechannel WHERE nameWC= ?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,obj.getName());
			res=pstate.executeQuery();
			System.out.println("Channel deleted");
		}catch (SQLException e){
			e.printStackTrace();
		}

	}

	@Override
	public WorkspaceChannel update(WorkspaceChannel obj) {//update the channel
		try {
			String sql= "UPDATE workspacechannel SET nameWK = ? WHERE nameWK= ?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,obj.getName());
			//pstate.setString(2, obj.getName());
			res=pstate.executeQuery();
			System.out.println("Channel updated ! ");
		}catch (SQLException e){
			e.printStackTrace();
		}
		return new WorkspaceChannel(obj.getName());
	}

	@Override
	public WorkspaceChannel select(String key) { //return a workspace of the channel
		WorkspaceChannel wC=null;
		try{
			String sql= "SELECT nameWK FROM workspacechannel WHERE nameWC=?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,key);
			res=pstate.executeQuery();
			while (res.next()){
				wC=new WorkspaceChannel(res.getString("nameWK"));
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return wC;
	}
}
