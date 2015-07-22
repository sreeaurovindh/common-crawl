package edu.asu.html.cleaner;

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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import com.adrianmouat.xpathgen.ChildNumber;
import com.adrianmouat.xpathgen.XPathGen;

public class HtmlCleanerHelper {

	public static void main(String[] args) {
		TagNode node;
		// hardcoding URL
		String option_url = "http://www.w3schools.com/html/tryit.asp?filename=tryhtml_basic_document";
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
			// System.out.println(xmlContent);
			getXpath(xmlContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getXpath(String xml) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));
			DocumentTraversal traversal = (DocumentTraversal) doc;
			NodeIterator iterator = traversal.createNodeIterator(doc.getDocumentElement(), NodeFilter.SHOW_ELEMENT,
					null, true);

			for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
				// System.out.println("Element: " + ((Element) n).getTagName());
				String tagname = ((Element) n).getTagName();
				NamedNodeMap map = ((Element) n).getAttributes();
				if (!(map.getLength() > 0)) {
					// System.out.println(tagname + " =" +
					// ((Element)n).getTextContent());
					//System.out.println(XPathGen.getXPath(n));
					System.out.println(getXPath(n));
				} else {
					/*
					 * for(int i=0; i<map.getLength(); i++) { Node node =
					 * map.item(i); System.out.println(node.getNodeName() + "="
					 * + node.getNodeValue()); }
					 */
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public static String getXPath(final Node n) {
		 
	        String xpath;
	        
	        if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
	            //Slightly special case for attributes as they are considered to
	            //have no parent
	            ((Attr) n).getOwnerElement();
	            xpath = getXPath(((Attr) n).getOwnerElement())
	                 + "/@" + n.getNodeName();
	            
	        } else if (n.getNodeType() == Node.DOCUMENT_NODE) {
	            xpath = "/";
	        } else if (n.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
	            
	            throw new IllegalArgumentException(
	                    "DocumentType nodes cannot be identified with XPath");
	            
	        } else if (n.getParentNode().getNodeType() == Node.DOCUMENT_NODE) {
	            
	            ChildNumber cn = new ChildNumber(n);
	            xpath = "/node()[" + cn.getXPath() + "]"; 
	            
	        } else {

	            ChildNumber cn = new ChildNumber(n);

	            xpath = getXPath(n.getParentNode()) 
	                + "/node()[" + cn.getXPath() + "]";
	        }
	        
	        return xpath;
	    }
}