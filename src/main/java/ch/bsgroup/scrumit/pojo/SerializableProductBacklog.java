package ch.bsgroup.scrumit.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class SerializableProductBacklog {

	private int id;

	private int estimatedDuration;
	
	private int priority;
	
	private int projectId;
	
	private String name;
	
	private String description;

	private Date creationDate;

	
	public SerializableProductBacklog(int id, int estimatedDuration, int priority, int projectId,
			String name, String description, Date creationDate) {
		this.setId(id);
		this.setEstimatedDuration(estimatedDuration);
		this.setPriority(priority);
		this.setProjectId(projectId);
		this.setName(name);
		this.setDescription(description);
		this.setCreationDate(creationDate);	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(int estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
		
	}public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}