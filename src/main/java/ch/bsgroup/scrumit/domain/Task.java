package ch.bsgroup.scrumit.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;

/**
 * Class Task manages Tasks
 * 		Every Task is defined by a unique Id
 * 		It also has a Description, a xCoord/YCoord pair, Status, Duration, CreationDate
 */
@Entity
public class Task {
	/**
	 * Unique Id of the SprintBacklog to identify it
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	/**
	 * Task has a Description
	 */
	private String description;

	/**
	 * Task has a X coordinate
	 */
	private int xCoord;

	/**
	 * Task has a Y coordinate
	 */
	private int yCoord;

	/**
	 * Task has a Status
	 */
	@Audited
	private int status;

	/**
	 * Task has a Duration
	 */
	private int duration;

	/**
	 * Project has a CreationDate
	 */
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name="sprintbacklog_id", referencedColumnName="id", nullable=true, updatable=false, insertable=true)
	private SprintBacklog sprintBacklog;
	
	@NotNull
	private int commencement;
	
	@NotNull
	private int position;
	
    /**
     * Task has assign date
     */
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date assignDate;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
		@JoinTable(name = "Task_Issue",
			joinColumns = {
				@JoinColumn(name="task_id", referencedColumnName="id")
			},
			inverseJoinColumns = {
				@JoinColumn(name="issue_id", referencedColumnName="id")
			}
		)
	private Set<Issue> issues = new HashSet<Issue>();
	

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
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public int getyCoord() {
		return yCoord;
	}

	/**
	 * @param yCoord the yCoord to set
	 */
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
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


	public SprintBacklog getSprintBacklog() {
		return sprintBacklog;
	}

	public void setSprintBacklog(SprintBacklog sprintBacklog) {
		this.sprintBacklog = sprintBacklog;
	}
	
	public int getCommencement() {
		return commencement;
	}

	public void setCommencement(int commencement) {
		this.commencement = commencement;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	
    /**
     * Task has a list of Persons - mapping owner
     */
    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="person_id", referencedColumnName="id")
    private Person person;
    
    /**
     * @return the persons
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param persons the persons to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }
    
    /**
     * @return the task assign date
     */
    public Date getAssignDate() {
        return assignDate;
    }

    /**
     * @param assignDate the task assign date
     */
    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }
	
    @Transient
    private Integer personId;
    
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
    
    public Integer getPersonId() {
		return this.personId;	
    }
    
	public Set<Issue> getIssues() {
		return issues;
	}
	
	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}
}