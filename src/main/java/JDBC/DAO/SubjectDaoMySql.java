package JDBC.DAO;

import JDBC.DAO.Exception.DaoException;
import JDBC.DTO.StudentDto;
import JDBC.DTO.SubjectDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoMySql extends DaoMySql implements AutoCloseable {
    private static final String GET_ALL_SUBJECTS = "select id," +
            "subj_name from subject";
    private static final String GET_SUBJECT = "select id," +
            "subj_name from subject WHERE id = ?";
    private static final String UPDATE_SUBJECT = "UPDATE subject SET subj_name = ? WHERE id = ?";
    private static final String ADD_SUBJECT = "INSERT INTO subject (subj_name) VALUE (?)";
    private static final String REMOVE_SUBJECT_MARKS = "DELETE FROM marks WHERE subject_id = ?";
    private static final String REMOVE_SUBJECT = "DELETE FROM subject WHERE id = ?";

    private PreparedStatement preparedStatementGetAllSubjects;
    private PreparedStatement preparedStatementGetSubject;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementAdd;
    private PreparedStatement preparedStatementRemove;
    private PreparedStatement preparedStatementRemoveRelativeMarks;

    public SubjectDaoMySql() throws DaoException {
    }

    public List<SubjectDto> getAllSubjects() throws DaoException {
        List<SubjectDto> list = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            preparedStatementGetAllSubjects = getStatement(preparedStatementGetAllSubjects, GET_ALL_SUBJECTS);
            resultSet = preparedStatementGetAllSubjects.executeQuery();

            while (resultSet.next()) {
                SubjectDto subjectDto = new SubjectDto();
                subjectDto.setId(resultSet.getInt(1));
                subjectDto.setSubjectName(resultSet.getString(2));
                list.add(subjectDto);
            }
        } catch (SQLException e) {
            throw new DaoException("Problem with getAllSubjects()", e);
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

    public SubjectDto getSubject(int studentId) throws DaoException {
        SubjectDto subjectDto = new SubjectDto();
        ResultSet resultSet = null;

        try {
            preparedStatementGetSubject = getStatement(preparedStatementGetSubject, GET_SUBJECT);
            preparedStatementGetSubject.setInt(1, studentId);
            resultSet = preparedStatementGetSubject.executeQuery();

            if (resultSet.next()) {
                subjectDto = new SubjectDto(resultSet.getInt(1),
                        resultSet.getString(2));
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
        return subjectDto;
    }

    public void update(SubjectDto subjectDto) throws DaoException {
        try {
            preparedStatementUpdate = getStatement(preparedStatementUpdate, UPDATE_SUBJECT);
            preparedStatementUpdate = connection.prepareStatement(UPDATE_SUBJECT);
            preparedStatementUpdate.setString(1, subjectDto.getSubjectName());
            preparedStatementUpdate.setInt(2, subjectDto.getId());
            preparedStatementUpdate.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with updateSubject()", e);
        }
    }

    public void add(SubjectDto subjectDto) throws DaoException {
        try {
            preparedStatementAdd = getStatement(preparedStatementAdd, ADD_SUBJECT);
            preparedStatementAdd.setString(1, subjectDto.getSubjectName());
            preparedStatementAdd.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with addSubject()", e);
        }
    }

    public void removeSubjectById(int subjectId) throws DaoException {
        removeRelativeMarks(subjectId);

        try {
            preparedStatementRemove = getStatement(preparedStatementRemove, REMOVE_SUBJECT);
            preparedStatementRemove.setInt(1, subjectId);
            preparedStatementRemove.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with removeSubject()", e);
        }
    }

    private void removeRelativeMarks(int markId) throws DaoException {
        try {
            preparedStatementRemoveRelativeMarks = getStatement(preparedStatementRemoveRelativeMarks, REMOVE_SUBJECT_MARKS);
            preparedStatementRemoveRelativeMarks.setInt(1, markId);
            preparedStatementRemoveRelativeMarks.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with removeRelativeMarks()", e);
        }
    }

    @Override
    public void close() {
        try {
            if (preparedStatementGetAllSubjects != null) {
                preparedStatementGetAllSubjects.close();
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