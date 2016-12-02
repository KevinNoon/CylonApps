package com.kevinnoon.cylonapps;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 16/03/2016.
 */

public class TypeFunctions {
    public static void main(String[] args) {
        List<TypeDotNet> dotList = GetDotNetTypes(Paths.get("C:\\temp\\controllers.xml"));
        for (int i = 0; i < dotList.size();i++){
            System.out.println(dotList.get(i));
        }
        List<TypeSubNet> subList = GetSubNetTypes(Paths.get("C:\\temp\\controllers.xml"));
        for (int i = 0; i < subList.size();i++){
            System.out.println(subList.get(i).getTotalCount());
        }
    }


    static public List<TypeDotNet> GetDotNetTypes(Path controllersXML) {
        List<TypeDotNet> typeDotNetList = new ArrayList<TypeDotNet>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;
            doc = docBuilder.parse( controllersXML.toFile() );
            // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList listOfDevices = doc.getElementsByTagName( "device" );

            for (int s = 0; s < listOfDevices.getLength(); s++) {
                Node device = listOfDevices.item( s );
                TypeDotNet typeDotNet = new TypeDotNet();
                if (device.getNodeType() == Node.ELEMENT_NODE) {

                    Element firstDevice = (Element) device;

                    typeDotNet.setID( Integer.parseInt( firstDevice.getAttribute( "ID" ) ) - 1 );

                    NodeList deviceList = firstDevice.getElementsByTagName( "variation_name" );
                    Element deviceElement = (Element) deviceList.item( 0 );
                    typeDotNet.setVariationName( deviceElement.getTextContent() );

                    deviceList = firstDevice.getElementsByTagName( "release_status_ID" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeDotNet.setReleaseStatus( Integer.parseInt( deviceElement.getTextContent() ) );

                    deviceList = firstDevice.getElementsByTagName( "family" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeDotNet.setFamily( deviceElement.getTextContent() );

                    deviceList = firstDevice.getElementsByTagName( "UEC_controller_list_primary" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeDotNet.setUECListPrimary( deviceElement.getTextContent().contains( "yes" ) );

                    deviceList = firstDevice.getElementsByTagName( "keypad_type" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeDotNet.setKeyPadType( Integer.parseInt( deviceElement.getTextContent() ) );

                    NodeList commsConfigList = firstDevice.getElementsByTagName( "comms_config" );
                    Element commsConfig = (Element) commsConfigList.item( 0 );
                    //    controllerTypes.setVariationName();
                    if (!(commsConfig == null)) {
                        if (commsConfig.getElementsByTagName( "modbus_support" ).getLength() > 0) {
                            typeDotNet.setModbusSupport( commsConfig.getElementsByTagName( "modbus_support" ).item( 0 ).
                                    getFirstChild().getNodeValue().contains( "yes" ) );
                        }
                        if (commsConfig.getElementsByTagName( "embedded_web_supervisor_pages" ).getLength() > 0) {
                            typeDotNet.seteWebSupervisorPages( commsConfig.getElementsByTagName( "embedded_web_supervisor_pages" ).item( 0 ).
                                    getFirstChild().getNodeValue().contains( "yes" ) );
                        }
                        if (commsConfig.getElementsByTagName( "embedded_web_email_alarms" ).getLength() > 0) {
                            typeDotNet.seteWebEmailAlarms( commsConfig.getElementsByTagName( "embedded_web_email_alarms" ).item( 0 ).
                                    getFirstChild().getNodeValue().contains( "yes" ) );
                        }
                        if (commsConfig.getElementsByTagName( "embedded_web_configuration_pages" ).getLength() > 0) {
                            typeDotNet.seteWebConfigPages( commsConfig.getElementsByTagName( "embedded_web_configuration_pages" ).item( 0 ).
                                    getFirstChild().getNodeValue().contains( "yes" ) );
                        }
                        typeDotNetList.add( typeDotNet );
                    }

                }


            }

        } catch (SAXParseException err) {

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return typeDotNetList;
    }

    static public List<TypeSubNet> GetSubNetTypes(Path controllersXML) {
        List<TypeSubNet> typeSubNetList = new ArrayList<TypeSubNet>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;
            doc = docBuilder.parse( controllersXML.toFile() );
            // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList listOfDevices = doc.getElementsByTagName( "device" );

            for (int s = 0; s < listOfDevices.getLength(); s++) {
                Node device = listOfDevices.item( s );
                TypeSubNet typeSubNet = new TypeSubNet();
                if (device.getNodeType() == Node.ELEMENT_NODE) {

                    Element firstDevice = (Element) device;

                    typeSubNet.setID( Integer.parseInt( firstDevice.getAttribute( "ID" ) ) - 1 );

                    NodeList deviceList = firstDevice.getElementsByTagName( "variation_name" );
                    Element deviceElement = (Element) deviceList.item( 0 );
                    typeSubNet.setVariationName( deviceElement.getTextContent() );

                    deviceList = firstDevice.getElementsByTagName( "release_status_ID" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeSubNet.setReleaseStatus( Integer.parseInt( deviceElement.getTextContent() ) );

                    deviceList = firstDevice.getElementsByTagName( "family" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeSubNet.setFamily( deviceElement.getTextContent() );

                    deviceList = firstDevice.getElementsByTagName( "UEC_controller_list_primary" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeSubNet.setUECListPrimary( deviceElement.getTextContent().contains( "yes" ) );

                    deviceList = firstDevice.getElementsByTagName( "keypad_type" );
                    deviceElement = (Element) deviceList.item( 0 );
                    typeSubNet.setKeyPadType( Integer.parseInt( deviceElement.getTextContent() ) );


                    NodeList fieldConfigList = firstDevice.getElementsByTagName( "field_config" );
                    Element fieldConfig = (Element) fieldConfigList.item( 0 );
                    //    controllerTypes.setVariationName();
                    if (!(fieldConfig == null)) {
                        if (fieldConfig.getElementsByTagName( "subnet_limit" ).getLength() > 0) {
                            ;
                            typeSubNet.setSubnetLimit( Integer.parseInt( fieldConfig.getElementsByTagName( "subnet_limit" ).item( 0 ).
                                    getFirstChild().getNodeValue() ) );
                        }
                        if (fieldConfig.getElementsByTagName( "strategy_blocks" ).getLength() > 0) {
                            typeSubNet.setStrategyBlocks( Integer.parseInt( fieldConfig.getElementsByTagName( "strategy_blocks" ).item( 0 ).
                                    getFirstChild().getNodeValue() ) );
                        }
                        if (fieldConfig.getElementsByTagName( "controller_points" ).getLength() > 0) {
                            typeSubNet.setControllerPoints( Integer.parseInt( fieldConfig.getElementsByTagName( "controller_points" ).item( 0 ).
                                    getFirstChild().getNodeValue() ) );
                        }
                        if (fieldConfig.getElementsByTagName( "pulse_counting_support" ).getLength() > 0) {
                            typeSubNet.setPulseCountSupport( fieldConfig.getElementsByTagName( "pulse_counting_support" ).item( 0 ).
                                    getFirstChild().getNodeValue().contains( "yes" ) );
                        }
                        if (fieldConfig.getElementsByTagName( "virtual" ).getLength() > 0) {
                            typeSubNet.setVirtual( fieldConfig.getElementsByTagName( "virtual" ).item( 0 ).
                                    getFirstChild().getNodeValue().contains( "yes" ) );
                        }
                        if (fieldConfig.getElementsByTagName( "peer_to_peer_support" ).getLength() > 0) {
                            typeSubNet.setPeerToPeerSupport( fieldConfig.getElementsByTagName( "peer_to_peer_support" ).item( 0 ).
                                    getFirstChild().getNodeValue().contains( "yes" ) );
                        }
                        if (fieldConfig.getElementsByTagName( "dec_blocks" ).getLength() > 0) {
                            typeSubNet.setStrategyBlocks( Integer.parseInt( fieldConfig.getElementsByTagName( "dec_blocks" ).item( 0 ).
                                    getFirstChild().getNodeValue() ) );
                        }

                        NodeList textDLList = fieldConfig.getElementsByTagName( "datalogs" );
                        Element DLConfig = (Element) textDLList.item( 0 );
                        if (textDLList.getLength() > 0) {
                            if (DLConfig.getElementsByTagName( "number_of_datalogs" ).getLength() > 0) {
                                typeSubNet.setDataLogNumbers( Integer.parseInt( DLConfig.getElementsByTagName( "number_of_datalogs" ).item( 0 ).
                                        getFirstChild().getNodeValue() ) );
                            }
                            if (DLConfig.getElementsByTagName( "datalog_length" ).getLength() > 0) {
                                typeSubNet.setDataLogLength( Integer.parseInt( DLConfig.getElementsByTagName( "datalog_length" ).item( 0 ).
                                        getFirstChild().getNodeValue() ) );
                            }
                        }

                        NodeList BacNetList = fieldConfig.getElementsByTagName( "BACnet" );
                        Element BacNetConfig = (Element) textDLList.item( 0 );
                        if (BacNetList.getLength() > 0) {
                            if (BacNetConfig.getElementsByTagName( "object_name_max_chars" ).getLength() > 0) {
                                typeSubNet.setBacNetMaxChar( Integer.parseInt( BacNetConfig.getElementsByTagName( "object_name_max_chars" ).item( 0 ).
                                        getFirstChild().getNodeValue() ) );
                            }
                            if (BacNetConfig.getElementsByTagName( "max_exposed_points" ).getLength() > 0) {
                                typeSubNet.setBacNetMaxExpPoints( Integer.parseInt( BacNetConfig.getElementsByTagName( "max_exposed_points" ).item( 0 ).
                                        getFirstChild().getNodeValue() ) );
                            }
                            if (BacNetConfig.getElementsByTagName( "max_unit_strings" ).getLength() > 0) {
                                typeSubNet.setBacNetMaxUnitString( Integer.parseInt( BacNetConfig.getElementsByTagName( "max_unit_strings" ).item( 0 ).
                                        getFirstChild().getNodeValue() ) );
                            }
                        }

                        NodeList pointList = fieldConfig.getElementsByTagName( "point" );
                        Map<Integer, Integer> points = new HashMap<Integer, Integer>();

                        int type = 0, number = 0;
                        for (int x = 0; x < pointList.getLength(); x++) {

                            type = Integer.parseInt( pointList.item( x ).getAttributes().getNamedItem( "db_type" ).getNodeValue() );
                            number = Integer.parseInt( pointList.item( x ).getAttributes().getNamedItem( "number" ).getNodeValue() );
                            switch (type) {
                                case (1): {
                                    typeSubNet.setType01Count( typeSubNet.getType01Count() + 1 );
                                    break;
                                }
                                case (2): {
                                    typeSubNet.setType02Count( typeSubNet.getType02Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (3): {
                                    typeSubNet.setType03Count( typeSubNet.getType03Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (4): {
                                    typeSubNet.setType04Count( typeSubNet.getType04Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (5): {
                                    typeSubNet.setType05Count( typeSubNet.getType05Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (6): {
                                    typeSubNet.setType06Count( typeSubNet.getType06Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (7): {
                                    typeSubNet.setType07Count( typeSubNet.getType07Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (8): {
                                    typeSubNet.setType08Count( typeSubNet.getType08Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (9): {
                                    typeSubNet.setType09Count( typeSubNet.getType09Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                                case (10): {
                                    typeSubNet.setType10Count( typeSubNet.getType10Count() + 1 );
                                    typeSubNet.setTotalCount( typeSubNet.getTotalCount() + 1 );
                                    break;
                                }
                            }
                            points.put( number, type );
                            typeSubNet.setPoints( points );

                        }
                        typeSubNetList.add( typeSubNet );
                    }

                }

            }

        } catch (SAXParseException err) {

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return typeSubNetList;
    }

}