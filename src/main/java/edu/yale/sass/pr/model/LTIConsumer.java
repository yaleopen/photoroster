package edu.yale.sass.pr.model;

import javax.persistence.*;
import java.util.Date;

/**
 * This class will represent a LTI consumer key
 */

@Entity
@Table (name="lti_consumer")
public class LTIConsumer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
 
	@Column(name="name", unique = false, nullable = false)
	String name;
	 
	@Column(name="consumer_version", unique = false, nullable = true)
	String consumer_version;
	@Column(name="consumer_guid", unique = false, nullable = true)
	String consumer_guid;
	
	@Column(name="css_path", unique = false, nullable = true)
	String css_path;
	
	@Column(name="protected", unique = false, nullable = false)
	boolean protect;
	
	@Column(name="enabled", unique = false, nullable = false)
	boolean enabled;
	
	@Column(name="lti_key", unique = true, nullable = false)
	String key;
	
	@Column(name="secret", unique = false, nullable = false)
	String secret;
	
	
	@Column(name="enable_from", nullable=true)
	Date enable_from;
	
	@Column(name="enable_until", nullable=true)
	Date enable_until;
	
	@Column(name="created", nullable=false)
	Date created;
	
	@Column(name="updated", nullable=false)
	Date updated;
	
	
	@Column(name="active", unique = false, nullable = false)
	boolean active;
	public LTIConsumer(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	 

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 

	public String getConsumer_version() {
		return consumer_version;
	}

	public void setConsumer_version(String consumer_version) {
		this.consumer_version = consumer_version;
	}

	public String getConsumer_guid() {
		return consumer_guid;
	}

	public void setConsumer_guid(String consumer_guid) {
		this.consumer_guid = consumer_guid;
	}

	public String getCss_path() {
		return css_path;
	}

	public void setCss_path(String css_path) {
		this.css_path = css_path;
	}

	public boolean isProtect() {
		return protect;
	}

	public void setProtect(boolean protect) {
		this.protect = protect;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getEnable_from() {
		return enable_from;
	}

	public void setEnable_from(Date enable_from) {
		this.enable_from = enable_from;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getEnable_until() {
		return enable_until;
	}

	public void setEnable_until(Date enable_until) {
		this.enable_until = enable_until;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
