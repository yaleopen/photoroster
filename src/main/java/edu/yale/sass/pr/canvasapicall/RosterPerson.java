package edu.yale.sass.pr.canvasapicall;

import java.util.ArrayList;
import java.util.List;

public class RosterPerson {

    private String email;
    private String section;
    private String role;
    private String name;
    private String upi;
    private StudentData studentData;
    private boolean hasImage;
    private List<String> sectionsStrings;
    private String netId;

    RosterPerson() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    List<String> getSectionsStrings() {
        if (sectionsStrings == null) {
            sectionsStrings = new ArrayList<String>();
        }
        return sectionsStrings;
    }

    public void setSectionsStrings(List<String> sectionsStrings) {
        this.sectionsStrings = sectionsStrings;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public StudentData getStudentData() {
        return studentData;
    }

    public void setStudentData(StudentData studentData) {
        this.studentData = studentData;
    }
}
