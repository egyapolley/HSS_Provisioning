/*
package com.company;


import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Properties properties = new Properties();


        try (
                InputStream inputStream = Files.newInputStream(Paths.get("/hss/provision/config.properties"))
        ) {
            long start = System.currentTimeMillis();
            properties.load(inputStream);
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            String input_dir_prop = properties.getProperty("file.input_dir");
            String output_dir_prop = properties.getProperty("file.output_dir");

            String file_name = properties.getProperty("file.name");


            try {
                Scanner scanner = new Scanner(Paths.get(input_dir_prop, file_name), StandardCharsets.UTF_8.name());
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String[] lineArray = line.split(",");
                    DataSet dataSet = new DataSet();
                    dataSet.setMsisdn(lineArray[0].trim());
                    dataSet.setIccid(lineArray[1].trim());
                    dataSet.setImsi(lineArray[2].trim());
                    dataSet.setKeys(lineArray[3].trim());

                    dataSet.setProfileID(lineArray[1].trim().substring(12));

                    Authentication_Keys authentication_keys = new Authentication_Keys(dataSet);
                    try {
                        SOAPMessage soapRequestKeys = authentication_keys.createSOAPRequest();
                        System.out.println("\n\n======Sending Auth Command====\n\n");
                        SOAPMessage soapResponsekeys = authentication_keys.sendSOAPRequest(soapRequestKeys);
                        System.out.println("\n\n====Processing Auth Response====\n\n");
                        boolean statusKeys = authentication_keys.processSOAPResponse(soapResponsekeys);
                        if (statusKeys) {
                            Subscriber_Details subscriber_details = new Subscriber_Details(dataSet);
                            SOAPMessage soapRequestSubs = subscriber_details.createSOAPRequest();

                            System.out.println("\n\n=====Sending Subs Command======\n\n");
                            SOAPMessage soapResponseSubs = subscriber_details.sendSOAPRequest(soapRequestSubs);
                            System.out.println("\n\n======Processing Subs Response====\n\n");
                            boolean statusSubs = subscriber_details.processSOAPResponse(soapResponseSubs);
                            if (statusSubs) {
                                System.out.println("Provisiong Successfully");
                            }


                        }



                    } catch (SOAPException e) {
                        e.printStackTrace();
                    }

                }
                String outputFileName = (LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))) + "-" + file_name;
                Files.move(Paths.get(input_dir_prop, file_name), Paths.get(output_dir_prop, outputFileName), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
*/
