package edu.yale.sass.pr;

import blackboard.blti.message.BLTIMessage;
import blackboard.blti.provider.BLTIProvider;
import edu.yale.sass.pr.api.AdminService;
import edu.yale.sass.pr.bean.Constants;
import edu.yale.sass.pr.canvasapicall.CanvasAPI;
import edu.yale.sass.pr.canvasapicall.RosterPerson;
import edu.yale.sass.pr.canvasapicall.StudentData;
import edu.yale.sass.pr.model.LTIConsumer;
import edu.yale.sass.pr.model.RosterEvent;
import edu.yale.sass.pr.service.Person;
import edu.yale.sass.pr.service.YalePhotoDirectoryService;
import edu.yale.sass.pr.service.YalePhotoDirectoryServiceException;
import edu.yale.sass.pr.service.impl.PersonImpl;
import edu.yale.sass.pr.util.RosterUtil;
import edu.yale.sass.pr.util.ServerConfig;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class RosterServlet extends HttpServlet implements Constants {

	private static Logger logger = Logger.getLogger(RosterServlet.class);
	public  static final long serialVersionUID = -3;
	private YalePhotoDirectoryService yalePhotoDirectoryService;
	private AdminService adminService;
	private String APITOKEN;
	private String APIHOST;
	private CanvasAPI canvasAPI;

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		try {
			APITOKEN = ServerConfig.getString("apiToken", null);
			APIHOST = ServerConfig.getString("apiHost", "");
			ApplicationContext context
					= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
			yalePhotoDirectoryService = (YalePhotoDirectoryService) context.getBean("YalePhotoDirectoryService");
			adminService = (AdminService) context.getBean("adminService");
			canvasAPI = (CanvasAPI) context.getBean("canvasAPI");
		} catch (Throwable t) {
			throw new ServletException("Failed to initialise RosterTool servlet.", t);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/NotAllowed.jsf");
		requestDispatcher.forward(request, response);
		//   doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate(); //restart new session
		request.setCharacterEncoding("UTF-8");


		String key =(String) request.getParameter("oauth_consumer_key");
		LTIConsumer consumer = adminService.getConsumerByKey(key);

		String courseId = (String)request.getParameter("custom_canvas_course_id"); //this is where the courseId, get from
		String roles =(String)request.getParameter("roles");
		String userId = (String)request.getParameter("custom_canvas_user_login_id");
		String contextTitle = (String)request.getParameter("context_title");
		String contextLabel = (String)request.getParameter("context_label");
		String enrollmentState  = (String) request.getParameter("custom_canvas_enrollment_state");
		String personEmail = (String) request.getParameter("lis_person_contact_email_primary");
		String ltiVersion = (String) request.getParameter("lti_version");
		String consumerGuid = (String) request.getParameter("tool_consumer_instance_guid");
		RosterEvent event = new RosterEvent();
		String clientIP = request.getRemoteHost();
		String browser =  request.getHeader("User-Agent");
		String lms = (String)request.getParameter("ext_lms");
		String platform = (String) request.getParameter("ext_canvas_serverid");//maybe not necessary
		String consumerVersion = request.getParameter("tool_consumer_info_product_family_code")+":"+request.getParameter("tool_consumer_info_version");
		event.setClientIP(clientIP);
		event.setBrowser(browser);
		event.setLms(lms);
		event.setLtiVersion(ltiVersion);
		event.setPlatform(platform);
		event.setRole(roles);
		System.out.println("event----");
		event.setSessionStart(new Date());
		System.out.println("userId----"+userId);
		if (userId==null){
			//	userId ="test";
			RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/NotAllowed.jsf");
			requestDispatcher.forward(request, response);
		}
		event.setUserId(userId);
		event.setCourseId(courseId);
		event.setContextTitle(contextTitle);

		if (consumer==null){
			event.setSuccess(false);

			event.setSession("null "+ new Date().getTime());
			adminService.addEvent(event);

			RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/NotAllowed.jsf");
			requestDispatcher.forward(request, response);

		}

		BLTIMessage ltiMessage = BLTIProvider.getMessage(request);

		String LTISecret = consumer.getSecret();
		consumer.setConsumer_guid(consumerGuid);
		consumer.setConsumer_version(consumerVersion);
		adminService.updateConsumer(consumer);
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(7200);//should be 2 hour for now, should never expired!!!


		if (ltiMessage == null) {
			System.out.println("ltiMessage, LTIKey, or LTISecret is null");
			RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/NotAllowed.jsf");
			requestDispatcher.forward(request, response);
		}
		else {
			if (BLTIProvider.isValid(ltiMessage, LTISecret) )
			{
				System.out.println(" the lti sercret is valid ....");
				event.setSessionEnd(null);
				event.setSuccess(true);

				event.setSession(session.getId());
				adminService.addEvent(event);
			}
			else {

				System.out.println("the key/secret is not valid");
				RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/UnAuthorized.jsf");
				requestDispatcher.forward(request, response);
			}
		}

		Map map =request.getParameterMap();

		Set keys = map.keySet();


		ServletContext context = getServletContext();

		if (!isInstructor(roles) && !isTA(roles) && !isAdmin(roles)){ //should see nothing, but should not crash
			session.setAttribute("people", null);//nothing will be in there
			session.setAttribute("UsersCount", 0);
			session.setAttribute("courseTitle", contextLabel);
			session.setAttribute("courseSections", new String[]{});
			RequestDispatcher dispatcher = context.getRequestDispatcher("/picture.jsf");
			dispatcher.forward(request, response);

		}
		else{

			System.out.println("the course id is "+courseId);
			if (courseId ==null){
				//courseId = "197";

				System.out.println("the course id is null");
				RequestDispatcher   requestDispatcher= request.getRequestDispatcher("/UnAuthorized.jsf");
				requestDispatcher.forward(request, response);
			}
			try{

				Map<String, List<RosterPerson>> sectionUsers = new HashMap<String, List<RosterPerson>> ();
				loadPhotos(request, courseId, session, sectionUsers, roles, userId);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			System.out.println("load the pictures");
			RequestDispatcher dispatcher = context.getRequestDispatcher("/picture.jsf");
			dispatcher.forward(request, response);
		}

	}

	void loadPhotos(HttpServletRequest request, String courseId, HttpSession session, Map<String, List<RosterPerson>> sectionUsers, String roles, String userId){
		List<RosterPerson> rosterPersons = canvasAPI.getRosterPersonsEnrollments(courseId, APIHOST, APITOKEN, session);
		//set section info
		canvasAPI.getRosterPersons(courseId, APIHOST, APITOKEN, session, sectionUsers);
		Map<String,String> ids = getNetIdBannerIdMap(rosterPersons);//must get this from Canvas

		List<Person> persons = getPersonsList(new ArrayList<>(ids.keySet()));
		loadPhotos(ids, roles, userId);  //load the real photos from the Yale database

		Map<String, Person> photoPersons = new HashMap<> ();
		for(Person person : persons){
			photoPersons.put(person.getNetid(), person);
		}

		for(RosterPerson rosterPerson: rosterPersons){
			try{
				String netId = rosterPerson.getNetId();
				Person person;
				boolean isStudent = true;
				if(photoPersons.get(netId) != null){
					person = photoPersons.get(netId);
				}
				else{
					person = new PersonImpl();
					person.setNetid(netId);
					isStudent = false;
				}
				person.setName(rosterPerson.getName());
				String[] splitName = rosterPerson.getName().split(",");
				if(splitName.length == 2){
					person.setFirstName(splitName[1]);
					person.setLastName(splitName[0]);
				}
				else{
					person.setFirstName(rosterPerson.getName());
					person.setLastName(rosterPerson.getName());
				}

				person.setEmail(rosterPerson.getEmail());
				person.setRole(rosterPerson.getRole());
				person.setClassYear("");
				person.setMajor("");
				person.setResidentialCollege("");

				StudentData sd = rosterPerson.getStudentData();
				if (sd != null){
					if(sd.getClassYear() != null){
						person.setClassYear(sd.getClassYear());
					}
					if(sd.getMajor() != null){
						person.setMajor(sd.getMajor());
					}
					if(sd.getResidentialCollege() != null){
						person.setResidentialCollege(sd.getResidentialCollege());
					}
					String email = rosterPerson.getEmail();
					if(email != null){
						person.setEmail(email);
					}
					if(sd.getSid() != null){
						person.setSid(sd.getSid());
					}
					if(sd.getSchool() != null){
						person.setSchool(sd.getSchool());
					}
				}
				if(!isStudent){
					photoPersons.put(netId,person);
					persons.add(person);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}


		}

		session.setAttribute("people", persons);
		session.setAttribute("exportPeople", persons);
		session.setAttribute("UsersCount", persons.size());
		RosterUtil.buildUserRoleStrings(persons, session);


		buildSectionUsers(sectionUsers, photoPersons, session);//
	}

	private void buildSectionUsers(Map<String, List<RosterPerson>> sectionUsers, Map<String, Person> persons, HttpSession session){

		Map<String, List<Person>> sectionUsers2 = new HashMap<String, List<Person>> ();
		//list of section names
		Set<String> keys = sectionUsers.keySet();
		//iterate over each section and respective roster
		System.out.println("BUILD SECTION USERS");
		for (String key : keys){
			System.out.println(key);
			List<Person> single = sectionUsers2.computeIfAbsent(key, k -> new ArrayList<Person>());
			List<RosterPerson> rPersons = sectionUsers.get(key);
			rPersons.removeIf(Objects::isNull);
			for(RosterPerson rPerson : rPersons){
				String netId = rPerson.getNetId();
				System.out.println(netId);
				single.add(persons.get(netId));
			}

			session.setAttribute(key, single);
		}

	}

	private List<Person> getPersonsList(List<String> ids){
		List<Person> list = new ArrayList<Person> ();
		for(String netid : ids){

			Person person = new PersonImpl();
			person.setNetid(netid);
			list.add(person);
		}
		return list;
	}

	public void loadPhotos(Map<String,String> ids, String roles, String userId){
		List persons = null;
		try{
			persons = yalePhotoDirectoryService.loadPhotos(ids, roles, userId);
		}
		catch(YalePhotoDirectoryServiceException e){
			e.printStackTrace();
		}

	}





	boolean isInstructor(String s) {
		return s != null && s.contains("Instructor");
	}


	boolean isTA(String s) {
		return s != null && s.contains("TeachingAssistant");
	}

	boolean isAdmin(String s) {
		return s != null && s.contains("Administrator");
	}

	public Map<String, String> getNetIdBannerIdMap(List<RosterPerson> rosterPersons){

		Map<String, String> ids = new HashMap<String, String>();
		for(RosterPerson rosterPerson : rosterPersons){
			if(rosterPerson != null && rosterPerson.getStudentData() != null && rosterPerson.getStudentData().getBannerId() != null){
				ids.put(rosterPerson.getNetId(), rosterPerson.getStudentData().getBannerId());
			}
		}
		return ids;
	}


}
