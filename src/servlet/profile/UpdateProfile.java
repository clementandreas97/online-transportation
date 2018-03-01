package servlet.profile;

import com.sun.org.apache.xpath.internal.operations.Bool;
import services.profile.EditProfile;
import services.profile.EditProfileImplService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.misc.IOUtils;
import utility.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UpdateProfile")
@MultipartConfig
public class UpdateProfile extends HttpServlet {
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
            Part filePart = request.getPart("newpic");
            StringBuilder stringBuilder = new StringBuilder();
            BASE64Encoder base64Encoder = new BASE64Encoder();
            InputStream inputStream = filePart.getInputStream();
            String pictureBlob = "";
            byte[] buffer = new byte[1024];
            if (filePart.getSize() > 0) {
                try {
                    // remaining is the number of bytes to read to fill the buffer
                    int remaining = buffer.length;
                    while (true) {
                        int read = inputStream.read(buffer, buffer.length - remaining, remaining);
                        pictureBlob += base64Encoder.encode(buffer);
                        if (read >= 0) { // some bytes were read
                        } else {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String name = request.getParameter("newname");
            String phone = request.getParameter("phone");
            Boolean status = request.getParameter("status") != null;
            // update profile using soap api
            EditProfileImplService editProfileImplService = new EditProfileImplService();
            EditProfile editProfile = editProfileImplService.getEditProfileImplPort();

            String reply = editProfile.editProfile(token, username, userId, name, phone, status, pictureBlob);
            List<Map<String, String>> maps = StringMapper.toMapArray(reply);

            if (maps.get(0).get("status").equals("valid"))
                response.sendRedirect("/profile");
            else
                response.sendRedirect("/index.jsp?error=token" + maps.get(0).get("status"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void writeBlock(int blockNumber, byte[] buffer, int length) throws IOException {
        FileOutputStream fos = new FileOutputStream("output_" + blockNumber + ".dat");
        try {
            fos.write(buffer, 0, length);
        } finally {
            fos.close();
        }
    }
}
