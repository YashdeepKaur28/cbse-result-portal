import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class DisplayAllServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>All Students</title>");
        out.println("<style>table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; }</style>");
        out.println("</head><body>");
        out.println("<h2>All Students - CBSE Result</h2>");
        out.println("<a href='ExcelServlet'> Download all results</a><br><br>");

        try {
 /*c = (Connection) getServletContext().getAttribute("con");
            if (c == null) {
                out.println("<h3>Database connection not available.</h3>");
                return;
            }*/
	  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
            c = ds.getConnection();		

            String sql = "SELECT * FROM cbse_result ORDER BY student_id";

            
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Class</th>");
            out.println("<th>Maths</th><th>Science</th><th>English</th><th>Hindi</th><th>SST</th>");
            out.println("<th>Total</th><th>%</th><th>Grade</th></tr>");

            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("student_id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("class") + "</td>");
                out.println("<td>" + rs.getDouble("math_marks") + "</td>");
                out.println("<td>" + rs.getDouble("science_marks") + "</td>");
                out.println("<td>" + rs.getDouble("english_marks") + "</td>");
                out.println("<td>" + rs.getDouble("hindi_marks") + "</td>");
                out.println("<td>" + rs.getDouble("sst_marks") + "</td>");
                out.println("<td>" + rs.getDouble("total_marks") + "</td>");
                out.println("<td>" + rs.getDouble("percentage") + "%</td>");
                out.println("<td>" + rs.getString("grade") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            if (!hasRows) {
                out.println("<p>No students found.</p>");
            }

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            
        }

        out.println("<br><a href='index.html'>Back to Home</a>");
        out.println("</body></html>");
        out.close();
    }
}
