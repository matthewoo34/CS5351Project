package ch.bsgroup.scrumit.service;

import java.util.Set;

import ch.bsgroup.scrumit.domain.Issue;

public interface IIssueService {
	public Issue addIssue(Issue i);
	public void updateIssue(Issue i);
	public void removeIssue(Integer issueId);
	public Set<Issue> findAllIssues();
	public Issue findIssueById(int issueId);
}
