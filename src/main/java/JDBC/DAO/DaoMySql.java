package JDBC.DAO;

import JDBC.DAO.Exception.DaoException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public abstract class DaoMySql {
    protected final String USER_NAME;
    protected final String PASS;
    protected final String URL;
    protected Connection connection;

    public DaoMySql() throws DaoException {
        Properties properties = new Properties();
        InputStream iStream = null;

        try {
            iStream = this.getClass().getClassLoader().getResourceAsStream("app.properties");
            properties.load(iStream);

            PASS = properties.getProperty("PASS");
            USER_NAME = properties.getProperty("USER_NAME");
            URL = properties.getProperty("URL");

            Class.forName(properties.getProperty("Driver"));
            connection = DriverManager.getConnection(URL, USER_NAME, PASS);

        } catch (IOException e) {
            throw new DaoException("File with properties not found", e);
        } catch (ClassNotFoundException e) {
            throw new DaoException("Driver is not found", e);
        } catch (SQLException e) {
            throw new DaoException("Problem with connection", e);
        } finally {
            try {
                if(iStream != null){
                    iStream.close();
                }
            } catch (IOException e) {
                System.out.println("Problem with closing InputStream in StudentDaoMySqlConstructor");
                e.printStackTrace();
            }
        }
    }

    protected PreparedStatement getStatement(PreparedStatement preparedStatement, String query) throws SQLException {
        if (preparedStatement == null) {
            return connection.prepareStatement(query);
        }
        return preparedStatement;
    }
}
