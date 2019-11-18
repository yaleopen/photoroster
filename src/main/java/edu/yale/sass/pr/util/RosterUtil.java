package edu.yale.sass.pr.util;

import edu.yale.sass.pr.bean.Constants;
import edu.yale.sass.pr.service.Person;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RosterUtil implements Constants{
	public static void buildUserRoleStrings(List<Person> persons, HttpSession session){
		Map<String, Integer> roles = new HashMap<String, Integer> ();
		int instructor =0;
		int student = 0;
		int auditor = 0;
		int ta = 0;
		int noRole = 0;
		int observer = 0;
		int designer = 0;
		int viewer =0;
		int shopper = 0;
		int guest = 0;
		for(Person person : persons){ 
			if (person==null){
				continue;
			}
			
			if (person.getRole()==null) {
				noRole++;
				continue;
			}
			if (person.getRole().equals(OBSERVER_ROLE)) observer++;
			if (person.getRole().equals(STUDENT_ROLE)) student++;
			if (person.getRole().equals(INSTRUCTOR_ROLE)) instructor++;
			if (person.getRole().equals(TA_ROLE)) ta++;
			if (person.getRole().equals(AUDITOR_ROLE)) auditor++;

			if (person.getRole().equals(SHOPPER_ROLE)) shopper++;
			if (person.getRole().equals(VIEWER_ROLE)) viewer++;
			if (person.getRole().equals(DESIGNER_ROLE)) designer++;
			if (person.getRole().equals(GUEST_ROLE)) guest++;
			
		}
		StringBuilder detailMessage  = new StringBuilder ();
		detailMessage.append("(");
		if (auditor > 0){
			detailMessage.append(auditor).append(" in Auditor role");
		}
		if (observer > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(observer).append(" in Observer role");
		}
		
		if (instructor > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(instructor).append(" in Instructor role");
		}
		if (student > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(student).append(" in Student role");
		}
		if (noRole > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(noRole).append(" with No role");
		}
		if (designer > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(designer).append(" in Designer role");
		}
		if (ta > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(ta).append(" in Teaching Assistant role");
		}
		if (shopper > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(shopper).append(" in Shopper role");
		}
		if (viewer > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(viewer).append(" in Viewer role");
		}
		if (guest > 0){

			if (detailMessage.length()>1) detailMessage.append(", ");
			detailMessage.append(guest).append(" in Guest role");
		}
		
		detailMessage.append(")");
		session.setAttribute("detailedMessage", detailMessage.toString());
	}

}
