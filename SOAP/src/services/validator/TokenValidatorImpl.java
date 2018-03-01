package services.validator;

import org.w3c.dom.Document;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//Service Implementation
@WebService(endpointInterface = "services.validator.TokenValidator")
public class TokenValidatorImpl implements TokenValidator{

    @Override
    public String validateToken(String token, String uname) {
        String soap = "";
        StringBuilder result = new StringBuilder();
        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapMsg = factory.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();

            SOAPEnvelope envelope = part.getEnvelope();
            SOAPHeader header = envelope.getHeader();
            SOAPBody body = envelope.getBody();

            SOAPBodyElement element = body.addBodyElement(envelope.createName("token"));
            element.addTextNode(token);

            SOAPBodyElement element1 = body.addBodyElement(envelope.createName("uname"));
            element1.addTextNode(uname);

            HttpURLConnection httpURLConnection;

            URL urlSend = new URL("http://localhost:8084/rest/validatetoken");
            httpURLConnection = (HttpURLConnection)urlSend.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            soapMsg.writeTo(dataOutputStream);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document soapResponse = documentBuilder.parse(inputStream);
                String status  = soapResponse.getElementsByTagName("status").item(0).getTextContent();
                result.append(status);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();

    }

}