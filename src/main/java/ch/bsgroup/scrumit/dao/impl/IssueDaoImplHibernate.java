package ch.bsgroup.scrumit.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.dao.IIssueDao;
import ch.bsgroup.scrumit.dao.IPersonDao;
import ch.bsgroup.scrumit.utils.HibernateUtil;

/**
 * Person Dao Hibernate Implementation
 */
public class IssueDaoImplHibernate implements IIssueDao {
	/**
	 * Add Issue
	 */
	public Issue addIssue(Issue issue) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.save(issue);
		sess.flush();
		tx.commit();

		return issue;
	}

	/**
	 * Update Issue
	 */
	public void updateIssue(Issue issue) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.update(issue);
		tx.commit();
	}

	/**
	 * Delete Issue
	 */
	public void removeIssue(Issue issue){		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
     	sess.delete(issue);
		tx.commit();
	}

	/**
	 * Get all Issue
	 */
	public Set<Issue> getAllIssues() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Issue> list = sess.createQuery("from Issue").list();
		Set<Issue> issues = new HashSet<Issue>(list);
		tx.commit();

		return issues;
	}

	/**
	 * Find Issue by ID
	 */
	public Issue findIssueById(int issueID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		try {
			Issue issue = (Issue)sess.createQuery("from Issue where id = "+issueID).list().get(0);
			tx.commit();
			return issue;
		}
		catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	/**
	 * Get all Persons which are associated to a given Project(Id)
	 */
	public Set<Issue> getAllIssuesByProjectId(int projectID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Issue> list = sess.createQuery("from Issue where projectID = "+ projectID).list();
		Set<Issue> issues = new HashSet<Issue>(list);
		tx.commit();

		return issues;
	}
	
	public Set<Issue> getAllIssuesByTaskId(int taskId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Issue> list = sess.createQuery("select i from Issue i join i.tasks task where task.id = :id").setParameter("id", taskId).list(); 
		Set<Issue> issues = new HashSet<Issue>(list);
		tx.commit();

		return issues;
	}
}