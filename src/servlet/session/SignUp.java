package servlet.session;

import utility.CookieChecker;
import utility.CookiesMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SignUp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> cookiesMap = CookiesMap.getCookiesMap(request.getCookies());
        if (cookiesMap.containsKey("token")) {
            String token = cookiesMap.get("token");
            String userId = cookiesMap.get("id");
            String username = cookiesMap.get("uname");

            if (CookieChecker.check(request.getCookies()).equals("valid")) {
                response.sendRedirect("/profile");
            } else
                request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else
            request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
