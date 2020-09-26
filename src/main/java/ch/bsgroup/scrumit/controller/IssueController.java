package ch.bsgroup.scrumit.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.service.IIssueService;

@Controller
@RequestMapping(value="/issue/")
public class IssueController {
	private IIssueService issueService;
	private Validator validator;
	
	@Autowired
	public IssueController(Validator validator) {
		this.validator = validator;
	}
	
	public void setIssueService(IIssueService issueService) {
		this.issueService = issueService;
	}
	
	@RequestMapping()
	public @ResponseBody Map<String, ? extends Object> addIssue(@RequestBody Issue issue, HttpServletResponse response) {
		Set<ConstraintViolation<Issue>> failures = validator.validate(issue);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessages(failures);
		} else {
			return Collections.singletonMap("project", this.issueService.addIssue(issue));
		}
	}
	
	private Map<String, String> validationMessages(Set<ConstraintViolation<Issue>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Issue> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
}
