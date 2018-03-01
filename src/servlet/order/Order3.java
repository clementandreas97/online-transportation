package servlet.order;

import services.order.Order2ImplService;
import services.order.Order3ImplService;
import utility.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Order3")
public class Order3 extends HttpServlet {
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
            String pick = request.getParameter("pick");
            String dest = request.getParameter("dest");
            String driverId = request.getParameter("driverId");
            String driverUsername = request.getParameter("driverUsername");
            String restData = String.format("drivername=%s", driverUsername);
            String nodeReply = RequestSender.sendRequest("http://localhost:8081/api/users/finishorder", "POST", "application/x-www-form-urlencoded", restData);

            String token = cookiesMap.get("token");
            String username = cookiesMap.get("uname");
            String userId = cookiesMap.get("id");

            Order3ImplService o3impl = new Order3ImplService();
            services.order.Order3 o3 = o3impl.getOrder3ImplPort();
            //list map driver data service
            String driver_data_service = o3.getDriverDataService(token, username, driverId);
            List<Map<String, String>> maps1 = StringMapper.toMapArray(driver_data_service);

            if (maps1.get(0).get("status").equals("valid")) {
                request.setAttribute("driver_data_service", maps1.get(0));

                //list map driver data service
                String driver_data_account = o3.getDriverDataAccount(token, username, driverId);
                List<Map<String, String>> maps2 = StringMapper.toMapArray(driver_data_account);

                if (maps2.get(0).get("status").equals("expired")) {
                    // renew token here
                    String data = String.format("token=%s", token);
                    String restRequest = RequestSender.sendRequest("http://localhost:8084/renewtoken", "POST", "application/x-www-form-urlencoded", data);
                    // need to fetch driver data again
                    driver_data_account = o3.getDriverDataAccount(token, username, driverId);
                    maps2 = StringMapper.toMapArray(driver_data_account);
                }

                if (maps1.get(0).get("status").equals("valid")) {
                    request.setAttribute("driver_data_account", maps2.get(0));
                    request.setAttribute("pick", pick);
                    request.setAttribute("dest", dest);
                    request.setAttribute("driverId", driverId);

                    request.getRequestDispatcher("/order3.jsp").forward(request, response);
                } else
                    response.sendRedirect("/index.jsp?error=token" + maps2.get(0).get("status"));
            } else
                response.sendRedirect("/index.jsp?error=token" + maps1.get(0).get("status"));
        }
    }
}
