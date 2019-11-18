/*
 * PeopleFilePersonImpl.java
 *
 * Created on April 5, 2006, 9:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.yale.sass.pr.service.impl;

import edu.yale.sass.pr.service.Person;
import org.primefaces.model.StreamedContent;

import java.io.Serializable;
 
/**
 *
 * @author mikea
 */
public class PersonImpl implements Person, Serializable  
{
    
    /** Creates a new instance of PeopleFilePersonImpl */
    public PersonImpl()
    {
    }

    /**
     * Holds value of property firstName.
     */
    private String firstName;

    /**
     * Getter for property firstName.
     * @return Value of property firstName.
     */
    public String getFirstName()
    {
        return this.firstName;
    }

    /**
     * Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Holds value of property middleName.
     */
    private String middleName;

    /**
     * Getter for property middleName.
     * @return Value of property middleName.
     */
    public String getMiddleName()
    {
        return this.middleName;
    }

    /**
     * Setter for property middleName.
     * @param middleName New value of property middleName.
     */
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    /**
     * Holds value of property lastName.
     */
    private String lastName;

    /**
     * Getter for property lastName.
     * @return Value of property lastName.
     */
    public String getLastName()
    {
        return this.lastName;
    }

    /**
     * Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Holds value of property email.
     */
    private String email;

    /**
     * Getter for property email.
     * @return Value of property email.
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Setter for property email.
     * @param email New value of property email.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Holds value of property peopleId.
     */
    private String peopleId;

    /**
     * Getter for property peopleId.
     * @return Value of property peopleId.
     */
    public String getPeopleId()
    {
        return this.peopleId;
    }

    /**
     * Setter for property peopleId.
     * @param peopleId New value of property peopleId.
     */
    public void setPeopleId(String peopleId)
    {
        this.peopleId = peopleId;
    }

    /**
     * Holds value of property major.
     */
    private String major;

    /**
     * Getter for property major.
     * @return Value of property major.
     */
    public String getMajor()
    {
        return this.major;
    }

    /**
     * Setter for property major.
     * @param major New value of property major.
     */
    public void setMajor(String major)
    {
        this.major = major;
    }

    /**
     * Holds value of property classYear.
     */
    private String classYear;

    /**
     * Getter for property classYear.
     * @return Value of property classYear.
     */
    public String getClassYear()
    {
        return this.classYear;
    }

    /**
     * Setter for property classYear.
     * @param classYear New value of property classYear.
     */
    public void setClassYear(String classYear)
    {
        this.classYear = classYear;
    }

    /**
     * Holds value of property school.
     */
    private String school;

    /**
     * Getter for property school.
     * @return Value of property school.
     */
    public String getSchool()
    {
        return this.school;
    }

    /**
     * Setter for property school.
     * @param school New value of property school.
     */
    public void setSchool(String school)
    {
        this.school = school;
    }

    /**
     * Holds value of property residentialCollege.
     */
    private String residentialCollege;

    /**
     * Getter for property residentialCollege.
     * @return Value of property residentialCollege.
     */
    public String getResidentialCollege()
    {
        return this.residentialCollege;
    }

    /**
     * Setter for property residentialCollege.
     * @param residentialCollege New value of property residentialCollege.
     */
    public void setResidentialCollege(String residentialCollege)
    {
        this.residentialCollege = residentialCollege;
    }

    /**
     * Holds value of property personType.
     */
    private String personType;

    /**
     * Getter for property personType.
     * @return Value of property personType.
     */
    public String getPersonType()
    {
        return this.personType;
    }

    /**
     * Setter for property personType.
     * @param personType New value of property personType.
     */
    public void setPersonType(String personType)
    {
        this.personType = personType;
    }

    /**
     * Holds value of property studentFlag.
     */
    private String studentFlag;

    /**
     * Getter for property studentFlag.
     * @return Value of property studentFlag.
     */
    public String getStudentFlag()
    {
        return this.studentFlag;
    }

    /**
     * Setter for property studentFlag.
     * @param studentFlag New value of property studentFlag.
     */
    public void setStudentFlag(String studentFlag)
    {
        this.studentFlag = studentFlag;
    }

    /**
     * Holds value of property netid.
     */
    private String netid;

    /**
     * Getter for property netid.
     * @return Value of property netid.
     */
    public String getNetid()
    {
        return this.netid;
    }

    /**
     * Setter for property netid.
     * @param netid New value of property netid.
     */
    public void setNetid(String netid)
    {
        this.netid = netid;
    }

    /**
     * Holds value of property bannerId.
     */
    private String bannerId;

    /**
     * Getter for property bannerId.
     * @return Value of property bannerId.
     */
    public String getBannerId()
    {
        return this.bannerId;
    }

    /**
     * Setter for property bannerId.
     * @param bannerId New value of property bannerId.
     */
    public void setBannerId(String bannerId)
    {
        this.bannerId = bannerId;
    }

    public int compareTo(Object o)
    {
        Person other = (Person) o;
        int retval = compareStrings(getLastName(), other.getLastName());
        if (retval != 0) return retval;
        retval = compareStrings(getFirstName(), other.getFirstName());
        if (retval != 0) return retval;
        retval = compareStrings(getMiddleName(), other.getMiddleName());
        if (retval != 0) return retval;
        return getNetid().compareTo(other.getNetid());
    }

    private int compareStrings(String s1, String s2)
    {
        if (s1 == null) return 0;
        if (s2 == null) return 0;
        return s1.compareTo(s2);
    }
    
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Person)) return false;
        Person other = (Person) obj;
        return getNetid().equals(other.getNetid());
    }
    

    private String preferredName;
    
	public String getPreferredName(){
		return this.preferredName;
	}	
	
	public void setPreferredName(String preferredName){
		this.preferredName = preferredName;
	}
    
	StreamedContent image;
	

	public StreamedContent getImage(){
		return image;
	}
	
	public void setImage(StreamedContent sc){
		this.image = sc;
	}

	private String name;


    private String sid;

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

	public void setName(String name) {
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	private String role;

	public void setRole(String role) {
		this.role = role;
		
	}

	public String getRole() {
		return role;
	}
	
	public String toString (){
		return "name= " + name + " role = "+ role + " major = "+ major + " year = "+ classYear + " college="+ residentialCollege+" email = "+ email+" sid = "+sid;
	}

	public boolean hasText(String s){
		if (s==null || name==null || netid==null){
			return false;
		}
		s = s.toLowerCase();
		
		String lowName = name.toLowerCase();
		if (lowName !=null && lowName.indexOf(s)>=0){
			return true;
		}
		String lowNetid = netid.toLowerCase();
		if (lowNetid!=null && lowNetid.indexOf(s)>=0){
			return true;
		}
	 
		return false;
	}
}
