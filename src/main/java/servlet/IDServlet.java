package servlet;// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import static servlet.JDBCUtils.*;

// Import Java Libraries

@WebServlet(name = "IDServlet", urlPatterns = {"/id"})
public class IDServlet extends HttpServlet
{
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
   String id = request.getParameter("id");

   //sql
   String sql = "select A.PUBLICATIONID AS PUBLICATIONID, YEAR, TYPE, TITLE, SUMMARY, URL, AUTHOR " +
           "FROM PUBLICATIONS A " +
           "LEFT JOIN (SELECT PUBLICATIONID, GROUP_CONCAT( AUTHOR ) AS AUTHOR FROM AUTHORS GROUP BY PUBLICATIONID) B ON A.PUBLICATIONID = B.PUBLICATIONID " +
           "WHERE A.PUBLICATIONID = ?";
   Connection connection = getConnection();
   // PreparedStatement
   PreparedStatement preparedStatement = null;
   // result set
   ResultSet resultSet = null;

   StringBuffer stringBuffer = new StringBuffer();
   try {
      // got preparedStatement
      preparedStatement = connection.prepareStatement(sql);
      // 为preparedStatement对象的sql中的占位符设置参数
      preparedStatement.setString(1, id);
      // Execute SQL statement，return resultSet
      resultSet = preparedStatement.executeQuery();
      // Iterate the result set
      while (resultSet.next()) {
         stringBuffer.append("<p>").append("PUBLICATIONID: ").append(resultSet.getString("PUBLICATIONID")).append("</p>");
         stringBuffer.append("<p>").append("YEAR: ").append(resultSet.getString("YEAR")).append("</p>");
         stringBuffer.append("<p>").append("AUTHOR: ").append(resultSet.getString("AUTHOR")).append("</p>");
         stringBuffer.append("<p>").append("TYPE: ").append(resultSet.getString("TYPE")).append("</p>");
         stringBuffer.append("<p>").append("TITLE: ").append(resultSet.getString("TITLE")).append("</p>");
         stringBuffer.append("<p>").append("SUMMARY: ").append(resultSet.getString("SUMMARY")).append("</p>");
         stringBuffer.append("<p>").append("URL: ").append(resultSet.getString("URL")).append("</p>");
      }
   } catch (SQLException throwables) {
      throwables.printStackTrace();
   }finally {
      closeStatement(preparedStatement);
      closeResultSet(resultSet);
      closeConnection(connection);
   }


   response.setContentType("text/html");
   PrintWriter out = response.getWriter();

   out.println("<html>");
   // no-cache lets the page reload by clicking on the reload link
   out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
   out.println("<head>");
   out.println(" <title>detail</title>");
   out.println("</head>");
   out.println("");

   out.println("<body>");

   out.println(stringBuffer);

   out.println("</body>");
   out.println("</html>");
   out.flush();
   out.close();
} // End doGet
} //End  SessionLifeCycle
