package servlet.order;

import services.order.FinishOrderImplService;
import services.order.Order3ImplService;
import utility.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "FinishOrder")
public class FinishOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            String rating = request.getParameter("rating").toString();
            String comment = request.getParameter("comment").toString();

            String token = cookiesMap.get("token");
            String username = cookiesMap.get("uname");
            String userId = cookiesMap.get("id");
            FinishOrderImplService finishimpl = new FinishOrderImplService();
            services.order.FinishOrder finish = finishimpl.getFinishOrderImplPort();

            String insertUser = finish.insertDataUser(token, username, driverId, rating);
            List<Map<String, String>> mapsUser = StringMapper.toMapArray(insertUser);

            if (mapsUser.get(0).get("status").equals("expired")) {
                // renew token here
                String data = String.format("token=%s", token);
                String restRequest = RequestSender.sendRequest("http://localhost:8084/renewtoken", "POST", "application/x-www-form-urlencoded", data);
                // need to insert user data again
                insertUser = finish.insertDataUser(token, username, driverId, rating);
                mapsUser = StringMapper.toMapArray(insertUser);
            }

            if (mapsUser.get(0).get("status").equals("valid")) {
                String insertOrder = finish.insertDataOrder(token, username, userId, driverId, pick, dest, rating, comment);
                List<Map<String, String>> mapsOrder = StringMapper.toMapArray(insertOrder);

                if (mapsOrder.get(0).get("status").equals("valid")) {
                    response.sendRedirect("order1");
                } else
                    response.sendRedirect("/index.jsp?error=token" + mapsOrder.get(0).get("status"));
            } else
                response.sendRedirect("/index.jsp?error=token" + mapsUser.get(0).get("status"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
