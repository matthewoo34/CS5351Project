package ch.bsgroup.scrumit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.bsgroup.scrumit.service.IIssueService;
import ch.bsgroup.scrumit.service.IPersonService;
import ch.bsgroup.scrumit.service.IProjectService;
import ch.bsgroup.scrumit.utils.ResourceNotFoundException;
import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.pojo.SerializableIssue;
import ch.bsgroup.scrumit.pojo.SerializablePerson;
import ch.bsgroup.scrumit.pojo.SerializableProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Controller
@RequestMapping(value="/project/")
public class ProjectPersonController {
	private IProjectService projectService;
	private IPersonService personService;
	private IIssueService issueService;
	private Validator validator;

	@Autowired
	public ProjectPersonController(Validator validator) {
		this.validator = validator;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}
	
	public void setIssueService(IIssueService issueService) {
		this.issueService = issueService;
	}

	@RequestMapping(method=RequestMethod.GET)
	public String getProjectPerson(Model model) {
		return "project/project-person";
	}

	@RequestMapping(value="allprojects/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableProject> getAllProjects() {
		Set<Project> projects = this.projectService.getAllProjects();
		List<SerializableProject> serializedProjects = new ArrayList<SerializableProject>();
		for (Iterator<Project> iterator = projects.iterator(); iterator.hasNext();) {
			Project p = iterator.next();
			SerializableProject sp = new SerializableProject(p.getId(), p.getName(), p.getDuration(), p.getCost());
			serializedProjects.add(sp);
		}
		return serializedProjects;
	}

	@RequestMapping(value="allpersons/{projectid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializablePerson> getAllPersonsOfProject(@PathVariable int projectid) {
		Set<Person> persons = this.personService.getAllPersonsByProjectId(projectid);
		List<SerializablePerson> serializedPersons = new ArrayList<SerializablePerson>();
		for (Iterator<Person> iterator = persons.iterator(); iterator.hasNext();) {
			Person p = iterator.next();
			SerializablePerson sp = new SerializablePerson(p.getId(), p.getFirstName(), p.getLastName());
			serializedPersons.add(sp);
		}
		return serializedPersons;
	}

	@RequestMapping(value="{projectid}/", method=RequestMethod.GET)
	public @ResponseBody SerializableProject getProjectById(@PathVariable int projectid) {
		Project p = this.projectService.findProjectById(projectid);
		if (p == null) {
			throw new ResourceNotFoundException(projectid);
		}
		return new SerializableProject(p.getId(), p.getName(), p.getDescription(), p.getCreationDate(), 
				p.getDuration(), p.getCost());
	}
	
	@RequestMapping(value="person/{personid}/", method=RequestMethod.GET)
	public @ResponseBody SerializablePerson getPersonById(@PathVariable int personid) {
		Person p = this.personService.findPersonById(personid);
		if (p == null) {
			throw new ResourceNotFoundException(personid);
		}
		return new SerializablePerson(p.getId(), p.getFirstName(), p.getLastName(), p.getEmail());
	}
	
	@RequestMapping(value="{projectid}/cost/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableIssue> getAllIssuesOfProjectId(@PathVariable int projectid) {
		Set<Issue> issues = this.issueService.getAllIssuesByProjectId(projectid);
		List<SerializableIssue> serializedIssues = new ArrayList<SerializableIssue>();
		for (Iterator<Issue> iterator = issues.iterator(); iterator.hasNext();) {
			Issue issue = iterator.next();
			SerializableIssue serializableIssue = new SerializableIssue(issue.getId(), issue.getCategory(),
					issue.getDescription(), issue.getExtraDuration(), issue.getCreationDate(), issue.getSprintBacklogID(), 
					issue.getProjectID(), issue.getPersonID(), issue.getCommencement(), issue.getCost());
			serializedIssues.add(serializableIssue);
		}
		return serializedIssues;
	}

	@RequestMapping(value="update/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> updateProject(@RequestBody Project p, HttpServletResponse response) {
		Set<ConstraintViolation<Project>> failures = validator.validate(p);
		Project project = this.projectService.findProjectById(p.getId());
		project.setName(p.getName().trim());
		project.setDescription(p.getDescription().trim());
		project.setDuration(p.getDuration());
		project.setCost(p.getCost());		
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessages(failures);
		} else {
			this.projectService.updateProject(project);
			SerializableProject sp = new SerializableProject(project.getId(), project.getName(), 
					project.getDescription(), project.getCreationDate(), project.getDuration(), project.getCost());
			return Collections.singletonMap("project", sp);
		}
	}

	@RequestMapping(value="person/update/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> updatePerson(@RequestBody Person p, HttpServletResponse response) {
		p.setFirstName(p.getFirstName().trim());
		p.setLastName(p.getLastName().trim());
		p.setEmail(p.getEmail().trim());
		Set<ConstraintViolation<Person>> failures = validator.validate(p);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesPerson(failures);
		} else {
			this.personService.updatePerson(p);
			return Collections.singletonMap("person", p);
		}
	}

	@RequestMapping(value="remove/{projectid}/", method=RequestMethod.GET)
	public @ResponseBody void removeProjectById(@PathVariable int projectid) {
		this.projectService.removeProject(projectid);
	}

	@RequestMapping(value="person/remove/{personid}/", method=RequestMethod.GET)
	public @ResponseBody void removePersonById(@PathVariable int personid) {
		this.personService.removePerson(personid);
	}

	@RequestMapping(value="add/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> addProject(@RequestBody Project p, HttpServletResponse response) {
		p.setName(p.getName().trim());
		p.setDescription(p.getDescription().trim());
		p.setCreationDate(new Date());
		p.setDuration(p.getDuration());
		p.setCost(p.getCost());
		Set<ConstraintViolation<Project>> failures = validator.validate(p);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessages(failures);
		} else {
			return Collections.singletonMap("project", this.projectService.addProject(p));
		}
	}

	@RequestMapping(value="person/add/{projectid}/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> addPerson(@PathVariable int projectid, @RequestBody Person p, HttpServletResponse response) {
		p.setFirstName(p.getFirstName().trim());
		p.setLastName(p.getLastName().trim());
		p.setEmail(p.getEmail().trim());
		Set<ConstraintViolation<Person>> failures = validator.validate(p);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesPerson(failures);
		} else {
			Project project = this.projectService.findProjectById(projectid);
			Person newPerson = this.personService.addPerson(p);
			
			Set<Person> persons = this.personService.getAllPersonsByProjectId(projectid);
			persons.add(newPerson);
			project.setPersons(persons);

			this.projectService.updateProject(project);
			return Collections.singletonMap("person", newPerson);
		}
	}

	// internal helper
	private Map<String, String> validationMessages(Set<ConstraintViolation<Project>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Project> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
	private Map<String, String> validationMessagesPerson(Set<ConstraintViolation<Person>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Person> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
}