import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
res.setContentType("text/html");
PrintWriter out = res.getWriter();

String registered = req.getParameter("registered");
 if ("true".equals(registered)) {
 out.println("<p style=\"color: green; font-size: 24px; font-weight: bold; \">Registration successful! Registration Email sent successfully. Please log in below.</p>");
        }

        String savedUsername = "";
        String savedPassword = "";
        boolean rememberChecked = false;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if ("username".equals(ck.getName())) {
                    savedUsername = ck.getValue();
                    rememberChecked = true;
                }
                if ("password".equals(ck.getName())) {
                    savedPassword = ck.getValue();
                }
            }
        }

        out.println("<html><head><title>Login</title></head><body>");
        out.println("<h2>Login to CBSE Student Result Management</h2>");
        out.println("<form action='login' method='post'>");
        out.println("Username or Email: <input type='text' name='username' value='" + savedUsername + "'><br>");
        out.println("Password: <input type='password' name='password' value='" + savedPassword + "'><br>");
        out.println("<input type='checkbox' name='remember' value='yes' " + (rememberChecked ? "checked" : "") + "> Remember Me<br>");
        out.println("<input type='submit' value='Login'>");
        out.println("</form>");
        out.println("<br><a href='register.html'>New User? Register here</a>");
        out.println("</body></html>");
        out.close();
    } 

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
  String loginInput = req.getParameter("username");
  String password = req.getParameter("password");
  String remember = req.getParameter("remember");

  
    try {
           
		  InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("tindi");
           Connection c = ds.getConnection();	

PreparedStatement ps = c.prepareStatement(
"SELECT * FROM users WHERE (LOWER(username) = LOWER(?) OR LOWER(email) = LOWER(?)) AND password = ?" );
            ps.setString(1, loginInput);
			ps.setString(2, loginInput);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();

  if (rs.next()) {
	  
String actualUsername = rs.getString("username"); 
    HttpSession session = req.getSession();
    session.setAttribute("loggedInUser", actualUsername);

          if ("yes".equals(remember)) {
             Cookie ckUser = new Cookie("username", loginInput);
             Cookie ckPass = new Cookie("password", password);
              ckUser.setMaxAge(24 * 60 * 60);
              ckPass.setMaxAge(24 * 60 * 60);
              ckUser.setPath("/");
              ckPass.setPath("/");
              res.addCookie(ckUser);
              res.addCookie(ckPass);
           } else {
             Cookie[] cookies = req.getCookies();
             if (cookies != null) {
                for (Cookie ck : cookies) {
if ("username".equals(ck.getName()) || "password".equals(ck.getName())) {
                     ck.setMaxAge(0);
                     ck.setPath("/");
                     res.addCookie(ck);
                            }
                        }
                    }
                }
                rs.close();
                ps.close();
                c.close();
				
                res.sendRedirect("index.html");
            } else {
res.setContentType("text/html");
PrintWriter out = res.getWriter();
out.println("<html><body>");
out.println("<h3>Invalid username/email or password</h3>");
out.println("<form action='login' method='post'>");
out.println("Username or Email: <input type='text' name='username' value='" + loginInput + "'><br>");
out.println("Password: <input type='password' name='password'><br>");
out.println("<input type='checkbox' name='remember' value='yes'> Remember Me<br>");
out.println("<input type='submit' value='Login'>");
out.println("</form>");
out.println("<br><a href='register.html'>New User? Register here</a>");
out.println("</body></html>");
            }
        } catch (Exception e) {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            out.println("<a href='login'>Try again</a>");
        }
    }
}