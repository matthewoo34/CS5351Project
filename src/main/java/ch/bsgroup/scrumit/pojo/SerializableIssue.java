package ch.bsgroup.scrumit.pojo;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.Project;

/**
 * Class Issue manages Issue 
 */
public class SerializableIssue {
	private int id;

	private int category;

	private String description;

	private int extraDuration;

	private Date creationDate;
	
	private int sprintBacklogID;
	
	private int projectID;

	private int personID;
	
	private int commencement;
	
	private int cost;

	public SerializableIssue(int id, int category, String description, int extraDuration,  
			Date creationDate,  int sprintBacklogID, int projectID, int personID, int commencement,
			int cost) {
		this.setId(id);
		this.setCategory(category);
		this.setDescription(description);
		this.setExtraDuration(extraDuration);
		this.setCreationDate(creationDate);
		this.setSprintBacklogID(sprintBacklogID);
		this.setProjectID(projectID);
		this.setPersonID(personID);
		this.setCommencement(commencement);
		this.setCost(cost);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getExtraDuration() {
		return extraDuration;
	}

	public void setExtraDuration(int extraDuration) {
		this.extraDuration = extraDuration;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public int getSprintBacklogID() {
		return sprintBacklogID;
	}

	public void setSprintBacklogID(int sprintBacklogID) {
		this.sprintBacklogID = sprintBacklogID;
	}
	
	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}
	
	public int getCommencement() {
		return commencement;
	}

	public void setCommencement(int commencement) {
		this.commencement = commencement;
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}