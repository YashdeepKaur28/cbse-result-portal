import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;


public class AddStudentServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        Connection c = null;
        PreparedStatement ps = null;

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String cls = req.getParameter("class");

            double math = Double.parseDouble(req.getParameter("math"));
            double science = Double.parseDouble(req.getParameter("science"));
            double english = Double.parseDouble(req.getParameter("english"));
            double hindi = Double.parseDouble(req.getParameter("hindi"));
            double sst = Double.parseDouble(req.getParameter("sst"));

            if (math < 0 || math > 100 || science < 0 || science > 100 ||
                english < 0 || english > 100 || hindi < 0 || hindi > 100 ||
                sst < 0 || sst > 100) {
                out.println("<h3>Error: Marks must be between 0 and 100.</h3>");
                out.println("<a href='addStudent.html'>Try Again</a>");
                return;
            }

            Object[] result = GradeUtil.calculateFullResult(math, science, english, hindi, sst);
            double total = (Double) result[0];
            double percentage = (Double) result[1];
            String grade = (String) result[2];


  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
            c = ds.getConnection();			
			

            String sql = "INSERT INTO cbse_result (student_id, name, class, math_marks, science_marks, " +
                         "english_marks, hindi_marks, sst_marks, total_marks, percentage, grade) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            
            ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, cls);
            ps.setDouble(4, math);
            ps.setDouble(5, science);
            ps.setDouble(6, english);
            ps.setDouble(7, hindi);
            ps.setDouble(8, sst);
            ps.setDouble(9, total);
            ps.setDouble(10, percentage);
            ps.setString(11, grade);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                out.println("<h3>Student added successfully!</h3>");
               /* out.println("ID: " + id + "<br>");
                out.println("Total: " + total + "<br>");
                out.println("Percentage: " + percentage + "%<br>");
                out.println("Grade: " + grade + "<br>");*/
            } else {
                out.println("<h3>Failed to add student.</h3>");
            }

        } catch (NumberFormatException e) {
            out.println("<h3>Error: Invalid numeric input.</h3>");
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (ps != null) 
				ps.close(); } 
		  catch (SQLException e) {}
            
        }

        out.println("<a href='addStudent.html'>Add Another</a> | ");
        out.println("<a href='index.html'>Home</a>");
        out.close();
    }
}


