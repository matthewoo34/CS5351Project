package ch.bsgroup.scrumit.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

	private int duration;
	
	private Integer commencement;
	
	private Integer cost;
	
	private SerializableTask task;

	public SerializableIssue(int id, int category, String description, int duration,  
			Integer commencement, Integer cost) {
		this.setId(id);
		this.setCategory(category);
		this.setDescription(description);
		this.setDuration(duration);
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
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Integer getCommencement() {
		return commencement;
	}

	public void setCommencement(Integer commencement) {
		this.commencement = commencement;
	}
	
	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	public SerializableTask getTask() {
		return task;
	}

	public void setTask(SerializableTask task) {
		this.task = task;
	}
}