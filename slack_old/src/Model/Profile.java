package Model;

import java.io.Serializable;


public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String currentStatus; // current status in a specific workspace
	private String completeName; // complete name of the user in a specific workspace
	private String shownName; // shown name in a specific workspace
	private String actualWorkPosition; // actual work position in a specific workspace
	private String phoneNumber; // phone number in a specific workspace
	private String timezone; // timezone in a specific workspace

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Profile [currentStatus=" + currentStatus + ", completeName=" + completeName + ", shownName=" + shownName
				+ ", actualWorkPosition=" + actualWorkPosition + ", phoneNumber=" + phoneNumber + ", timezone="
				+ timezone + "]";
	}

}
