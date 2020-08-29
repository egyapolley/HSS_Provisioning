package com.company;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Subscriber_Details {


    private String authXML = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:bd=\"http://www.3gpp.org/ftp/Specs/archive/32_series/32607/schema/32607-700/BasicCMIRPData\" xmlns:bs=\"http://www.3gpp.org/ftp/Specs/archive/32_series/32607/schema/32607-700/BasicCMIRPSystem\" xmlns:gd=\"http://www.3gpp.org/ftp/Specs/archive/32_series/32317/schema/32317-700/GenericIRPData\" xmlns:mO=\"http://www.alcatel-lucent.com/soap_cm\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "   <SOAP-ENV:Body>\n" +
            "      <bd:createMO>\n" +
            "         <mOIElementLoc>gsmServiceProfileId=1,suMSubscriptionProfileId=1,suMSubscriberProfileId=1-XXXX,subscriptionFunctionId=1,managedElementId=HSS1</mOIElementLoc>\n" +
            "         <referenceObjectInstance />\n" +
            "         <mO>\n" +
            "            <moiLocation>gsmServiceProfileId=1,suMSubscriptionProfileId=1,suMSubscriberProfileId=1-XXXX,subscriptionFunctionId=1,managedElementId=HSS1</moiLocation>\n" +
            "            <mO:moAttributeList>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>mSubIdentificationNumberId</mO:name>\n" +
            "                  <mO:value>MSIN</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>mobileCountryCodeId</mO:name>\n" +
            "                  <mO:value>620</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>mobileNetworkCodeId</mO:name>\n" +
            "                  <mO:value>08</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>MainSNwithBearerService</mO:name>\n" +
            "                  <mO:value>233-30-MSISDN:GPRS</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>networkAccessMode</mO:name>\n" +
            "                  <mO:value>GPRSonly</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>accessRestrictionData</mO:name>\n" +
            "                  <mO:value>NORES</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>epsAccessSubscriptionType</mO:name>\n" +
            "                  <mO:value>3GPP</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>maxRequestedBandwidthDL</mO:name>\n" +
            "                  <mO:value>104857600</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>maxRequestedBandwidthUL</mO:name>\n" +
            "                  <mO:value>503316480</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>epsApnContextSetList</mO:name>\n" +
            "                  <mO:value>1/15/3GPP///50575C60/////3GPP/0</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>apnOiReplacement</mO:name>\n" +
            "                  <mO:value>mnc008.mcc620.gprs</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>ratFreqSelectPriorityId</mO:name>\n" +
            "                  <mO:value>1</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>epsServiceProfile</mO:name>\n" +
            "                  <mO:value>true</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "               <mO:moAttribute>\n" +
            "                  <mO:name>chargingCharacteristics</mO:name>\n" +
            "                  <mO:value>NORMAL</mO:value>\n" +
            "               </mO:moAttribute>\n" +
            "            </mO:moAttributeList>\n" +
            "         </mO>\n" +
            "      </bd:createMO>\n" +
            "   </SOAP-ENV:Body>\n" +
            "</SOAP-ENV:Envelope>";

    private DataSet dataSet;

    public Subscriber_Details(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    private String authXML_dataSubstition(){
        authXML = authXML.replaceAll("XXXX", dataSet.getProfileID());
        authXML =authXML.replace("MSIN", dataSet.getImsi().substring(5));
        authXML = authXML.replace("MSISDN", dataSet.getMsisdn().substring(5));
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
