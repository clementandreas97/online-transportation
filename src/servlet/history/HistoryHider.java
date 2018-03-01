package servlet.history;

import services.history.History;
import services.history.HistoryImplService;
import utility.CookiesMap;
import utility.RequestSender;
import utility.StringMapper;
import utility.TokenChecker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HistoryHider")
public class HistoryHider extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            String id = request.getParameter("orderId");
            String isUser = request.getParameter("isUser");
            String driver = "driver";
            if (isUser.equals("true")) {
                driver = "user";
            }
            HistoryImplService historyImplService = new HistoryImplService();
            History history = historyImplService.getHistoryImplPort();
            String reply = history.hideHistory(token, username, Integer.parseInt(id), driver);
            List<Map<String, String>> maps = StringMapper.toMapArray(reply);

            if (!maps.get(0).get("status").equals("valid"))
                response.sendRedirect("/index.jsp?error=token" + maps.get(0).get("status"));
        }

    }

}
