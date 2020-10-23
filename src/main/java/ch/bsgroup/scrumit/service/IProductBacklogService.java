package ch.bsgroup.scrumit.service;

import java.util.Set;

import ch.bsgroup.scrumit.domain.ProductBacklog;

public interface IProductBacklogService {
	public ProductBacklog addProductBacklog(ProductBacklog p);
	public void updateProductBacklog(ProductBacklog p);
	public void removeProductBacklog(ProductBacklog p);
	public Set<ProductBacklog> getAllProductBacklogs();
	public ProductBacklog findProductBacklogById(int productBacklogId);
}