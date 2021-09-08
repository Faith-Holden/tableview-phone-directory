package solution;

import com.sun.source.tree.Tree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PhoneDirectory {
    private HashMap<String, PhoneDirectoryEntry> directory;
    public PhoneDirectory(){
        directory = new HashMap<>();
    }





    public void readXMLPhoneDirectory(File readFile) throws Exception {
        directory = new HashMap<>();
        String lastName = "";
        String firstName = "";
        String phoneNum = "";
        Document xmldoc;
        Element phoneDirectory;

        DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        xmldoc = docReader.parse(readFile);
        phoneDirectory = xmldoc.getDocumentElement();
        NodeList entries = phoneDirectory.getElementsByTagName("entry");

        for(int i=0; i<entries.getLength(); i++) {
            if (entries.item(i) instanceof Element) {
                Element entry = (Element) entries.item(i);
                if(entry.getTagName().equals("entry")) {
                    lastName = entry.getAttribute("last_name");
                    firstName = entry.getAttribute("first_name");
                    phoneNum = entry.getAttribute("phone_number");
                    directory.put(lastName, new PhoneDirectoryEntry(lastName, firstName, phoneNum));
                }
            }
        }
    }

    public void writeXMLPhoneDirectory(File saveFile) throws IOException {
        PrintWriter out;
        out = new PrintWriter( new FileWriter(saveFile) );

        out.println("<?xml version=\"1.0\"?>");
        out.println("<phonedirectory version=\"1.0\">");

        for (Map.Entry<String, PhoneDirectoryEntry> entry : directory.entrySet()){
            out.println("       <entry last_name='"+entry.getValue().lastNameProperty().get()
                    + "' first_name='"+entry.getValue().firstNameProperty().get() + "' phone_number='"+entry.getValue().phoneNumberProperty().get() + "'/>");
        }
        out.println("</phonedirectory>");
        out.flush();
        out.close();
//        if (out.checkError())
//            System.out.println("ERROR: Some error occurred while writing data file.");
    }


    public HashMap<String, PhoneDirectoryEntry> get(){
        return directory;
    }

    public void putToDirectory(String key, PhoneDirectoryEntry value){
        directory.put(key, value);
    }

    public void removeFromDirectory(String key){
        directory.remove(key);
    }
}
