package edu.yale.sass.pr.bean;

import edu.yale.sass.pr.api.AdminService;
import edu.yale.sass.pr.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
/**
 *
 * This class is handling the admin users. Add/edit/delete
 */
@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean {
	
	@ManagedProperty(value = "#{adminService}")
	AdminService adminService;

	String name;
	 
	String netId;
	boolean admin;
	String buttonText="Add New User";
	public AdminService getAdminService() {
		return adminService;
	}
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNetId() {
		return netId;
	}
	public void setNetId(String netId) {
		this.netId = netId;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	List<User> users;
	public List<User> getUsers() {
		users = adminService.getUsers();
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public String update(){

		boolean isNew = false;
		if (name==null || netId==null){
			return "user";
		}
		if (name.equals("") || netId.equals("")){
			return "user";
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		String currentUser = (String) session.getAttribute("netId");
		if (currentUser!=null && currentUser.equals(netId)){
			clear();
			return "user";//down't do anything now
		}
		User u = adminService.getUserByNetId(netId);

		System.out.println("  update the uer: "+ u);
		if (u==null){
			u = new User();//must be to add a new user
			isNew = true;
		}
		u.setUserId(netId);
		u.setAdmin(admin);
		u.setName(name);
		if (isNew){

			u.setCreatedOn(new Date());
			adminService.addUser(u);
		}
		else{
			adminService.updateUser(u);
		}
		clear();
		return "user";
	}
	
	public String delete(){
		if (netId ==null || netId.equals("")){
			return "user";
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		String currentUser = (String) session.getAttribute("netId");
		if (currentUser!=null && currentUser.equals(netId)){
			clear();
			return "user";//down't do anything now
		}
		User u = adminService.getUserByNetId(netId);
		u.setActive(false);
		adminService.deleteUser(u);//soft delete
		clear();
		return "user";
	}
	
	public String editit(){
		buttonText="Update";
		return "user";
	}
	
	void clear(){
		netId="";
		name="";
		admin=false;
	}
}
