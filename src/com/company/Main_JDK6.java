package com.company;


import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main_JDK6 {



    public static void main(String[] args) {


        try {
            boolean provisionSuccess = false;
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File("/hss/provision/config.properties")));
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            String input_dir_prop = properties.getProperty("file.input_dir");
            String output_dir_prop = properties.getProperty("file.output_dir");

            String file_name = properties.getProperty("file.name");

            String SQL = "insert into vodafoneAccts (msisdn, imsi, iccid,profileID, authkeys, fileName,status) values(?,?,?,?,?,?,?)";
            Connection connection = DatabaseUtils.getConnection(url, username, password);
            if (connection != null) {
                DataSet dataSet = new DataSet();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);


                try {
                    int counter =0;
                    File fileInput = new File(input_dir_prop + "/" + file_name);
                    Scanner scanner = new Scanner(fileInput, "UTF-8");
                    scanner.useDelimiter(Pattern.compile("\\n"));
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine();
                        String[] lineArray = line.split(",");

                        dataSet.setMsisdn(lineArray[0].trim());
                        dataSet.setIccid(lineArray[1].trim());
                        dataSet.setImsi(lineArray[2].trim());
                        dataSet.setKeys(lineArray[3].trim());
                        dataSet.setProfileID(lineArray[1].trim().substring(12));

                        Authentication_Keys authentication_keys = new Authentication_Keys(dataSet);
                        try {
                            SOAPMessage soapRequestKeys = authentication_keys.createSOAPRequest();

                            SOAPMessage soapResponsekeys = authentication_keys.sendSOAPRequest(soapRequestKeys);

                            boolean statusKeys = authentication_keys.processSOAPResponse(soapResponsekeys);
                            if (statusKeys) {
                                Subscriber_Details subscriber_details = new Subscriber_Details(dataSet);
                                SOAPMessage soapRequestSubs = subscriber_details.createSOAPRequest();


                                SOAPMessage soapResponseSubs = subscriber_details.sendSOAPRequest(soapRequestSubs);

                                boolean statusSubs = subscriber_details.processSOAPResponse(soapResponseSubs);
                                if (statusSubs) {
                                    provisionSuccess = true;
                                    System.out.println(dataSet.getMsisdn() + " successfully provisioned");
                                    counter++;
                                }


                            }


                        } catch (SOAPException e) {
                            e.printStackTrace();
                        }

                        if (provisionSuccess) {

                            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyMMddHHmmss");
                            String outputFile = output_dir_prop + "/" +  simpleDateFormat.format(new Date()) + "-" + file_name;
                            boolean fileMoveStatus = fileInput.renameTo(new File(outputFile));

                            preparedStatement.setString(1, dataSet.getMsisdn());
                            preparedStatement.setString(2, dataSet.getImsi());
                            preparedStatement.setString(3, dataSet.getIccid());
                            preparedStatement.setString(4, dataSet.getProfileID());
                            preparedStatement.setString(5, dataSet.getKeys());
                            preparedStatement.setString(6, outputFile);
                            preparedStatement.setString(7,"active");
                            preparedStatement.executeUpdate();
                        }
                        provisionSuccess = false;

                    }
                    System.out.println("\n Total account created: "+counter);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
