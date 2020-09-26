package ch.bsgroup.scrumit.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ch.bsgroup.scrumit.dao.IIssueDao;
import ch.bsgroup.scrumit.domain.Issue;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.utils.HibernateUtil;

public class IssueDaoImplHibernate implements IIssueDao{

	@Override
	public Issue addIssue(Issue i) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.save(i);
		sess.flush();
		tx.commit();

		return i;
	}

	@Override
	public void updateIssue(Issue i) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.update(i);
		tx.commit();
	}

	@Override
	public void removeIssue(int issueId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		try {
			Issue i = (Issue)sess.createQuery("from Issue where id = "+issueId).list().get(0);
			i.setPerson(null);
			sess.delete(i);
			tx.commit();
		}
		catch (IndexOutOfBoundsException ex) {
			System.out.println("exception: "+ex);
		}
	}

	@Override
	public Issue findIssueById(int issueId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		try {
			Issue issue = (Issue)sess.createQuery("from Issue where id = "+issueId).list().get(0);
			tx.commit();
			return issue;
		}
		catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	@Override
	public Set<Issue> findAllIssues() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Issue> list = sess.createQuery("from Issue").list();
		Set<Issue> issues = new HashSet<Issue>(list);
		tx.commit();

		return issues;
	}

}
