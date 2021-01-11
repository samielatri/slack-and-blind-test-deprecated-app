package Model;

import java.io.Serializable;

import javafx.scene.image.Image;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String email; // email address of the user
	private String password; // password of the user
	private Profile profile;

	public User() {
		email = "";
		password = "";
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", profile=" + profile + "]";
	}

}
