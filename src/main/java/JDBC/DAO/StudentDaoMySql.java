package JDBC.DAO;

import JDBC.DAO.Exception.DaoException;
import JDBC.DTO.MarkDto;
import JDBC.DTO.StudentDto;
import JDBC.DTO.SubjectDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDaoMySql extends DaoMySql implements AutoCloseable {
    private static final String GET_ALL_STUDENTS = "select id, first_name, " +
            "second_name, birth_date, enter_year from student";
    private static final String GET_STUDENT = "select id, first_name, " +
            "second_name, birth_date, enter_year from student WHERE id = ?";
    private static final String GET_STUDENT_MARKS = "select SUBJ_NAME, MARK " +
            "from marks JOIN subject ON subject.ID = SUBJECT_ID WHERE STUDENT_ID = (" +
            "select id from student where first_name = ? and second_name = ? " +
            "and birth_date = ? and enter_year = ?)";
    private static final String GET_STUDENT_MARKS_BY_ID = "select SUBJ_NAME, MARK " +
            "from marks JOIN subject ON subject.ID = SUBJECT_ID WHERE STUDENT_ID = ?";
    private static final String UPDATE_STUDENT = "UPDATE student SET first_name = ?, " +
            "second_name = ?, birth_date = ?, enter_year = ? WHERE id = ?";
    private static final String ADD_STUDENT = "INSERT INTO student " +
            "(first_name, second_name, birth_date, enter_year) VALUE (?, ?, ?, ?)";
    private static final String REMOVE_STUDENT = "DELETE FROM student WHERE id = ?";
    private static final String REMOVE_STUDENTS_MARKS = "DELETE FROM marks WHERE student_id = ?";

    private PreparedStatement preparedStatementGetAllStudents;
    private PreparedStatement preparedStatementGetStudent;
    private PreparedStatement preparedStatementGetStudentMarks;
    private PreparedStatement preparedStatementGetStudentMarksById;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementAdd;
    private PreparedStatement preparedStatementRemove;
    private PreparedStatement preparedStatementRemoveRelativeMarks;

    public StudentDaoMySql() throws DaoException {
    }

    public List<StudentDto> getAllStudents() throws DaoException {
        List<StudentDto> list = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            preparedStatementGetAllStudents = getStatement(preparedStatementGetAllStudents, GET_ALL_STUDENTS);
            resultSet = preparedStatementGetAllStudents.executeQuery();

            while (resultSet.next()) {
                StudentDto studentDto = new StudentDto(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));

                list.add(studentDto);
            }

        } catch (SQLException e) {
            throw new DaoException("Problem with getAllStudents()", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Problem with closing resultSet");
                e.printStackTrace();
            }
        }

        return list;
    }

    public StudentDto getStudent(int studentId) throws DaoException {
        StudentDto studentDto = new StudentDto();
        ResultSet resultSet = null;

        try {
            preparedStatementGetStudent = getStatement(preparedStatementGetStudent, GET_STUDENT);
            preparedStatementGetStudent.setInt(1, studentId);
            resultSet = preparedStatementGetStudent.executeQuery();

            if (resultSet.next()) {
                studentDto = new StudentDto(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new DaoException("Problem with getStudent()", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Problem with closing resultSet");
                e.printStackTrace();
            }
        }
        return studentDto;
    }

    public Map<SubjectDto, List<MarkDto>> getStudentMarks(StudentDto studentDto) throws DaoException {
        HashMap<SubjectDto, List<MarkDto>> subjectDtoListHashMap = new HashMap<>();
        ResultSet resultSet = null;

        try {
            preparedStatementGetStudentMarks = getStatement(preparedStatementGetStudentMarks, GET_STUDENT_MARKS);
            preparedStatementGetStudentMarks.setString(1, studentDto.getFirstName());
            preparedStatementGetStudentMarks.setString(2, studentDto.getSecondName());
            preparedStatementGetStudentMarks.setDate(3, Date.valueOf(studentDto.getBirthDate()));
            preparedStatementGetStudentMarks.setInt(4, Integer.parseInt(studentDto.getEnterYear()));

            resultSet = preparedStatementGetStudentMarks.executeQuery();

            while (resultSet.next()) {
                SubjectDto subjectDto = new SubjectDto();
                MarkDto markDto = new MarkDto();

                subjectDto.setSubjectName(resultSet.getString(1));
                markDto.setMark(resultSet.getInt(2));

                if (subjectDtoListHashMap.containsKey(subjectDto)) {
                    subjectDtoListHashMap.get(subjectDto).add(markDto);
                } else {
                    ArrayList<MarkDto> list = new ArrayList<>();
                    list.add(markDto);
                    subjectDtoListHashMap.put(subjectDto, list);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Problem with getStudentMarks()", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Problem with closing resultSet");
                e.printStackTrace();
            }
        }

        return subjectDtoListHashMap;
    }

    public Map<SubjectDto, List<MarkDto>> getStudentMarksById(int id) throws DaoException {
        HashMap<SubjectDto, List<MarkDto>> subjectDtoListHashMap = new HashMap<>();
        ResultSet resultSet = null;

        try {
            preparedStatementGetStudentMarks = getStatement(preparedStatementGetStudentMarksById, GET_STUDENT_MARKS_BY_ID);
            preparedStatementGetStudentMarks.setInt(1, id);
            resultSet = preparedStatementGetStudentMarks.executeQuery();

            while (resultSet.next()) {
                SubjectDto subjectDto = new SubjectDto();
                MarkDto markDto = new MarkDto();

                subjectDto.setSubjectName(resultSet.getString(1));
                markDto.setMark(resultSet.getInt(2));

                if (subjectDtoListHashMap.containsKey(subjectDto)) {
                    subjectDtoListHashMap.get(subjectDto).add(markDto);
                } else {
                    ArrayList<MarkDto> list = new ArrayList<>();
                    list.add(markDto);
                    subjectDtoListHashMap.put(subjectDto, list);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Problem with getStudentMarksById()", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Problem with closing resultSet");
                e.printStackTrace();
            }
        }

        return subjectDtoListHashMap;
    }

    public void update(StudentDto studentDto) throws DaoException {
        try {
            preparedStatementUpdate = getStatement(preparedStatementUpdate, UPDATE_STUDENT);
            preparedStatementUpdate.setString(1, studentDto.getFirstName());
            preparedStatementUpdate.setString(2, studentDto.getSecondName());
            preparedStatementUpdate.setString(3, studentDto.getBirthDate());
            preparedStatementUpdate.setString(4, studentDto.getEnterYear());
            preparedStatementUpdate.setInt(5, studentDto.getId());
            preparedStatementUpdate.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with updateStudent()", e);
        }
    }

    public void add(StudentDto studentDto) throws DaoException {
        try {
            preparedStatementAdd = getStatement(preparedStatementAdd, ADD_STUDENT);
            preparedStatementAdd.setString(1, studentDto.getFirstName());
            preparedStatementAdd.setString(2, studentDto.getSecondName());
            preparedStatementAdd.setString(3, studentDto.getBirthDate());
            preparedStatementAdd.setString(4, studentDto.getEnterYear());
            preparedStatementAdd.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with addStudent()", e);
        }
    }

    public void removeStudentById(int studentId) throws DaoException {
        removeRelativeMarks(studentId);

        try {
            preparedStatementRemove = getStatement(preparedStatementRemove, REMOVE_STUDENT);
            preparedStatementRemove.setInt(1, studentId);
            preparedStatementRemove.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with removeStudent()", e);
        }
    }

    private void removeRelativeMarks(int markId) throws DaoException {
        try {
            preparedStatementRemoveRelativeMarks = getStatement(preparedStatementRemoveRelativeMarks, REMOVE_STUDENTS_MARKS);
            preparedStatementRemoveRelativeMarks.setInt(1, markId);
            preparedStatementRemoveRelativeMarks.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with removeMark()", e);
        }
    }

    @Override
    public void close() {
        try {
            if (preparedStatementGetAllStudents != null) {
                preparedStatementGetAllStudents.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementGetStudentMarks != null) {
                preparedStatementGetStudentMarks.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementGetStudentMarksById != null) {
                preparedStatementGetStudentMarksById.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementUpdate != null) {
                preparedStatementUpdate.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementAdd != null) {
                preparedStatementAdd.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementRemove != null) {
                preparedStatementRemove.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementRemoveRelativeMarks != null) {
                preparedStatementRemoveRelativeMarks.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing connection");
            e.printStackTrace();
        }
    }
}
