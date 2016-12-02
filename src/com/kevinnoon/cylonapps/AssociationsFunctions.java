package com.kevinnoon.cylonapps;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by kevin on 27/03/2016 at 07:02.
 *
 */



public class AssociationsFunctions {


    public static boolean IsAddressUsed(Path fileName,int address) {
        // This will check if a DotNet exists in a associations file
        // fileName: Path of Associations file
        //address: Address to check if in use
        //Returns true if the address is used in the associations file
        boolean isAddress = false;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( String.valueOf( fileName ) );

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName( "comms_controller" );
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

    public static int GetNextFreeAddress(Path fileName){
        // This will get the next free DotNet address in an associations file
        // fileName: Path of Associations file
        //Returns the next free address in the associations file
        //If no free addresses returns -1
        for (int i = 1;i <= Constants.MaxDotNets;i++){
            if (!IsAddressUsed(fileName,i)){
                return i;
            }
        }
        return -1;
    }

    public static int GetNoOfFreeAddresses(Path fileName){
        // This will get the number of free DotNet address in an associations file
        // fileName: Path of Associations file
        //Returns the number free DotNet address in the associations file
        //If no free addresses returns 0
        int FreeAddresses = 0;
        for (int i = 1;i <= Constants.MaxDotNets;i++){
            if (!IsAddressUsed(fileName,i)){
                FreeAddresses++;
            }
        }
        return FreeAddresses;
    }

    public static int GetNoOfUsedAddresses(Path fileName){
        // This will get the number of used DotNet address in an associations file
        // fileName: Path of Associations file
        //Returns the number used DotNet address in the associations file
        //If used addresses returns 0
        int UsedAddresses = 0;
        for (int i = 1;i <= Constants.MaxDotNets;i++){
            if (IsAddressUsed(fileName,i)){
                UsedAddresses++;
            }
        }
        return UsedAddresses;
    }

    public static boolean CreateNewXML(Path fileName){
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("associations");
            doc.appendChild(rootElement);
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileName.toFile());
            transformer.transform(source, result);

    } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static int AddDotNet(Path fileName, LinkedHashMap<Integer,String> subNetAssociations,int DotNetNo) {
        // This will add a DotNet with SubNet controllers to an Associations file
        // fileName: Path of Associations file
        // DotNetSubNets: Map of SubNet Controllers with the address as the key and the file name as the value
        // DotNetNo Address of DotNet to be added
        // Returns the address of the inserted DotNet.
        // If addresses used returns -1
        if (IsAddressUsed(fileName,DotNetNo)) return -1;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( String.valueOf( fileName ) );
            Element root = doc.getDocumentElement();
            Element newDotNet;
            newDotNet = doc.createElement( "comms_controller" );
            newDotNet.setAttribute( "address", String.valueOf( DotNetNo ) );

            for(int key: subNetAssociations.keySet()){
                Element SubNetCtrl = doc.createElement( "field_controller" );
                SubNetCtrl.setAttribute( "address", String.valueOf( key ) );
                SubNetCtrl.setTextContent(subNetAssociations.get(key));
                newDotNet.appendChild( SubNetCtrl );

                root.appendChild( newDotNet );
            }
            DOMSource source = new DOMSource( doc );
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult( String.valueOf( fileName ));
            transformer.transform( source, result );
        } catch (
                Exception ex
                ) {
            ex.printStackTrace();
        }
        return DotNetNo;
    }

    public static LinkedHashMap<Integer,LinkedHashMap<Integer,String>> GetAssociations(Path fileName) {
        // This will load associations file into an array of Associations
        // Associations has fields DotNetNumber,SubNetNumber,FileName
        // fileName: Path of Associations file
        // Returns an Array of Associations
        ArrayList<Associations> AssList = new ArrayList<>();
        int commsNumber;
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( String.valueOf( fileName ) );

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName( "comms_controller" );
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item( temp );
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    commsNumber = Integer.parseInt( eElement.getAttribute( "address" ) );
                    NodeList fList = nNode.getChildNodes();
                    for (int tempn = 0; tempn < fList.getLength(); tempn++) {
                        Node fNode = fList.item( tempn );
                        if (fNode.getNodeType() == Node.ELEMENT_NODE) {
                            Associations Ass = new Associations();
                            Element fElement = (Element) fNode;
                            Ass.setCtrlNumber( commsNumber );
                            Ass.setSubNetNumber( Integer.parseInt( fElement.getAttribute( "address" ) ) );
                            Ass.setFileName( fElement.getTextContent() );
                            AssList.add( Ass );
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int dotNetNo;
        LinkedHashMap<Integer,LinkedHashMap<Integer,String>>  subNetAssociations = new LinkedHashMap<>();
        for (int i = 0; i < AssList.size(); i++) {
            LinkedHashMap<Integer,String>  netAssociations = new LinkedHashMap<>();
            dotNetNo = AssList.get(i).getCtrlNumber();
            while (dotNetNo == AssList.get(i).getCtrlNumber()&&(i<AssList.size()-1)) {
                netAssociations.put(AssList.get(i).getSubNetNumber(),AssList.get(i).getFileName());
                i++;
            };
            if (!(dotNetNo == AssList.get(i).getCtrlNumber())) i--;
            //if (i < AssList.size()-1) i--;
            subNetAssociations.put(dotNetNo,netAssociations);
            netAssociations.put(AssList.get(i).getSubNetNumber(),AssList.get(i).getFileName());
        }
        return subNetAssociations;



    }





}
