package ch.bsgroup.scrumit.service;

import java.util.Set;

import ch.bsgroup.scrumit.domain.SprintBacklog;

/**
 * SprintBacklog Service Interface
 */
public interface ISprintBacklogService {
	public SprintBacklog addSprintBacklog(SprintBacklog s);
	public void updateSprintBacklog(SprintBacklog s);
	public void removeSprintBacklog(int sprintBacklogId);
	public Set<SprintBacklog> getAllSprintBacklogs();
	public SprintBacklog findSprintBacklogById(int sprintBacklogId);
	public Set<SprintBacklog> getAllSprintBacklogsBySprintId(int sprintId);
}