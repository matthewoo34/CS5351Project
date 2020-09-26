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
import ch.bsgroup.scrumit.domain.UserStory;

/**
 * Class Issue manages Issue 
 */
public class SerializableIssue {
	private int id;

	private int category;

	private String description;

	private int extraDuration;

	private Date creationDate;
	
	private UserStory userStory;
	
	private Project project;

	private Person person;

	public SerializableIssue(int category, String description, int extraDuration,  
			Date creationDate,  UserStory userStory, Project project, Person person) {
		this.setCategory(category);
		this.setDescription(description);
		this.setExtraDuration(extraDuration);
		this.setCreationDate(creationDate);
		this.setUserStory(userStory);
		this.setProject(project);
		this.setPerson(person);
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
	
	public UserStory getUserStory() {
		return userStory;
	}

	public void setUserStory(UserStory userStory) {
		this.userStory = userStory;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}