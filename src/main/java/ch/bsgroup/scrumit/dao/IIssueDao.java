package ch.bsgroup.scrumit.dao;

import java.util.Set;

import ch.bsgroup.scrumit.domain.Issue;

public interface IIssueDao {
	public Issue addIssue(Issue i);
	public void updateIssue(Issue i);
	public void removeIssue(int issueId);
	public Set<Issue> findAllIssues();
	public Issue findIssueById(int issueId);
}
