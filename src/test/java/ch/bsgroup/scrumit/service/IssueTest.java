package ch.bsgroup.scrumit.service;

import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.domain.ProductBacklog;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.domain.SprintBacklog;
import ch.bsgroup.scrumit.domain.Task;

import ch.bsgroup.scrumit.service.impl.IssueServiceImpl;
import ch.bsgroup.scrumit.service.impl.ProductBacklogServiceImpl;
import ch.bsgroup.scrumit.service.impl.ProjectServiceImpl;
import ch.bsgroup.scrumit.service.impl.SprintBacklogServiceImpl;
import ch.bsgroup.scrumit.service.impl.TaskServiceImpl;

import org.dbunit.DBTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class IssueTest extends DBTestCase{
	static ProductBacklogServiceImpl pBService;
	static SprintBacklogServiceImpl sBservice;
	static TaskServiceImpl taskService;
	static IssueServiceImpl issueService;

	public IssueTest() {
		UtilityTest.databaseProperties();
		try {
			this.setUpBeforeClass();
		} catch (Exception ex) {
			System.out.println("Constructor: "+ex.getMessage());
		}
	}

	/**
	 * Fixture logic runs once - Initialization Spring, Reference obtaining from Spring, Invoking DbUnit
	 * @throws Exception
	 */
	public void setUpBeforeClass() throws Exception {
		final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		pBService = (ProductBacklogServiceImpl) context.getBean("productBacklogServiceImpl");
		sBservice = (SprintBacklogServiceImpl) context.getBean("sprintBacklogServiceImpl");
		taskService = (TaskServiceImpl) context.getBean("taskServiceImpl");
		issueService = (IssueServiceImpl) context.getBean("issueServiceImpl");
	}

	/**
	 * Connection to the database and performing a clean data insert
	 * @throws Exception
	 */
	protected void handleSetUpOperation() throws Exception {
		final IDatabaseConnection connection = UtilityTest.getConnection();
		final IDataSet data = getDataSet();
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, data);
		} finally {
			connection.close();
		}
	}

	/**
	 * Build the xml dataset file
	 * @return IDataSet - XML dataset
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected IDataSet getDataSet() throws IOException, DataSetException {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("full.xml"));
	}

	/**
	 * Initialization before test method
	 * @throws Exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		handleSetUpOperation();
	}

	/**
	 * must be implemented, otherwise warnings
	 */
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
	}
	
	@Test
	public void testAddProductBacklog() {
		Set<ProductBacklog> pbs = pBService.getAllProductBacklogs();
		
        ProductBacklog pb = new ProductBacklog();
        pb.setName("ProductBacklog1");
        pb.setPriority(1);
        pb.setProjectId(1);
        
        pBService.addProductBacklog(pb);
        Set<ProductBacklog> newPbs = pBService.getAllProductBacklogs();
        assertEquals(newPbs.size(), pbs.size()+1);
	}
	
	@Test
	public void testAddSprintBacklog() {
		Set<SprintBacklog> sbs = sBservice.getAllSprintBacklogs();
		
		SprintBacklog sb = new SprintBacklog();
		sb.setAcceptanceTest("test1");
		sb.setProductBacklogId(1);
		
		sBservice.addSprintBacklog(sb);
		Set<SprintBacklog> newSbs = sBservice.getAllSprintBacklogs();
		assertEquals(newSbs.size(), sbs.size()+1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddTask() {
		Set<Task> tasks = taskService.getAllTasks();
		
		Task task = new Task();
		task.setAssignDate(new Date(2020, 10, 10));
		task.setCommencement(1);
		task.setCreationDate(new Date(2020, 10, 10));
		task.setDescription("task1");
		task.setDuration(1);
		task.setIsFromPreviousSprint(null);
		task.setPosition(1);
		task.setStatus(1);
		task.setxCoord(1);
		task.setyCoord(1);
		
		SprintBacklog sb = sBservice.getAllSprintBacklogs().iterator().next();
		task.setSprintBacklog(sb);		
	
		
		taskService.addTask(task);
		Set<Task> newTasks = taskService.getAllTasks();
		assertEquals(newTasks.size(), tasks.size()+1);
	}

	@Test
	public void testAddIssue() {
		Set<Issue> issues = issueService.getAllIssues();
		
		Issue issue = new Issue();
		issue.setCategory("cat1");
		issue.setCommencement(1);
		issue.setCost(1);
		issue.setDescription("des");
		issue.setDuration(1);
		Task task = taskService.getAllTasks().iterator().next();
		issue.setTask(task);
		
		issueService.addIssue(issue);
		Set<Issue> newIssues = issueService.getAllIssues();
		assertEquals(newIssues.size(), issues.size()+1);
	}
}