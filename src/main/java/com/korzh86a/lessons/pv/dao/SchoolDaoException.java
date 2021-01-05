package com.korzh86a.LessonJUnit.JDBC.DAO.Exception;

import java.sql.SQLException;

public class DaoException extends SQLException {
    public DaoException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
