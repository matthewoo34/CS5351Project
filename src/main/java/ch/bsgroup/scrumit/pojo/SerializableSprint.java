package ch.bsgroup.scrumit.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Sprint manages Sprint within a specific Project
 * 		Every Sprint is defined by a unique Id and has a Slogan
 * 		The StartDate and EndDate is the time frame
 */
public class SerializableSprint {
	/**
	 * Unique Id of the Sprint to identify it
	 */
	private int id;

	/**
	 * Every Sprint has a Slogan
	 */
	private String slogan;

	/**
	 * Sprint is joined with a Project
	 */
	private SerializableProject project;

	/**
	 * Sprint has SprintBacklog
	 */
	private Set<SerializableSprintBacklog> sprintBacklog = new HashSet<SerializableSprintBacklog>();

	private int endHour;
	
	/**
	 * @desc Constructor
	 */
	public SerializableSprint(int id, String slogan, int endHour/*, SerializableProject project, Set<SerializableUserStory> userStories*/) {
		this.setId(id);
		this.setSlogan(slogan);
		this.setEndHour(endHour);
		//this.setProject(project);
		//this.setUserStories(userStories);
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
	 * @return the slogan
	 */
	public String getSlogan() {
		return slogan;
	}

	/**
	 * @param slogan the slogan to set
	 */
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	/**
	 * @return the project
	 */
	public SerializableProject getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(SerializableProject project) {
		this.project = project;
	}

	/**
	 * @return the SprintBacklog
	 */
	public Set<SerializableSprintBacklog> getSprintBacklog() {
		return sprintBacklog;
	}

	/**
	 * @param SprintBacklog the SprintBacklog to set
	 */
	public void setSprintBacklog(Set<SerializableSprintBacklog> sprintBacklog) {
		this.sprintBacklog = sprintBacklog;
	}
	
	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
}