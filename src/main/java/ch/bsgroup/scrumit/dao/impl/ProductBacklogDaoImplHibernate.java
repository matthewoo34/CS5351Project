package ch.bsgroup.scrumit.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.ProductBacklog;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.dao.IPersonDao;
import ch.bsgroup.scrumit.dao.IProductBacklogDao;
import ch.bsgroup.scrumit.utils.HibernateUtil;

public class ProductBacklogDaoImplHibernate implements IProductBacklogDao {

	public ProductBacklog addProductBacklog(ProductBacklog p) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.save(p);
		sess.flush();
		tx.commit();

		return p;
	}

	public void updateProductBacklog(ProductBacklog p) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.update(p);
		tx.commit();
	}

	public void removeProductBacklog(ProductBacklog p){		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		sess.delete(p);
		tx.commit();
	}

	public Set<ProductBacklog> getAllProductBacklogs() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<ProductBacklog> list = sess.createQuery("from ProductBacklog").list();
		Set<ProductBacklog> productBacklogs = new HashSet<ProductBacklog>(list);
		tx.commit();

		return productBacklogs;
	}

	public ProductBacklog findProductBacklogById(int productBacklogId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		try {
			ProductBacklog productBacklog = (ProductBacklog)sess.createQuery("from ProductBacklog where id = "+productBacklogId).list().get(0);
			tx.commit();
			return productBacklog;
		}
		catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	public Set<ProductBacklog> getAllProductBacklogsByProjectId(int projectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();

		Transaction tx = sess.beginTransaction();
		@SuppressWarnings("unchecked")
		List<ProductBacklog> list = sess.createQuery("from ProductBacklog where projectId = "+projectId).list();
		Set<ProductBacklog> productBacklogs = new HashSet<ProductBacklog>(list);
		tx.commit();

		return productBacklogs;
	}

}