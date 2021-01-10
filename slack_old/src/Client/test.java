package Client;

import java.util.ArrayList;

import Model.User;
import Model.Workspace;
import Utils.WorkplaceDao;

public class test {

	public static void main(String[] args) {
		User user = new User();
		user.setEmail("test@gmail");
		user.setPassword("1342453");
		user.getProfile().setCompleteName("Sami");
		user.getProfile().setShownName("samsam");
		user.getProfile().setActualWorkPosition("SinemWS");
		user.getProfile().setPhoneNumber("6969");
		user.getProfile().setTimezone("17/212");

		//Client r = new Client(null, null);
		//r.setConnection();

		User user1 = WorkplaceDao.getUserById(1);
		User user2 = WorkplaceDao.getUserById(2);
		User user3 = WorkplaceDao.getUserById(3);

		Workspace workspace = new Workspace("WSRadjNahi1");
		workspace.setCreatedBy(1);

		ArrayList<User> admins = workspace.getAdmins();
		admins.add(user1);
		workspace.setAdmins(admins);

		ArrayList<User> members = workspace.getMembers();
		members.add(user1);
		members.add(user2);
		members.add(user3);
		workspace.setAdmins(admins);

		WorkplaceDao.createWorkspace(workspace);

		ArrayList<Workspace> workspaces = WorkplaceDao.getWorkspace();
		
		System.out.println(workspaces.toString());
	}

}
