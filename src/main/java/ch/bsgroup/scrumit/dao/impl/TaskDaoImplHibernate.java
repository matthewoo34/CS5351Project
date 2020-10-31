package ch.bsgroup.scrumit.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.Task;
import ch.bsgroup.scrumit.dao.ITaskDao;
import ch.bsgroup.scrumit.utils.HibernateUtil;

/**
 * Task Dao Hibernate Implementation
 */
public class TaskDaoImplHibernate implements ITaskDao {
	/**
	 * Add Task
	 */
	public Task addTask(Task t) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.save(t);
		sess.flush();
		tx.commit();

		return t;
	}

	/**
	 * Update Task
	 */
	public void updateTask(Task t) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.update(t);
		sess.flush();
		tx.commit();
	}

	/**
	 * Delete Task
	 */
	public void removeTask(int taskId){
		Task t = findTaskById(taskId);

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.delete(t);
		sess.flush();
		tx.commit();
	}

	/**
	 * Get all Tasks
	 */
	public Set<Task> getAllTasks() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Task> list = sess.createQuery("from Task").list();
		Set<Task> tasks = new HashSet<Task>(list);
		tx.commit();

		return tasks;
	}

	/**
	 * Find Task by ID
	 */
	public Task findTaskById(int taskId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		try {
			Task task = (Task)sess.createQuery("from Task where id = "+taskId).list().get(0);
			tx.commit();
			return task;
		}
		catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	public Set<Task> getAllTasksBySprintBacklogId(int sprintBacklogId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Task> list = sess.createQuery("from Task where sprintbacklog_id = :id").setParameter("id", sprintBacklogId).list();
		Set<Task> tasks = new HashSet<Task>(list);
		tx.commit();

		return tasks;
	}

	@Override
	public Set<Task> getAllTasksByProjectId(int projectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Task> list = sess.createQuery("select t from Task t,SprintBacklog b,Sprint s,Project p where t.sprintbacklog_id = b.id and b.sprint_id = s.id and s.project_id = p.id and p.id = :id").setParameter("id", projectId).list();
		Set<Task> tasks = new HashSet<Task>(list);
		tx.commit();

		return tasks;
	}
}