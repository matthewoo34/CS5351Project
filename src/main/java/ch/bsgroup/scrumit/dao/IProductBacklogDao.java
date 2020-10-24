package ch.bsgroup.scrumit.dao;

import java.util.Set;

import ch.bsgroup.scrumit.domain.ProductBacklog;

public interface IProductBacklogDao {
	public ProductBacklog addProductBacklog(ProductBacklog p);
	public void updateProductBacklog(ProductBacklog p);
	public void removeProductBacklog(ProductBacklog p);
	public Set<ProductBacklog> getAllProductBacklogs();
	public ProductBacklog findProductBacklogById(int productBacklogId);
	public Set<ProductBacklog> getAllProductBacklogsByProjectId(int projectId);
}