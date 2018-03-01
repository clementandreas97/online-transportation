package utility;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class CookiesMap {
    public static Map<String, String> getCookiesMap(Cookie[] cookies) {
        Map<String, String> map = new HashMap<String, String>();
        for (Cookie cooky : cookies) {
            map.put(cooky.getName(), cooky.getValue());
        }
        return map;
    }
}
