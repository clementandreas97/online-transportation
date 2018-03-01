package servlet.location;

import com.sun.org.apache.xpath.internal.operations.Bool;
import services.location.Location;
import services.location.LocationImplService;
import services.profile.EditProfile;
import services.profile.EditProfileImplService;
import utility.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class UpdateLocation extends HttpServlet {
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
            String userId = cookiesMap.get("id");
            String newLocation = request.getParameter("newlocation");
            String oldLocation = request.getParameter("oldlocation");
            // update edit location using soap api
            LocationImplService locationImplService = new LocationImplService();
            Location location = locationImplService.getLocationImplPort();

            String reply = location.editLocation(token, username, userId, newLocation, oldLocation);
            List<Map<String, String>> maps = StringMapper.toMapArray(reply);

            if (maps.get(0).get("status").equals("valid"))
                response.sendRedirect("/editlocation");
            else
                response.sendRedirect("/index.jsp?error=token" + maps.get(0).get("status"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
