package Model;

import java.io.Serializable;

import javafx.scene.image.Image;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String email; // email address of the user
	private String password; // password of the user
	private String currentStatus; // current status in a specific workspace
	private String completeName; // complete name of the user in a specific workspace
	private String shownName; // shown name in a specific workspace
	private String actualWorkPosition; // actual work position in a specific workspace
	private String phoneNumber; // phone number in a specific workspace
	private String timezone; // timezone in a specific workspace
	private Image profilePicture; // profile picture in a specific workspace

	public User() {
		email = "";
		password = "";
		currentStatus = "";
		completeName = "";
		shownName = "";
		actualWorkPosition = "";
		phoneNumber = "";
		timezone = "";
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

	public String getCurrentStatus() {
		return currentStatus;
	}

	public String getCompleteName() {
		return completeName;
	}

	public String getShownName() {
		return shownName;
	}

	public String getActualWorkPosition() {
		return actualWorkPosition;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getTimezone() {
		return timezone;
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

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public void setShownName(String shownName) {
		this.shownName = shownName;
	}

	public void setActualWorkPosition(String actualWorkPosition) {
		this.actualWorkPosition = actualWorkPosition;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Image getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Image profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", currentStatus=" + currentStatus
				+ ", completeName=" + completeName + ", shownName=" + shownName + ", actualWorkPosition="
				+ actualWorkPosition + ", phoneNumber=" + phoneNumber + ", timezone=" + timezone + ", profilePicture="
				+ profilePicture + "]";
	}

}
