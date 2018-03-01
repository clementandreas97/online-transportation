package identityservice;

import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utility.DatabaseConnector;
import utility.TokenGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.Calendar;
import java.util.stream.Collectors;

@WebServlet(name = "TokenValidator")
public class TokenValidator extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");

        try {
            InputStream inputStream = request.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document soap = documentBuilder.parse(inputStream);

            String token = soap.getElementsByTagName("token").item(0).getTextContent();

            String uname = soap.getElementsByTagName("uname").item(0).getTextContent();


            Connection connection = DatabaseConnector.connect("blackjek_account");
            String selectQuery = " SELECT (if(expiry_time > now(), 'valid', 'expired')) as status from user WHERE uname = ? AND access_token = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, token);
            ResultSet queryResult = preparedStatement.executeQuery();

            String status = "invalid";
            if (queryResult.next()){
                status = queryResult.getString("status");
            }

            if (status.equals("valid")) {
                // renewing expiry time for the token
                String updateQuery = "UPDATE user SET expiry_time = ? WHERE access_token = ?";
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, new TokenGenerator().generateExpiryTime());
                preparedStatement.setString(2, token);
                preparedStatement.executeUpdate();
            }

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapMsg = factory.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();

            SOAPEnvelope envelope = part.getEnvelope();
            SOAPHeader header = envelope.getHeader();
            SOAPBody body = envelope.getBody();

            SOAPBodyElement element = body.addBodyElement(envelope.createName("status"));
            element.addTextNode(status);

            soapMsg.writeTo(response.getOutputStream());



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
