package servlet.order;

import services.order.Order3ImplService;
import utility.CookiesMap;
import utility.RequestSender;
import utility.StringMapper;
import utility.TokenChecker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ChatOrder extends HttpServlet {
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
            String pick = request.getParameter("pick").toString();
            String dest = request.getParameter("dest").toString();
            String driverId = request.getParameter("driverId").toString();

            String token = cookiesMap.get("token");
            String username = cookiesMap.get("uname");
            String userId = cookiesMap.get("id");

            request.setAttribute("userId", userId);
            request.setAttribute("driverId", driverId);
            request.setAttribute("pick",pick);
            request.setAttribute("dest", dest);

            Order3ImplService o3impl = new Order3ImplService();
            services.order.Order3 o3 = o3impl.getOrder3ImplPort();
            //list map driver data service
            String driver_data_account = o3.getDriverDataAccount(token, username, driverId);
            List<Map<String, String>> maps1 = StringMapper.toMapArray(driver_data_account);

            if (maps1.get(0).get("status").equals("expired")) {
                // renew token here
                String data = String.format("token=%s", token);
                String restRequest = RequestSender.sendRequest("http://localhost:8084/renewtoken", "POST", "application/x-www-form-urlencoded", data);
                // need to fetch driver data again
                driver_data_account = o3.getDriverDataAccount(token, username, driverId);
                maps1 = StringMapper.toMapArray(driver_data_account);
            }

            if (maps1.get(0).get("status").equals("valid")) {
                request.setAttribute("driver_username", maps1.get(0).get("uname"));
            } else
                response.sendRedirect("/index.jsp?error=token" + maps1.get(0).get("status"));

            request.setAttribute("username", username);


            String data = String.format("username=%s&driverId=%s", username, driverId);
            String nodeReply = RequestSender.sendRequest("http://localhost:8081/api/users/startorder", "POST", "application/x-www-form-urlencoded", data);
            request.getRequestDispatcher("/chatuser.jsp").forward(request, response);
        }
    }
}
