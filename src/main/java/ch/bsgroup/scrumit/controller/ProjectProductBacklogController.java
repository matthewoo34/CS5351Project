package ch.bsgroup.scrumit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.bsgroup.scrumit.domain.Person;
import ch.bsgroup.scrumit.domain.ProductBacklog;
import ch.bsgroup.scrumit.domain.Project;
import ch.bsgroup.scrumit.pojo.SerializableProductBacklog;
import ch.bsgroup.scrumit.pojo.SerializableProject;
import ch.bsgroup.scrumit.service.IProductBacklogService;
import ch.bsgroup.scrumit.service.IProjectService;
import ch.bsgroup.scrumit.utils.ResourceNotFoundException;

@Controller
@RequestMapping(value="/project/")
public class ProjectProductBacklogController {
	
	private IProjectService projectService;
	private IProductBacklogService productBacklogService;
	private Validator validator;
	
	@Autowired
	public ProjectProductBacklogController(Validator validator) {
		this.validator = validator;
	}
	
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
	public void setProductBacklogService(IProductBacklogService productBacklogService) {
		this.productBacklogService = productBacklogService;
	}
	
	@RequestMapping(value="allproductbacklog/{projectid}/", method=RequestMethod.GET)
	public @ResponseBody List<SerializableProductBacklog> getAllProductBackLog(@PathVariable int projectid) {
		Set<ProductBacklog> productBacklogs = this.productBacklogService.getAllProductBacklogsByProjectId(projectid);
		List<SerializableProductBacklog> serializedProductBacklogs = new ArrayList<SerializableProductBacklog>();
		for (Iterator<ProductBacklog> iterator = productBacklogs.iterator(); iterator.hasNext();) {
			ProductBacklog p = iterator.next();
			SerializableProductBacklog spb = new SerializableProductBacklog(p.getId(),p.getEstimatedDuration(),p.getPriority(),
					p.getProjectId(),p.getName(),p.getDescription(),p.getCreationDate());
			serializedProductBacklogs.add(spb);
		}
		return serializedProductBacklogs;
	}
	
	@RequestMapping(value="productbacklog/{productbacklogid}/", method=RequestMethod.GET)
	public @ResponseBody SerializableProductBacklog getProductBacklogById(@PathVariable int productbacklogid) {
		ProductBacklog p = this.productBacklogService.findProductBacklogById(productbacklogid);
		if (p == null) {
			throw new ResourceNotFoundException(productbacklogid);
		}
		return new SerializableProductBacklog(p.getId(),p.getEstimatedDuration(),p.getPriority(),
				p.getProjectId(),p.getName(),p.getDescription(),p.getCreationDate());
	}
	
	@RequestMapping(value="productbacklog/add/{projectid}/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> addProductBacklog(@PathVariable int projectid, @RequestBody ProductBacklog p, HttpServletResponse response) {
		p.setProjectId(projectid);
		p.setName(p.getName().trim());
		Set<ConstraintViolation<ProductBacklog>> failures = validator.validate(p);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesProductBacklog(failures);
		} else {
			ProductBacklog newProductBacklog = this.productBacklogService.addProductBacklog(p);
			return Collections.singletonMap("productbacklog", newProductBacklog);
		}
	}
	
	@RequestMapping(value="productbacklog/update/", method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> updateProductBacklog(@RequestBody ProductBacklog p, HttpServletResponse response) {
		ProductBacklog pb = this.productBacklogService.findProductBacklogById(p.getId());
		p.setProjectId(pb.getProjectId());
		p.setName(p.getName().trim());
		//TODO associate with project relationship
		Set<ConstraintViolation<ProductBacklog>> failures = validator.validate(p);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return validationMessagesProductBacklog(failures);
		} else {
			this.productBacklogService.updateProductBacklog(p);
			return Collections.singletonMap("productbacklog", p);
		}
	}
	
	@RequestMapping(value="productbacklog/remove/{productbacklogid}/", method=RequestMethod.GET)
	public @ResponseBody void removeProductBacklogById(@PathVariable int productbacklogid) {
		this.productBacklogService.removeProductBacklog(productbacklogid);
	}
	
	private Map<String, String> validationMessagesProductBacklog(Set<ConstraintViolation<ProductBacklog>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<ProductBacklog> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
}
