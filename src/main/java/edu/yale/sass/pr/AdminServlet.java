package edu.yale.sass.pr;

import edu.yale.sass.pr.api.AdminService;
import edu.yale.sass.pr.model.User;
import edu.yale.sass.pr.util.ServerConfig;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminServlet extends HttpServlet {

	public static final long serialVersionUID = -4;
	static Logger logger = Logger.getLogger(AdminServlet.class);
	AdminService adminService;
	
	  public void init(ServletConfig config) throws ServletException {

	        super.init(config); 
	        try {
	        	 
	            ApplicationContext context
	                = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	            
	            adminService = (AdminService) context.getBean("adminService");
	        } catch (Throwable t) {
	            throw new ServletException("Failed to initialise RosterTool servlet.", t);
	        }
	    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean valid = checkUser(request, response);
		if (valid){
		 RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/admin/admin.jsf");
		 requestDispatcher.forward(request, response);
		}
		else{
			 RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/UnAuthorized.jsf");
			 requestDispatcher.forward(request, response);
		}
	}

	boolean checkUser(HttpServletRequest request, HttpServletResponse response){
		  

		String netId =  request.getRemoteUser();
		HttpSession session =request.getSession(true);
		boolean isAdminUser = isAdmin(netId);
		if (netId!=null){
			session.setAttribute("netId", netId);
		}
		if (isAdminUser) return true;
		System.out.println("User " + netId + " try to access admin page");
  
		
		if (netId !=null && adminService !=null){
		
			User user = adminService.getUserByNetId(netId);
			if (user!=null && !user.isAdmin()){
				try{
					System.out.println("User " + netId + " try to access admin page, but failed");
					return false;
				}
				catch(Exception e){
				}
			}
			if (user==null){
					return false;
			}
			return true;
			
		}
		return false;
	}
	
	boolean isAdmin(String netId){
		String admins = ServerConfig.getString("admins", "");
		if (admins!=null && netId !=null &&  admins.indexOf(netId)>=0){
			return true;
		}
		return false;
	}
}
