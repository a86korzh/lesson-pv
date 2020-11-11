package JDBC.DAO;

import JDBC.DAO.Exception.DaoException;
import JDBC.DTO.MarkDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarkDaoMySql extends DaoMySql implements AutoCloseable {
    private static final String UPDATE_MARK = "UPDATE marks SET mark = ? WHERE id = ?";
    private static final String ADD_MARK = "INSERT INTO marks (student_id, subject_id, mark) " +
            "VALUE (?, ?, ?)";
    private static final String ADD_SUBJECT_TO_STUDENT = "INSERT INTO marks (student_id, subject_id) " +
            "VALUE (?, ?)";
    private static final String REMOVE_MARK = "DELETE FROM marks WHERE id = ?";

    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementAdd;
    private PreparedStatement preparedStatementAddSubjectToStudent;
    private PreparedStatement preparedStatementRemove;

    public MarkDaoMySql() throws DaoException {
    }

    public void update(MarkDto markDto) throws DaoException {
        try {
            preparedStatementUpdate = getStatement(preparedStatementUpdate, UPDATE_MARK);
            preparedStatementUpdate.setInt(1, markDto.getMark());
            preparedStatementUpdate.setInt(2, markDto.getId());
            preparedStatementUpdate.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with updateMark()", e);
        }
    }

    public void add(MarkDto markDto) throws DaoException {
        try {
            preparedStatementAdd = getStatement(preparedStatementAdd, ADD_MARK);
            preparedStatementAdd.setInt(1, markDto.getStudentId());
            preparedStatementAdd.setInt(2, markDto.getSubjectId());
            preparedStatementAdd.setInt(3, markDto.getMark());
            preparedStatementAdd.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with addMark()", e);
        }
    }

    public void addSubjectToStudent(MarkDto markDto) throws DaoException {
        try {
            preparedStatementAddSubjectToStudent = getStatement(preparedStatementAddSubjectToStudent,
                    ADD_SUBJECT_TO_STUDENT);
            preparedStatementAddSubjectToStudent.setInt(1, markDto.getStudentId());
            preparedStatementAddSubjectToStudent.setInt(2, markDto.getSubjectId());
            preparedStatementAddSubjectToStudent.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with addMark()", e);
        }
    }

    public void removeStudentById(int markId) throws DaoException {
        try {
            preparedStatementRemove = getStatement(preparedStatementRemove, REMOVE_MARK);
            preparedStatementRemove.setInt(1, markId);
            preparedStatementRemove.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with removeMark()", e);
        }
    }

    @Override
    public void close() {
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
            if (preparedStatementAddSubjectToStudent != null) {
                preparedStatementAddSubjectToStudent.close();
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
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing connection");
            e.printStackTrace();
        }
    }
}


