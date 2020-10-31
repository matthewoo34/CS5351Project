package ch.bsgroup.scrumit.service.impl;

import java.util.Set;

import ch.bsgroup.scrumit.dao.IIssueDao;
import ch.bsgroup.scrumit.dao.IPersonDao;
import ch.bsgroup.scrumit.dao.impl.IssueDaoImplHibernate;
import ch.bsgroup.scrumit.dao.impl.PersonDaoImplHibernate;
import ch.bsgroup.scrumit.service.IIssueService;
import ch.bsgroup.scrumit.service.IPersonService;
import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.domain.Person;

/**
 * Issue Service Implementation
 */
public class IssueServiceImpl implements IIssueService {
	/**
	 * DAO binding
	 */
	private IIssueDao issueDao;

	public void setIssueDao(IIssueDao value) {
		issueDao = value;
	}

	/**
	 * Constructor
	 */
	public IssueServiceImpl() {
		issueDao = new IssueDaoImplHibernate();
	}

	/**
	 * Service calls (delegation)
	 */
	public Issue addIssue(Issue issue) {
		return issueDao.addIssue(issue);
	}

	public void updateIssue(Issue issue) {
		issueDao.updateIssue(issue);
	}

	public void removeIssue(int issueId) {
		issueDao.removeIssue(issueId);
	}

	public Set<Issue> getAllIssues() {
		return issueDao.getAllIssues();
	}

	public Issue findIssueById(int issueId) {
		return issueDao.findIssueById(issueId);
	}

	public Set<Issue> getAllIssuesByProjectId(int projectId) {
		return issueDao.getAllIssuesByProjectId(projectId);
	}
	
	public Set<Issue> getAllIssuesByTaskId(int taskId) {
		return issueDao.getAllIssuesByTaskId(taskId);
	}

	@Override
	public Set<Issue> getAllIssueByPersonId(int personId) {
		return issueDao.getAllIssuesByPersonId(personId);
	}
}