package servlet.session;

import utility.CookieChecker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignIn extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = CookieChecker.check(request.getCookies());

        if (status.equals("valid")) {
            response.sendRedirect("/profile");
        } else {
            String errorMessage = "";
            if (request.getParameter("error") != null) {
                if (request.getParameter("error").equals("loginfailed"))
                    errorMessage = "Wrong username or password.";
                else if (request.getParameter("error").equals("tokenexpired"))
                    errorMessage = "Token has expired, please login again";
                else if (request.getParameter("error").equals("tokeninvalid"))
                    errorMessage = "Invalid token, please login again";
                else if (request.getParameter("error").equals("notoken"))
                    errorMessage = "You haven't login, please input your username and password to login";
            }
            request.setAttribute("errormessage", errorMessage);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
