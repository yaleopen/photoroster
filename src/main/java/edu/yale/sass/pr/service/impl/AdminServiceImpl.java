package edu.yale.sass.pr.service.impl;

import edu.yale.sass.pr.api.AdminService;
import edu.yale.sass.pr.api.dao.AdminDaoService;
import edu.yale.sass.pr.model.LTIConsumer;
import edu.yale.sass.pr.model.RosterEvent;
import edu.yale.sass.pr.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService{

	AdminDaoService adminDao;
	
	@Transactional(readOnly = false)

	public void addUser(User user) {
		getAdminDao().addUser(user);
		
	}

	@Transactional(readOnly = false)
	public void updateUser(User user) {
		getAdminDao().updateUser(user);
		
	}

	@Transactional(readOnly = false)

	public void deleteUser(User user) {
		getAdminDao().deleteUser(user);
		
	}

	public User getUserById(long id) {
		return getAdminDao().getUserById(id);
	}

	public User getUserByNetId(String netId) {
		return getAdminDao().getUserByNetId(netId);
	}

	public List<User> getUsers(){
		return getAdminDao().getUsers();
	}
	
	@Transactional(readOnly = false)
	public void addConsumer(LTIConsumer consumer) {
		getAdminDao().addConsumer(consumer);
		
	}

	@Transactional(readOnly = false)
	public void updateConsumer(LTIConsumer consumer) {
		getAdminDao().updateConsumer(consumer);
		
	}

	@Transactional(readOnly = false)
	public void deleteConsumer(LTIConsumer consumer) {
		getAdminDao().deleteConsumer(consumer);
		
	}

	public LTIConsumer getConsumerById(long id) {
		return getAdminDao().getConsumerById(id);
	}

	public LTIConsumer getConsumerByKey(String key) {
		return getAdminDao().getConsumerByKey(key);
	}

	public List<LTIConsumer> getConsumers() {
		return getAdminDao().getConsumers();
	}

	@Transactional(readOnly = false)
	public void addEvent(RosterEvent event) {
		getAdminDao().addEvent(event);
		
	}

	@Transactional(readOnly = false)
	public void updateEvent(RosterEvent event) {
		getAdminDao().updateEvent(event);
		
	}

	@Transactional(readOnly = false)
	public void deleteEvent(RosterEvent event) {
	}

	public RosterEvent getEventBySessionId(String id) {
		return getAdminDao().getEventBySessionId(id);
	}

	public List<RosterEvent> getEvents() {
		return getAdminDao().getEvents();
	}

	public List<RosterEvent> getEvents(String value) {
		return getAdminDao().getEvents(value);
	}

	public AdminDaoService getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDaoService adminDao) {
		this.adminDao = adminDao;
	}

}
