import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class UpdateStudentServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        Connection c = null;
        PreparedStatement ps = null;

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            double math = Double.parseDouble(req.getParameter("math"));
            double science = Double.parseDouble(req.getParameter("science"));
            double english = Double.parseDouble(req.getParameter("english"));
            double hindi = Double.parseDouble(req.getParameter("hindi"));
            double sst = Double.parseDouble(req.getParameter("sst"));

            if (math < 0 || math > 100 || science < 0 || science > 100 ||
                english < 0 || english > 100 || hindi < 0 || hindi > 100 ||
                sst < 0 || sst > 100) {
                out.println("<h3>Error: Marks must be between 0 and 100.</h3>");
                out.println("<a href='updateStudent.html'>Try Again</a>");
                return;
            }

            Object[] result = GradeUtil.calculateFullResult(math, science, english, hindi, sst);
            double total = (Double) result[0];
            double percentage = (Double) result[1];
            String grade = (String) result[2];

 /*c = (Connection) getServletContext().getAttribute("con");
            if (c == null) {
                out.println("<h3>Database connection not available.</h3>");
                return;
            }
*/
  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
            c = ds.getConnection();
			
            String sql = "UPDATE cbse_result SET math_marks=?, science_marks=?, english_marks=?, " +
                         "hindi_marks=?, sst_marks=?, total_marks=?, percentage=?, grade=? WHERE student_id=?";

            
            ps = c.prepareStatement(sql);
            ps.setDouble(1, math);
            ps.setDouble(2, science);
            ps.setDouble(3, english);
            ps.setDouble(4, hindi);
            ps.setDouble(5, sst);
            ps.setDouble(6, total);
            ps.setDouble(7, percentage);
            ps.setString(8, grade);
            ps.setInt(9, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                out.println("<h3>Student marks updated successfully!</h3>");
                out.println("New Total: " + total + "<br>");
                out.println("New Percentage: " + percentage + "%<br>");
                out.println("New Grade: " + grade + "<br>");
            } else {
                out.println("<h3>No student found with ID: " + id + "</h3>");
            }

        } catch (NumberFormatException e) {
            out.println("<h3>Error: Invalid numeric input.</h3>");
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            
        }

        out.println("<a href='updateStudent.html'>Update Another</a> | ");
        out.println("<a href='index.html'>Home</a>");
        out.close();
    }
}

