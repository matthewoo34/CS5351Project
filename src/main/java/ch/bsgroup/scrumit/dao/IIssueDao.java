package ch.bsgroup.scrumit.dao;

import ch.bsgroup.scrumit.domain.Issue;

public interface IIssueDao {
	public Issue addIssue(Issue i);
	public Issue updateIssue(Issue i);
	public void removeIssue(int issueId);
}
