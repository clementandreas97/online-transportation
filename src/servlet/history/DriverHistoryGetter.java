package servlet.history;

import services.history.History;
import services.history.HistoryImplService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utility.CookiesMap;
import utility.RequestSender;
import utility.StringMapper;
import utility.TokenChecker;

@WebServlet(name = "DriverHistoryGetter")
public class DriverHistoryGetter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> cookiesMap = CookiesMap.getCookiesMap(request.getCookies());
        if (!cookiesMap.containsKey("token")) {
            response.sendRedirect("index.jsp?error=notoken");
        } else {
            String statusToken = TokenChecker.checkToken(request);
            if (!statusToken.equals("status=true")) {
                response.sendRedirect("index.jsp?" + statusToken);
            }
            String token = cookiesMap.get("token");
            String username = cookiesMap.get("uname");
            String id = cookiesMap.get("id");

            HistoryImplService historyGetterImplService = new HistoryImplService();
            History historyGetter = historyGetterImplService.getHistoryImplPort();
            String reply = historyGetter.getDriverHistory(token, username, Integer.parseInt(id));
            List<Map<String, String>> maps = StringMapper.toMapArray(reply);

            if (maps.get(0).get("status").equals("valid")) {
                request.setAttribute("history", maps);
                if (maps.get(0).containsKey("name")) {
                    request.setAttribute("history", maps);
                } else {
                    maps = new ArrayList<>();
                    request.setAttribute("history", maps);
                }
                request.setAttribute("id", id);
                request.getRequestDispatcher("driverhistory.jsp").forward(request, response);
            } else
                response.sendRedirect("/index.jsp?error=token" + maps.get(0).get("status"));
        }
    }
}
