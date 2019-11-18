package edu.yale.sass.pr.canvasapicall;

import java.io.Serializable;

public class StudentData implements Serializable {
    public static final long serialVersionUID = 1L;
    private String classYear;
    private String major;
    private String residentialCollege;
    private String sid;
    private String bannerId;
    private String school;

    public StudentData() {
    }

    public String getClassYear() {
        return this.classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getResidentialCollege() {
        return this.residentialCollege;
    }

    public void setResidentialCollege(String residentialCollege) {
        this.residentialCollege = residentialCollege;
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBannerId() {
        return this.bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}