package ch.bsgroup.scrumit.dao;

import ch.bsgroup.scrumit.domain.Issue;
import java.util.Set;

/**
 * IIssue Dao
 */
public interface IIssueDao {
	public Issue addIssue(Issue issue);
	public void updateIssue(Issue issue);
	public void removeIssue(Issue issue);
	public Set<Issue> getAllIssues();
	public Issue findIssueById(int issueId);
	public Set<Issue> getAllIssuesByProjectId(int projectId);
	public Set<Issue> getAllIssuesByTaskId(int taskId);
}