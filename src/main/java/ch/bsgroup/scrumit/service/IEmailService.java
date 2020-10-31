package ch.bsgroup.scrumit.service;

/**
 * 
 * Email Service Interface
 *
 */
public interface IEmailService {
	void send(String to,String subject,String message);
}
