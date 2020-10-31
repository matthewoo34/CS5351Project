package ch.bsgroup.scrumit.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.bsgroup.scrumit.domain.BurnDown;
import ch.bsgroup.scrumit.domain.BurnDownChart;
import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.domain.Sprint;
import ch.bsgroup.scrumit.domain.SprintBacklog;
import ch.bsgroup.scrumit.domain.Task;
import ch.bsgroup.scrumit.pojo.SerializableBurnDownChart;
import ch.bsgroup.scrumit.pojo.SerializableIssue;
import ch.bsgroup.scrumit.pojo.SerializableSprintBacklog;
import ch.bsgroup.scrumit.pojo.SerializableTask;
import ch.bsgroup.scrumit.service.IBurnDownChartService;
import ch.bsgroup.scrumit.service.IIssueService;
import ch.bsgroup.scrumit.service.IPersonService;
import ch.bsgroup.scrumit.service.IProjectService;
import ch.bsgroup.scrumit.service.ISprintBacklogService;
import ch.bsgroup.scrumit.service.ISprintService;
import ch.bsgroup.scrumit.service.ITaskService;
import ch.bsgroup.scrumit.utils.ResourceNotFoundException;

@Controller
@RequestMapping(value="/board/")
public class BoardController {
	private IProjectService projectService;
	private IPersonService personService;
	private ISprintService sprintService;
	private ISprintBacklogService sprintBacklogService;
	private ITaskService taskService;
	private IIssueService issueService;
	private IBurnDownChartService burnDownChartService;

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}

	public void setSprintService(ISprintService sprintService) {
		this.sprintService = sprintService;
	}

	public void setSprintBacklogService(ISprintBacklogService sprintBacklogService) {
		this.sprintBacklogService = sprintBacklogService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	
	public void setIssueService(IIssueService issueService) {
		this.issueService = issueService;
	}

	public void setBurnDownChartService(IBurnDownChartService burnDownChartService) {
		this.burnDownChartService = burnDownChartService;
	}

	@RequestMapping(value="{projectid}/{sprintid}/", method=RequestMethod.GET)
	public String getBoard(@PathVariable("projectid") int pid, @PathVariable("sprintid") int sid, Model model) {
		model.addAttribute("projectid", pid);
		model.addAttribute("sprintid", sid);
		Project p = this.projectService.findProjectById(pid);
		if (p == null) {
			throw new ResourceNotFoundException(pid);
		}
		model.addAttribute("projectname", p.getName());
		Sprint s = this.sprintService.findSprintById(sid);
		if (s == null) {
			throw new ResourceNotFoundException(sid);
		}
		model.addAttribute("sprintslogan", s.getSlogan());
		return "board/board";
	}

	@RequestMapping(value="allsprintbacklogs/{sprintid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableSprintBacklog> getAllSprintBacklogsOfSprint(@PathVariable Integer sprintid) {
		Set<SprintBacklog> sprintBacklog = this.sprintBacklogService.getAllSprintBacklogsBySprintId(sprintid);
		List<SerializableSprintBacklog> serializedSprintBacklogs = new ArrayList<SerializableSprintBacklog>();
		for (Iterator<SprintBacklog> iterator = sprintBacklog.iterator(); iterator.hasNext();) {
			SprintBacklog s = iterator.next();
			SerializableSprintBacklog ss = new SerializableSprintBacklog(s.getId(), s.getAcceptanceTest(), s.getProductBacklogId());
			serializedSprintBacklogs.add(ss);
		}
		return serializedSprintBacklogs;
	}

	@RequestMapping(value="alltasks/{sprintbacklogid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableTask> getAllTasksOfSprintBacklog(@PathVariable Integer sprintbacklogid) {
		Set<Task> tasks = this.taskService.getAllTasksBySprintBacklogId(sprintbacklogid);
		List<SerializableTask> serializedTasks = new ArrayList<SerializableTask>();
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task t = iterator.next();
			String personName="";
			if (t.getPerson()!=null) {
				personName = t.getPerson().getLastName() + " " + t.getPerson().getFirstName();
			}
			SerializableTask st = new SerializableTask(t.getId(), t.getDescription(), t.getxCoord(), 
					t.getyCoord(), t.getStatus(), t.getDuration(), t.getCreationDate(), t.getCommencement(),
					t.getPosition(),personName,t.getAssignDate());
			serializedTasks.add(st);
		}
		return serializedTasks;
	}
	
	@RequestMapping(value="alltasks/{projectid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableTask> getAllTasksOfProject(@PathVariable int projectid) {
		Set<Task> tasks = this.taskService.getAllTasksByProjectId(projectid);
		List<SerializableTask> serializedTasks = new ArrayList<SerializableTask>();
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task t = iterator.next();
			String personName="";
			if (t.getPerson()!=null) {
				personName = t.getPerson().getLastName() + " " + t.getPerson().getFirstName();
			}
			SerializableTask st = new SerializableTask(t.getId(), t.getDescription(), t.getxCoord(), 
					t.getyCoord(), t.getStatus(), t.getDuration(), t.getCreationDate(), t.getCommencement(),
					t.getPosition(),personName,t.getAssignDate());
			serializedTasks.add(st);
		}
		return serializedTasks;
	}

