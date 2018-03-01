package servlet.session;

import utility.RequestSender;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        Cookie[] cookies = null;
        cookies = request.getCookies();
        String token = null, userId = null;
        if (cookies != null) {
            for (Cookie cooky : cookies) {
                String cookieName = cooky.getName();
                String cookieValue = cooky.getValue();
                if (cookieName.equals("token")) {
                    token = cookieValue;
                    cooky.setMaxAge(0);
                    response.addCookie(cooky);
                }
                if (cookieName.equals("id")) {
                    userId = cookieValue;
                    cooky.setMaxAge(0);
                    response.addCookie(cooky);
                }
                if (cookieName.equals("uname")) {
                    cooky.setMaxAge(0);
                    response.addCookie(cooky);
                }
            }
        }

        if (token != null && userId != null) {
            String data = String.format("token=%s&user_id=%s", token, userId);
            String reply = RequestSender.sendRequest("http://localhost:8084/logoutservice", "POST", "application/x-www-form-urlencoded", data);
        }

        response.sendRedirect("index.jsp");
    }
}
