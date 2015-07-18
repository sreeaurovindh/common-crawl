package cleaner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import com.adrianmouat.xpathgen.XPathGen;

public class HtmlCleanerHelper {

	public static void main(String[] args) {
		TagNode node;
		//hardcoding URL
	    String option_url = "http://www.w3schools.com/html/tryit.asp?filename=tryhtml_basic_document";
	    //final String NAME_XPATH = "//div[@class='row result']";
	    
        
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
 
        try {
			
			URL url = new URL(option_url);
			URLConnection conn = url.openConnection();
 
			
			InputStream input = conn.getInputStream();
			InputStreamReader inp = new InputStreamReader(input);
			node = cleaner.clean(inp);
 
			
			String xmlContent = new PrettyXmlSerializer(props).getAsString(node, "utf-8");
			//System.out.println(xmlContent);
			getXpath(xmlContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getXpath(String xml){
		    try {
		        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		        Document doc = builder.parse(new InputSource(new StringReader(xml)));

		        DocumentTraversal traversal = (DocumentTraversal) doc;

		        NodeIterator iterator = traversal.createNodeIterator(
		          doc.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);

		        for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
		            //System.out.println("Element: " + ((Element) n).getTagName());
		            String tagname = ((Element) n).getTagName();

		            NamedNodeMap map = ((Element)n).getAttributes();
		            if(map.getLength() > 0) {
		                /*for(int i=0; i<map.getLength(); i++) {
		                    Node node = map.item(i);
		                    System.out.println(node.getNodeName() + "=" + node.getNodeValue());
		                }*/
		            }
		            else {
		                //System.out.println(tagname + " =" + ((Element)n).getTextContent());
		                System.out.println(XPathGen.getXPath(n));
		            }
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    }
}
