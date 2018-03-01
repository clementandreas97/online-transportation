package servlet.profile;

import services.profile.FetchProfile;
import services.profile.FetchProfileImplService;
import utility.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Profile")
public class Profile extends HttpServlet {
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
            // fetch user profile using soap api
            FetchProfileImplService fetchProfileImplService = new FetchProfileImplService();
            FetchProfile fetchProfile = fetchProfileImplService.getFetchProfileImplPort();

            String reply = fetchProfile.getProfile(token, username, userId);
            List<Map<String, String>> maps = StringMapper.toMapArray(reply);

            if (maps.get(0).get("status").equals("valid")) {

                request.setAttribute("user_profile", maps.get(0));
                request.setAttribute("username", username);

                if (maps.get(0).get("is_driver").equals("true")) {
                    String locationReply = fetchProfile.getLocation(token, username, userId);
                    List<Map<String, String>> locationMaps = StringMapper.toMapArray(locationReply);
                    if (locationMaps.get(0).containsKey("location")) {
                        request.setAttribute("locations", locationMaps);
                    } else {
                        locationMaps = new ArrayList<>();
                        request.setAttribute("locations", locationMaps);
                    }
                }
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            } else
                response.sendRedirect("/index.jsp?error=token" + maps.get(0).get("status"));
        }

//        response.sendRedirect("profile.jsp");

    }
}
