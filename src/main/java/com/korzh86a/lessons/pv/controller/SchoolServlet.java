package com.korzh86a.lessons.pv.controller;

import com.korzh86a.lessons.pv.dao.SchoolDao;
import com.korzh86a.lessons.pv.dao.SchoolDaoException;
import com.korzh86a.lessons.pv.entity.Mark;
import com.korzh86a.lessons.pv.entity.Student;
import com.korzh86a.lessons.pv.entity.Subject;
import com.korzh86a.lessons.pv.entity.SubjectWithMarks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configurable
public class SchoolServlet extends HttpServlet {
	@Autowired
	private SchoolDao schoolDao;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public void destroy() {
		if (schoolDao != null) {
			schoolDao.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String action = req.getServletPath();
		resp.setContentType("text/html;charset=utf-8");

		try {
			switch (action) {
				case "/newStudent" -> showNewStudentForm(req, resp);
				case "/addStudent" -> addStudent(req, resp);
				case "/editStudent" -> showEditStudentForm(req, resp);
				case "/updateStudent" -> updateStudent(req, resp);
				case "/deleteStudent" -> deleteStudent(req, resp);
				case "/allSubjects" -> allSubjects(req, resp);
				case "/newSubject" -> showNewSubjectForm(req, resp);
				case "/addSubject" -> addSubject(req, resp);
				case "/editSubject" -> showEditSubjectForm(req, resp);
				case "/updateSubject" -> updateSubject(req, resp);
				case "/deleteSubject" -> deleteSubject(req, resp);
				case "/getStudentMarksById" -> allMarks(req, resp);
				case "/addSubjectToStudent" -> addSubjectToStudent(req, resp);
				default -> allStudents(req, resp);
			}
		} catch (SchoolDaoException throwables) {
			throw new ServletException("Ошибка с доступом к базе данных", throwables);
		}
	}

	private void allStudents(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SchoolDaoException {
		long studentsAmount = schoolDao.getStudentAmount();

		int representedStudentsAmount = validateValue(
				req.getParameter("representedStudentsAmount"), 5);

		int actualPageFirstRow = validateValue(
				req.getParameter("actualPageFirstRow"), 0);

		List<Student> studentsList = schoolDao.getAllStudents(representedStudentsAmount, actualPageFirstRow);

		req.setAttribute("studentsList", studentsList);
		req.setAttribute("studentsAmount", studentsAmount);
		req.setAttribute("representedStudentsAmount", representedStudentsAmount);
		req.setAttribute("actualPageFirstRow", actualPageFirstRow);

		RequestDispatcher dispatcher = req.getRequestDispatcher("view/StudentList.jsp");
		dispatcher.forward(req, resp);
	}

	private int validateValue(String strValue, int defaultValue) {
		int result;
		if (strValue == null) {
			result = defaultValue;
		} else if ((result = Integer.parseInt(strValue)) < 1){
			result = defaultValue;
		}
		return result;
	}

	private void allSubjects(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SchoolDaoException {
		List<Subject> allSubjects;
		allSubjects = schoolDao.getAllSubjects();

		req.setAttribute("allSubjects", allSubjects);

		RequestDispatcher dispatcher = req.getRequestDispatcher("view/SubjectList.jsp");
		dispatcher.forward(req, resp);
	}

	private void allMarks(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SchoolDaoException {
		int id = Integer.parseInt(req.getParameter("id"));
		Map<Subject, List<Mark>> allMarks = schoolDao.getStudentMarks(schoolDao.getStudent(id));
		List<Subject> allSubjects = schoolDao.getAllSubjects();

		req.setAttribute("studentId", id);
		req.setAttribute("allMarks", allMarks);
		req.setAttribute("allSubjects", allSubjects);

		RequestDispatcher dispatcher = req.getRequestDispatcher("view/MarksList.jsp");
		dispatcher.forward(req, resp);
	}

	private void showNewStudentForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/StudentForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void addStudent(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SchoolDaoException {
		Student student = new Student(
				req.getParameter("name"),
				req.getParameter("surname"),
				req.getParameter("birthdate"),
				req.getParameter("enterYear")
		);

		checkStudent(student);

		schoolDao.addSchoolObject(student);

		resp.sendRedirect("list");
	}

	private void checkStudent (Student student) throws SchoolDaoException {
		try {
			if (student.getFirstName() == null || student.getSecondName() == null ||
					student.getBirthDate() == null || student.getEnterYear() == null) {
				throw new SchoolDaoException("fill all data");
			}

			int enterYear = Integer.parseInt(student.getEnterYear());
			if (enterYear > 2100 || enterYear < 1900){
				throw new SchoolDaoException("enter correct entered year");
			}

			if (!validateDate(student.getBirthDate())) {
				throw new SchoolDaoException("enter correct birth date");
			}

		} catch (NumberFormatException e) {
			throw new SchoolDaoException("enter correct entered year");
		}
	}

	private boolean validateDate (String date){
		Pattern pattern = Pattern.compile("((19|20)\\d\\d)[-](0?[1-9]|[12][0-9]|3[01])[-](0?[1-9]|1[012])");
		Matcher matcher = pattern.matcher(date);

		if(matcher.matches()){
			matcher.reset();

			if(matcher.find()){
				String day = matcher.group(3);
				String month = matcher.group(2);
				int year = Integer.parseInt(matcher.group(1));

				if (day.equals("31") &&
						(month.equals("4") || month .equals("6") || month.equals("9") ||
								month.equals("11") || month.equals("04") || month .equals("06") ||
								month.equals("09"))) {
					return false;
				}

				else if (month.equals("2") || month.equals("02")) {
					if(year % 4==0){
						if(day.equals("30") || day.equals("31")){
							return false;
						}
						else{
							return true;
						}
					}
					else{

						if(day.equals("29")||day.equals("30")||day.equals("31")){
							return false;
						}
						else{
							return true;
						}
					}
				}

				else{
					return true;
				}
			}

			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

	private void showNewSubjectForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/SubjectForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void addSubject(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SchoolDaoException {
		Subject subject = new Subject(req.getParameter("subject"));

		schoolDao.addSchoolObject(subject);

		resp.sendRedirect("/allSubjects");
	}

	private void showEditStudentForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SchoolDaoException {

		int id = Integer.parseInt(request.getParameter("id"));
		Student student = schoolDao.getStudent(id);
		request.setAttribute("student", student);

		RequestDispatcher dispatcher = request.getRequestDispatcher("view/StudentForm.jsp");
		dispatcher.forward(request, response);
	}

	private void updateStudent(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SchoolDaoException {
		Student student = new Student(
				req.getParameter("name"),
				req.getParameter("surname"),
				req.getParameter("birthdate"),
				req.getParameter("enterYear")
		);
		student.setId(Integer.parseInt(req.getParameter("id")));
		checkStudent(student);

		schoolDao.updateSchoolDao(student);

		resp.sendRedirect("list");
	}

	private void showEditSubjectForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SchoolDaoException {
		int id = Integer.parseInt(request.getParameter("id"));
		Subject subject = schoolDao.getSubject(id);

		RequestDispatcher dispatcher = request.getRequestDispatcher("view/SubjectForm.jsp");
		request.setAttribute("subject", subject);
		dispatcher.forward(request, response);
	}

	private void updateSubject(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SchoolDaoException {
		Subject subject = new Subject();
		subject.setId(Integer.parseInt(req.getParameter("id")));
		subject.setSubjectName(req.getParameter("subject"));

		schoolDao.updateSchoolDao(subject);

		resp.sendRedirect("/allSubjects");
	}

	private void deleteStudent(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SchoolDaoException {
		schoolDao.removeStudent(Integer.parseInt(req.getParameter("id")));

		resp.sendRedirect("list");
	}

	private void deleteSubject(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SchoolDaoException {
		schoolDao.removeSubject(Integer.parseInt(req.getParameter("id")));

		resp.sendRedirect("/allSubjects");
	}

	private void addSubjectToStudent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SchoolDaoException {
		int studentId = Integer.parseInt(req.getParameter("studentId"));
		int subjectId = Integer.parseInt(req.getParameter("subjectId"));

		Student student = schoolDao.getStudent(studentId);
		Subject subject = schoolDao.getSubject(subjectId);
		List<Mark> markList = new ArrayList<>();

		SubjectWithMarks marks = new SubjectWithMarks();
		marks.setStudent(student);
		marks.setSubject(subject);
		marks.setMarks(markList);

		schoolDao.addSchoolObject(marks);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/getStudentMarksById?id="
				+ req.getParameter("studentId"));
		dispatcher.forward(req, resp);
	}
}