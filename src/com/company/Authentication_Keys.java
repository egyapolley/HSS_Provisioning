package com.company;


import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Authentication_Keys {

    private   String authXML = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:al=\"http://www.alcatel-lucent.com/soap_cm\" xmlns:bd=\"http://www.3gpp.org/ftp/Specs/archive/32_series/32607/schema/32607-700/BasicCMIRPData\" xmlns:bs=\"http://www.3gpp.org/ftp/Specs/archive/32_series/32607/schema/32607-700/BasicCMIRPSystem\" xmlns:gd=\"http://www.3gpp.org/ftp/Specs/archive/32_series/32317/schema/32317-700/GenericIRPData\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "   <SOAP-ENV:Body>\n" +
            "      <bd:createMO>\n" +
            "         <mOIElementLoc>aucServiceProfileId=1,mSubIdentificationNumberId=MSIN,mobileNetworkCodeId=08,mobileCountryCodeId=620,plmnFunctionId=1,managedElementId=HSS1</mOIElementLoc>\n" +
            "         <referenceObjectInstance />\n" +
            "         <mO>\n" +
            "            <moiLocation>aucServiceProfileId=1,mSubIdentificationNumberId=MSIN,mobileNetworkCodeId=08,mobileCountryCodeId=620,plmnFunctionId=1,managedElementId=HSS1</moiLocation>\n" +
            "            <al:moAttributeList>\n" +
            "               <al:moAttribute>\n" +
            "                  <al:name>authenticationSubscriberType</al:name>\n" +
            "                  <al:value>UMTS_MS</al:value>\n" +
            "               </al:moAttribute>\n" +
            "               <al:moAttribute>\n" +
            "                  <al:name>authKey</al:name>\n" +
            "                  <al:value>KEYS</al:value>\n" +
            "               </al:moAttribute>\n" +
            "               <al:moAttribute>\n" +
            "                  <al:name>algorithmPosition</al:name>\n" +
            "                  <al:value>1</al:value>\n" +
            "               </al:moAttribute>\n" +
            "               <al:moAttribute>\n" +
            "                  <al:name>allowedSequenceNumber</al:name>\n" +
            "                  <al:value>PS</al:value>\n" +
            "                  <al:value>EPS</al:value>\n" +
            "                  <al:value>IMS</al:value>\n" +
            "               </al:moAttribute>\n" +
            "            </al:moAttributeList>\n" +
            "         </mO>\n" +
            "      </bd:createMO>\n" +
            "   </SOAP-ENV:Body>\n" +
            "</SOAP-ENV:Envelope>";


    private DataSet dataSet;

    public Authentication_Keys(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    private String authXML_dataSubstition(){
        authXML =authXML.replaceAll("MSIN", dataSet.getImsi().substring(5));
        authXML = authXML.replace("KEYS", dataSet.getKeys());
        return authXML;
    }

    public SOAPMessage createSOAPRequest() throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage SOAPmessage = messageFactory.createMessage(new MimeHeaders(), new ByteArrayInputStream(authXML_dataSubstition().getBytes()));
        SOAPmessage.saveChanges();
        return SOAPmessage;
    }

    public SOAPMessage sendSOAPRequest(SOAPMessage message) throws SOAPException, IOException {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();
        String API_ENDPOINT = "http://172.21.7.6:18100/";
        return connection.call(message, API_ENDPOINT);

    }

    public boolean processSOAPResponse(SOAPMessage soapMessage){
        try {
            if (soapMessage.getSOAPBody().hasFault()) {
                return false;
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return true;
    }




}
