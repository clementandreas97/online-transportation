package utility;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.util.Map;

public class TokenChecker {
    public static String checkToken(HttpServletRequest request) {
        try {
            String userAgent = request.getHeader("User-Agent").replace(';', '!');
            String ipAddress = Inet4Address.getLocalHost().getHostAddress();

            Map<String, String> cookiesMap = CookiesMap.getCookiesMap(request.getCookies());
            String token = cookiesMap.get("token");
            String[] splitString = token.split("#");

            if (splitString[1].equals(userAgent) && splitString[2].equals(ipAddress))
                return "status=true";
            else
                return "status=invalidtoken";
        } catch (Exception e) {
            return "status=error";
        }
    }
}
