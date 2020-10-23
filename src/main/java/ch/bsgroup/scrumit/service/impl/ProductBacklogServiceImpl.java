package ch.bsgroup.scrumit.service.impl;

import java.util.Set;

import ch.bsgroup.scrumit.dao.IPersonDao;
import ch.bsgroup.scrumit.dao.IProductBacklogDao;
import ch.bsgroup.scrumit.dao.impl.PersonDaoImplHibernate;
import ch.bsgroup.scrumit.dao.impl.ProductBacklogDaoImplHibernate;
import ch.bsgroup.scrumit.service.IPersonService;
import ch.bsgroup.scrumit.service.IProductBacklogService;
import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.ProductBacklog;

/**
 * Person Service Implementation
 */
public class ProductBacklogServiceImpl implements IProductBacklogService {
	/**
	 * DAO binding
	 */
	private IProductBacklogDao productBacklogDao;

	public void setProductBacklogDao(IProductBacklogDao value) {
		productBacklogDao = value;
	}

	public ProductBacklogServiceImpl() {
		productBacklogDao = new ProductBacklogDaoImplHibernate();
	}

	public ProductBacklog addProductBacklog(ProductBacklog p) {
		return productBacklogDao.addProductBacklog(p);
	}

	public void updateProductBacklog(ProductBacklog p) {
		productBacklogDao.updateProductBacklog(p);
	}

	public void removeProductBacklog(ProductBacklog p) {
		productBacklogDao.removeProductBacklog(p);
	}

	public Set<ProductBacklog> getAllProductBacklogs() {
		return productBacklogDao.getAllProductBacklogs();
	}

	public ProductBacklog findProductBacklogById(int productBacklogId) {
		return productBacklogDao.findProductBacklogById(productBacklogId);
	}

	public Set<ProductBacklog> getAllProductBacklogsByProjectId(int projectId) {
		return productBacklogDao.getAllProductBacklogsByProjectId(projectId);
	}
}