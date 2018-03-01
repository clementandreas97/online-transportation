package servlet.order;

import services.profile.FetchProfile;
import services.profile.FetchProfileImplService;
import utility.CookieChecker;
import utility.CookiesMap;
import utility.StringMapper;
import utility.TokenChecker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Order1 extends HttpServlet {
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
            String userId = cookiesMap.get("id");
            String username = cookiesMap.get("uname");

            String status = CookieChecker.check(request.getCookies());

            FetchProfileImplService fetchProfileImplService = new FetchProfileImplService();
            FetchProfile fetchProfile = fetchProfileImplService.getFetchProfileImplPort();

            String reply = fetchProfile.getProfile(token, username, userId);
            List<Map<String, String>> maps = StringMapper.toMapArray(reply);

            if (status.equals("valid")) {
                if (maps.get(0).get("is_driver").equals("true")) {
                    request.setAttribute("username", username);
                    request.setAttribute("user_id", userId);
                    request.getRequestDispatcher("/driverorder.jsp").forward(request,response);
                } else {
                    request.getRequestDispatcher("/order.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("index.jsp?error=token" + status);
            }
        }

    }
}
