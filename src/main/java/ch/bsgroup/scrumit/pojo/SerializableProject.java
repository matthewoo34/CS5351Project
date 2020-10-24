package ch.bsgroup.scrumit.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Project manages Projects
 * 		A Project consists of Sprints and Persons which are involved with a role
 * 		A Project is defined by the unique Id. It also has a Name and a Description, also a CreationDate
 */
public class SerializableProject {
	/**
	 * Unique Id of the Project to identify it
	 */
	private int id;

	/**
	 * Project has a Name
	 */
	private String name;

	/**
	 * Project has a Description
	 */
	private String description;

	/**
	 * Project has a CreationDate
	 */
	private Date creationDate;

	/**
	 * Project has a list of Persons
	 */
	private Set<SerializablePerson> persons = new HashSet<SerializablePerson>();
	
	/**
	 * Project has a list of Sprints
	 */
	private Set<SerializableSprint> sprints = new HashSet<SerializableSprint>();

	private int duration;
	
	private int cost;
	
	private Date startDate;
	
	private int noOfWeekPerSprint;
	
	/**
	 * @desc Constructor
	 */
	public SerializableProject(int id, String name, int duration, int cost/*, String description, Date creationDate, Set<SerializablePerson> persons, Set<SerializableSprint> sprints*/) {
		this.setId(id);
		this.setName(name);
		this.setDuration(duration);
		this.setCost(cost);
		
		//this.setDescription(description);
		//this.setCreationDate(creationDate);
		//this.setPersons(persons);
		//this.setSprints(sprints);
	}

	public SerializableProject(int id, String name, String description, Date creationDate,int duration, int cost/*, Set<SerializablePerson> persons, Set<SerializableSprint> sprints*/,
			Date startDate,int noOfWeekPerSprint) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setCreationDate(creationDate);
		this.setDuration(duration);
		this.setCost(cost);
		
		//this.setPersons(persons);
		//this.setSprints(sprints);
		this.setStartDate(startDate);
		this.setNoOfWeekPerSprint(noOfWeekPerSprint);
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the sprints
	 */
	public Set<SerializableSprint> getSprints() {
		return sprints;
	}

	/**
	 * @param sprints the sprints to set
	 */
	public void setSprints(Set<SerializableSprint> sprints) {
		this.sprints = sprints;
	}

	/**
	 * @return the persons
	 */
	public Set<SerializablePerson> getPersons() {
		return persons;
	}

	/**
	 * @param persons the persons to set
	 */
	public void setPersons(Set<SerializablePerson> persons) {
		this.persons = persons;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public int getNoOfWeekPerSprint() {
		return noOfWeekPerSprint;
	}
	
	public void setNoOfWeekPerSprint(int noOfWeekPerSprint) {
		this.noOfWeekPerSprint = noOfWeekPerSprint;
	}
}