package servlet.order;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import services.order.Order2ImplService;
import services.profile.FetchProfile;
import services.profile.FetchProfileImplService;
import utility.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Order2")
public class Order2 extends HttpServlet {
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
            String pref_driver = request.getParameter("pref-driver").toString();

            String token = cookiesMap.get("token");
            String username = cookiesMap.get("uname");
            String userId = cookiesMap.get("id");

            Order2ImplService o2impl = new Order2ImplService();
            services.order.Order2 o2 = o2impl.getOrder2ImplPort();

            //list map preferred driver

            String preferred_driver = o2.getDriverPref(token, username, pref_driver, userId);
            List<Map<String, String>> maps1 = StringMapper.toMapArray(preferred_driver);

            if (maps1.get(0).get("status").equals("expired")) {
                // renew token here
                String data = String.format("token=%s", token);
                String restRequest = RequestSender.sendRequest("http://localhost:8084/renewtoken", "POST", "application/x-www-form-urlencoded", data);
                // need to fetch preferred drivers list again
                preferred_driver = o2.getDriverPref(token, username, pref_driver, userId);
                maps1 = StringMapper.toMapArray(preferred_driver);
            }

            if (maps1.get(0).get("status").equals("valid")) {
                String nodeReply = RequestSender.sendRequest("http://localhost:8081/api/users/getfinding", "POST", "application/x-www-form-urlencoded", "");
                Gson gson = new GsonBuilder().create();
                List<Map<String, String>> myList = gson.fromJson(nodeReply,
                    new TypeToken<List<HashMap<String, String>>>() {
                    }.getType());
                List<Integer> list = new ArrayList<>();
                for (Map<String, String> map : myList) {
                    list.add(Integer.parseInt(map.get("userId")));
                }
                if (maps1.get(0).size() > 1){
                    List<Map<String, String>> mapsFilteredPreferred = new ArrayList<>();
                    for (Map<String, String> map : maps1) {
                        if (list.contains(Integer.parseInt(map.get("user_id")))) {
                            mapsFilteredPreferred.add(map);
                        }
                    }
                    request.setAttribute("preferred_driver", mapsFilteredPreferred);
                } else {
                    maps1 = new ArrayList<>();
                    request.setAttribute("preferred_driver", maps1);
                }

                //list map driver from destination
                String destination_driver = o2.getDriverDest(token, username, dest, userId);

                List<Map<String, String>> maps2 = StringMapper.toMapArray(destination_driver);

                if (maps2.get(0).get("status").equals("valid")) {
                    myList = gson.fromJson(nodeReply,
                            new TypeToken<List<HashMap<String, String>>>() {
                            }.getType());
                    list = new ArrayList<>();
                    for (Map<String, String> map : myList) {
                        list.add(Integer.parseInt(map.get("userId")));
                    }
                    if (maps2.get(0).size() > 1){
                        List<Map<String, String>> mapsFiltered = new ArrayList<>();
                        for (Map<String, String> map : maps2) {
                            if (list.contains(Integer.parseInt(map.get("user_id")))) {
                                mapsFiltered.add(map);
                            }
                        }
                        request.setAttribute("driver_destination", mapsFiltered);
                    } else {
                        maps2 = new ArrayList<>();
                        request.setAttribute("driver_destination", maps2);
                    }
                    request.setAttribute("pick", pick);
                    request.setAttribute("dest", dest);
                    request.getRequestDispatcher("/order2.jsp").forward(request, response);
                } else
                    response.sendRedirect("/index.jsp?error=token" + maps2.get(0).get("status"));
            } else
                response.sendRedirect("/index.jsp?error=token" + maps1.get(0).get("status"));

//        response.sendRedirect("profile.jsp");
        }


    }
}
