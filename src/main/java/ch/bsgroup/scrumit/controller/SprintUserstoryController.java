package ch.bsgroup.scrumit.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.bsgroup.scrumit.domain.BurnDown;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.domain.Sprint;
import ch.bsgroup.scrumit.domain.SprintBacklog;
import ch.bsgroup.scrumit.domain.Task;
import ch.bsgroup.scrumit.pojo.SerializableSprint;
import ch.bsgroup.scrumit.pojo.SerializableSprintBacklog;
import ch.bsgroup.scrumit.service.IBurnDownChartService;
import ch.bsgroup.scrumit.service.IProjectService;
import ch.bsgroup.scrumit.service.ISprintBacklogService;
import ch.bsgroup.scrumit.service.ISprintService;
import ch.bsgroup.scrumit.service.ITaskService;
import ch.bsgroup.scrumit.utils.ResourceNotFoundException;

@Controller
@RequestMapping(value="/sprint/")
public class SprintUserstoryController {
	private ISprintService sprintService;
	private ISprintBacklogService sprintBacklogService;
	private IProjectService projectService;
	private IBurnDownChartService burnDownChartService;
	private ITaskService taskService;
	private Validator validator;

	@Autowired
	public SprintUserstoryController(Validator validator) {
		this.validator = validator;
	}

	public void setSprintService(ISprintService sprintService) {
		this.sprintService = sprintService;
	}

	public void setSprintBacklogService(ISprintBacklogService sprintBacklogService) {
		this.sprintBacklogService = sprintBacklogService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public void setBurnDownChartService(IBurnDownChartService burnDownChartService) {
		this.burnDownChartService = burnDownChartService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value="{projectid}/", method=RequestMethod.GET)
	public String getSprintSprintBacklog(@PathVariable("projectid") int id, Model model) {
		Project p = this.projectService.findProjectById(id);
		if (p == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute("projectid", id);
		model.addAttribute("projectname", p.getName());
		return "sprint/sprint-sprintbacklog";
	}
	
	@RequestMapping(value="all/{projectid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableSprint> getAllSprintsOfProject(@PathVariable int projectid) {
		Set<Sprint> sprints = this.sprintService.getAllSprintsByProjectId(projectid);
		List<SerializableSprint> serializedSprints = new ArrayList<SerializableSprint>();
		for (Iterator<Sprint> iterator = sprints.iterator(); iterator.hasNext();) {
			Sprint s = iterator.next();
			SerializableSprint ss = new SerializableSprint(s.getId(), s.getSlogan(), s.getStartDate(), s.getEndDate(), s.getEndHour());
			serializedSprints.add(ss);
		}
		return serializedSprints;
	}

	@RequestMapping(value="allsprintbacklogs/{sprintid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableSprintBacklog> getAllSprintBacklogOfSprint(@PathVariable int sprintid) {
		Set<SprintBacklog> sprintBacklogs = this.sprintBacklogService.getAllSprintBacklogsBySprintId(sprintid);
		List<SerializableSprintBacklog> serializedSprintBacklogs = new ArrayList<SerializableSprintBacklog>();
		for (Iterator<SprintBacklog> iterator = sprintBacklogs.iterator(); iterator.hasNext();) {
			SprintBacklog s = iterator.next();
			SerializableSprintBacklog ss = new SerializableSprintBacklog(s.getId());
			serializedSprintBacklogs.add(ss);
		}
		return serializedSprintBacklogs;
	}

	@RequestMapping(value="sprint/{sprintid}/", method=RequestMethod.GET)
	public @ResponseBody SerializableSprint getSprint(@PathVariable int sprintid) {
		Sprint s = this.sprintService.findSprintById(sprintid);
		if (s == null) {
			throw new ResourceNotFoundException(sprintid);
		}
		return new SerializableSprint(s.getId(), s.getSlogan(), s.getStartDate(), s.getEndDate(), s.getEndHour());
	}

	@RequestMapping(value="update/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> updateSprint(@RequestBody Sprint s, HttpServletResponse response) {
		Set<ConstraintViolation<Sprint>> failures = validator.validate(s);
		Sprint sprint = this.sprintService.findSprintById(s.getId());
		sprint.setSlogan(s.getSlogan().trim());
		sprint.setStartDate(s.getStartDate());
		sprint.setEndDate(s.getEndDate());
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesSprint(failures);
		} else {
			this.sprintService.updateSprint(sprint);
			return Collections.singletonMap("sprint", 
				new SerializableSprint(
					sprint.getId(), 
					sprint.getSlogan(), 
					sprint.getStartDate(), 
					sprint.getEndDate(),
					sprint.getEndHour()
				
			));
		}
	}

	@RequestMapping(value="sprintbacklog/{sprintbacklogid}/", method=RequestMethod.GET)
	public @ResponseBody SerializableSprintBacklog getSprintBacklog(@PathVariable int sprintbacklogid) {
		SprintBacklog s = this.sprintBacklogService.findSprintBacklogById(sprintbacklogid);
		if (s == null) {
			throw new ResourceNotFoundException(sprintbacklogid);
		}
		return new SerializableSprintBacklog(s.getId(), s.getAcceptanceTest(), s.getProductBacklogId());
	}
	
	@RequestMapping(value="sprintbacklog/update/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> updateSprintBacklog(@RequestBody SprintBacklog s, HttpServletResponse response) {
		SprintBacklog sb = this.sprintBacklogService.findSprintBacklogById(s.getId());
		sb.setAcceptanceTest(s.getAcceptanceTest().trim());
		sb.setProductBacklogId(s.getProductBacklogId());
		Set<ConstraintViolation<SprintBacklog>> failures = validator.validate(sb);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesSprintBacklog(failures);
		} else {
			this.sprintBacklogService.updateSprintBacklog(sb);
			return Collections.singletonMap("sprintbacklog", 
					new SerializableSprintBacklog(
						sb.getId(), 
						sb.getAcceptanceTest(),
						sb.getProductBacklogId()
					)
			);
		}
	}

