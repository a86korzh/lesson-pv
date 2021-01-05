package com.korzh86a.lessons.pv.dao;

import java.sql.SQLException;

public class SchoolDaoException extends SQLException {
    public SchoolDaoException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
