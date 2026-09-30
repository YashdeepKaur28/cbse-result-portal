import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class SearchStudentServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            int id = Integer.parseInt(req.getParameter("id"));
/*
 c = (Connection) getServletContext().getAttribute("con");
            if (c == null) {
                out.println("<h3>Database connection not available.</h3>");
                return;
            }*/
			
  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
            c = ds.getConnection();

            String sql = "SELECT * FROM cbse_result WHERE student_id = ?";

            
            ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h3>Student Result Card</h3>");
                out.println("<table border='1'><tr><th>Subject</th><th>Marks</th></tr>");
                out.println("<tr><td>Name: </td><td>" + rs.getString("name") + "</td></tr>");
              out.println("<tr><td>Class: </td><td>" + rs.getString("class") + "</td></tr>");
				out.println("<tr><td>Maths</td><td>" + rs.getDouble("math_marks") + "</td></tr>");
                out.println("<tr><td>Science</td><td>" + rs.getDouble("science_marks") + "</td></tr>");
                out.println("<tr><td>English</td><td>" + rs.getDouble("english_marks") + "</td></tr>");
                out.println("<tr><td>Hindi</td><td>" + rs.getDouble("hindi_marks") + "</td></tr>");
                out.println("<tr><td>Social Science</td><td>" + rs.getDouble("sst_marks") + "</td></tr>");
                
                out.println("<tr><td>Total: </td><td>" + rs.getDouble("total_marks") + "</td></tr>");
                out.println("<tr><td>Percentage: </td><td>" + rs.getDouble("percentage") + "</td></tr>");
                out.println("<tr><td>Grade: </td><td>" + rs.getString("grade") + "</td></tr>");
				out.println("</table>");
                out.println("<a href='ExcelServlet?id=" + id + "'>Download</a><br>");
            } else {
                out.println("<h3>No student found with ID: " + id + "</h3>");
            }

        } catch (NumberFormatException e) {
            out.println("<h3>Error: Invalid ID.</h3>");
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
           
        }

        out.println("<a href='searchStudent.html'>Search Again</a> | ");
        out.println("<a href='index.html'>Home</a>");
        out.close();
    }
}

