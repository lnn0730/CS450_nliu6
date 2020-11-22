package servlet;

import java.sql.*;

public class JDBCUtils {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://p2jxdb.mysql.database.azure.com:3306/papers?useSSL=true&requireSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "db_user@p2jxdb";
    private static final String PASSWORD = "zksTu8%d";


    static {
        /**
         * 
         */
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    /**
     * get Connetion
     *
     * @return conn
     */
    public static Connection getConnection()  {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * release source
     *
     * @param conn 
     * @param  preparedStatement 
     * @param rs 
     */
    public static void colseResource(Connection conn, PreparedStatement preparedStatement, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(preparedStatement);
        closeConnection(conn);
    }

    /**
     * release Connection
     *
     * @param conn 
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //wait return
        conn = null;
    }

    /**
     * release preparedStatement
     *
     * @param preparedStatement 
     */
    public static void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        preparedStatement = null;
    }

    /**
     * release ResultSet
     *
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rs = null;
    }
}
