package servlet;// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static servlet.JDBCUtils.*;

// Import Java Libraries

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet
{
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
   String author = request.getParameter("author");
   String title = request.getParameter("title");
   String year = request.getParameter("year");
   String type = request.getParameter("type");
   String sort = request.getParameter("sort");
   String page = request.getParameter("page");

   //sql
   String sql = "select A.PUBLICATIONID AS PUBLICATIONID, YEAR, TYPE, TITLE, SUMMARY, URL, AUTHOR " +
           "FROM PUBLICATIONS A " +
           "LEFT JOIN (SELECT PUBLICATIONID, GROUP_CONCAT( AUTHOR ) AS AUTHOR FROM AUTHORS GROUP BY PUBLICATIONID) B ON A.PUBLICATIONID = B.PUBLICATIONID " +
           "WHERE 1 = 1";
   if(author != null && !"".equals(author)){
      sql += " and AUTHOR like '%" + author + "%'";
   }
   if(title != null && !"".equals(title)){
      sql += " and TITLE = '" + title + "'";
   }
   if(year != null && !"".equals(year)){
      sql += " and YEAR = '" + year + "'";
   }
   if(type != null && !"".equals(type)){
      sql += " and TYPE = '" + type + "'";
   }
   if(sort != null && !"".equals(sort)){
      sql += " order by " + sort;
   }
   if(page != null && !"".equals(page)){
      sql += " limit 0," + page;
   }

   Connection connection = getConnection();
   // 预编译对象 PreparedStatement
   PreparedStatement preparedStatement = null;
   // 结果集
   ResultSet resultSet = null;

   StringBuffer stringBuffer = new StringBuffer();
   try {
      // 获取preparedStatement
      preparedStatement = connection.prepareStatement(sql);
      // 执行sql语句，并返回结果集到resultSet中
      resultSet = preparedStatement.executeQuery();
      // 遍历结果集
      while (resultSet.next()) {
         stringBuffer.append("<p>").append("PUBLICATIONID: ").append(resultSet.getString("PUBLICATIONID")).append("</p>");
         stringBuffer.append("<p>").append("YEAR: ").append(resultSet.getString("YEAR")).append("</p>");
         stringBuffer.append("<p>").append("AUTHOR: ").append(resultSet.getString("AUTHOR")).append("</p>");
         stringBuffer.append("<p>").append("TYPE: ").append(resultSet.getString("TYPE")).append("</p>");
         stringBuffer.append("<p>").append("TITLE: ").append(resultSet.getString("TITLE")).append("</p>");
         stringBuffer.append("<p>").append("SUMMARY: ").append(resultSet.getString("SUMMARY")).append("</p>");
         stringBuffer.append("<p>").append("URL: ").append(resultSet.getString("URL")).append("</p>");
         stringBuffer.append("<p>").append("-----------------------------------------------------------------------------------------------------------------------------------------------").append("</p>");
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
   out.println(" <title>search</title>");
   out.println("</head>");
   out.println("");

   out.println("<body>");

   out.println(stringBuffer);
//   for (int i = 1; i < 10 ; i++) {
//      out.println("<a href='/page?page='>"+ i +"</a>");
//   }
   out.println("</body>");
   out.println("</html>");
   out.close();
} // End doGet
} //End  SessionLifeCycle
