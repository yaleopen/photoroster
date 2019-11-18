package edu.yale.sass.pr;

import edu.yale.sass.pr.service.Person;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class ExportServlet extends HttpServlet {

    public static final long serialVersionUID = -4;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/NotAllowed.jsf");
            requestDispatcher.forward(request, response);
        }

        String site = (String) session.getAttribute("courseTitle");
        List<Person> rosterMembers = (List<Person>) session.getAttribute("exportPeople");
        if (site == null || rosterMembers == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/empty.jsf");
            requestDispatcher.forward(request, response);
        }
        String filename = getFilename(site);
        addResponseHeader(response, filename);

        List<List<String>> dataInRows = new ArrayList<List<String>>();

        List<String> title = new ArrayList<String>();
        title.add(site);
        dataInRows.add(title);
        dataInRows.add(new ArrayList<String>());
        List<String> header = createColumnHeader(null, site);

        if (null != rosterMembers) {
            addOverviewRows(dataInRows, rosterMembers, header, "+++");//this is the most important one
        }

        Workbook workBook = new HSSFWorkbook();
        Sheet sheet = workBook.createSheet();

        for (int i = 0; i < dataInRows.size(); i++) {

            Row row = sheet.createRow(i);

            for (int j = 0; j < dataInRows.get(i).size(); j++) {

                Cell cell = row.createCell(j);
                cell.setCellValue(dataInRows.get(i).get(j));
            }
        }

        workBook.write(response.getOutputStream());
        response.getOutputStream().close();
    }

    private void addOverviewRows(List<List<String>> dataInRows,
                                 List<Person> rosterMembers, List<String> header, String siteId) {

        dataInRows.add(header);
        dataInRows.add(new ArrayList<String>());

        for (Person member : rosterMembers) {
            List<String> row = new ArrayList<String>();
            row.add(member.getLastName());
            row.add(member.getFirstName());
            row.add(member.getNetid());
            row.add(member.getEmail());
            row.add(member.getRole());
            setYaleData(member, row);
            dataInRows.add(row);
        }
    }

    private void addResponseHeader(HttpServletResponse response, String filename) {

        response.addHeader("Content-Encoding", "base64");
        response.addHeader("Content-Type", "application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + filename);
    }

    private void setYaleData(Person rosterMember, List<String> row) {
        if ("YC".equals(rosterMember.getSchool())) {
            row.add(rosterMember.getResidentialCollege());
        } else {
            row.add(rosterMember.getSchool());
        }

        row.add(rosterMember.getMajor());
        row.add(rosterMember.getClassYear());
    }

    private List<String> createColumnHeader(Map<String, Object> parameters, String siteId) {

        List<String> header = new ArrayList<String>();
        header.add("Last Name");
        header.add("First Name");
        header.add("Net Id");
        header.add("Email");
        header.add("Role");
        header.add("Col");
        header.add("Major");
        header.add("Year");

        return header;
    }

    private String getFilename(String title) {
        title = title.replaceAll(" ", "_");
        Calendar cal = new GregorianCalendar();
        title = title + "__" + cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.MONTH) + "_" + cal.get(Calendar.DAY_OF_MONTH);
        title = title.substring(0, Math.min(title.length(), 100));
        return title + ".xls";
    }
}
