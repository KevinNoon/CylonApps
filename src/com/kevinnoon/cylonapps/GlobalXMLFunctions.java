package com.kevinnoon.cylonapps;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by kevin on 07/04/2016. XML for GlobalHeader
 */

@SuppressWarnings("WeakerAccess")
public class GlobalXMLFunctions {


    public enum globalType {
        local("localGlobal"),
        smart("smartGlobal"),
        wideSource("wideSourceGlobal"),
        wideDestination("wideDestinationGlobal"),
        temporary("tempGlobal");

        private String typeName;

        globalType(String typeName) {
            this.typeName = typeName;
        }
    }

    public static boolean CreateNewXML(Path fileName) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("globals");
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

    public static boolean isDotNetUsed(Path fileName, int address) throws IOException, SAXException, ParserConfigurationException {
        /*
        No checks are done. i.e does file exit.
        This will check if a DotNet exists
        fileName: Path of Associations file
        address: Address to check if in use
        */
        boolean isAddress = false;

        Document doc = getDocument(fileName);
        NodeList dotNetNodeList = doc.getElementsByTagName("commsController");

        for (int dotNetCount = 0; dotNetCount < dotNetNodeList.getLength(); dotNetCount++) {
            Node dotNetNode = dotNetNodeList.item(dotNetCount);
            if (dotNetNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) dotNetNode;
                if (address == Integer.parseInt(eElement.getAttribute("address"))) {
                    isAddress = true;
                    break;
                }
            }
        }
        return isAddress;
    }

    public static int addDotNet(Path fileName, int address) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        /*
        No checks are for does file exit.
        This will add a blank DotNet
        fileName: Path of Globals file
        address of dotNet to add
        Returns the address of the inserted DotNet or -1 if it fails.
        */
        if (isDotNetUsed(fileName, address)) return -1;
        if (address > Constants.MaxDotNets) return -1;

        Document doc = getDocument(fileName);
        Element root = doc.getDocumentElement();

        Element newDotNet;
        newDotNet = doc.createElement("commsController");
        newDotNet.setAttribute("address", String.valueOf(address));
        Element local = doc.createElement("local");
        newDotNet.appendChild(local);
        Element wideDestination = doc.createElement("wideDestination");
        newDotNet.appendChild(wideDestination);
        Element wideSource = doc.createElement("wideSource");
        newDotNet.appendChild(wideSource);
        Element temporary = doc.createElement("temporary");
        newDotNet.appendChild(temporary);
        Element smart = doc.createElement("smart");
        newDotNet.appendChild(smart);
        root.appendChild(newDotNet);
        saveDocument(fileName, doc);
        return address;
    }

    public static int deleteDotNet(Path fileName, int address) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        // No checks are done. i.e does file exit.
        // This will delete a dotNet in a xml file
        // fileName: Path of Associations file
        // address: Address of dotNet to be removed
        // Returns the address of the removed dotNet or -1 if none removed.
        int deletedDotNet = -1;
        Document doc = getDocument(fileName);
        NodeList dotNetNodeList = doc.getElementsByTagName("commsController");

        for (int dotNetCount = 0; dotNetCount < dotNetNodeList.getLength(); dotNetCount++) {
            Node dotNetNode = dotNetNodeList.item(dotNetCount);
            if (dotNetNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) dotNetNode;
                if (address == Integer.parseInt(eElement.getAttribute("address"))) {
                    dotNetNode.getParentNode().removeChild(dotNetNode);
                    deletedDotNet = address;
                    saveDocument(fileName, doc);
                    break;
                }
            }
        }
        return deletedDotNet;
    }

    public static ArrayList<Integer> getUsedDotNets(Path fileName) throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Integer> usedsDotNets = new ArrayList<>();
        for (int n = 1; n <= Constants.MaxDotNets; n++) {
            if (isDotNetUsed(fileName, n)) usedsDotNets.add(n);
        }
        return usedsDotNets;
    }

    public static boolean isGlobalUsed(Path fileName, int dotNetAddress, globalType globalType, int globalNumber) {
        // This will check if a Global exists
        // fileName: Path of Associations file
        //dotNetAddress: Address of dotNet
        //type of global Valid types are local, wideSource, wideDestination, temporary, smart
        // globalNumber: Number of dotNet to check if in use
        //Returns true if the global is used
        boolean isAddress = false;
        try {
            Document doc = getDocument(fileName);
            NodeList dotNetNodeList = doc.getElementsByTagName("commsController");
            for (int dotNetCount = 0; dotNetCount < dotNetNodeList.getLength(); dotNetCount++) {
                Node dotNetNode = dotNetNodeList.item(dotNetCount);
                if (dotNetNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) dotNetNode;
                    if (dotNetAddress == Integer.parseInt(eElement.getAttribute("address"))) {
                        NodeList globalTypeNodeList = dotNetNode.getChildNodes();

                        for (int globalTypeCount = 0; globalTypeCount < globalTypeNodeList.getLength(); globalTypeCount++) {
                            if (globalTypeNodeList.item(globalTypeCount).getNodeName().equals(globalType.name())) {

                                NodeList globalNodeList = globalTypeNodeList.item(globalTypeCount).getChildNodes();

                                for (int globalCount = 0; globalCount < globalNodeList.getLength(); globalCount++) {

                                    if (globalNodeList.item(globalCount).getNodeName().equals(globalType.typeName)) {
                                        String no = ((Element) globalNodeList.item(globalCount)).getAttribute("number");
                                        if (!(no.contains("null"))) {
                                            int gNumber = Integer.parseInt(no);
                                            if (globalNumber == gNumber) {
                                                isAddress = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAddress;
    }

    public static boolean isGlobalConnectionUsed(Path fileName, globalType globalType, int dotNetNumber, int globalNumber) {
        // This will check if a Global exists
        // fileName: Path of Associations file
        //dotNetAddress: Address of dotNet
        //type of global Valid types are local, wideSource, wideDestination, temporary, smart
        // globalNumber: Number of dotNet to check if in use
        //Returns true if the global is used
        boolean isAddress = false;
        //TODO Create option for DotNet only connection used so that a list of used connection list can be created
        try {
            Document doc = getDocument(fileName);
            NodeList dotNetNodeList = doc.getElementsByTagName("commsController");
            for (int dotNetCount = 0; dotNetCount < dotNetNodeList.getLength(); dotNetCount++) {
                Node dotNetNode = dotNetNodeList.item(dotNetCount);
                if (dotNetNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) dotNetNode;
                    if (dotNetNumber == Integer.parseInt(eElement.getAttribute("address"))) {
                        //                   System.out.println(Integer.parseInt( eElement.getAttribute( "address" ) ));
                        NodeList globalTypeNodeList = dotNetNode.getChildNodes();

                        for (int globalTypeCount = 0; globalTypeCount < globalTypeNodeList.getLength(); globalTypeCount++) {
                            if (globalTypeNodeList.item(globalTypeCount).getNodeName().equals(globalType.name())) {

                                NodeList globalNodeList = globalTypeNodeList.item(globalTypeCount).getChildNodes();

                                for (int globalCount = 0; globalCount < globalNodeList.getLength(); globalCount++) {

                                    if (globalNodeList.item(globalCount).getNodeName().equals(globalType.typeName)) {
                                        String no = "null";
                                        NodeList globalConNoList = globalNodeList.item(globalCount).getChildNodes();
                                        for (int glocalConNoCount = 0; glocalConNoCount < globalConNoList.getLength(); glocalConNoCount++) {
                                            if (globalConNoList.item(glocalConNoCount).getNodeName().contains("wideConnectionNumber")) {
                                                no = globalConNoList.item(glocalConNoCount).getTextContent();
                                            }
                                        }
                                        if (!(no.contains("null"))) {
                                            int gNumber = Integer.parseInt(no);
                                            if (globalNumber == gNumber) {
                                                isAddress = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAddress;
    }

    public static int getFreeWideTotal(Path fileName, globalType globalType, int dotNetNumber) {
        int freeWideGlobal = 0;
        ArrayList<Integer> freeWide = new ArrayList<>();
        ArrayList<Integer> freeConnection = new ArrayList<>();
        if (globalType == GlobalXMLFunctions.globalType.wideSource) {
            freeWide = getUsedWideSourceGlobals(fileName, dotNetNumber);
        }
        if (globalType == GlobalXMLFunctions.globalType.wideDestination) {
            freeWide = getUsedWideDestinationGlobals(fileName, dotNetNumber);
        }
        freeConnection = getGlobalConnectionList(fileName, globalType, 0);
        freeWideGlobal = Math.min(Constants.MaxWideGlobals - freeWide.size(), Constants.MaxGlobalsConnections - freeConnection.size());

        return freeWideGlobal;
    }

    public static ArrayList<Integer> getGlobalConnectionList(Path fileName, globalType globalType, int globalNumber) {
        ArrayList<Integer> usedGlobalConnections = new ArrayList<>();
        if (globalNumber == 0) {
            for (int c = 1; c <= Constants.MaxDotNets; c++) {
                for (int n = 1; n <= Constants.MaxGlobalsConnections; n++) {
                    if (isGlobalConnectionUsed(fileName, globalType, c, n)) usedGlobalConnections.add(n);
                }
            }

        } else {
            for (int n = 1; n <= Constants.MaxGlobalsConnections; n++) {
                if (isGlobalConnectionUsed(fileName, globalType, globalNumber, n)) usedGlobalConnections.add(n);

            }
        }
        return usedGlobalConnections;
    }

    public static ArrayList<Integer> getUsedLocalGlobals(Path fileName, int dotNetAddress) {
        ArrayList<Integer> usedGlobals = new ArrayList<>();
        for (int n = 1; n <= Constants.MaxLocalGlobals; n++) {
            if (isGlobalUsed(fileName, dotNetAddress, globalType.local, n)) usedGlobals.add(n);
        }
        return usedGlobals;
    }

    public static ArrayList<Integer> getUsedSmartGlobals(Path fileName, int dotNetAddress) {
        ArrayList<Integer> usedGlobals = new ArrayList<>();
        for (int n = 1; n <= Constants.MaxSmartGlobals; n++) {
            if (isGlobalUsed(fileName, dotNetAddress, globalType.smart, n)) usedGlobals.add(n);
        }
        return usedGlobals;
    }

    public static ArrayList<Integer> getUsedWideSourceGlobals(Path fileName, int dotNetAddress) {
        ArrayList<Integer> usedGlobals = new ArrayList<>();
        for (int n = 1; n <= Constants.MaxWideGlobals; n++) {
            if (isGlobalUsed(fileName, dotNetAddress, globalType.wideSource, n)) usedGlobals.add(n);
        }
        return usedGlobals;
    }

    public static ArrayList<Integer> getUsedWideDestinationGlobals(Path fileName, int dotNetAddress) {
        ArrayList<Integer> usedGlobals = new ArrayList<>();
        for (int n = 1; n <= Constants.MaxWideGlobals; n++) {
            if (isGlobalUsed(fileName, dotNetAddress, globalType.wideDestination, n)) usedGlobals.add(n);
        }
        return usedGlobals;
    }

    public static int deleteGlobal(Path fileName, int dotNetAddress, globalType globalType, int globalNumber) {
        // No checks are done. i.e does file exit.
        // This will delete a global in a xml file
        // fileName: Path of Associations file
        // address: Address of dotNet to with gloabl to be removed
        // Type of global Valid types are local, wideSource, wideDestination, temporary, smart
        // Global number to be removed.
        // Returns the address of the removed dotNet or -1 if none removed.
        int deletedGlobal = -1;
        try {
            Document doc = getDocument(fileName);
            NodeList dotNetNodeList = doc.getElementsByTagName("commsController");

            for (int dotNetCount = 0; dotNetCount < dotNetNodeList.getLength(); dotNetCount++) {
                Node dotNetNode = dotNetNodeList.item(dotNetCount);
                if (dotNetNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) dotNetNode;
                    if (dotNetAddress == Integer.parseInt(eElement.getAttribute("address"))) {
                        NodeList globalTypeNodeList = dotNetNode.getChildNodes();

                        for (int globalTypeCount = 0; globalTypeCount < globalTypeNodeList.getLength(); globalTypeCount++) {
                            if (globalTypeNodeList.item(globalTypeCount).getNodeName().equals(globalType.name())) {

                                NodeList globalNodeList = globalTypeNodeList.item(globalTypeCount).getChildNodes();
                                for (int globalCount = 0; globalCount < globalNodeList.getLength(); globalCount++) {
                                    if (globalNodeList.item(globalCount).getNodeName().equals(globalType.typeName)) {
                                        int gNumber = Integer.parseInt(((Element) globalNodeList.item(globalCount)).getAttribute("number"));
                                        if (globalNumber == gNumber) {
                                            Node globalNode = globalNodeList.item(globalCount);
                                            globalNode.getParentNode().removeChild(globalNode);
                                            deletedGlobal = gNumber;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            saveDocument(fileName, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedGlobal;
    }

    public static int addGlobal(Path fileName, GlobalHeader globals, globalType type) {

        // No checks are done. i.e does file exit. Is the address in range
        // fileName: Path of Associations file
        // Returns the address of the inserted DotNet.
        // If no free addresses returns -1
        int dotNetAddress = globals.getDotNetNo();
        int globalAddress = globals.getNumber();
        if (!type.name().contains("temporary"))
            if (!type.name().contains("smart"))
        if (isGlobalUsed(fileName, dotNetAddress, type, globalAddress)) return -1;

        try {

            Document doc = getDocument(fileName);
            NodeList dotNetNodeList = doc.getElementsByTagName("commsController");
            for (int dotNetCount = 0; dotNetCount < dotNetNodeList.getLength(); dotNetCount++) {
                Node dotNetNode = dotNetNodeList.item(dotNetCount);
                if (dotNetNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) dotNetNode;
                    if (dotNetAddress == Integer.parseInt(eElement.getAttribute("address"))) {
                        NodeList globalTypeNodeList = dotNetNode.getChildNodes();

                        for (int globalTypeCount = 0; globalTypeCount < globalTypeNodeList.getLength(); globalTypeCount++) {


                            if (globalTypeNodeList.item(globalTypeCount).getNodeName().equals(type.name())) {

                                NodeList globalNodeList = globalTypeNodeList.item(globalTypeCount).getChildNodes();


                                //     for (int globalCount = 0; globalCount < globalNodeList.getLength(); globalCount++) {

                                //          if (globalNodeList.item( 0 ).getNodeName().equals( type.typeName )) {


                                Element globalToAdd = doc.createElement(type.typeName);
                                globalToAdd.setAttribute("number", String.valueOf(globals.getNumber()));
                                globalToAdd.setAttribute("name", String.valueOf(globals.getName()));
                                globalToAdd.setAttribute("servicePeriod", String.valueOf(globals.getServicePeriod()));
                                globalToAdd.setAttribute("pointType", String.valueOf(globals.getPointType()));
                                globalToAdd.setAttribute("id", String.valueOf(globals.getId()));
                                globalToAdd.setAttribute("hasModule", String.valueOf(globals.getHasModule()));

                                if (globals instanceof GlobalLocal) {
                                    Element source = doc.createElement("source");
                                    source.setAttribute("fieldControllerAddress", String.valueOf(((GlobalLocal) globals).source.getFieldControllerAddress()));
                                    source.setAttribute("pointAddress", String.valueOf(((GlobalLocal) globals).source.getPointAddress()));
                                    globalToAdd.appendChild(source);
                                    Element destination = doc.createElement("destination");
                                    destination.setAttribute("fieldControllerAddress", String.valueOf(((GlobalLocal) globals).destination.getFieldControllerAddress()));
                                    destination.setAttribute("pointAddress", String.valueOf(((GlobalLocal) globals).destination.getPointAddress()));
                                    globalToAdd.appendChild(destination);
                                    Element broadcast = doc.createElement("broadcast");
                                    broadcast.setAttribute("destinationFieldControllers", String.valueOf(((GlobalLocal) globals).broadcast.getDestinationFieldControllers()));
                                    if (((GlobalLocal) globals).broadcast.getDestinationFieldControllers() != null) {
                                        globalToAdd.appendChild(broadcast);
                                    }
                                    Element defaultValue = doc.createElement("defaultValue");
                                    defaultValue.setTextContent(String.valueOf(((GlobalLocal) globals).getDefaultValue()));
                                    globalToAdd.appendChild(defaultValue);
                                }

                                if (globals instanceof GlobalWideDestination) {
                                    Element destination = doc.createElement("destination");
                                    destination.setAttribute("fieldControllerAddress", String.valueOf(((GlobalWideDestination) globals).destination.getFieldControllerAddress()));
                                    destination.setAttribute("pointAddress", String.valueOf(((GlobalWideDestination) globals).destination.getPointAddress()));
                                    globalToAdd.appendChild(destination);
                                    Element broadcast = doc.createElement("broadcast");
                                    broadcast.setAttribute("destinationFieldControllers", String.valueOf(((GlobalWideDestination) globals).broadcast.getDestinationFieldControllers()));
                                    if (((GlobalWideDestination) globals).broadcast.getDestinationFieldControllers() != null) {
                                        globalToAdd.appendChild(broadcast);
                                    }
                                    Element wideConnectionNumber = doc.createElement("wideConnectionNumber");
                                    wideConnectionNumber.setTextContent(String.valueOf(((GlobalWideDestination) globals).getConnectionNumber()));
                                    globalToAdd.appendChild(wideConnectionNumber);
                                    Element defaultValue = doc.createElement("defaultValue");
                                    defaultValue.setTextContent(String.valueOf(((GlobalWideDestination) globals).getDefaultValue()));
                                    globalToAdd.appendChild(defaultValue);
                                }

                                if (globals instanceof GlobalWideSource) {
                                    Element source = doc.createElement("source");
                                    source.setAttribute("fieldControllerAddress", String.valueOf(((GlobalWideSource) globals).source.getFieldControllerAddress()));
                                    source.setAttribute("pointAddress", String.valueOf(((GlobalWideSource) globals).source.getPointAddress()));
                                    globalToAdd.appendChild(source);
                                    Element wideConnectionNumber = doc.createElement("wideConnectionNumber");
                                    wideConnectionNumber.setTextContent(String.valueOf(((GlobalWideSource) globals).getConnectionNumber()));
                                    globalToAdd.appendChild(wideConnectionNumber);
                                    Element defaultValue = doc.createElement("defaultValue");
                                    defaultValue.setTextContent(String.valueOf(((GlobalWideSource) globals).getDefaultValue()));
                                    globalToAdd.appendChild(defaultValue);
                                }
                                if (globals instanceof GlobalTemporary) {
                                    globalToAdd.setAttribute("RunBurstLength", String.valueOf(((GlobalTemporary) globals).getRunBurstLength()));

                                    Element source = doc.createElement("source");
                                    source.setAttribute("fieldControllerAddress", String.valueOf(((GlobalTemporary) globals).source.getFieldControllerAddress()));
                                    source.setAttribute("pointAddress", String.valueOf(((GlobalTemporary) globals).source.getPointAddress()));
                                    globalToAdd.appendChild(source);
                                    Element destination = doc.createElement("destination");
                                    destination.setAttribute("fieldControllerAddress", String.valueOf(((GlobalTemporary) globals).destination.getFieldControllerAddress()));
                                    destination.setAttribute("pointAddress", String.valueOf(((GlobalTemporary) globals).destination.getPointAddress()));
                                    globalToAdd.appendChild(destination);
                                    Element defaultValue = doc.createElement("defaultValue");
                                    defaultValue.setTextContent(String.valueOf(((GlobalTemporary) globals).getDefaultValue()));
                                    globalToAdd.appendChild(defaultValue);

                                    Element aDefault = doc.createElement("default");
                                    Element maximumValue = doc.createElement("maximumValue");
                                    maximumValue.setTextContent(String.valueOf(((GlobalTemporary) globals).adefault.getMaxAddresses()));
                                    aDefault.appendChild(maximumValue);
                                    Element minimumValue = doc.createElement("minimumValue");
                                    minimumValue.setTextContent(String.valueOf(((GlobalTemporary) globals).adefault.getMinAddresses()));
                                    aDefault.appendChild(minimumValue);
                                    Element sumValue = doc.createElement("sumValue");
                                    sumValue.setTextContent(String.valueOf(((GlobalTemporary) globals).adefault.getSumAddresses()));
                                    aDefault.appendChild(sumValue);
                                    Element averageValue = doc.createElement("averageValue");
                                    averageValue.setTextContent(String.valueOf(((GlobalTemporary) globals).adefault.getAverageAddresses()));
                                    aDefault.appendChild(averageValue);

                                    globalToAdd.appendChild(aDefault);
                                }
                                if (globals instanceof GlobalSmart) {
                                    globalToAdd.setAttribute("RunBurstLength", String.valueOf(((GlobalSmart) globals).getRunBurstLength()));

                                    Element source = doc.createElement("source");
                                    source.setAttribute("fieldControllerAddress", String.valueOf(((GlobalSmart) globals).source.getFieldControllerAddress()));
                                    source.setAttribute("pointAddress", String.valueOf(((GlobalSmart) globals).source.getPointAddress()));
                                    ArrayList<Integer> smartFieldAddesses = ((GlobalSmart) globals).source.getSmartFieldAddesses();
                                    for (int smartFAddress : smartFieldAddesses) {
                                        Element smartFieldAddress = doc.createElement("smartFieldAddress");
                                        smartFieldAddress.setTextContent(String.valueOf(smartFAddress));
                                        source.appendChild(smartFieldAddress);
                                    }


                                    globalToAdd.appendChild(source);
                                    Element destination = doc.createElement("destination");
                                    destination.setAttribute("fieldControllerAddress", String.valueOf(((GlobalSmart) globals).destination.getFieldControllerAddress()));
                                    destination.setAttribute("pointAddress", String.valueOf(((GlobalSmart) globals).destination.getPointAddress()));

                                    Element maximumAddress = doc.createElement("maximumAddress");
                                    maximumAddress.setTextContent(String.valueOf(((GlobalSmart) globals).destination.getMaxAddresses()));
                                    destination.appendChild(maximumAddress);

                                    Element minimumAddress = doc.createElement("minimumAddress");
                                    minimumAddress.setTextContent(String.valueOf(((GlobalSmart) globals).destination.getMinAddresses()));
                                    destination.appendChild(minimumAddress);

                                    Element sumAddress = doc.createElement("sumAddress");
                                    sumAddress.setTextContent(String.valueOf(((GlobalSmart) globals).destination.getSumAddresses()));
                                    destination.appendChild(sumAddress);

                                    Element averageAddress = doc.createElement("averageAddress");
                                    averageAddress.setTextContent(String.valueOf(((GlobalSmart) globals).destination.getAverageAddresses()));
                                    destination.appendChild(averageAddress);

//                                    destination.setAttribute("maximumAddress", String.valueOf(((GlobalSmart) globals).destination.getMaxAddresses()));
//                                    destination.setAttribute("minimumAddress", String.valueOf(((GlobalSmart) globals).destination.getMinAddresses()));
//                                    destination.setAttribute("sumAddress", String.valueOf(((GlobalSmart) globals).destination.getSumAddresses()));
//                                    destination.setAttribute("averageAddress", String.valueOf(((GlobalSmart) globals).destination.getAverageAddresses()));
                                    globalToAdd.appendChild(destination);
                                    Element aDefault = doc.createElement("default");

                                    Element maximumValue = doc.createElement("maximumValue");
                                    maximumValue.setTextContent(String.valueOf(((GlobalSmart) globals).adefault.getMaxAddresses()));
                                    aDefault.appendChild(maximumValue);
                                    Element minimumValue = doc.createElement("minimumValue");
                                    minimumValue.setTextContent(String.valueOf(((GlobalSmart) globals).adefault.getMinAddresses()));
                                    aDefault.appendChild(minimumValue);
                                    Element sumValue = doc.createElement("sumValue");
                                    sumValue.setTextContent(String.valueOf(((GlobalSmart) globals).adefault.getSumAddresses()));
                                    aDefault.appendChild(sumValue);
                                    Element averageValue = doc.createElement("averageValue");
                                    averageValue.setTextContent(String.valueOf(((GlobalSmart) globals).adefault.getAverageAddresses()));
                                    aDefault.appendChild(averageValue);




//                                    aDefault.setAttribute("maximumValue", String.valueOf(((GlobalSmart) globals).adefault.getMaxAddresses()));
//                                    aDefault.setAttribute("minimumValue", String.valueOf(((GlobalSmart) globals).adefault.getMinAddresses()));
//                                    aDefault.setAttribute("sumValue", String.valueOf(((GlobalSmart) globals).adefault.getSumAddresses()));
//                                    aDefault.setAttribute("averageValue", String.valueOf(((GlobalSmart) globals).adefault.getAverageAddresses()));
                                    globalToAdd.appendChild(aDefault);
                                }
                                globalTypeNodeList.item(globalTypeCount).appendChild(globalToAdd);
                                break;
                                //         }
                                //      }
                                // break;
                            }
                        }
                    }
                }
            }
            saveDocument(fileName, doc);
        } catch (
                Exception ex
                ) {
            ex.printStackTrace();
        }
        return globalAddress;
    }

    public static ArrayList<GlobalHeader> GetGlobals(Path fileName) {
        // This will load associations file into an array of Associations
        // Associations has fields DotNetNumber,SubNetNumber,FileName
        // fileName: Path of Associations file
        // Returns an Array of Associations
        ArrayList<GlobalHeader> AssList = new ArrayList<>();
        int commsNumber;
        try {

            Document doc = getDocument(fileName);

            NodeList ctrlList = doc.getElementsByTagName( "commsController" );
            for (int ctrlLoopCnt = 0; ctrlLoopCnt < ctrlList.getLength(); ctrlLoopCnt++) {
                Node ctrlNode = ctrlList.item( ctrlLoopCnt );
                if (ctrlNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ctrlElement = (Element) ctrlNode;
                    commsNumber = Integer.parseInt( ctrlElement.getAttribute( "address" ) );

                    NodeList globalTableNodeList = ctrlNode.getChildNodes();

                    for (int globalTableNodeCnt = 0; globalTableNodeCnt < globalTableNodeList.getLength(); globalTableNodeCnt++) {
                        Node globalTableNode = globalTableNodeList.item( globalTableNodeCnt );
                        if (globalTableNode.getNodeType() == Node.ELEMENT_NODE) {
                            int dotNetNo;
                            int number = 0;
                            String name;
                            int servicePeriod;
                            String pointType;
                            int id;
                            int hasModule;
                            int runBurstLength;

                            NodeList globalList = globalTableNode.getChildNodes();
                            for (int globalNodeCount = 0; globalNodeCount < globalList.getLength(); globalNodeCount++) {
                                Node globalNode = globalList.item( globalNodeCount );
                                if (globalNode.getNodeType() == Node.ELEMENT_NODE) {

                                    Element globalElement = (Element) globalNode;
                                    dotNetNo = commsNumber;
                                    if (!globalTableNode.getNodeName().equals( "temporary" )) {
                                        number = Integer.parseInt( globalElement.getAttribute( "number" ) );
                                    }
                                    name = globalElement.getAttribute( "name" );
                                    servicePeriod = Integer.parseInt( globalElement.getAttribute( "servicePeriod" ) );
                                    pointType = globalElement.getAttribute( "pointType" );
                                    id = Integer.parseInt( globalElement.getAttribute( "id" ) );
                                    hasModule = Integer.parseInt( globalElement.getAttribute( "hasModule" ) );

                                    if (globalTableNode.getNodeName().equals( "local" )) {
                                        GlobalLocal Glocals = new GlobalLocal();
                                        Glocals.setDotNetNo( dotNetNo );
                                        Glocals.setNumber( number );
                                        Glocals.setName( name );
                                        Glocals.setServicePeriod( servicePeriod );
                                        Glocals.setPointType( pointType );
                                        Glocals.setId( id );
                                        Glocals.setHasModule( hasModule );
                                        Glocals.setDefaultValue( Double.parseDouble( globalElement.getElementsByTagName( "defaultValue" ).item( 0 ).getTextContent() ) );

                                        NodeList globalSubList = globalNode.getChildNodes();
                                        for (int globalSubNodeCnt = 0; globalSubNodeCnt < globalSubList.getLength(); globalSubNodeCnt++) {
                                            Node globalSubNode = globalSubList.item( globalSubNodeCnt );
                                            if (globalSubNode.getNodeType() == Node.ELEMENT_NODE) {
                                                if (globalSubNode.getNodeName().contentEquals( "source" )) {
                                                    String fieldCtrlAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                    Glocals.source.setFieldControllerAddress( Integer.parseInt( fieldCtrlAddress ) );
                                                    String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                    Glocals.source.setPointAddress( Integer.parseInt( pointAddress ) );

                                                    if (((Element) globalSubNode).hasAttribute( "ScheduleOnTimePointAddress" )) {
                                                        String schedOnTimePointAddress = ((Element) globalSubNode).getAttributeNode( "ScheduleOnTimePointAddress" ).getValue();
                                                        Glocals.source.setScheduleOnTimePointAddress( Integer.parseInt( schedOnTimePointAddress ) );
                                                    }
                                                    if (((Element) globalSubNode).hasAttribute( "ScheduleOffTimePointAddress" )) {
                                                        String scheduleOffTimePointAddress = ((Element) globalSubNode).getAttributeNode( "ScheduleOffTimePointAddress" ).getValue();
                                                        Glocals.source.setScheduleOffTimePointAddress( Integer.parseInt( scheduleOffTimePointAddress ) );
                                                    }
                                                }
                                            }

                                            if (globalSubNode.getNodeName().contentEquals( "destination" )) {
                                                String fieldControllerAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                Glocals.destination.setFieldControllerAddress( Integer.parseInt( fieldControllerAddress ) );
                                                String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                Glocals.destination.setPointAddress( Integer.parseInt( pointAddress ) );
                                            }

                                            if (globalSubNode.getNodeName().contentEquals( "broadcast" )) {
                                                String destFieldCtrlAddress = ((Element) globalSubNode).getAttributeNode( "destinationFieldControllers" ).getValue();
                                                Glocals.broadcast.setDestinationFieldControllers( destFieldCtrlAddress );
                                            }
                                        }
                                        AssList.add( Glocals );
                                    }

                                    if (globalTableNode.getNodeName().equals( "wideDestination" )) {
                                        GlobalWideDestination Glocals = new GlobalWideDestination();
                                        Glocals.setDotNetNo( dotNetNo );
                                        Glocals.setNumber( number );
                                        Glocals.setName( name );
                                        Glocals.setServicePeriod( servicePeriod );
                                        Glocals.setPointType( pointType );
                                        Glocals.setId( id );
                                        Glocals.setHasModule( hasModule );
                                        Glocals.setDefaultValue( Double.parseDouble( globalElement.getElementsByTagName( "defaultValue" ).item( 0 ).getTextContent() ) );
                                        Glocals.setConnectionNumber( Integer.parseInt( globalElement.getElementsByTagName( "wideConnectionNumber" ).item( 0 ).getTextContent() ) );

                                        NodeList globalSubList = globalNode.getChildNodes();
                                        for (int globalSubNodeCnt = 0; globalSubNodeCnt < globalSubList.getLength(); globalSubNodeCnt++) {
                                            Node globalSubNode = globalSubList.item( globalSubNodeCnt );
                                            if (globalSubNode.getNodeType() == Node.ELEMENT_NODE) {

                                                if (globalSubNode.getNodeName().contentEquals( "destination" )) {
                                                    String fieldControllerAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                    Glocals.destination.setFieldControllerAddress( Integer.parseInt( fieldControllerAddress ) );
                                                    String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                    Glocals.destination.setPointAddress( Integer.parseInt( pointAddress ) );
                                                }

                                                if (globalSubNode.getNodeName().contentEquals( "broadcast" )) {
                                                    String destFieldCtrlAddress = ((Element) globalSubNode).getAttributeNode( "destinationFieldControllers" ).getValue();
                                                    Glocals.broadcast.setDestinationFieldControllers( destFieldCtrlAddress );
                                                }
                                            }
                                        }
                                        AssList.add( Glocals );
                                    }

                                    if (globalTableNode.getNodeName().equals( "wideSource" )) {
                                        GlobalWideSource Glocals = new GlobalWideSource();
                                        Glocals.setDotNetNo( dotNetNo );
                                        Glocals.setNumber( number );
                                        Glocals.setName( name );
                                        Glocals.setServicePeriod( servicePeriod );
                                        Glocals.setPointType( pointType );
                                        Glocals.setId( id );
                                        Glocals.setHasModule( hasModule );
                                        Glocals.setDefaultValue( Double.parseDouble( globalElement.getElementsByTagName( "defaultValue" ).item( 0 ).getTextContent() ) );
                                        Glocals.setConnectionNumber( Integer.parseInt( globalElement.getElementsByTagName( "wideConnectionNumber" ).item( 0 ).getTextContent() ) );

                                        NodeList globalSubList = globalNode.getChildNodes();
                                        for (int globalSubNodeCnt = 0; globalSubNodeCnt < globalSubList.getLength(); globalSubNodeCnt++) {
                                            Node globalSubNode = globalSubList.item( globalSubNodeCnt );
                                            if (globalSubNode.getNodeType() == Node.ELEMENT_NODE) {

                                                if (globalSubNode.getNodeName().contentEquals( "source" )) {
                                                    String fieldControllerAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                    Glocals.source.setFieldControllerAddress( Integer.parseInt( fieldControllerAddress ) );
                                                    String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                    Glocals.source.setPointAddress( Integer.parseInt( pointAddress ) );
                                                }
                                            }
                                        }
                                        AssList.add( Glocals );
                                    }

                                    if (globalTableNode.getNodeName().equals( "temporary" )) {
                                        GlobalTemporary Glocals = new GlobalTemporary();
                                        Glocals.setDotNetNo( dotNetNo );
                                        Glocals.setNumber( number );
                                        Glocals.setName( name );
                                        Glocals.setServicePeriod( servicePeriod );
                                        Glocals.setPointType( pointType );
                                        Glocals.setId( id );
                                        Glocals.setHasModule( hasModule );
                                        Glocals.setDefaultValue( Double.parseDouble( globalElement.getElementsByTagName( "defaultValue" ).item( 0 ).getTextContent() ) );
                                        runBurstLength = Integer.parseInt( globalElement.getAttribute( "RunBurstLength" ) );
                                        Glocals.setRunBurstLength( runBurstLength);

                                        NodeList globalSubList = globalNode.getChildNodes();
                                        for (int globalSubNodeCnt = 0; globalSubNodeCnt < globalSubList.getLength(); globalSubNodeCnt++) {
                                            Node globalSubNode = globalSubList.item( globalSubNodeCnt );
                                            if (globalSubNode.getNodeType() == Node.ELEMENT_NODE) {
                                                if (globalSubNode.getNodeName().contentEquals( "source" )) {
                                                    String fieldCtrlAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                    Glocals.source.setFieldControllerAddress( Integer.parseInt( fieldCtrlAddress ) );
                                                    String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                    Glocals.source.setPointAddress( Integer.parseInt( pointAddress ) );
                                                }
                                            }

                                            if (globalSubNode.getNodeName().contentEquals( "destination" )) {
                                                String fieldControllerAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                Glocals.destination.setFieldControllerAddress( Integer.parseInt( fieldControllerAddress ) );
                                                String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                Glocals.destination.setPointAddress( Integer.parseInt( pointAddress ) );
                                            }

                                            if (globalSubNode.getNodeName().contentEquals( "default" )) {
                                                Double max = Double.parseDouble(globalElement.getElementsByTagName( "maximumValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setMaxAddresses(max);
                                                Double min = Double.parseDouble(globalElement.getElementsByTagName( "minimumValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setMinAddresses(min);
                                                Double sum = Double.parseDouble(globalElement.getElementsByTagName( "sumValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setSumAddresses(sum);
                                                Double average = Double.parseDouble(globalElement.getElementsByTagName( "averageValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setAverageAddresses(average);
                                            }

                                        }
                                        AssList.add( Glocals );
                                    }

                                    if (globalTableNode.getNodeName().equals( "smart" )) {
                                        GlobalSmart Glocals = new GlobalSmart();
                                        Glocals.setDotNetNo( dotNetNo );
                                        Glocals.setNumber( number );
                                        Glocals.setName( name );
                                        Glocals.setServicePeriod( servicePeriod );
                                        Glocals.setPointType( pointType );
                                        Glocals.setId( id );
                                        Glocals.setHasModule( hasModule );
                                        runBurstLength = Integer.parseInt( globalElement.getAttribute( "RunBurstLength" ) );
                                        Glocals.setRunBurstLength( runBurstLength);

                                        NodeList globalSubList = globalNode.getChildNodes();
                                        for (int globalSubNodeCnt = 0; globalSubNodeCnt < globalSubList.getLength(); globalSubNodeCnt++) {
                                            Node globalSubNode = globalSubList.item( globalSubNodeCnt );
                                            if (globalSubNode.getNodeType() == Node.ELEMENT_NODE) {
                                                if (globalSubNode.getNodeName().contentEquals( "source" )) {
                                                    String fieldCtrlAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                    Glocals.source.setFieldControllerAddress( Integer.parseInt( fieldCtrlAddress ) );
                                                    String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                    Glocals.source.setPointAddress( Integer.parseInt( pointAddress ) );
                                                }
                                            }

                                            if (globalSubNode.getNodeName().contentEquals( "destination" )) {
                                                String fieldControllerAddress = ((Element) globalSubNode).getAttributeNode( "fieldControllerAddress" ).getValue();
                                                Glocals.destination.setFieldControllerAddress( Integer.parseInt( fieldControllerAddress ) );
                                                String pointAddress = ((Element) globalSubNode).getAttributeNode( "pointAddress" ).getValue();
                                                Glocals.destination.setPointAddress( Integer.parseInt( pointAddress ) );

                                                int max = Integer.parseInt(globalElement.getElementsByTagName( "maximumAddress" ).item( 0 ).getTextContent() );
                                                Glocals.destination.setMaxAddresses(max);
                                                int min = Integer.parseInt(globalElement.getElementsByTagName( "minimumAddress" ).item( 0 ).getTextContent() );
                                                Glocals.destination.setMinAddresses(min);
                                                int sum = Integer.parseInt(globalElement.getElementsByTagName( "sumAddress" ).item( 0 ).getTextContent() );
                                                Glocals.destination.setSumAddresses(sum);
                                                int average = Integer.parseInt(globalElement.getElementsByTagName( "averageAddress" ).item( 0 ).getTextContent() );
                                                Glocals.destination.setAverageAddresses(average);



                                            }

                                            if (globalSubNode.getNodeName().contentEquals( "default" )) {
                                                Double max = Double.parseDouble(globalElement.getElementsByTagName( "maximumValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setMaxAddresses(max);
                                                Double min = Double.parseDouble(globalElement.getElementsByTagName( "minimumValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setMinAddresses(min);
                                                Double sum = Double.parseDouble(globalElement.getElementsByTagName( "sumValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setSumAddresses(sum);
                                                Double average = Double.parseDouble(globalElement.getElementsByTagName( "averageValue" ).item( 0 ).getTextContent() );
                                                Glocals.adefault.setAverageAddresses(average);
                                            }
                                        }
                                        AssList.add( Glocals );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AssList;
    }

    private static void saveDocument(Path fileName, Document doc) throws TransformerException {
        DOMSource sourceXML = new DOMSource(doc);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(String.valueOf(fileName));
        transformer.transform(sourceXML, result);
    }

    private static Document getDocument(Path fileName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(String.valueOf(fileName));
        doc.getDocumentElement().normalize();
        return doc;
    }


}
