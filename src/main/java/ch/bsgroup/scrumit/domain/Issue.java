package ch.bsgroup.scrumit.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Class Issue manages Issues
 *     Issue affect the Cost and the duration of a task
 */
@Entity
public class Issue {
	/**
	 * Unique Id of the Issue to identify it
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@NotNull
	private int category;

	@NotNull
	@Size(min = 1, max = 255)
	private String description;

	@NotNull
	private int extraDuration;

	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@NotNull
	private int sprintBacklogID;
	
	@NotNull
	private int projectID;
	
	@NotNull
	private int personID;
	
	@NotNull
	private int commencement;
	
	@NotNull
	private int cost;
		
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="task_id", referencedColumnName="id", updatable=false)
	private Task task;
	
	public Issue() {
		
	}

	public Issue(int category, String description, int extraDuration,  
			Date creationDate,  int sprintBacklogID, int projectID, int personID, int commencement, int cost) {
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
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}