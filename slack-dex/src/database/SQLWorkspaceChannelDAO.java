package database;

import model.SlackSystem;
import model.communication.WorkspaceChannel;
import model.user.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public class SQLWorkspaceChannelDAO extends AbstractSQLDAO<WorkspaceChannel> {
	Connection conn = DBConnection.createConnection();
	Statement state = conn.createStatement();
	ResultSet res=null;
	private SlackSystem system=new SlackSystem();

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
			res=state.executeQuery("SELECT nameWC FROM workspaceChannel");
			while(res.next()){
				wcDB=res.getString("nameWC");
				if(obj.getName().equals(wcDB)){
					System.out.println("This channel is already in this workspace");
				}
			}
			String sql= "INSERT INTO workspaceChannel (nameWK, nameWC) VALUES (?,?)";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,system.getCurrentConnectedWorkspace().getName());
			pstate.setString(2,obj.getName());
			res=pstate.executeQuery();
			System.out.println("Channel created !");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public void delete(WorkspaceChannel obj) { //delete the channel
		try{
			String sql= "DELETE FROM workspaceChannel WHERE nameWC= ?";
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
			String sql= "UPDATE workspaceChannel SET nameWC = ? WHERE nameWK= ?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,obj.getName());
			pstate.setString(2, system.getCurrentConnectedWorkspace().getName());
			res=pstate.executeQuery();
			System.out.println("Channel updated ! ");
		}catch (SQLException e){
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public WorkspaceChannel select(String key) { //return a workspace of the channel
		WorkspaceChannel wC=null;
		try{
			String sql= "SELECT nameWC FROM workspaceChannel WHERE nameWK=?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,key);
			res=pstate.executeQuery();
			while (res.next()){
				wC=new WorkspaceChannel(res.getString("nameWC"),system.getCurrentConnectedProfile());
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return wC;
	}

	/*public ArrayList<WorkspaceChannel> selectAll(){
		ArrayList<WorkspaceChannel> listWC=new ArrayList<>();
		WorkspaceChannel wC=null;
		try {
			String sql="SELECT * FROM workspaceChannel";
			res=state.executeQuery(sql);
			while (res.next()){
				wC=new WorkspaceChannel(res.getString("nameWC"),system.getCurrentConnectedProfile());
				listWC.add(wC);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return listWC;
	}*/

}
