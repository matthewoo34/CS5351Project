package ch.bsgroup.scrumit.service.impl;

import java.util.Set;

import ch.bsgroup.scrumit.dao.IIssueDao;
import ch.bsgroup.scrumit.dao.impl.IssueDaoImplHibernate;
import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.service.IIssueService;

public class IssueServiceImpl implements IIssueService {
	
	private IIssueDao issueDao;
	
	public void setIssueDao(IIssueDao value) {
		this.issueDao = value;
	}
	
	public IssueServiceImpl() {
		issueDao = new IssueDaoImplHibernate();
	}

	@Override
	public Issue addIssue(Issue i) {
		return issueDao.addIssue(i);
	}

	@Override
	public void updateIssue(Issue i) {
		issueDao.updateIssue(i);;
	}

	@Override
	public void removeIssue(Integer issueId) {
		issueDao.removeIssue(issueId);;
	}

	@Override
	public Issue findIssueById(int issueId) {
		return issueDao.findIssueById(issueId);
	}

	@Override
	public Set<Issue> findAllIssues() {
		return issueDao.findAllIssues();
	}

}
