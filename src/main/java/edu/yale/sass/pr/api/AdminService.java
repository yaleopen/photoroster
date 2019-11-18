package edu.yale.sass.pr.api;

import edu.yale.sass.pr.model.LTIConsumer;
import edu.yale.sass.pr.model.RosterEvent;
import edu.yale.sass.pr.model.User;

import java.util.List;

/**
 * 
 * @author sz49
 * This Service will do to things, handle the User, and the Consumers
 */
public interface AdminService {
	
	void addUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
	User getUserByNetId(String netId);
	
	List<User> getUsers();
	
	void addConsumer(LTIConsumer consumer);
	
	void updateConsumer(LTIConsumer consumer);
	
	LTIConsumer getConsumerByKey(String key);
	
	List<LTIConsumer> getConsumers();
	
	void addEvent(RosterEvent event);
	
	void updateEvent(RosterEvent event);
	
	RosterEvent getEventBySessionId(String id);
	
	List<RosterEvent> getEvents();

	List<RosterEvent> getEvents(String value);

}
