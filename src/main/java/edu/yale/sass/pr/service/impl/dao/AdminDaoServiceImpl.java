package edu.yale.sass.pr.service.impl.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.yale.sass.pr.api.dao.AdminDaoService;
import edu.yale.sass.pr.model.LTIConsumer;
import edu.yale.sass.pr.model.RosterEvent;
import edu.yale.sass.pr.model.User;

public class AdminDaoServiceImpl implements AdminDaoService{

	private SessionFactory sessionFactory;

	/**
	 * Get Hibernate Session Factory
	 * 
	 * @return SessionFactory - Hibernate Session Factory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Set Hibernate Session Factory
	 * 
	 * @param SessionFactory - Hibernate Session Factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void addUser(User user) {
		getSessionFactory().getCurrentSession().save(user);
		
	}

	public void updateUser(User user) {
		getSessionFactory().getCurrentSession().update(user);
		
	}

	public void deleteUser(User user) {

		getSessionFactory().getCurrentSession().delete(user);
		
	}

	public User getUserById(long id) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from User  where  active=1 and id=?")
		        .setParameter(0, id).list();
				if(list == null || list.isEmpty() ){
				return null;
				}
				else
				return (User)list.get(0);
	}

	public User getUserByNetId(String netId) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from User where  active=1 and user_id=?")
		        .setParameter(0, netId).list();
		if(list == null || list.isEmpty() ){
		return null;
		}
		else
		return (User)list.get(0);
	}

	public List<User> getUsers(){
		List list = getSessionFactory().getCurrentSession().createQuery("from User where active=1").list();
		return list;
	}

	public void addConsumer(LTIConsumer consumer) {
		getSessionFactory().getCurrentSession().save(consumer);
		
	}

	public void updateConsumer(LTIConsumer consumer) {
		
		getSessionFactory().getCurrentSession().update(consumer);
		
	}

	public void deleteConsumer(LTIConsumer consumer) {

		getSessionFactory().getCurrentSession().delete(consumer);
		
	}

	public LTIConsumer getConsumerById(long id) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from LTIConsumer where active=1 and id=?")
		        .setParameter(0, id).list();
				if(list == null || list.isEmpty() ){
				return null;
				}
				else
		return (LTIConsumer)list.get(0);
	}

	public LTIConsumer getConsumerByKey(String key) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from LTIConsumer where  active=1 and lti_key=?")
		        .setParameter(0, key).list();
		if(list == null || list.isEmpty() ){
		return null;
		}
		else
		return (LTIConsumer)list.get(0);
	}

	public List<LTIConsumer> getConsumers() {
		List list = getSessionFactory().getCurrentSession().createQuery("from LTIConsumer where active=1").list();
		return list;
	}

	public void addEvent(RosterEvent event) {
		getSessionFactory().getCurrentSession().save(event);
		
	}

	public void updateEvent(RosterEvent event) {

		getSessionFactory().getCurrentSession().update(event);
		
	}

	public void deleteEvent(RosterEvent event) {

		getSessionFactory().getCurrentSession().delete(event);
		
	}

	public RosterEvent getEventBySessionId(String id) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("from RosterEvent where session_id=?")
		        .setParameter(0, id).list();
				if(list == null || list.isEmpty() ){
				return null;
				}
				else
		return (RosterEvent)list.get(0);
	}

	public List<RosterEvent> getEvents() {
		
		List list = getSessionFactory().getCurrentSession().createQuery("from RosterEvent").list();
		return list;
	}

	public List<RosterEvent> getEvents(String value) {
		
		List<RosterEvent> list1 =  getSessionFactory().getCurrentSession().createQuery("from RosterEvent where user_id like :searchValue  ").setMaxResults(1000)
				.setParameter("searchValue", "%"+value+"%").list();
		List<RosterEvent> list2 = getSessionFactory().getCurrentSession().createQuery("from RosterEvent where   ip like :searchValue  ").setMaxResults(1000)
				.setParameter("searchValue", "%"+value+"%").list();
		List<RosterEvent> list3 = getSessionFactory().getCurrentSession().createQuery("from RosterEvent where    course_id like :searchValue ").setMaxResults(1000)
				.setParameter("searchValue", "%"+value+"%").list();
		for(RosterEvent re : list2){
			if (!list1.contains(re)){
				list1.add(re);
			}
		}
		for(RosterEvent re: list3){
			if (!list1.contains(re)){
				list1.add(re);
			}
		}
		return list1;
	}

}
