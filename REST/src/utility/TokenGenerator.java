package utility;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TokenGenerator {
    public String generateToken(String username, String expiryTime, String userAgent, String ipAddress) {
        String string = "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx" + expiryTime;
        RandomString randomizer = new RandomString(23, new SecureRandom(), string);
        return String.format("%s#%s#%s", randomizer.nextString(), userAgent, ipAddress).replace(';', '!');
    }

    public String generateExpiryTime() {
        Calendar time = Calendar.getInstance();
        time.setTime(Calendar.getInstance().getTime());
        time.add(Calendar.MINUTE, 60);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getTime());
        return timeStamp;
    }
}
