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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class SprintBacklog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@NotNull
	private int productBacklogId;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String acceptanceTest;

	@JsonIgnore
	@ManyToMany(mappedBy="sprintBacklog")
	private Set<Sprint> sprints = new HashSet<Sprint>();

	@JsonIgnore
	@OneToMany
	@JoinColumn(name="sprintbacklog_id", referencedColumnName="id", updatable=true)
	private Set<Task> tasks = new HashSet<Task>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getProductBacklogId() {
		return productBacklogId;
	}

	public void setProductBacklogId(int productBacklogId) {
		this.productBacklogId = productBacklogId;
	}

	public String getAcceptanceTest() {
		return acceptanceTest;
	}

	public void setAcceptanceTest(String acceptanceTest) {
		this.acceptanceTest = acceptanceTest;
	}

	public Set<Sprint> getSprints() {
		return sprints;
	}

	public void setSprints(Set<Sprint> sprints) {
		this.sprints = sprints;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
}