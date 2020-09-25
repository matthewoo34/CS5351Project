package ch.bsgroup.scrumit.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	private Integer category;
	
	@NotNull
	@Size(min=1)
	private String description;
	
	@NotNull
	private Integer extraDuration;
	
	@DateTimeFormat(pattern = "dd.MM.yy HH:mm:ss")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date creationDate;
	
    @ManyToOne
    @JoinColumn(name="userstory_id", referencedColumnName="id", nullable=true, updatable=false, insertable=true)
    private UserStory userStory;

    @ManyToOne
    @JoinColumn(name="persion_id", referencedColumnName="id", nullable=true, updatable=false, insertable=true)
    private Person person;
    
    public Issue(Integer category, String description, Integer extraDuration,  
            Date creationDate,  UserStory userStory, Person person) {
        this.setCategory(category);
        this.setDescription(description);
        this.setExtraDuration(extraDuration);
        this.setCreationDate(creationDate);
        this.setUserStory(userStory);
        this.setPerson(person);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getExtraDuration() {
		return extraDuration;
	}

	public void setExtraDuration(Integer extraDuration) {
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