	@RequestMapping(value="add/{projectid}/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> addSprint(@RequestBody Sprint s, @PathVariable int projectid, HttpServletResponse response) throws ParseException {
		Project p = this.projectService.findProjectById(projectid);
		if (p == null) {
			throw new ResourceNotFoundException(projectid);
		}
		s.setProject(p);
		Set<ConstraintViolation<Sprint>> failures = validator.validate(s);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesSprint(failures);
		} else {
			s.setSlogan(s.getSlogan().trim());
			Sprint sprint = this.sprintService.addSprint(s);
			List<BurnDown> bdList = new ArrayList<BurnDown>();

			Calendar c = Calendar.getInstance();
			c.setTime(sprint.getStartDate());
			Calendar startCalendar = new GregorianCalendar(
				c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
			c.setTime(s.getEndDate());	
			Calendar endCalendar = new GregorianCalendar(
				c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
			boolean writeBurnDown = false;
			BurnDown bd;
			if (startCalendar.compareTo(endCalendar) <= 0) {
				writeBurnDown = true;
			}
			while (startCalendar.compareTo(endCalendar) <= 0) {
				bd = new BurnDown(0, 0, sprint.getId(), startCalendar.getTime());
				bdList.add(bd);
				startCalendar.add(Calendar.DATE, 1);
			}
			if (writeBurnDown) {
				this.burnDownChartService.addBurnDownForSprint(bdList);
			}
			return Collections.singletonMap("sprint", 
					new SerializableSprint(
							sprint.getId(), 
							sprint.getSlogan(), 
							sprint.getStartDate(), 
							sprint.getEndDate(),
							sprint.getEndHour()
					)
			);
		}
	}

	@RequestMapping(value="add/sprintbacklog/{sprintid}/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> addSprintBacklog(@PathVariable int sprintid, @RequestBody SprintBacklog s, HttpServletResponse response) {
		Sprint sprint = this.sprintService.findSprintById(sprintid);
		if (sprint == null) {
			throw new ResourceNotFoundException(sprintid);
		}
		s.setAcceptanceTest(s.getAcceptanceTest().trim());
		s.setProductBacklogId(s.getProductBacklogId());
		Set<ConstraintViolation<SprintBacklog>> failures = validator.validate(s);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesSprintBacklog(failures);
		} else {
			SprintBacklog newSprintBacklog = this.sprintBacklogService.addSprintBacklog(s);

			Set<SprintBacklog> sprintBacklogs = this.sprintBacklogService.getAllSprintBacklogsBySprintId(sprintid);
			sprintBacklogs.add(newSprintBacklog);
			sprint.setSprintBacklog(sprintBacklogs);
			this.sprintService.updateSprint(sprint);

			return Collections.singletonMap("sprintbacklog", 
					new SerializableSprintBacklog(
							newSprintBacklog.getId(), 
							newSprintBacklog.getAcceptanceTest(),
							newSprintBacklog.getProductBacklogId()
					)
			);
		}
	}

	@RequestMapping(value="remove/{sprintid}/", method=RequestMethod.GET)
	public @ResponseBody void removeSprintById(@PathVariable int sprintid) {
		this.sprintService.removeSprint(sprintid);
		// ToDo, not yet implemented
		this.burnDownChartService.removeBurnDown(sprintid);
	}

	@RequestMapping(value="sprintbacklog/remove/{sprintbacklogid}/", method=RequestMethod.GET)
	public @ResponseBody void removesprintbacklogById(@PathVariable int sprintbacklogid) {
		Set<Task> tasks = this.taskService.getAllTasksBySprintBacklogId(sprintbacklogid);
		int taskDurationOfSprintBacklog = 0;
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task t = iterator.next();
			taskDurationOfSprintBacklog += t.getDuration();
		}
		if (taskDurationOfSprintBacklog > 0) {
			// Update BurnDownChart
			
		}
		this.sprintBacklogService.removeSprintBacklog(sprintbacklogid);
	}

	// internal helper
	private Map<String, String> validationMessagesSprint(Set<ConstraintViolation<Sprint>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Sprint> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
	private Map<String, String> validationMessagesSprintBacklog(Set<ConstraintViolation<SprintBacklog>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<SprintBacklog> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
}