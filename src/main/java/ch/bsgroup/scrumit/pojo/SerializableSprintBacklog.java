package ch.bsgroup.scrumit.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SerializableSprintBacklog {

	private int id;

	private String acceptanceTest;

	private int productBacklogId;
	/**
	 * SprintBacklog has Sprints
	 */
	private Set<SerializableSprint> sprints = new HashSet<SerializableSprint>();

	/**
	 * SprintBacklog has Tasks
	 */
	private Set<SerializableTask> tasks = new HashSet<SerializableTask>();

	/**
	 * @desc Constructor
	 */
	public SerializableSprintBacklog(int id) {
		this.setId(id);
	}

	public SerializableSprintBacklog(int id, String acceptanceTest, int productBacklogId) {
		this.setId(id);
		this.setProductBacklogId(productBacklogId);
		this.setAcceptanceTest(acceptanceTest);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAcceptanceTest() {
		return acceptanceTest;
	}

	public void setAcceptanceTest(String acceptanceTest) {
		this.acceptanceTest = acceptanceTest;
	}
	
	public int getProductBacklogId() {
		return productBacklogId;
	}
	
	public void setProductBacklogId(int productBacklogId) {
		this.productBacklogId = productBacklogId;
	}

	public Set<SerializableSprint> getSprints() {
		return sprints;
	}

	public void setSprints(Set<SerializableSprint> sprints) {
		this.sprints = sprints;
	}

	public Set<SerializableTask> getTasks() {
		return tasks;
	}

	public void setTasks(Set<SerializableTask> tasks) {
		this.tasks = tasks;
	}
}