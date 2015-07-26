package cleaner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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



public class HtmlCleanerHelper {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		TagNode node;
		// path config
		String dataUrl = "/home/sree/Projects/common-crawl/data/indeed-html/";
		String cleanedhtml = "/home/sree/Projects/common-crawl/data/cleanedhtml/";
		List<String> xpaths = new ArrayList<String>();
		HashSet<String> visualElements = new HashSet<String>(Arrays.asList("a",
				"area", "audio", "embed", "option", "applet",
				"button", "canvas", "embed", "figure", "frame", "iframe",
				"img", "input", "menuitem", "option",
				 "td", "textarea", "li", "video"));
		ArrayList<String> selectedElements = new ArrayList<String>();
		ArrayList<String> prevElement = new ArrayList<String>();
		try {
			PrintWriter writer = new PrintWriter("/home/sree/Projects/common-crawl/data/resultXpaths.txt", "UTF-8");
		
			for (String line : Files.readAllLines(Paths.get("/home/sree/Projects/common-crawl/data/index_may2015/1/html_index.txt"))) {
				for (String part : line.split(",")) {
					
					if (part.contains("html")) {
						StringBuilder inp = new StringBuilder();
						for (String l : Files.readAllLines(Paths.get(dataUrl + part))) {
							inp.append(l);
						}
						String pathToCleanedWriter = cleanedhtml + part;
						//Write cleaned files to disk
						FileWriter filewriter = new FileWriter(new File(pathToCleanedWriter));
						PrintWriter clWriter = new  PrintWriter(filewriter);
						// System.out.println(inp);
						HtmlCleaner cleaner = new HtmlCleaner();
						CleanerProperties props = cleaner.getProperties();
						props.setAllowHtmlInsideAttributes(true);
						props.setAllowMultiWordAttributes(true);
						props.setRecognizeUnicodeChars(true);
						props.setOmitComments(true);
						String rawInput = inp.toString();
						int begin = rawInput.indexOf("<html");
						String onlyHtml = "";
						if (begin != -1) {
							onlyHtml = rawInput.substring(begin);
						}else{
							continue;
						}
						
						
						node = cleaner.clean(onlyHtml);
					
						String xmlContent = new PrettyXmlSerializer(props).getAsString(node, "utf-8");
						//System.out.println(xmlContent);
						
						
						clWriter.write(xmlContent);
						clWriter.close();
						xpaths = getXpath(xmlContent);
						Set<String> setItems = new LinkedHashSet<String>(xpaths);
						xpaths.clear();
						xpaths.addAll(setItems);
						
						//remove all duplicate xpaths
						Iterator<String> xPathItr = xpaths.iterator();
						while (xPathItr.hasNext()) {
							String singleXPath = xPathItr.next();
							int beginIndex = singleXPath.lastIndexOf("/");
							if (beginIndex != -1) {
								String element = singleXPath.substring(beginIndex + 1);
								if (visualElements.contains(element)) {
									selectedElements.add(singleXPath);
								}
							}
						}

					} else if (part.contains("http")) {
						Collections.sort(selectedElements);
						StringBuilder result = new StringBuilder();
						result.append(part).append("\t{");
						for(String p : selectedElements){
							result.append("("+p+")");
							result.append(",");
						}
						result.setLength(result.length() - 1);
						result.append("}");
						if(!prevElement.isEmpty()){
							HashSet<String> s1 = new HashSet<String>(prevElement);
							HashSet<String> s2 = new HashSet<String>(selectedElements);
							s1.removeAll(s2);
							Iterator<String> itr = s1.iterator();
							StringBuilder diffPaths = new StringBuilder();
							while(itr.hasNext()){
								diffPaths.append(","+itr.next());
							}
							System.out.println("Difference::"+diffPaths.toString());
						}
						prevElement = (ArrayList<String>) selectedElements.clone();
						selectedElements.clear();
						System.out.println(result);
						writer.println(result.toString());
					}
				}
			}
			//bw.close();
			writer.close();
		} catch (IOException e) {
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

//			ChildNumber cn = new ChildNumber(n);
			// xpath = "/node()[" + cn.getXPath() + "]";
			xpath = "/" + n.getNodeName();
		} else {

//			ChildNumber cn = new ChildNumber(n);

			// xpath = getXPath(n.getParentNode()) + "/node()[" + cn.getXPath()
			// + "]";
			xpath = getXPath(n.getParentNode()) + "/" + n.getNodeName();
		}

		return xpath;
	}
}
