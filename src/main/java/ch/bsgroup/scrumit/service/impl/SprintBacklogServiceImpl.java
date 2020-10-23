package ch.bsgroup.scrumit.service.impl;

import java.util.Set;

import ch.bsgroup.scrumit.dao.ISprintBacklogDao;
import ch.bsgroup.scrumit.dao.impl.SprintBacklogDaoImplHibernate;
import ch.bsgroup.scrumit.service.ISprintBacklogService;
import ch.bsgroup.scrumit.domain.SprintBacklog;


public class SprintBacklogServiceImpl implements ISprintBacklogService {
	/**
	 * DAO binding
	 */
	private ISprintBacklogDao sprintBacklogDao;

	public void setSprintBacklogDao(ISprintBacklogDao value) {
		sprintBacklogDao = value;
	}

	/**
	 * Constructor
	 */
	public SprintBacklogServiceImpl() {
		sprintBacklogDao = new SprintBacklogDaoImplHibernate();
	}

	/**
	 * Service calls (delegation)
	 */
	public SprintBacklog addSprintBacklog(SprintBacklog s) {
		return sprintBacklogDao.addSprintBacklog(s);
	}

	public void updateSprintBacklog(SprintBacklog s) {
		sprintBacklogDao.updateSprintBacklog(s);
	}

	public void removeSprintBacklog(int sprintBacklogId) {
		sprintBacklogDao.removeSprintBacklog(sprintBacklogId);
	}

	public Set<SprintBacklog> getAllSprintBacklogs() {
		return sprintBacklogDao.getAllSprintBacklogs();
	}

	public SprintBacklog findSprintBacklogById(int sprintBacklogId) {
		return sprintBacklogDao.findSprintBacklogById(sprintBacklogId);
	}

	public Set<SprintBacklog> getAllSprintBacklogsBySprintId(int sprintId) {
		return sprintBacklogDao.getAllSprintBacklogsBySprintId(sprintId);
	}
}