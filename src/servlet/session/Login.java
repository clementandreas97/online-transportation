package servlet.session;

import utility.RequestSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.util.Map;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        // get the username and password from POST method
        String username = request.getParameter("uname");
        String password = request.getParameter("pass");
        String userAgent = request.getHeader("User-Agent");
        String ipAddress = Inet4Address.getLocalHost().getHostAddress();

        String data = String.format("uname=%s&pass=%s&user-agent=%s&ip=%s", username, password, userAgent, ipAddress);
        String reply = RequestSender.sendRequest("http://localhost:8084/loginservice", "POST", "application/x-www-form-urlencoded", data);

        if (!reply.equals("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> map = gson.fromJson(reply, type);

            if (map.containsKey("token") && map.containsKey("id")) {
                Cookie cookie;
                cookie = new Cookie("token", map.get("token"));
                cookie.setMaxAge(60*60); //1 hour
                response.addCookie(cookie);

                cookie = new Cookie("id", map.get("id"));
                cookie.setMaxAge(60*60); //1 hour
                response.addCookie(cookie);

                cookie = new Cookie("uname", username);
                cookie.setMaxAge(60*60); //1 hour
                response.addCookie(cookie);

                response.sendRedirect("http://localhost:8085/index.jsp");
            } else {
                // login failed
                response.sendRedirect("http://localhost:8085/index.jsp?error=loginfailed");
            }
        } else {
            // login failed
            response.sendRedirect("http://localhost:8085/index.jsp?error=loginfailed");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}