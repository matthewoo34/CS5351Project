package ch.bsgroup.scrumit.service;

import ch.bsgroup.scrumit.domain.Issue;

import java.util.Set;

/**
 * Person Service Interface
 */
public interface IIssueService {
	public Issue addIssue(Issue issue);
	public void updateIssue(Issue issue);
	public void removeIssue(Issue issue);
	public Set<Issue> getAllIssues();
	public Issue findIssueById(int issueId);
	public Set<Issue> getAllIssuesByProjectId(int projectId);
}