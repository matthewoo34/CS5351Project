package ch.bsgroup.scrumit.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ch.bsgroup.scrumit.domain.Sprint;
import ch.bsgroup.scrumit.domain.SprintBacklog;
import ch.bsgroup.scrumit.dao.ISprintBacklogDao;
import ch.bsgroup.scrumit.utils.HibernateUtil;


public class SprintBacklogDaoImplHibernate implements ISprintBacklogDao {

	public SprintBacklog addSprintBacklog(SprintBacklog s) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.save(s);
		sess.flush();
		tx.commit();

		return s;
	}


	public void updateSprintBacklog(SprintBacklog s) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.update(s);
		tx.commit();
	}


	public void removeSprintBacklog(int sprintBacklogId){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();

		try {
			SprintBacklog u = (SprintBacklog)sess.createQuery("from SprintBacklog where id = "+sprintBacklogId).list().get(0);
			Set<Sprint> sprints = u.getSprints();
			for (Sprint sprint : sprints) {
				if (sprint.getSprintBacklog().contains(u)) {
					sprint.getSprintBacklog().remove(u);
			        sess.saveOrUpdate(sprint);
				}
			}
			sess.delete(u);
		    tx.commit();
		}
		catch (Exception ex) {
			System.err.println("Failed deleting Person: "+ex.getMessage());
		}
	}

	public Set<SprintBacklog> getAllSprintBacklogs() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<SprintBacklog> list = sess.createQuery("from SprintBacklog").list();
		Set<SprintBacklog> sprintBacklogs = new HashSet<SprintBacklog>(list);
		tx.commit();

		return sprintBacklogs;
	}

	public SprintBacklog findSprintBacklogById(int sprintBacklogId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		try {
			SprintBacklog sprintBacklog = (SprintBacklog)sess.createQuery("from SprintBacklog where id = "+sprintBacklogId).list().get(0);
			tx.commit();
			return sprintBacklog;
		}
		catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	public Set<SprintBacklog> getAllSprintBacklogsBySprintId(int sprintId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<SprintBacklog> list = sess.createQuery("select u from SprintBacklog u join u.sprints spri where spri.id = :id").setParameter("id", sprintId).list();  
		
		Set<SprintBacklog> sprintBacklogs = new HashSet<SprintBacklog>(list);
		tx.commit();

		return sprintBacklogs;
	}
}