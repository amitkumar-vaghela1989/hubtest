import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyParser {

	public static String myprefix = "";
	public static void main(String[] args) {
		
		String filePath = args[0];
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		if(args.length==2){
			myprefix = args[1];
		}
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			updateAttributeValue(doc);
		
			//write the updated document to file or console
			doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
            		StreamResult result = new StreamResult(new File("new_LA.UM.6.4.r1-05700-8x98.0.xml"));
            		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            		transformer.transform(source, result);
     			System.out.println("XML file updated successfully");
		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
			e1.printStackTrace();
		}

	}
	
	private static void updateAttributeValue(Document doc) {
		
		NodeList employees = doc.getElementsByTagName("project");
		Element emp = null;
		//loop for each employee
        	for(int i=0; i<employees.getLength();i++){
			emp = (Element) employees.item(i);
			if (emp.getNodeType() == Node.ELEMENT_NODE) {
				Element element2 = (Element) emp;
				String ele = element2.getAttribute("name");
				ele = ele.replace("/", "_");
				System.out.println("Names: " +(myprefix+ele));
				emp.setAttribute("name", myprefix+ele);
			}
			//String project = emp.getElementsByTagName("project").item(0).getFirstChild().getNodeValue();
			//System.out.println("string: "+project +"\n");
		}
	}
	
}
