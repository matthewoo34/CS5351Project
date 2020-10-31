package ch.bsgroup.scrumit.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Class Sprint manages Sprint within a specific Project
 * 		Every Sprint is defined by a unique Id and has a Slogan
 * 		The StartDate and EndDate is the time frame
 */
@Entity
public class Sprint {
	/**
	 * Unique Id of the Sprint to identify it
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	/**
	 * Every Sprint has a Slogan
	 */
	@NotNull
	@Size(min = 1, max = 255)
	private String slogan;

	/**
	 * Sprint has a StartDate
	 */
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date startDate;

	/**
	 * Sprint has an EndDate
	 */
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date endDate;
	
	@NotNull
	private int endHour;

	/**
	 * Sprint is joined with a Project
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="project_id", referencedColumnName="id", updatable=false)
	private Project project;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
		@JoinTable(name = "Sprint_SprintBacklog",
			joinColumns = {
				@JoinColumn(name="sprint_id", referencedColumnName="id")
			},
			inverseJoinColumns = {
				@JoinColumn(name="sprintbacklog_id", referencedColumnName="id")
			}
		)
	private Set<SprintBacklog> sprintBacklog = new HashSet<SprintBacklog>();

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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	public Set<SprintBacklog> getSprintBacklog() {
		return sprintBacklog;
	}

	public void setSprintBacklog(Set<SprintBacklog> sprintBacklog) {
		this.sprintBacklog = sprintBacklog;
	}
	
	public int getEndHour() {
		return endHour;
	}
	
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
}