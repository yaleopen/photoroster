package edu.yale.sass.pr.canvasapicall;

import edu.yale.sass.pr.bean.Constants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CanvasAPI implements Constants {

    private CacheManager manager;

    private Cache studentDataCache;

    public void init() {
        manager = CacheManager.create();

        int MAX_ELEMENTS_IN_CACHE = 10000;
        // 24hours
        int TTI = 24 * 60 * 60;
        // 24hours
        int TTL = 24 * 60 * 60;
        String STUDENTDATA_MAP = "studentdata_map";
        studentDataCache = new Cache(STUDENTDATA_MAP, MAX_ELEMENTS_IN_CACHE, false,
                false, TTL, TTI);
        manager.addCache(studentDataCache);
    }

    public void close() {
        manager.shutdown();
    }
    public static void main(String[] args) {

    }

    private String getRole(String type) {

        String role;
        if (type.startsWith("Student")) {
            role = STUDENT_ROLE;

        } else if (type.startsWith("Teacher")) {
            role = INSTRUCTOR_ROLE;

        } else if (type.startsWith("Ta")) {
            role = TA_ROLE;

        } else if (type.startsWith("Designer")) {
            role = "Designer";


        } else if (type.startsWith("Observer")) {
            role = "Observer";

        } else {
            role = type;// "Other";
        }
        return role;

    }


    /**
     * This method will build the section information
     */
    public void getRosterPersons(String courseid, String apiHost, String apiToken, HttpSession session, Map<String, List<RosterPerson>> sectionUsers) {


        List<RosterPerson> personList = new ArrayList<>();

        List<JSONObject> sectionEnrollments = getCanvasSectionEnrollments(courseid, apiHost, apiToken);

        StringBuilder courseName = new StringBuilder();
        List<String> courseSections = new ArrayList<>();
        courseSections.add("All Available");
        courseSections.add("---------------------");

        try {

            for (JSONObject sectionEnrollment : sectionEnrollments) {
                if (courseName.length() > 0) {
                    courseName.append("/");
                }
                String sectionName = sectionEnrollment.getString("name");

                courseName.append(sectionName);

                sectionUsers.computeIfAbsent(COURSE + "-" + sectionName, k -> new ArrayList<>());
                courseSections.add(sectionName);

                JSONArray studentObjs = sectionEnrollment.optJSONArray("students");
                if(studentObjs != null){
                    IntStream.range(0,studentObjs.length()).forEach(i -> processStudentObjs(studentObjs.getJSONObject(i), personList, sectionUsers, sectionName));
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        String[] courseSectionsArray = new String[courseSections.size()];
        for (int i = 0; i < courseSections.size(); i++) courseSectionsArray[i] = courseSections.get(i);
        if (session != null) {
            String courseTerm = (String) session.getAttribute("course_term");
            session.setAttribute("courseTitle", courseName.toString() + "(" + courseTerm + ")");
            session.setAttribute("courseSections", courseSectionsArray);
        }
    }

    private void processStudentObjs(JSONObject studentObj, List personList,  Map<String, List<RosterPerson>> singleSectionUsers, String sectionName){
        try {
            String login_id = studentObj.getString("login_id");
            String studentName = studentObj.getString("sortable_name");

            RosterPerson rosterPerson = new RosterPerson();
            String sectionname = "---";
            rosterPerson.setSection(sectionname);
            rosterPerson.getSectionsStrings().add(sectionname);
            rosterPerson.setName(studentName);
            rosterPerson.setNetId(login_id);
            singleSectionUsers.get(COURSE + "-" + sectionName).add(rosterPerson);

            String userEmail = "";
            if (login_id != null && login_id.contains("@")) {
                userEmail = login_id;
            }
            rosterPerson.setEmail(userEmail);
            String role = "Student";
            rosterPerson.setRole(role);
            personList.add(rosterPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method will get all the enrollments from a given course
     */

    public List<RosterPerson> getRosterPersonsEnrollments(String courseid, String apiHost, String apiToken, HttpSession session) {


        List<RosterPerson> personList = new ArrayList<>();
        List<JSONObject> courseUsers = getCanvasCourseUsers(courseid, apiHost, apiToken);

        HashMap<String, RosterPerson> hm = new HashMap<>();
        try {
            courseUsers.forEach(user -> {
                if(user == null){
                    return;
                }
                JSONObject enrollment = user.getJSONArray("enrollments").getJSONObject(0);
                String loginId = "";
                String upi = "";
                String userEmail = "";
                try{
                    loginId = user.getString("login_id");
                    upi = user.getString(Constants.SIS_USER_ID);
                } catch(Exception e){
                    e.printStackTrace();
                } finally{
                    String studentName = user.getString("sortable_name");
                    if(hm.containsKey(loginId) || studentName.equals("Test Student")) {
                        //do nothing
                    }
                    else{
                        RosterPerson rosterPerson = new RosterPerson();
                        rosterPerson.setName(studentName);
                        rosterPerson.setUpi(upi);
                        rosterPerson.setNetId(loginId);
                        if(upi.length() > 0){
                            rosterPerson.setStudentData(getStudentData(upi,Constants.SIS_USER_ID,apiHost,apiToken));
                        }

                        String role = getRole(enrollment.getString("role"));
                        rosterPerson.setRole(role);
                        try{
                            userEmail = user.getString("email");
                        } catch(Exception e){
                            e.printStackTrace();
                        } finally{
                            rosterPerson.setEmail(userEmail);
                            personList.add(rosterPerson);
                            hm.put(loginId, rosterPerson);
                        }
                    }
                    }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("course_term", "");
        return personList;
    }

    /**
     * section info from canvas
     */
    private List<JSONObject> getCanvasSectionEnrollments(String courseid, String apiHost, String apiToken) {
        System.out.println("getCanvasSectionEnrollments");
        List<JSONObject> sectionEnrollments = new ArrayList<>();
        String apiPath = "/api/v1/courses/" + courseid + "/sections";
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair("include[]", "students"));
        nameValuePairs.add(new BasicNameValuePair("per_page", "100"));
        JSONArray array = new JSONArray(getCanvasData(apiPath, nameValuePairs, apiHost, apiToken));

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObj = array.getJSONObject(i);
            sectionEnrollments.add(jsonObj);
        }
        return sectionEnrollments;
    }


    /**
     * This method will get all the enrollments for a given course
     */
    private List<JSONObject> getCanvasCourseUsers(String courseid, String apiHost, String apiToken) {
        List<JSONObject> enrollments = new ArrayList<>();
        int perPage = 100;
        int page = 1;
        while (true) {
            List<JSONObject> singlePageEnrollments = getSinglePageEnrollments(courseid, apiHost, apiToken, perPage, page);
            if (singlePageEnrollments.size() == 0) {
                break;
            }
            enrollments.addAll(singlePageEnrollments);
            page++;
        }
        return enrollments;
    }

    /**
     * Get a single page
     */
    private List<JSONObject> getSinglePageEnrollments(String courseid, String apiHost, String apiToken, int perPage, int page) {
        List<JSONObject> enrollments = new ArrayList<>();

        String apiPath = "/api/v1/courses/" + courseid + "/users";
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair("per_page", perPage + ""));
        nameValuePairs.add(new BasicNameValuePair("page", page + ""));
        nameValuePairs.add(new BasicNameValuePair("include[]", "email"));
        nameValuePairs.add(new BasicNameValuePair("include[]", "enrollments"));

        JSONArray array = new JSONArray(getCanvasData(apiPath, nameValuePairs, apiHost, apiToken));
        IntStream.range(0,array.length()).forEach(i -> {
            JSONObject jsonObj = array.getJSONObject(i);
            enrollments.add(jsonObj);
        });

        return enrollments;
    }

    /**
     * Get a student data
     */
    public StudentData getStudentData(String id, String idType, String apiHost, String apiToken) {
        String apiPath;
        Element element = studentDataCache.get(id);
        if(element != null){
            return (StudentData)element.getValue();
        }

        if(idType.equals(Constants.SIS_USER_ID)){
            apiPath = "/api/v1/users/sis_user_id:" + id + "/custom_data/student";
        }
        else if(idType.equals(Constants.SIS_LOGIN_ID)){
            apiPath = "/api/v1/users/sis_login_id:" + id + "/custom_data/student";
        }
        else{
            return null;
        }

        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair("ns", "edu.yale.canvas"));


        JSONObject jsonObj = new JSONObject(getCanvasData(apiPath, nameValuePairs, apiHost, apiToken));
        System.out.println("jsonObj: " + jsonObj.toString());
        JSONObject data;
        StudentData sd = new StudentData();
        try{
            data = jsonObj.getJSONObject("data");
            if(data != null){
                sd.setBannerId(data.get("banner_id").toString());
                sd.setClassYear(data.get("class_year").toString());
                sd.setMajor(data.get("major").toString());
                String school = data.getString("school");
                sd.setSid(data.get("sid").toString());
                sd.setSchool(school);
            }
        }
        catch(Exception e){
            System.out.println("Exception parsing student data json");
        }
        Element e = new Element(id,sd);
        studentDataCache.put(e);
        return sd;
    }


    /**
     * This is the base method
     */

    private String getCanvasData(String apiPath, List<NameValuePair> namevaluepair, String apiHost, String apiToken) {
        // this method calls a Canvas API.
        StringBuilder result = new StringBuilder();
        System.out.println("Call canvase with apiPath = " + apiPath);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme("https").setHost(apiHost).setPath(apiPath);
            if (namevaluepair != null && !namevaluepair.isEmpty()) {
                uriBuilder.setParameters(namevaluepair);
            }

            URI uri = uriBuilder.build();
            System.out.println("URI  ----" + uri);
            HttpGet httpget = new HttpGet(uri);
            httpget.addHeader("Authorization", "Bearer " + apiToken);

            HttpResponse response = httpclient.execute(httpget);
            System.out.println("Response Code: " + response.getStatusLine());


            // only 200 continue
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Response String: " + result.toString());
        return result.toString();
    }
}
