package servlets;

import JDBC.DAO.Exception.DaoException;
import JDBC.DAO.MarkDaoMySql;
import JDBC.DAO.StudentDaoMySql;
import JDBC.DAO.SubjectDaoMySql;
import JDBC.DTO.MarkDto;
import JDBC.DTO.StudentDto;
import JDBC.DTO.SubjectDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class JspServlet extends HttpServlet {
	private StudentDaoMySql studentDaoMySql;
	private SubjectDaoMySql subjectDaoMySql;
	private MarkDaoMySql markDaoMySql;

	@Override
	public void init() throws ServletException {
		try {
			studentDaoMySql = new StudentDaoMySql();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServletException("Ошибка с доступом к базе данных, таблица студентов");
		}
		try {
			subjectDaoMySql = new SubjectDaoMySql();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServletException("Ошибка с доступом к базе данных, таблица предметов");
		}
		try {
			markDaoMySql = new MarkDaoMySql();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServletException("Ошибка с доступом к базе данных, таблица отметок");
		}
	}

	@Override
	public void destroy() {
		if (studentDaoMySql != null) {
			studentDaoMySql.close();
		}
		if (subjectDaoMySql != null) {
			subjectDaoMySql.close();
		}
		if (markDaoMySql != null) {
			markDaoMySql.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
		} catch (DaoException e) {
			throw new ServletException("Ошибка с доступом к базе данных", e);
		}
	}

	private void allStudents(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
		List<StudentDto> allStudents;
		allStudents = studentDaoMySql.getAllStudents();

		req.setAttribute("allStudents", allStudents);
		RequestDispatcher dispatcher = req.getRequestDispatcher("StudentList.jsp");
		dispatcher.forward(req, resp);
	}

	private void showNewStudentForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("StudentForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws DaoException, IOException {
		StudentDto studentDto = new StudentDto();
		studentDto.setFirstName(req.getParameter("name"));
		studentDto.setSecondName(req.getParameter("surname"));
		studentDto.setBirthDate(req.getParameter("birthdate"));
		studentDto.setEnterYear(req.getParameter("enterYear"));

		studentDaoMySql.add(studentDto);

		resp.sendRedirect("list");
	}

	private void showEditStudentForm(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		StudentDto studentDto;

		studentDto = studentDaoMySql.getStudent(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("StudentForm.jsp");
		request.setAttribute("student", studentDto);
		dispatcher.forward(request, response);
	}

	private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws DaoException, IOException {
		StudentDto studentDto = new StudentDto();
		studentDto.setId(Integer.parseInt(req.getParameter("id")));
		studentDto.setFirstName(req.getParameter("name"));
		studentDto.setSecondName(req.getParameter("surname"));
		studentDto.setBirthDate(req.getParameter("birthdate"));
		studentDto.setEnterYear(req.getParameter("enterYear"));

		studentDaoMySql.update(studentDto);

		resp.sendRedirect("list");
	}

	private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws DaoException, IOException {
		studentDaoMySql.removeStudentById(Integer.parseInt(req.getParameter("id")));

		resp.sendRedirect("list");
	}

	private void allSubjects(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
		List<SubjectDto> allSubjects;
		allSubjects = subjectDaoMySql.getAllSubjects();

		req.setAttribute("allSubjects", allSubjects);
		RequestDispatcher dispatcher = req.getRequestDispatcher("SubjectList.jsp");
		dispatcher.forward(req, resp);
	}

	private void showNewSubjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("SubjectForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void addSubject(HttpServletRequest req, HttpServletResponse resp) throws DaoException, IOException {
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setSubjectName(req.getParameter("subject"));

		subjectDaoMySql.add(subjectDto);

		resp.sendRedirect("/allSubjects");
	}

	private void showEditSubjectForm(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		SubjectDto subjectDto;

		subjectDto = subjectDaoMySql.getSubject(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("SubjectForm.jsp");
		request.setAttribute("subject", subjectDto);
		dispatcher.forward(request, response);
	}

	private void updateSubject(HttpServletRequest req, HttpServletResponse resp) throws DaoException, IOException {
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setId(Integer.parseInt(req.getParameter("id")));
		subjectDto.setSubjectName(req.getParameter("subject"));

		subjectDaoMySql.update(subjectDto);

		resp.sendRedirect("/allSubjects");
	}

	private void deleteSubject(HttpServletRequest req, HttpServletResponse resp) throws DaoException, IOException {
		subjectDaoMySql.removeSubjectById(Integer.parseInt(req.getParameter("id")));

		resp.sendRedirect("/allSubjects");
	}

	private void allMarks(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Map<SubjectDto, List<MarkDto>> allMarks;
		allMarks = studentDaoMySql.getStudentMarksById(id);

		List<SubjectDto> allSubjects;
		allSubjects = subjectDaoMySql.getAllSubjects();

		req.setAttribute("allMarks", allMarks);
		req.setAttribute("allSubjects", allSubjects);
		req.setAttribute("studentId", id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("MarksList.jsp");
		dispatcher.forward(req, resp);
	}

	private void addSubjectToStudent(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
		MarkDto markDto = new MarkDto();
		markDto.setStudentId(Integer.parseInt(req.getParameter("studentId")));
		markDto.setSubjectId(Integer.parseInt(req.getParameter("subjectId")));

		markDaoMySql.addSubjectToStudent(markDto);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/getStudentMarksById?id="
				+ req.getParameter("studentId"));
		dispatcher.forward(req, resp);
	}
}