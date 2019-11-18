package edu.yale.sass.pr.api.dao;

import edu.yale.sass.pr.model.LTIConsumer;
import edu.yale.sass.pr.model.RosterEvent;
import edu.yale.sass.pr.model.User;

import java.util.List;

public interface AdminDaoService {

	
	void addUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
	User getUserById(long id);
	
	User getUserByNetId(String netId);
	
	void addConsumer(LTIConsumer consumer);
	
	void updateConsumer(LTIConsumer consumer);
	
	void deleteConsumer(LTIConsumer consumer);
	
	LTIConsumer getConsumerById(long id);
	
	LTIConsumer getConsumerByKey(String key);
	
	List<LTIConsumer> getConsumers();
	
	void addEvent(RosterEvent event);
	
	void updateEvent(RosterEvent event);
	
	RosterEvent getEventBySessionId(String id);
	
	List<RosterEvent> getEvents();
	List<User> getUsers();

	List<RosterEvent> getEvents(String value);



}
