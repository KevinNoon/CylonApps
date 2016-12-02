package com.kevinnoon.cylonapps;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kevin on 07/04/2016.
 */
public class XMLFunctions {

    public static void main(String[] args) {
        System.out.println( IsDotNetUsed(Paths.get( "C:\\temp\\Globals.xml" ),2 ));
        System.out.println(addCommsCntl(Paths.get( "C:\\temp\\Globals.xml" ),7,1 ));
        System.out.println( IsGlobalUsed(Paths.get( "C:\\temp\\Globals.xml" ),2 ));
    }


    public static int addSubNetCntl(Path fileName,int address,GlobalLocal globalLocal){        // This will add a DotNet with SubNet controllers to an Associations file
        // fileName: Path of Associations file
        // DotNetSubNets: Map of SubNet Controllers with the address as the key and the file name as the value
        // Returns the address of the inserted DotNet.
        // If no free addresses returns -1
        if(!IsDotNetUsed(fileName, address)) return -1;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( String.valueOf( fileName ) );
            Element root = doc.getDocumentElement();

            Element localGlobal = setLocalGlobal( doc, globalLocal );

            Element SubNetCtrl = doc.createElement( "local" );
            SubNetCtrl.appendChild(localGlobal);

            Element newDotNet;
            newDotNet = doc.createElement( "commsController" );
            newDotNet.setAttribute( "address", String.valueOf( address ) );
            newDotNet.appendChild( SubNetCtrl );

            root.appendChild( newDotNet );

            DOMSource sourceXML = new DOMSource( doc );
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult( String.valueOf( fileName ));
            transformer.transform( sourceXML, result );
        } catch (
                Exception ex
                ) {
            ex.printStackTrace();
        }
        return address;
    }








    public static int addCommsCntl(Path fileName,int address,int subaddress){        // This will add a DotNet with SubNet controllers to an Associations file
        // fileName: Path of Associations file
        // DotNetSubNets: Map of SubNet Controllers with the address as the key and the file name as the value
        // Returns the address of the inserted DotNet.
        // If no free addresses returns -1
        if(IsDotNetUsed(fileName, address)) return -1;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( String.valueOf( fileName ) );
            Element root = doc.getDocumentElement();

            GlobalLocal globalLocal = new GlobalLocal();
  //          globalLocal.broadcast.setDestinationFieldControllers( "1.2" );
            Element localGlobal = setLocalGlobal( doc, globalLocal );

            Element SubNetCtrl = doc.createElement( "local" );
            SubNetCtrl.appendChild(localGlobal);

            Element newDotNet;
            newDotNet = doc.createElement( "commsController" );
            newDotNet.setAttribute( "address", String.valueOf( address ) );
            newDotNet.appendChild( SubNetCtrl );

            root.appendChild( newDotNet );

            DOMSource sourceXML = new DOMSource( doc );
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult( String.valueOf( fileName ));
            transformer.transform( sourceXML, result );
        } catch (
                Exception ex
                ) {
            ex.printStackTrace();
        }
        return address;
    }

    private static Element setLocalGlobal(Document doc, GlobalLocal globalLocal) {

        Element source = doc.createElement( "source" );
        source.setAttribute( "fieldControllerAddress" ,String.valueOf( globalLocal.source.getFieldControllerAddress() ));
        source.setAttribute( "pointAddress" ,String.valueOf( globalLocal.source.getPointAddress() ));

        Element destination = doc.createElement( "destination" );
        destination.setAttribute( "fieldControllerAddress" ,String.valueOf( globalLocal.destination.getFieldControllerAddress() ));
        destination.setAttribute( "pointAddress" ,String.valueOf( globalLocal.destination.getPointAddress() ));

        Element broadcast = doc.createElement( "broadcast" );
        broadcast.setAttribute( "destinationFieldControllers", String.valueOf( globalLocal.broadcast.getDestinationFieldControllers() ) );

        Element localGlobal = doc.createElement( "localGlobal" );

        localGlobal.setAttribute( "hasModule", String.valueOf( globalLocal.getHasModule()));
        localGlobal.setAttribute( "id", String.valueOf( globalLocal.getId()));
        localGlobal.setAttribute( "name", String.valueOf( globalLocal.getName()));
        localGlobal.setAttribute( "number", String.valueOf( globalLocal.getName()));
        localGlobal.setAttribute( "pointType", String.valueOf( globalLocal.getPointType()));
        localGlobal.setAttribute( "servicePeriod", String.valueOf( globalLocal.getServicePeriod()));
        localGlobal.appendChild( source );
        localGlobal.appendChild( destination );
        if (globalLocal.broadcast.getDestinationFieldControllers() != null){
            localGlobal.appendChild( broadcast );}
        Element defaultValue = doc.createElement( "defaultValue" );
        defaultValue.setTextContent( String.valueOf( globalLocal.getDefaultValue()));
        localGlobal.appendChild(defaultValue);
        return localGlobal;
    }

    public static boolean IsDotNetUsed(Path fileName, int address) {
        // This will check if a DotNet exists in a xml file
        // fileName: Path of Associations file
        //address: Address to check if in use
        //Returns true if the address is used in the associations file
        boolean isAddress = false;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( String.valueOf( fileName ) );

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName( "commsController" );
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item( temp );
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (address == Integer.parseInt( eElement.getAttribute( "address" ))){
                        isAddress = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAddress;
    }

    public static boolean IsGlobalUsed(Path fileName, int address){
        // This will check if a Global exists in a xml file
        // fileName: Path of Associations file
        //address: Address to check if in use
        //Returns true if the address is used in the associations file
        boolean isAddress = false;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( String.valueOf( fileName ) );

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName( "commsController" );
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item( temp );
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (address == Integer.parseInt( eElement.getAttribute( "address" ))){
                        NodeList cNode = nNode.getChildNodes();
                              isAddress = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAddress;
    }

}
