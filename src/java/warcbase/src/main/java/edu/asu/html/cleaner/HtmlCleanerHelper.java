package edu.asu.html.cleaner;

 
import java.io.StringReader;
import java.util.ArrayList;

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


public class HtmlCleanerHelper {

	public static void main(String[] args) {
		String samplehtml ="\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en-US\">\n" +
                "<head>\n" +
                "<title>Tryit Editor v2.5</title>\n" +
                "<meta name=\"viewport\" content=\"width=device-width\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<link rel=\"stylesheet\" href=\"/trystyle.css\">\n" +
                "<!--[if lt IE 8]>\n" +
                "<style>\n" +
                ".textareacontainer, .iframecontainer {width:48%;}\n" +
                ".textarea, .iframe {height:800px;}\n" +
                "#textareaCode, #iframeResult {height:700px;}\n" +
                ".menu img {display:none;}\n" +
                "</style>\n" +
                "<![endif]-->\n" +
                "<script>\n" +
                "  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" +
                "  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" +
                "  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" +
                "  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n" +
                "  ga('create', 'UA-3855518-1', 'auto');\n" +
                "  ga('require', 'displayfeatures');\n" +
                "  ga('send', 'pageview');\n" +
                "</script>\n" +
                "<script>\n" +
                "var googletag = googletag || {};\n" +
                "googletag.cmd = googletag.cmd || [];\n" +
                "(function() {\n" +
                "var gads = document.createElement('script');\n" +
                "gads.async = true;\n" +
                "gads.type = 'text/javascript';\n" +
                "var useSSL = 'https:' == document.location.protocol;\n" +
                "gads.src = (useSSL ? 'https:' : 'http:') + \n" +
                "'//www.googletagservices.com/tag/js/gpt.js';\n" +
                "var node = document.getElementsByTagName('script')[0];\n" +
                "node.parentNode.insertBefore(gads, node);\n" +
                "})();\n" +
                "</script>\n" +
                "<script type='text/javascript'>\n" +
                " // GPT slots\n" +
                " var gptAdSlots = [];\n" +
                " googletag.cmd.push(function() {\n" +
                "\n" +
                "   var leaderMapping = googletag.sizeMapping().\n" +
                "   // Mobile ad\n" +
                "   addSize([0, 0], [320, 50]). \n" +
                "   // Vertical Tablet ad\n" +
                "   addSize([468, 0], [468, 60]). \n" +
                "   // Horizontal Tablet\n" +
                "   addSize([728, 0], [728, 90]).\n" +
                "   // Desktop and bigger ad\n" +
                "// addSize([970, 0], [[728, 90], [970, 90]]).build();\n" +
                "   addSize([970, 0], [728, 90]).build();\n" +
                "// gptAdSlots[0] = googletag.defineSlot('/16833175/TryitLeaderboard', [[728, 90], [970, 90]], 'div-gpt-ad-1428407818244-0').\n" +
                "   gptAdSlots[0] = googletag.defineSlot('/16833175/TryitLeaderboard', [728, 90], 'div-gpt-ad-1428407818244-0').\n" +
                "   defineSizeMapping(leaderMapping).addService(googletag.pubads());\n" +
                "\n" +
                "\n" +
                "   googletag.pubads().setTargeting(\"content\",\"tryhtml\");\n" +
                "   googletag.enableServices();\n" +
                " });\n" +
                "</script>\n" +
                "<script>\n" +
                "if (window.addEventListener) {              \n" +
                "    window.addEventListener(\"resize\", browserResize);\n" +
                "} else if (window.attachEvent) {                 \n" +
                "    window.attachEvent(\"onresize\", browserResize);\n" +
                "}\n" +
                "var xbeforeResize = window.innerWidth;\n" +
                "\n" +
                "function browserResize() {\n" +
                "    var afterResize = window.innerWidth;\n" +
                "    if ((xbeforeResize < (970) && afterResize >= (970)) || (xbeforeResize >= (970) && afterResize < (970)) ||\n" +
                "        (xbeforeResize < (728) && afterResize >= (728)) || (xbeforeResize >= (728) && afterResize < (728)) ||\n" +
                "        (xbeforeResize < (468) && afterResize >= (468)) ||(xbeforeResize >= (468) && afterResize < (468))) {\n" +
                "        xbeforeResize = afterResize;\n" +
                "        googletag.cmd.push(function() {\n" +
                "            googletag.pubads().refresh([gptAdSlots[0]]);\n" +
                "        });\n" +
                "    }\n" +
                "}\n" +
                "</script>\n" +
                "<script type=\"text/javascript\">\n" +
                "function submitTryit()\n" +
                "{\n" +
                "var t=document.getElementById(\"textareaCode\").value;\n" +
                "t=t.replace(/=/gi,\"w3equalsign\");\n" +
                "var pos=t.search(/script/i)\n" +
                "while (pos>0)\n" +
                "\t{\n" +
                "\tt=t.substring(0,pos) + \"w3\" + t.substr(pos,3) + \"w3\" + t.substr(pos+3,3) + \"tag\" + t.substr(pos+6);\n" +
                "\tpos=t.search(/script/i);\n" +
                "\t}\n" +
                "if ( navigator.userAgent.match(/Safari/i) ) {\n" +
                "\t    t=escape(t);\n" +
                "\t    document.getElementById(\"bt\").value=\"1\";\n" +
                "\t}\n" +
                "document.getElementById(\"code\").value=t;\n" +
                "document.getElementById(\"tryitform\").action=\"tryit_view.asp?x=\" + Math.random();\n" +
                "validateForm();\n" +
                "document.getElementById(\"iframeResult\").contentWindow.name = \"view\";\n" +
                "document.getElementById(\"tryitform\").submit();\n" +
                "}\n" +
                "function validateForm()\n" +
                "{\n" +
                "var code=document.getElementById(\"code\").value;\n" +
                "if (code.length>10000)\n" +
                "\t{\n" +
                "\tdocument.getElementById(\"code\").value=\"<h1>Error</h1>\";\n" +
                "\t}\n" +
                "}\n" +
                "</script>\n" +
                "<style>\n" +
                "\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id='tryitLeaderboard'>\n" +
                "<!-- TryitLeaderboard -->\n" +
                "<div id='div-gpt-ad-1428407818244-0'>\n" +
                "<script type='text/javascript'>googletag.cmd.push(function() { googletag.display('div-gpt-ad-1428407818244-0'); });</script>\n" +
                "</div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "  <div class=\"textareacontainer\">\n" +
                "    <div class=\"textarea\">\n" +
                "      <div style=\"overflow:auto;\">\n" +
                "        <div class=\"headerText\">Edit This Code:</div>\n" +
                "        <button class=\"seeResult\" type=\"button\" onclick=\"submitTryit()\">See Result &raquo;</button>\n" +
                "      </div>\n" +
                "      <div class=\"textareawrapper\">\n" +
                "        <textarea autocomplete=\"off\" class=\"code_input\" id=\"textareaCode\" wrap=\"logical\" spellcheck=\"false\"><!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>My First Heading</h1>\n" +
                "\n" +
                "<p>My first paragraph.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n" +
                "</textarea>\n" +
                "          <form autocomplete=\"off\" style=\"margin:0px;display:none;\" action=\"tryit_view.asp\" method=\"post\" target=\"view\" id=\"tryitform\" name=\"tryitform\" onsubmit=\"validateForm();\">\n" +
                "            <input type=\"hidden\" name=\"code\" id=\"code\" />\n" +
                "            <input type=\"hidden\" id=\"bt\" name=\"bt\" />\n" +
                "          </form>\n" +
                "       </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\"iframecontainer\">\n" +
                "    <div class=\"iframe\">\n" +
                "      <div style=\"overflow:auto;\">\n" +
                "        <div class=\"headerText\">Result:</div>\n" +
                "      </div>\n" +
                "      <div class=\"iframewrapper\">\n" +
                "        <iframe id=\"iframeResult\" class=\"result_output\" frameborder=\"0\" name=\"view\"></iframe>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\"footerText\">Try it Yourself - &copy; <a href=\"http://www.w3schools.com\">w3schools.com</a></div>      \n" +
                "</div>\n" +
                "<script>submitTryit()</script>\n" +
                "</body>\n" +
                "</html>";
		System.out.println(cleanHtml(samplehtml));
		
		
		
	}
	
	public static ArrayList<String> cleanHtml(String rawHtmlFile){
		
		TagNode node;
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setAllowHtmlInsideAttributes(true);
		props.setAllowMultiWordAttributes(true);
		props.setRecognizeUnicodeChars(true);
		props.setOmitComments(true);
		ArrayList<String> xPaths = new ArrayList<String>();
		try {
			node = cleaner.clean(rawHtmlFile);
			String xmlContent = new PrettyXmlSerializer(props).getAsString(node, "utf-8");
			xPaths = getXpath(xmlContent);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xPaths;
	}
	
	public static ArrayList<String> getXpath(String xml) {
		ArrayList<String> xPathNodes = new ArrayList<String>();
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
					xPathNodes.add(getXPath(n));
				}  
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xPathNodes;
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
            //xpath = "/node()[" + cn.getXPath() + "]";
            xpath = "/"+n.getNodeName();
            
            
        } else {

            ChildNumber cn = new ChildNumber(n);

            //xpath = getXPath(n.getParentNode()) + "/node()[" + cn.getXPath() + "]";
            xpath = getXPath(n.getParentNode()) + "/" + n.getNodeName();
        }
        
        return xpath;
    }
		 
	 
}