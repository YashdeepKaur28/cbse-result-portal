import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class ExcelServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        boolean isSingle = (idParam != null && !idParam.trim().isEmpty());

 Connection c = null; 
 
        try {
     /*       Connection c = (Connection) getServletContext().getAttribute("con");
            if (c == null) {
                res.setContentType("text/html");
                PrintWriter out = res.getWriter();
                out.println("<h3>Database connection not available.</h3>");
                return;
            }*/
			  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
            c = ds.getConnection();

            PreparedStatement ps;
            if (isSingle) {
                int id = Integer.parseInt(idParam);
                ps = c.prepareStatement("SELECT * FROM cbse_result WHERE student_id = ?");
                ps.setInt(1, id);
                res.setHeader("Content-Disposition", 
                    "attachment; filename=student_" + id + ".xls");
            } else {
                ps = c.prepareStatement("SELECT * FROM cbse_result ORDER BY student_id");
                res.setHeader("Content-Disposition", 
                    "attachment; filename=all_students.xls");
            }

            res.setContentType("application/vnd.ms-excel");
            PrintWriter out = res.getWriter();

            out.println("ID\tName\tClass\tMaths\tScience\tEnglish\tHindi\tSST\tTotal\tPercentage\tGrade");

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                out.print(rs.getInt("student_id") + "\t");
                out.print(rs.getString("name") + "\t");
                out.print(rs.getString("class") + "\t");
                out.print(rs.getDouble("math_marks") + "\t");
                out.print(rs.getDouble("science_marks") + "\t");
                out.print(rs.getDouble("english_marks") + "\t");
                out.print(rs.getDouble("hindi_marks") + "\t");
                out.print(rs.getDouble("sst_marks") + "\t");
                out.print(rs.getDouble("total_marks") + "\t");
                out.print(rs.getDouble("percentage") + "\t");
                out.println(rs.getString("grade"));
            }

            if (isSingle && !found) {
                out.println("\nNo student found with ID: " + idParam);
            }

            rs.close();
            ps.close();
           

        } catch (Exception e) {
            e.printStackTrace();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}