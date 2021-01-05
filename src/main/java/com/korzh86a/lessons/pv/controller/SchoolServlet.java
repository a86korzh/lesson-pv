package com.korzh86a.lessons_pv.controller;

import com.korzh86a.lessons_pv.dao.SchoolDao;
import com.korzh86a.lessons_pv.entity.Mark;
import com.korzh86a.lessons_pv.entity.Student;
import com.korzh86a.lessons_pv.entity.Subject;
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
import java.util.List;
import java.util.Map;

//@WebServlet("/")
@Configurable
public class SchoolServlet extends HttpServlet {
	@Autowired
	private SchoolDao schoolDao;

//	@Override
//	public void init() {
//		schoolDao = new SchoolDao();
//	}

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

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String action = req.getServletPath();
		resp.setContentType("text/html;charset=utf-8");

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
	}

	private void allStudents(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Student> allStudents;
		allStudents = schoolDao.getAllStudents();

		req.setAttribute("allStudents", allStudents);
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/StudentList.jsp");
		dispatcher.forward(req, resp);
	}

	private void showNewStudentForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/StudentForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Student student = new Student();
		student.setFirstName(req.getParameter("name"));
		student.setSecondName(req.getParameter("surname"));
		student.setBirthDate(req.getParameter("birthdate"));
		student.setEnterYear(req.getParameter("enterYear"));

		schoolDao.add(student);

		resp.sendRedirect("list");
	}

	private void showEditStudentForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Student student;

		student = schoolDao.getStudent(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("view/StudentForm.jsp");
		request.setAttribute("student", student);
		dispatcher.forward(request, response);
	}

	private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Student student = new Student();
		student.setId(Integer.parseInt(req.getParameter("id")));
		student.setFirstName(req.getParameter("name"));
		student.setSecondName(req.getParameter("surname"));
		student.setBirthDate(req.getParameter("birthdate"));
		student.setEnterYear(req.getParameter("enterYear"));

		schoolDao.update(student);

		resp.sendRedirect("list");
	}

	private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Student student = schoolDao.getStudent(Integer.parseInt(req.getParameter("id")));
		schoolDao.removeStudent(student);

		resp.sendRedirect("list");
	}

	private void allSubjects(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Subject> allSubjects;
		allSubjects = schoolDao.getAllSubjects();

		req.setAttribute("allSubjects", allSubjects);
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/SubjectList.jsp");
		dispatcher.forward(req, resp);
	}

	private void showNewSubjectForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/SubjectForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void addSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Subject subject = new Subject();
		subject.setSubjectName(req.getParameter("subject"));

		schoolDao.add(subject);

		resp.sendRedirect("/allSubjects");
	}

	private void showEditSubjectForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Subject subject;

		subject = schoolDao.getSubject(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("view/SubjectForm.jsp");
		request.setAttribute("subject", subject);
		dispatcher.forward(request, response);
	}

	private void updateSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Subject subject = new Subject();
		subject.setId(Integer.parseInt(req.getParameter("id")));
		subject.setSubjectName(req.getParameter("subject"));

		schoolDao.update(subject);

		resp.sendRedirect("/allSubjects");
	}

	private void deleteSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		schoolDao.removeSubjectById(Integer.parseInt(req.getParameter("id")));

		resp.sendRedirect("/allSubjects");
	}

	private void allMarks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Map<Subject, List<Mark>> allMarks = schoolDao.getStudentMarks(schoolDao.getStudent(id));
		List<Subject> allSubjects = schoolDao.getAllSubjects();

		req.setAttribute("allMarks", allMarks);
		req.setAttribute("allSubjects", allSubjects);
		req.setAttribute("studentId", id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("view/MarksList.jsp");
		dispatcher.forward(req, resp);
	}

	private void addSubjectToStudent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Mark mark = new Mark();
		mark.setStudentId(Integer.parseInt(req.getParameter("studentId")));
		mark.setSubjectId(Integer.parseInt(req.getParameter("subjectId")));

		schoolDao.add(mark);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/getStudentMarksById?id="
				+ req.getParameter("studentId"));
		dispatcher.forward(req, resp);
	}
}