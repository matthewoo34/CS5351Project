package ch.bsgroup.scrumit.dao;

import java.util.Set;

import ch.bsgroup.scrumit.domain.SprintBacklog;

public interface ISprintBacklogDao {
	public SprintBacklog addSprintBacklog(SprintBacklog s);
	public void updateSprintBacklog(SprintBacklog s);
	public void removeSprintBacklog(int sprintBacklogId);
	public Set<SprintBacklog> getAllSprintBacklogs();
	public SprintBacklog findSprintBacklogById(int sprintBacklogId);
	public Set<SprintBacklog> getAllSprintBacklogsBySprintId(int sprintId);
}