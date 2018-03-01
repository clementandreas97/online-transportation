package servlet.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utility.RequestSender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class UsernameCheckAjax extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("uname");
        String data = String.format("uname=%s", username);
        String reply = RequestSender.sendRequest("http://localhost:8084/rest/usernameajax", "POST", "application/x-www-form-urlencoded", data);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> map = gson.fromJson(reply, type);
        response.getWriter().write(map.get("check"));
    }
}
