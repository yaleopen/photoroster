package edu.yale.sass.pr;

import edu.yale.sass.pr.api.AdminService;
import edu.yale.sass.pr.model.RosterEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

public class RosterSessionListener implements HttpSessionListener {

	
	AdminService adminService;
	public RosterSessionListener(){
		
	}

	public void sessionCreated(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void sessionDestroyed(HttpSessionEvent e) {
		  ApplicationContext context
          = WebApplicationContextUtils.getRequiredWebApplicationContext(e.getSession().getServletContext()); 
		  adminService = (AdminService) context.getBean("adminService");
		HttpSession session = e.getSession();
		RosterEvent event = adminService.getEventBySessionId(session.getId());
		event.setSessionEnd(new Date());
		adminService.updateEvent(event);
		
	}

}
