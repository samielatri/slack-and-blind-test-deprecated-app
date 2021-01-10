package database;

import model.SlackSystem;
import model.communication.Workspace;

import java.sql.*;



public class SQLWorkspaceDAO extends AbstractSQLDAO<Workspace> {
	Connection conn = DBConnection.createConnection();
	Statement state = conn.createStatement();
	ResultSet res=null;
	private SlackSystem system=new SlackSystem();

	public SQLWorkspaceDAO() throws SQLException {
	}


	@Override
	public Workspace insert(Workspace obj) {//create a workspace for the profile in the database
		String workDB="";
		try{
			res=state.executeQuery("SELECT * FROM workspace");
			while(res.next()){
				workDB=res.getString("nameWK");
				if(obj.getName().equals(workDB)){
					System.out.println("This name of workspace is already taken");
				}
			}
			String sql= "INSERT INTO workspace (idProfile, nameWK) VALUES (?,?)";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,system.getCurrentConnectedProfile().getId());
			pstate.setString(2,obj.getName());
			res=pstate.executeQuery();
			System.out.println("Workspace successfully created !");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public void delete(Workspace obj) { //delete the workspace
		try{
			String sql= "DELETE FROM workspace WHERE nameWK= ?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,obj.getName());
			res=pstate.executeQuery();
			System.out.println("Workspace deleted successfully");
		}catch (SQLException e){
			e.printStackTrace();
		}

	}

	//a voir
	@Override
	public Workspace update(Workspace obj) {//update the name of the workspace
		try {
			String sql= "UPDATE workspace SET nameWK = ? WHERE nameWK= ?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,obj.getName());
			pstate.setString(2, obj.getName());
			res=pstate.executeQuery();
			System.out.println("Workspace updated ! ");
		}catch (SQLException e){
			e.printStackTrace();
		}
		return obj;
	}


	@Override
	public Workspace select(String key) { //return a workspace
		Workspace w=null;
		try{
			String sql= "SELECT * FROM workspace WHERE nameWK=?";
			PreparedStatement pstate= conn.prepareStatement(sql);
			pstate.setString(1,key);
			res=pstate.executeQuery();
			while (res.next()){
				w=new Workspace(res.getString("nameWK"),system.getCurrentConnectedProfile());
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return w;
	}

	/*public ArrayList<Workspace> selectAll(){
		ArrayList<Workspace> listWorkspace=new ArrayList<>();
		Workspace w=null;
		try {
			String sql="SELECT * FROM workspace";
			res=state.executeQuery(sql);
			while (res.next()){
				w=new Workspace(res.getString("nameWK"),system.getCurrentConnectedProfile());
				listWorkspace.add(w);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return listWorkspace;
	}*/

	@Override
	protected Workspace create(ResultSet rs) throws SQLException {
		return new Workspace(rs.getString("nameWk"),system.getCurrentConnectedProfile());
	}

	@Override
	protected String getTableName() {
		return "workspace";
	}
}
