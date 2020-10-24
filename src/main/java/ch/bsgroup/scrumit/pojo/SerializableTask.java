package ch.bsgroup.scrumit.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Task manages Tasks
 * 		Every Task is defined by a unique Id
 * 		It also has a Description, a xCoord/YCoord pair, Status, Duration, CreationDate
 */
public class SerializableTask {
	/**
	 * Unique Id of the UserStory to identify it
	 */
	private int id;

	/**
	 * Task has a Description
	 */
	private String description;

	/**
	 * Task has a X coordinate
	 */
	private int xCoord;

	/**
	 * Task has a Y coordinate
	 */
	private int yCoord;

	/**
	 * Task has a Status
	 */
	private int status;

	/**
	 * Task has a Duration
	 */
	private int duration;

	/**
	 * Project has a CreationDate
	 */
	private Date creationDate;

	/**
	 * Task is part of a UserStory
	 */
	private SerializableUserStory userStory;
	
	private int commencement;
	
	private int position;
	
	private String personName;
	
	private Date assignDate;

	/**
	 * @desc Constructor
	 */
	public SerializableTask(int id, String description, int xCoord, int yCoord, int status, int duration, 
			Date creationDate, int commencement, int position/*, SerializableUserStory userStory*/,String personName,Date assignDate) {
		this.setId(id);
		this.setDescription(description);
		this.setxCoord(xCoord);
		this.setyCoord(yCoord);
		this.setStatus(status);
		this.setDuration(duration);
		this.setCreationDate(creationDate);
		this.setCommencement(commencement);
		this.setPosition(position);
		//this.setUserStory(userStory);
		this.setPersonName(personName);
		this.setAssignDate(assignDate);
	}

	public SerializableTask(int id, String description, int xCoord, int yCoord, int status, Date creationDate, 
			int commencement, int position) {
		this.setId(id);
		this.setDescription(description);
		this.setxCoord(xCoord);
		this.setyCoord(yCoord);
		this.setStatus(status);
		this.setCreationDate(creationDate);
		this.setCommencement(commencement);
		this.setPosition(position);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public int getyCoord() {
		return yCoord;
	}

	/**
	 * @param yCoord the yCoord to set
	 */
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the userStory
	 */
	public SerializableUserStory getUserStory() {
		return userStory;
	}

	/**
	 * @param userStory the userStory to set
	 */
	public void setUserStory(SerializableUserStory userStory) {
		this.userStory = userStory;
	}
	
	public int getCommencement() {
		return commencement;
	}

	public void setCommencement(int commencement) {
		this.commencement = commencement;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public Date getAssignDate() {
		return assignDate;
	}
	
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
}