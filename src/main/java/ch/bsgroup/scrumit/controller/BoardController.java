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
import ch.bsgroup.scrumit.pojo.SerializablePerson;
import ch.bsgroup.scrumit.pojo.SerializableSprintBacklog;
import ch.bsgroup.scrumit.pojo.SerializableTask;
import ch.bsgroup.scrumit.service.IBurnDownChartService;
import ch.bsgroup.scrumit.service.IEmailService;
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
	private IEmailService emailService;

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
	
	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
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

	@RequestMapping(value="alltasks/sprintbacklog/{sprintbacklogid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableTask> getAllTasksOfSprintBacklog(@PathVariable Integer sprintbacklogid) {
		Set<Task> tasks = this.taskService.getAllTasksBySprintBacklogId(sprintbacklogid);
		List<SerializableTask> serializedTasks = new ArrayList<SerializableTask>();
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task t = iterator.next();
			//Integer personId = t.getPerson()!=null?t.getPerson().getId():null;
			SerializableTask st = new SerializableTask(t.getId(), t.getDescription(), t.getxCoord(), 
					t.getyCoord(), t.getStatus(), t.getDuration(), t.getCreationDate(), t.getCommencement(),
					t.getPosition(),/*personId,*/t.getAssignDate(),t.getIsFromPreviousSprint());
			serializedTasks.add(st);
		}
		return serializedTasks;
	}
	
	@RequestMapping(value="alltasks/project/{projectid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableTask> getAllTasksOfProject(@PathVariable int projectid) {
		Set<Task> tasks = this.taskService.getAllTasksByProjectId(projectid);
		List<SerializableTask> serializedTasks = new ArrayList<SerializableTask>();
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task t = iterator.next();
			//Integer personId = t.getPerson()!=null?t.getPerson().getId():null;
			SerializableTask st = new SerializableTask(t.getId(), t.getDescription(), t.getxCoord(), 
					t.getyCoord(), t.getStatus(), t.getDuration(), t.getCreationDate(), t.getCommencement(),
					t.getPosition(),/*personId,*/t.getAssignDate(),t.getIsFromPreviousSprint());
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
//        if (t.getPersonId() != null) {
//            Person p = this.personService.findPersonById(t.getPersonId());
//            if (p!=null) {
//                t.setPerson(p);
//                t.setAssignDate(new Date());
//            }
//        }
		Task task = this.taskService.addTask(t);
		String personName = "";
//		if (task.getPerson() != null) {
//			System.out.println("add task null person");
//			personName = task.getPerson().getLastName() + " " + task.getPerson().getFirstName();
//		}
		this.burnDownChartService.updateBurnDown(task.getDuration(), 0, sprintid);
		return new SerializableTask(task.getId(), task.getDescription(), task.getxCoord(), task.getyCoord(), 
				task.getStatus(), task.getDuration(), task.getCreationDate(), task.getCommencement(),
				task.getPosition(),/*personName,*/task.getAssignDate(),task.getIsFromPreviousSprint());
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
	
//    @RequestMapping(value="task/updateperson/", method=RequestMethod.POST)
//    public @ResponseBody void updateTaskPerson(@RequestBody Task t) {
//        Task task = this.taskService.findTaskById(t.getId());
//        if (task == null) {
//            throw new ResourceNotFoundException(t.getId());
//        }
//        
//        Integer personId = t.getPersonId();
//        if (personId != null) {
//            Person p = this.personService.findPersonById(personId);
//            if (p == null) {
//                throw new ResourceNotFoundException(personId);
//            }
//            task.setPerson(p);
//            task.setAssignDate(new Date());
//            this.taskService.updateTask(task);
//            this.emailService.send(p.getEmail(), "Task Assign", "New task is assigned to you, please check dashboard");
//        }
//    }
	
    @RequestMapping(value="task/addperson/{taskid}/{personid}/", method=RequestMethod.GET)
    public @ResponseBody void addTaskPerson(@PathVariable int taskid, @PathVariable int personid) {
        Task task = this.taskService.findTaskById(taskid);
        if (task == null) {
            throw new ResourceNotFoundException(taskid);
        }
      
        Person p = this.personService.findPersonById(personid);
        if (p == null) {
            throw new ResourceNotFoundException(personid);
        }
        Set<Person> persons = this.personService.getAllPersonsByTaskId(taskid);
        persons.add(p);
        task.setPersons(persons);
        this.taskService.updateTask(task);
    }
    
    @RequestMapping(value="task/removeperson/{taskid}/{personid}/", method=RequestMethod.GET)
    public @ResponseBody void removeTaskPerson(@PathVariable int taskid, @PathVariable int personid) {
        Task task = this.taskService.findTaskById(taskid);
        if (task == null) {
            throw new ResourceNotFoundException(taskid);
        }  
        Set<Person> persons = this.personService.getAllPersonsByTaskId(taskid);
		for (Iterator<Person> iterator = persons.iterator(); iterator.hasNext();) {
			Person p = iterator.next();
            if(p.getId() == personid) {
            	iterator.remove();
            }
		}       
        task.setPersons(persons);
        this.taskService.updateTask(task);
    }
    
    @RequestMapping(value="allpersons/{taskid}/", method=RequestMethod.GET)
    public @ResponseBody List<SerializablePerson> getAllPersonsOfTask(@PathVariable int taskid) {
        Set<Person> persons = this.personService.getAllPersonsByTaskId(taskid);
        List<SerializablePerson> serializablePerson = new ArrayList<SerializablePerson>();
		for (Iterator<Person> iterator = persons.iterator(); iterator.hasNext();) {
			Person p = iterator.next();
			SerializablePerson sp = new SerializablePerson(p.getId(), p.getFirstName(), p.getLastName());
			serializablePerson.add(sp);
		}       
		return serializablePerson;
    }
    
    @RequestMapping(value="task/updatestatus/", method=RequestMethod.POST)
    public @ResponseBody void updateTaskStatus(@RequestBody Task t) {
    	Task task = this.taskService.findTaskById(t.getId());
    	if (task == null) {
    		throw new ResourceNotFoundException(t.getId());
    	}
    	int previousStatus = task.getStatus();
    	int currentStatus = t.getStatus();
    	task.setStatus(currentStatus);
    	int position = t.getPosition();
    	task.setPosition(position);
    	this.taskService.updateTask(task);
    	if (previousStatus != currentStatus && currentStatus == 3) {
    		this.emailService.send("kafaatli3-c@my.cityu.edu.hk", "Task Completed", "The task - "+task.getDescription()+ " has been completed");
    	}
    }

	@RequestMapping(value="task/updatedescription/", method=RequestMethod.POST)
	public @ResponseBody void updateTaskDescription(@RequestBody Task t) {
		Task task = this.taskService.findTaskById(t.getId());
		task.setDescription(t.getDescription());
		task.setCommencement(t.getCommencement());
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
	
	@RequestMapping(value="add/issue/{taskid}", method=RequestMethod.POST)
	public @ResponseBody SerializableIssue addIssue(@PathVariable int taskid,@RequestBody Issue i) {
		Task t = this.taskService.findTaskById(taskid);
		if (t == null) {
			throw new ResourceNotFoundException(t.getId());
		}
		i.setTask(t);
		Issue issue = this.issueService.addIssue(i);
		return new SerializableIssue(issue.getId(),
				issue.getCategory(),
				issue.getDescription(),
				issue.getDuration(),
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
		issue.setDuration(i.getDuration());
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
					i.getDuration(),
					i.getCommencement(),
					i.getCost());
			serializedIssues.add(si);
		}
		return serializedIssues;
	}
	
//	@RequestMapping(value="allIssues/project/{projectid}/", method=RequestMethod.GET)
//	public @ResponseBody List<SerializableIssue> getAllIssuesOfProject(@PathVariable int projectId) {
//		Set<Issue> issues = this.issueService.getAllIssuesByProjectId(projectId);
//		List<SerializableIssue> serializedIssues = new ArrayList<SerializableIssue>();
//		for (Iterator<Issue> iterator = issues.iterator(); iterator.hasNext();) {
//			Issue i = iterator.next();
//			SerializableIssue si = new SerializableIssue(
//					i.getId(),
//					i.getCategory(),
//					i.getDescription(),
//					i.getDuration(),
//					i.getCommencement(),
//					i.getCost());
//			serializedIssues.add(si);
//		}
//		return serializedIssues;
//	}
//	
//	@RequestMapping(value="allIssues/person/{personid}/", method=RequestMethod.GET)
//	public @ResponseBody List<SerializableIssue> getAllIssuesOfPerson(@PathVariable int personid) {
//		Set<Issue> issues = this.issueService.getAllIssueByPersonId(personid);
//		List<SerializableIssue> serializedIssues = new ArrayList<SerializableIssue>();
//		for (Iterator<Issue> iterator = issues.iterator(); iterator.hasNext();) {
//			Issue i = iterator.next();
//			SerializableIssue si = new SerializableIssue(
//					i.getId(),
//					i.getCategory(),
//					i.getDescription(),
//					i.getDuration(),
//					i.getCommencement(),
//					i.getCost());
//			serializedIssues.add(si);
//		}
//		return serializedIssues;
//	}
}