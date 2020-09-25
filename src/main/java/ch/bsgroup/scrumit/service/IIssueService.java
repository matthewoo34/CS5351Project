package ch.bsgroup.scrumit.service;

import ch.bsgroup.scrumit.domain.Issue;

public interface IIssueService {
	public Issue addIssue(Issue i);
	public Issue updateIssue(Issue i);
	public Issue removeIssue(Integer issueId);
}
