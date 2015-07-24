package cleaner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import com.adrianmouat.xpathgen.ChildNumber;

public class HtmlCleanerHelper {

	public static void main(String[] args) {
		TagNode node;

		// path config
		String dataUrl = "/home/dilip/common-crawl-data/data/";
		List<String> xpaths = new ArrayList<String>();
		try {
			for (String line : Files.readAllLines(Paths.get("/home/dilip/common-crawl-data/sampleFile"))) {
				for (String part : line.split(",")) {
					
					if (part.contains("html")) {
						StringBuilder inp = new StringBuilder();
						for (String l : Files.readAllLines(Paths.get(dataUrl + part))) {
							inp.append(l);
						}
						// System.out.println(inp);
						HtmlCleaner cleaner = new HtmlCleaner();
						CleanerProperties props = cleaner.getProperties();
						props.setAllowHtmlInsideAttributes(true);
						props.setAllowMultiWordAttributes(true);
						props.setRecognizeUnicodeChars(true);
						props.setOmitComments(true);
						node = cleaner.clean(inp.toString());
						String xmlContent = new PrettyXmlSerializer(props).getAsString(node, "utf-8");
						//System.out.println(xmlContent);
						xpaths = getXpath(xmlContent);

					} else if (part.contains("http")) {
						StringBuilder result = new StringBuilder();
						result.append(part).append("\t{");
						for(String p : xpaths){
							result.append("("+p+")");
							result.append(",");
						}
						result.append("}");
						//xpaths = new ArrayList<String>();
						System.out.println(result);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<String> getXpath(String xml) {
		List<String> xpaths = new ArrayList<String>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));
			DocumentTraversal traversal = (DocumentTraversal) doc;
			NodeIterator iterator = traversal.createNodeIterator(doc.getDocumentElement(), NodeFilter.SHOW_ELEMENT,
					null, true);
			for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
				NodeList children = ((Element) n).getChildNodes();
				if (children.getLength() <= 1) {
					 //System.out.println(getXPath(n));
					xpaths.add(getXPath(n));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xpaths;
	}

	public static String getXPath(final Node n) {

		String xpath;

		if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
			// Slightly special case for attributes as they are considered to
			// have no parent
			((Attr) n).getOwnerElement();
			xpath = getXPath(((Attr) n).getOwnerElement()) + "/@" + n.getNodeName();

		} else if (n.getNodeType() == Node.DOCUMENT_NODE) {
			xpath = "/";
		} else if (n.getNodeType() == Node.DOCUMENT_TYPE_NODE) {

			throw new IllegalArgumentException("DocumentType nodes cannot be identified with XPath");

		} else if (n.getParentNode().getNodeType() == Node.DOCUMENT_NODE) {

			ChildNumber cn = new ChildNumber(n);
			// xpath = "/node()[" + cn.getXPath() + "]";
			xpath = "/" + n.getNodeName();
		} else {

			ChildNumber cn = new ChildNumber(n);

			// xpath = getXPath(n.getParentNode()) + "/node()[" + cn.getXPath()
			// + "]";
			xpath = getXPath(n.getParentNode()) + "/" + n.getNodeName();
		}

		return xpath;
	}
}
