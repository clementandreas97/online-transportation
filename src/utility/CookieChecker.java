package utility;

import services.validator.TokenValidator;
import services.validator.TokenValidatorImplService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CookieChecker {
    public static String check(Cookie[] cookies) throws ServletException, IOException {
        boolean hasToken = false;
        String tokenCooky = "";
        String usernameCooky = "";
        if (cookies != null) {
            for (Cookie cooky : cookies) {
                String cookieName = cooky.getName();
                String cookieValue = cooky.getValue();
                if (cookieName.equals("token")) {
                    hasToken = true;
                    tokenCooky = cookieValue;
                } else if (cookieName.equals("uname")) {
                    usernameCooky = cookieValue;
                }
            }
            if (hasToken) {
                // TODO :validate the token
                TokenValidatorImplService tokenValidatorImplService = new TokenValidatorImplService();
                TokenValidator tokenValidator = tokenValidatorImplService.getTokenValidatorImplPort();
                String reply = tokenValidator.validateToken(tokenCooky, usernameCooky);
                return reply;

            } else {
                // dont have token cookies
                return "notoken";
            }
        } else {
            // dont have cookies
            return "notoken";
        }
    }
}
