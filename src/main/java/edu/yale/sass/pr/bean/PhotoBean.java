package edu.yale.sass.pr.bean;

import edu.yale.sass.pr.service.Person;
import edu.yale.sass.pr.service.YalePhotoDirectoryService;
import edu.yale.sass.pr.util.RosterUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import java.util.*;

@ManagedBean(name = "photoBean")
@SessionScoped
public class PhotoBean implements Constants{

	@ManagedProperty(value = "#{YalePhotoDirectoryService}")
	private YalePhotoDirectoryService yalePhotoDirectoryService;

	public String overview() {
		return "overview";
	}

	public String picture() {
		return "picture";
	}

	private static final String FIRST_NAME = "First Name";
	private static final String LAST_NAME = "Last Name";
	private static final String ROLE = "Role";
	private static final String CLASS = "Class";
	private static final String MAJOR = "Major";

	private int count;
	private String tableWidth = "4";
	private String viewButtonText = "View in Single Column";
	private String detailedMessage;
	private String title;
	private String section;
	private String sortOption = LAST_NAME;

	private String[] sections = { "All Available", "---------------------", "Acct 270" };
	private String[] sortOptions = {"", FIRST_NAME, LAST_NAME, ROLE, CLASS, MAJOR};

	public String getViewButtonText() {
		return viewButtonText;
	}

	public void setViewButtonText(String viewButtonText) {
		this.viewButtonText = viewButtonText;
	}

	public String getTableWidth() {
		return tableWidth;
	}

	public void setTableWidth(String tableWidth) {
		this.tableWidth = tableWidth;
	}

	public int getCount() {

		count =0;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		try {
			count = (Integer) session.getAttribute("UsersCount");
		
		}
		catch(Exception ignored){
			
		}
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDetailedMessage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		detailedMessage = (String) session.getAttribute("detailedMessage");
 
		return detailedMessage;
	}

	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}

	public String getTitle() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		title = (String) session.getAttribute("courseTitle");
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSection() {
		 
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	
	public String[] getSections() { //todo
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		sections = (String []) session.getAttribute("courseSections");
		return sections;
	}

	public void setSections(String[] sections) {
		this.sections = sections;
	}
 

	public YalePhotoDirectoryService getYalePhotoDirectoryService() {
		return yalePhotoDirectoryService;
	}

	public void setYalePhotoDirectoryService(YalePhotoDirectoryService yalePhotoDirectoryService) {
		this.yalePhotoDirectoryService = yalePhotoDirectoryService;
	}

	List<Person> people;

	public List<Person> getPeople() {
		if (changed){
			//changed = false;
			return people;
		}
		 
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		people = (List<Person>) session.getAttribute("people");
		if(people != null){
			sortBy(sortOption);
		}
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	private String pictureGrid = "pics_grid";
	
	public String getPictureGrid() {
		return pictureGrid;
	}

	public void setPictureGrid(String pictureGrid) {
		this.pictureGrid = pictureGrid;
	}

	/**
	 * change the width of the display
	 * @return
	 */
	public String changeWidth() {
		if (tableWidth.equals("4")) {
			tableWidth = "1";
			viewButtonText = "View in Table";
			pictureGrid="pics_grid_small";
		} else {
			tableWidth = "4";
			viewButtonText = "View in Single Column";
			pictureGrid="pics_grid";
		}

		return "picutre";
	}
	
	boolean changed = false;
	public void sectionChanged(ValueChangeEvent e){
		String value = e.getNewValue().toString();
		changed = true;
		section = value;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (value.equals("All Available")){
			people = (List<Person>) session.getAttribute("people");
		}
		else{			
			people = (List<Person>) session.getAttribute(COURSE+"-"+value);
		}
		if (people!=null){
			people.removeIf(Objects::isNull);
			RosterUtil.buildUserRoleStrings(people, session);
			count = people.size();
			session.setAttribute("UsersCount", count);
			session.setAttribute("exportPeople", people);
			sortBy(sortOption);
		}
		else{
			session.setAttribute("UsersCount", 0);
			session.setAttribute("detailedMessage", "");
		}
	}
	 
 
	String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	
	void search(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		people = (List<Person>) session.getAttribute("people");
		List<Person> result = new ArrayList<Person> ();
		if (people==null) {
			changed = true;
			people = result;
			return;
		}
		for(int i =0; i < people.size(); i++){
			Person person = people.get(i);
			if (person.hasText(input)){
				result.add(person);
			}
		}
		changed = true;
		people = result;
	}
	
	public String findPicture(){
		search();
		return "picture";
	}
	
	public String find(){
		search();
		return "overview";
	}

	public void sortChanged(ValueChangeEvent e){
		String sortValue = e.getNewValue().toString();
		sortOption = sortValue;
		sortBy(sortValue);
	}

	public void sortBy(String sortValue){
		if(sortValue.equals(FIRST_NAME)){
			Collections.sort(people, new Comparator<Person>() {
				public int compare(Person o1, Person o2) {
					return o1.getFirstName().compareTo(o2.getFirstName());
				}
			});
		}
		else if(sortValue.equals(LAST_NAME)){
			Collections.sort(people, new Comparator<Person>() {
				public int compare(Person o1, Person o2) {
					return o1.getLastName().compareTo(o2.getLastName());
				}
			});
		}
		else if(sortValue.equals(ROLE)){
			Collections.sort(people, new Comparator<Person>() {
				public int compare(Person o1, Person o2) {
					return o1.getRole().compareTo(o2.getRole());
				}
			});
		}
		else if(sortValue.equals(CLASS)){
			Collections.sort(people, new Comparator<Person>() {
				public int compare(Person o1, Person o2) {
					return o1.getClassYear().compareTo(o2.getClassYear());
				}
			});
		}
		else if(sortValue.equals(MAJOR)){
			Collections.sort(people, new Comparator<Person>() {
				public int compare(Person o1, Person o2) {
					return o1.getMajor().compareTo(o2.getMajor());
				}
			});
		}
	}

	public String[] getSortOptions(){
		return sortOptions;
	}


	public String getSortOption() {
		return sortOption;
	}

	public void setSortOption(String sortOption) {
		this.sortOption = sortOption;
	}
}