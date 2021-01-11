package Client;

import java.util.ArrayList;

import Model.User;
import Model.Workspace;
import Utils.WorkplaceDao;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		User user = new User();
//		user.setEmail("xxx@gmail");
//		user.setPassword("1342453");
//		user.setCompleteName("Axxxxw ");
//		user.setShownName("Saxxxxxxee");
//		user.setActualWorkPosition("XxXXxxC");
//		user.setPhoneNumber("5868686");
//		user.setTimezone("17/2010");
//
//		Client r = new Client(user);
//		r.setConnection();
		
		
//		User user1 = WorkplaceDao.getUserById(1);
//		User user2 = WorkplaceDao.getUserById(2);
//		User user3 = WorkplaceDao.getUserById(3);
//
//		
//		Workspace workspace = new Workspace("SAmAe1");
//		workspace.setCreatedBy(1);
//		
//		
//		ArrayList<User> admins = workspace.getAdmins();
//		admins.add(user1);
//		workspace.setAdmins(admins);
//		
//		ArrayList<User> members = workspace.getMembers();
//		members.add(user1);
//		members.add(user2);
//		members.add(user3);
//		workspace.setAdmins(admins);
//		
//		
//		WorkplaceDao.createWorkspace(workspace);
		
		
		
		ArrayList<Workspace> workspace = WorkplaceDao.getWorkspace();
		
		System.out.println(workspace.toString());
		
	}

}
