import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
		String email = req.getParameter("email");
		
 
        try {
          
	  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
         Connection  c = ds.getConnection();		

            PreparedStatement ps = c.prepareStatement(
            "INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
			ps.setString(3, email);
			
int rows = ps.executeUpdate();

            if (rows > 0) {
			
 Email.sendRegistrationEmail(email, username);			
				
                ps.close();
                c.close();
				
res.sendRedirect("login?registered=true&username=" + username);
            } else {
                res.getWriter().println("Registration failed. Try again.");
            }
        } catch (SQLException e) {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println("<html><body>");
            out.println("<h3>Username already exists. Please choose another.</h3>");
            out.println("<a href='register.html'>Back</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}