package ch.bsgroup.scrumit.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ProductBacklog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	private int estimatedDuration;
	
	@NotNull
	private int priority;
	
	@NotNull
	private int projectId;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String name;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String description;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date creationDate;

	public ProductBacklog() {
		
	}

	public ProductBacklog(int estimatedDuration, int priority, int projectId,
			String name, String description, Date creationDate) {
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
	}
	public int getPriority() {
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