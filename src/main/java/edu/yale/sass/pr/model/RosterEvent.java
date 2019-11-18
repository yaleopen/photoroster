package edu.yale.sass.pr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="roster_event")
public class RosterEvent {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	@Column(name="session_id", unique = true, nullable = false)
	String session;
	

	@Column(name="user_id", unique = false, nullable = false)
	String userId;
	

	@Column(name="lms", unique = false, nullable = true)
	String lms;//which lms comes from
	

	@Column(name="lti_version", unique = false, nullable = true)
	String ltiVersion;
	

	@Column(name="role", unique = false, nullable = true)
	String role;
	
	@Column(name="session_start", nullable=false)
	Date sessionStart;
	
	@Column(name="session_end", nullable=true)
	Date sessionEnd;
	

	@Column(name="is_success", nullable=true)
	boolean success;
	

	@Column(name="ip", unique = false, nullable = true)
	String clientIP;
	

	@Column(name="browser", unique = false, nullable = true)
	String browser;
	

	@Column(name="platform", unique = false, nullable = true)
	String platform;
	
	@Column(name="course_id", unique = false, nullable = true)
	String courseId;
	
	@Column(name="context_title", unique = false, nullable = true)
	String contextTitle;
	
	public RosterEvent(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLms() {
		return lms;
	}

	public void setLms(String lms) {
		this.lms = lms;
	}

	public String getLtiVersion() {
		return ltiVersion;
	}

	public void setLtiVersion(String ltiVersion) {
		this.ltiVersion = ltiVersion;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getSessionStart() {
		return sessionStart;
	}

	public void setSessionStart(Date sessionStart) {
		this.sessionStart = sessionStart;
	}

	public Date getSessionEnd() {
		return sessionEnd;
	}

	public void setSessionEnd(Date sessionEnd) {
		this.sessionEnd = sessionEnd;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getContextTitle() {
		return contextTitle;
	}

	public void setContextTitle(String contextTitle) {
		this.contextTitle = contextTitle;
	}

 
}
