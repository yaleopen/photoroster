package edu.yale.sass.pr.bean;

import edu.yale.sass.pr.api.AdminService;
import edu.yale.sass.pr.model.LTIConsumer;
import edu.yale.sass.pr.model.RosterEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 *
 * This class will handle the LTI Consumer, create/delete/update/retrieve
 */
@ManagedBean(name = "adminBean")
@RequestScoped
public class AdminBean {

	@ManagedProperty(value = "#{adminService}")
	private
	AdminService adminService;

	private String name;
	 
	private String key;
	private String secret;
	private boolean enabled;
	private Date enableFrom;
	private Date enableUntil;
	private boolean protect;
	private String updateButtonText = "Add New Consumer";
	private String eventSearchValue;
	public AdminBean(){
	
		
	}
	
	
	public String getUpdateButtonText() {
		return updateButtonText;
	}

	public void setUpdateButtonText(String updateButtonText) {
		this.updateButtonText = updateButtonText;
	}
	
	private List<LTIConsumer> consumers;

	public List<LTIConsumer> getConsumers() {
		consumers = adminService.getConsumers();
		return consumers;
	}

	public void setConsumers(List<LTIConsumer> consumers) {
		this.consumers = consumers;
	}
	
	
	private List<RosterEvent> events;
	
	public List<RosterEvent> getEvents() {
		if (events ==null){
			events = adminService.getEvents();
		}
		return events;
	}


	public void setEvents(List<RosterEvent> events) {
		this.events = events;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * The default key always get a complicated secret automatically
	 */
	public String getSecret() {
		if (secret==null || secret.equals("")){
			secret = getRandomString();
		}
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getEnableFrom() {
		return enableFrom;
	}

	public void setEnableFrom(Date enableFrom) {
		this.enableFrom = enableFrom;
	}

	public Date getEnableUntil() {
		return enableUntil;
	}

	public void setEnableUntil(Date enableUntil) {
		this.enableUntil = enableUntil;
	}

	public boolean isProtect() {
		return protect;
	}

	public void setProtect(boolean protect) {
		this.protect = protect;
	}

	/**
	 * This method will add or update a LTI consumer
	 */
	public String update(){
 
		if (name==null ||name.equals("")) return "admin";
		if (key==null ||key.equals("")) return "admin";
		if (secret==null ||secret.equals("")) return "admin";
		boolean isNew = false;
		LTIConsumer consumer = adminService.getConsumerByKey(key);
 
		if (consumer ==null){
			
			isNew = true;
			consumer = new LTIConsumer();
		}
		consumer.setName(name);
		consumer.setCreated(new Date());
		consumer.setEnable_from(enableFrom);
		consumer.setEnable_until(enableUntil);
		consumer.setEnabled(enabled);
		consumer.setKey(key);
		consumer.setProtect(protect);
		consumer.setSecret(secret);
		consumer.setActive(true);//by default it is active, unless it is deleted, so with this logic, if you try to create a new one with the same key as the deleted one, the deleted will be ovrride and be active
		consumer.setConsumer_guid("---"+new Date().getTime());
		consumer.setCss_path("---"+new Date().getTime());
		consumer.setUpdated(new Date()); //should be updated when updated
		
	 
		if (isNew){
			adminService.addConsumer(consumer);
			System.out.println(" add a  consumer:"+consumer);
		}
		else{
			System.out.println(" update consumer:" + consumer);
			adminService.updateConsumer(consumer);
		}
		clear();
		return "admin";
	}
	
	public String editit(){ 
		updateButtonText = "Update";
	 
		return "admin";
	}
	
	public String cancel(){
		clear();
		return "admin";
		
	}
	
	private void clear(){
		key = "";
		name="";
		enableFrom = null;
		enabled = false;
		protect = false;
		secret= "";
		enableUntil = null;
	}
	
	
	
	private String getRandomString() {

	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	    Random rand = new Random();
	    StringBuilder value = new StringBuilder();
	    int charsLength = chars.length() - 1;

	    for (int i = 0; i < 32; i++) {
	      value.append(chars.charAt(rand.nextInt(charsLength)));
	    }

	    return value.toString();

	  }
	
	public String delete(){
		
		if (key==null){
			return "admin";
		}
		
		LTIConsumer consumer = adminService.getConsumerByKey(key);
		if (consumer ==null){
			return "admin";
		}
		consumer.setActive(false);
		adminService.updateConsumer(consumer);//delete it, let's do it softly
		return "admin";
	}
	
	public String getContext(){
		HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());

		return request.getContextPath();
	}


	public String getEventSearchValue() {
		return eventSearchValue;
	}


	public void setEventSearchValue(String eventSearchValue) {
		this.eventSearchValue = eventSearchValue;
	}
	
	public String searchEvent(){
		events = adminService.getEvents(eventSearchValue);
		return "event";
	}
}
