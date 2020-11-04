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
	private String category;

	@NotNull
	@Size(min = 1, max = 255)
	private String description;

	private Integer duration;
	
	private Integer commencement;
	
	private Integer cost;
		
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="task_id", referencedColumnName="id", updatable=false)
	private Task task;
	
	public Issue() {
		
	}

	public Issue(String category, String description, Integer duration, Integer commencement, Integer cost) {
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
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
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
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}