import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class DeleteStudentServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        Connection c = null;
        PreparedStatement ps = null;

        try {
            int id = Integer.parseInt(req.getParameter("id"));
/*
 c = (Connection) getServletContext().getAttribute("con");
            if (c == null) {
                out.println("<h3>Database connection not available.</h3>");
                return;
            } */
  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
            c = ds.getConnection();			

            String sql = "DELETE FROM cbse_result WHERE student_id = ?";

         
            ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                out.println("<h3>Student deleted successfully!</h3>");
            } else {
                out.println("<h3>No student found with the given ID.</h3>");
            }

        } catch (NumberFormatException e) {
            out.println("<h3>Error: Invalid ID.</h3>");
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
           
        }

        out.println("<a href='deleteStudent.html'>Delete Another</a> | ");
        out.println("<a href='index.html'>Home</a>");
        out.close();
    }
}

