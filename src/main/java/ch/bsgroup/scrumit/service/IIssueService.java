package ch.bsgroup.scrumit.service;

import ch.bsgroup.scrumit.domain.Issue;

import java.util.Set;

/**
 * Person Service Interface
 */
public interface IIssueService {
	public Issue addIssue(Issue issue);
	public void updateIssue(Issue issue);
	public void removeIssue(int issueId);
	public Set<Issue> getAllIssues();
	public Issue findIssueById(int issueId);
	public Set<Issue> getAllIssuesByProjectId(int projectId);
	public Set<Issue> getAllIssuesByTaskId(int taskId);
	public Set<Issue> getAllIssueByPersonId(int personId);
}