//	@RequestMapping(value="sprintbacklog/updatecoord/", method=RequestMethod.POST)
//	public @ResponseBody void updateUserstoryCoord(@RequestBody SprintBacklog s) {
//		UserStory us = this.userStoryService.findUserStoryById(u.getId());
//		us.setxCoord(u.getxCoord());
//		us.setyCoord(u.getyCoord());
//		this.userStoryService.updateUserStory(us);
//	}
//
//	@RequestMapping(value="userstory/updatename/", method=RequestMethod.POST)
//	public @ResponseBody void updateUserstoryName(@RequestBody UserStory u) {
//		UserStory us = this.userStoryService.findUserStoryById(u.getId());
//		us.setName(u.getName());
//		this.userStoryService.updateUserStory(us);
//	}

	@RequestMapping(value="add/task/{sprintbacklogid}/{sprintid}/", method=RequestMethod.POST)
	public @ResponseBody SerializableTask addTask(@PathVariable Integer sprintbacklogid, @PathVariable int sprintid, @RequestBody Task t) {
		SprintBacklog s = this.sprintBacklogService.findSprintBacklogById(sprintbacklogid);
		if (s == null) {
			throw new ResourceNotFoundException(sprintbacklogid);
		}
		t.setSprintBacklog(s);
		t.setCreationDate(new Date());
        //find related developer
        if (t.getPersonId() != null) {
            Person p = this.personService.findPersonById(t.getPersonId());
            if (p!=null) {
                t.setPerson(p);
                t.setAssignDate(new Date());
            }
        }
		Task task = this.taskService.addTask(t);
		String personName = "";
		if (task.getPerson() != null) {
			System.out.println("add task null person");
			personName = task.getPerson().getLastName() + " " + task.getPerson().getFirstName();
		}
		this.burnDownChartService.updateBurnDown(task.getDuration(), 0, sprintid);
		return new SerializableTask(task.getId(), task.getDescription(), task.getxCoord(), task.getyCoord(), 
				task.getStatus(), task.getDuration(), task.getCreationDate(), task.getCommencement(),
				task.getPosition(),personName,task.getAssignDate());
	}

	@RequestMapping(value="task/updatecoord/{sprintid}/", method=RequestMethod.POST)
	public @ResponseBody void updateTaskCoord(@PathVariable int sprintid, @RequestBody Task t) {
		Task task = this.taskService.findTaskById(t.getId());
		if (task == null) {
			throw new ResourceNotFoundException(t.getId());
		}
		task.setxCoord(t.getxCoord());
		task.setyCoord(t.getyCoord());
		if (task.getStatus() != t.getStatus()) {
			// status change
			if (t.getStatus() == 2 && task.getStatus() < 2) {
				// 0,1 -> 2
				this.burnDownChartService.updateBurnDown(-task.getDuration(), task.getDuration(), sprintid);
			}
			if (task.getStatus() == 2 && t.getStatus() < 2) {
				// 2 -> 0,1
				this.burnDownChartService.updateBurnDown(task.getDuration(), -task.getDuration(), sprintid);
			}
			task.setStatus(t.getStatus());
		}
		this.taskService.updateTask(task);
	}
	
    @RequestMapping(value="task/updateperson/", method=RequestMethod.POST)
    public @ResponseBody void updateTaskPerson(@RequestBody Task t) {
        Task task = this.taskService.findTaskById(t.getId());
        if (task == null) {
            throw new ResourceNotFoundException(t.getId());
        }
        
        Integer personId = t.getPersonId();
        if (personId != null) {
            Person p = this.personService.findPersonById(personId);
            if (p == null) {
                throw new ResourceNotFoundException(personId);
            }
            task.setPerson(p);
            task.setAssignDate(new Date());
        }
        this.taskService.updateTask(task);
    }

	@RequestMapping(value="task/updatedescription/", method=RequestMethod.POST)
	public @ResponseBody void updateTaskDescription(@RequestBody Task t) {
		Task task = this.taskService.findTaskById(t.getId());
		task.setDescription(t.getDescription());
		this.taskService.updateTask(task);
	}

	@RequestMapping(value="burndownchart/{sprintid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableBurnDownChart> getBurnDownChart(@PathVariable int sprintid) {
		List<BurnDownChart> bdchart = this.burnDownChartService.getAllBurnDownCharts(sprintid);
		List<SerializableBurnDownChart> sbdchart = new ArrayList<SerializableBurnDownChart>();
		for (Iterator<BurnDownChart> iterator = bdchart.iterator(); iterator.hasNext();) {
			BurnDownChart bdc = iterator.next();
			SerializableBurnDownChart sbdc = new SerializableBurnDownChart(bdc.getDate(), bdc.getReal(), bdc.getOptimal());
			System.out.println(bdc.getDate()+" "+bdc.getReal());
			sbdchart.add(sbdc);
		}
		return sbdchart;
	}

	@RequestMapping(value="burndown/{sprintid}/", method=RequestMethod.GET)
	public @ResponseBody List<BurnDown> getBurnDown(@PathVariable int sprintid) {
		return this.burnDownChartService.getBurnDown(sprintid);
	}
	
	@RequestMapping(value="add/issue/{taskId}", method=RequestMethod.POST)
	public @ResponseBody SerializableIssue addIssue(@PathVariable int taskid,@RequestBody Issue i) {
		Task t = this.taskService.findTaskById(taskid);
		if (t == null) {
			throw new ResourceNotFoundException(t.getId());
		}
		i.setTask(t);
		i.setCreationDate(new Date());
		Issue issue = this.issueService.addIssue(i);
		return new SerializableIssue(issue.getId(),
				issue.getCategory(),
				issue.getDescription(),
				issue.getExtraDuration(),
				issue.getCreationDate(),
				issue.getSprintBacklogID(),
				issue.getProjectID(),
				issue.getPersonID(),
				issue.getCommencement(),
				issue.getCost());
	}
	
	@RequestMapping(value="issue/update/", method=RequestMethod.POST)
	public @ResponseBody void updateIssue(@RequestBody Issue i) {
		Issue issue = this.issueService.findIssueById(i.getId());
		issue.setCategory(i.getCategory());
		issue.setCommencement(i.getCommencement());
		issue.setCost(i.getCost());
		issue.setDescription(i.getDescription());
		issue.setExtraDuration(i.getExtraDuration());
		issue.setPersonID(i.getPersonID());
		issue.setProjectID(i.getProjectID());
		issue.setSprintBacklogID(i.getSprintBacklogID());
		this.issueService.updateIssue(issue);
	}
	
	@RequestMapping(value="issue/remove/{issueId}", method=RequestMethod.GET)
	public @ResponseBody void removeIssue(@PathVariable int issueId) {
		this.issueService.removeIssue(issueId);
	}
	
	@RequestMapping(value="allIssues/task/{taskId}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableIssue> getAllIssuesOftask(@PathVariable int taskId) {
		Set<Issue> issues = this.issueService.getAllIssuesByTaskId(taskId);
		List<SerializableIssue> serializedIssues = new ArrayList<SerializableIssue>();
		for (Iterator<Issue> iterator = issues.iterator(); iterator.hasNext();) {
			Issue i = iterator.next();
			SerializableIssue si = new SerializableIssue(
					i.getId(),
					i.getCategory(),
					i.getDescription(),
					i.getExtraDuration(),
					i.getCreationDate(),
					i.getSprintBacklogID(),
					i.getProjectID(),
					i.getPersonID(),
					i.getCommencement(),
					i.getCost());
			serializedIssues.add(si);
		}
		return serializedIssues;
	}
	
	@RequestMapping(value="allIssues/project/{projectid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableIssue> getAllIssuesOfProject(@PathVariable int projectId) {
		Set<Issue> issues = this.issueService.getAllIssuesByProjectId(projectId);
		List<SerializableIssue> serializedIssues = new ArrayList<SerializableIssue>();
		for (Iterator<Issue> iterator = issues.iterator(); iterator.hasNext();) {
			Issue i = iterator.next();
			SerializableIssue si = new SerializableIssue(
					i.getId(),
					i.getCategory(),
					i.getDescription(),
					i.getExtraDuration(),
					i.getCreationDate(),
					i.getSprintBacklogID(),
					i.getProjectID(),
					i.getPersonID(),
					i.getCommencement(),
					i.getCost());
			serializedIssues.add(si);
		}
		return serializedIssues;
	}
	
	@RequestMapping(value="allIssues/person/{personid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableIssue> getAllIssuesOfPerson(@PathVariable int personid) {
		Set<Issue> issues = this.issueService.getAllIssueByPersonId(personid);
		List<SerializableIssue> serializedIssues = new ArrayList<SerializableIssue>();
		for (Iterator<Issue> iterator = issues.iterator(); iterator.hasNext();) {
			Issue i = iterator.next();
			SerializableIssue si = new SerializableIssue(
					i.getId(),
					i.getCategory(),
					i.getDescription(),
					i.getExtraDuration(),
					i.getCreationDate(),
					i.getSprintBacklogID(),
					i.getProjectID(),
					i.getPersonID(),
					i.getCommencement(),
					i.getCost());
			serializedIssues.add(si);
		}
		return serializedIssues;
	}
}