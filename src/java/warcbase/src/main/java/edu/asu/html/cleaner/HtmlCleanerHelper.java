package edu.asu.html.cleaner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
 
	public static void main(String[] args) {
		
		
		String sample = "HTTP/1.1 200 OK\n"
				+ "Date: Fri, 22 May 2015 08:35:18 GMT\n"
				+ "Server: Apache/2.2.22 (Ubuntu)\n"
				+ "X-Powered-By: PHP/5.3.10-1ubuntu3.18\n"
				+ "X-Content-Type-Options: nosniff\n"
				+ "Vary: Accept-Encoding,Cookie\n"
				+ "Expires: Thu, 01 Jan 1970 00:00:00 GMT\n"
				+ "Cache-Control: private, must-revalidate, max-age=0\n"
				+ "Last-Modified: Fri, 18 Apr 2014 05:39:01 GMT\n"
				+ "Content-Language: en\n"
				+ "Content-Encoding: gzip\n"
				+ "Connection: close\n"
				+ "Content-Type: text/html; charset=UTF-8\n"
				+ "\n"
				+ "<!DOCTYPE html>\n"
				+ "<html lang=\"en\" dir=\"ltr\" class=\"client-nojs\">\n"
				+ "<head>\n"
				+ "<meta charset=\"UTF-8\" /><title>Meeting91 - Whitespace (Hackerspace Gent)</title>\n"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EDGE\" /><meta name=\"generator\" content=\"MediaWiki 1.23alpha\" />\n"
				+ "<link rel=\"ExportRDF\" type=\"application/rdf+xml\" title=\"Meeting91\" href=\"/smw/index.php?title=Special:ExportRDF/Meeting91&amp;xmlmime=rdf\" />\n"
				+ "<link rel=\"alternate\" type=\"application/x-wiki\" title=\"Edit\" href=\"/smw/index.php?title=Meeting91&amp;action=edit\" />\n"
				+ "<link rel=\"edit\" title=\"Edit\" href=\"/smw/index.php?title=Meeting91&amp;action=edit\" />\n"
				+ "<link rel=\"shortcut icon\" href=\"/favicon.ico\" />\n"
				+ "<link rel=\"search\" type=\"application/opensearchdescription+xml\" href=\"/smw/opensearch_desc.php\" title=\"Whitespace (Hackerspace Gent) (en)\" />\n"
				+ "<link rel=\"EditURI\" type=\"application/rsd+xml\" href=\"http://members.0x20.be/smw/api.php?action=rsd\" />\n"
				+ "<link rel=\"alternate\" type=\"application/atom+xml\" title=\"Whitespace (Hackerspace Gent) Atom feed\" href=\"/smw/index.php?title=Special:RecentChanges&amp;feed=atom\" />\n"
				+ "<link rel=\"stylesheet\" href=\"http://members.0x20.be/smw/load.php?debug=false&amp;lang=en&amp;modules=mediawiki.legacy.commonPrint%2Cshared%7Cskins.common.interface%7Cskins.vector.styles&amp;only=styles&amp;skin=vector&amp;*\" />\n"
				+ "<meta name=\"ResourceLoaderDynamicStyles\" content=\"\" />\n"
				+ "<link rel=\"stylesheet\" href=\"http://members.0x20.be/smw/load.php?debug=false&amp;lang=en&amp;modules=site&amp;only=styles&amp;skin=vector&amp;*\" />\n"
				+ "<style>a:lang(ar),a:lang(kk-arab),a:lang(mzn),a:lang(ps),a:lang(ur){text-decoration:none}\n"
				+ "/* cache key: whitespacewiki-mw_:resourceloader:filter:minify-css:7:cfd106639e3c9353fb7de53167074bae */</style>\n"
				+ "\n"
				+ "<script src=\"http://members.0x20.be/smw/load.php?debug=false&amp;lang=en&amp;modules=startup&amp;only=scripts&amp;skin=vector&amp;*\"></script>\n"
				+ "<script>if(window.mw){\n"
				+ "mw.config.set({\"wgCanonicalNamespace\":\"\",\"wgCanonicalSpecialPageName\":false,\"wgNamespaceNumber\":0,\"wgPageName\":\"Meeting91\",\"wgTitle\":\"Meeting91\",\"wgCurRevisionId\":5187,\"wgRevisionId\":5187,\"wgArticleId\":1070,\"wgIsArticle\":true,\"wgIsRedirect\":false,\"wgAction\":\"view\",\"wgUserName\":null,\"wgUserGroups\":[\"*\"],\"wgCategories\":[\"Pages with a map rendered by the Maps extension\",\"Meetings\"],\"wgBreakFrames\":false,\"wgPageContentLanguage\":\"en\",\"wgPageContentModel\":\"wikitext\",\"wgSeparatorTransformTable\":[\"\",\"\"],\"wgDigitTransformTable\":[\"\",\"\"],\"wgDefaultDateFormat\":\"dmy\",\"wgMonthNames\":[\"\",\"January\",\"February\",\"March\",\"April\",\"May\",\"June\",\"July\",\"August\",\"September\",\"October\",\"November\",\"December\"],\"wgMonthNamesShort\":[\"\",\"Jan\",\"Feb\",\"Mar\",\"Apr\",\"May\",\"Jun\",\"Jul\",\"Aug\",\"Sep\",\"Oct\",\"Nov\",\"Dec\"],\"wgRelevantPageName\":\"Meeting91\",\"wgIsProbablyEditable\":true,\"wgRestrictionEdit\":[],\"wgRestrictionMove\":[],\"egMapsDebugJS\":false,\"egMapsAvailableServices\":[\"googlemaps3\",\"openlayers\",\"leaflet\"],\"sfgAutocompleteValues\":[],\"sfgAutocompleteOnAllChars\":false,\"sfgFieldProperties\":[],\"sfgDependentFields\":[],\"sfgShowOnSelect\":[],\"sfgScriptPath\":\"/smw/extensions/SemanticForms\"});\n"
				+ "}</script><script>if(window.mw){\n"
				+ "mw.loader.implement(\"user.options\",function(){mw.user.options.set({\"ccmeonemails\":0,\"cols\":80,\"date\":\"default\",\"diffonly\":0,\"disablemail\":0,\"editfont\":\"default\",\"editondblclick\":0,\"editsectiononrightclick\":0,\"enotifminoredits\":0,\"enotifrevealaddr\":0,\"enotifusertalkpages\":1,\"enotifwatchlistpages\":1,\"extendwatchlist\":0,\"fancysig\":0,\"forceeditsummary\":0,\"gender\":\"unknown\",\"hideminor\":0,\"hidepatrolled\":0,\"imagesize\":2,\"math\":1,\"minordefault\":0,\"newpageshidepatrolled\":0,\"nickname\":\"\",\"noconvertlink\":0,\"norollbackdiff\":0,\"numberheadings\":0,\"previewonfirst\":0,\"previewontop\":1,\"rcdays\":7,\"rclimit\":50,\"rememberpassword\":0,\"rows\":25,\"showhiddencats\":0,\"shownumberswatching\":1,\"showtoolbar\":1,\"skin\":\"vector\",\"stubthreshold\":0,\"thumbsize\":2,\"underline\":2,\"uselivepreview\":0,\"usenewrc\":0,\"vector-simplesearch\":1,\"watchcreations\":1,\"watchdefault\":true,\"watchdeletion\":0,\"watchlistdays\":3,\"watchlisthideanons\":0,\"watchlisthidebots\":0,\"watchlisthideliu\":0,\"watchlisthideminor\":0,\"watchlisthideown\":0,\n"
				+ "\"watchlisthidepatrolled\":0,\"watchmoves\":0,\"wllimit\":250,\"useeditwarning\":1,\"prefershttps\":1,\"lqtnotifytalk\":false,\"lqtdisplaydepth\":5,\"lqtdisplaycount\":25,\"lqtcustomsignatures\":true,\"lqt-watch-threads\":true,\"language\":\"en\",\"variant-gan\":\"gan\",\"variant-iu\":\"iu\",\"variant-kk\":\"kk\",\"variant-ku\":\"ku\",\"variant-shi\":\"shi\",\"variant-sr\":\"sr\",\"variant-tg\":\"tg\",\"variant-uz\":\"uz\",\"variant-zh\":\"zh\",\"searchNs0\":true,\"searchNs1\":false,\"searchNs2\":false,\"searchNs3\":false,\"searchNs4\":false,\"searchNs5\":false,\"searchNs6\":false,\"searchNs7\":false,\"searchNs8\":false,\"searchNs9\":false,\"searchNs10\":false,\"searchNs11\":false,\"searchNs12\":false,\"searchNs13\":false,\"searchNs14\":false,\"searchNs15\":false,\"searchNs90\":false,\"searchNs91\":false,\"searchNs92\":false,\"searchNs93\":false,\"searchNs102\":false,\"searchNs103\":false,\"searchNs104\":false,\"searchNs105\":false,\"searchNs106\":false,\"searchNs107\":false,\"searchNs108\":false,\"searchNs109\":false,\"searchNs274\":false,\"searchNs275\":false,\"searchNs420\":false,\"searchNs421\":false,\n"
				+ "\"variant\":\"en\"});},{},{});mw.loader.implement(\"user.tokens\",function(){mw.user.tokens.set({\"editToken\":\"+\\\\\",\"patrolToken\":false,\"watchToken\":false});},{},{});\n"
				+ "/* cache key: whitespacewiki-mw_:resourceloader:filter:minify-js:7:3d8b36b3b7486c62d49d3a395aab5b8d */\n"
				+ "}</script>\n"
				+ "<script>if(window.mw){\n"
				+ "mw.loader.load([\"ext.smw.style\",\"ext.smw.tooltips\",\"mediawiki.page.startup\",\"mediawiki.legacy.wikibits\",\"mediawiki.legacy.ajax\",\"skins.vector.js\"]);\n"
				+ "}</script>\n"
				+ "<script src=\"//maps.googleapis.com/maps/api/js?language=en&amp;sensor=false\"></script><script>if(window.mw){\n"
				+ "mw.config.set({\"egGoogleJsApiKey\":\"\"});\n"
				+ "}</script><!--[if lt IE 7]><style type=\"text/css\">body{behavior:url(\"/smw/skins/vector/csshover.min.htc\")}</style><![endif]--></head>\n"
				+ "<body class=\"mediawiki ltr sitedir-ltr ns-0 ns-subject page-Meeting91 skin-vector action-view vector-animateLayout\">\n"
				+ "\t\t<div id=\"mw-page-base\" class=\"noprint\"></div>\n"
				+ "\t\t<div id=\"mw-head-base\" class=\"noprint\"></div>\n"
				+ "\t\t<div id=\"content\" class=\"mw-body\" role=\"main\">\n"
				+ "\t\t\t<a id=\"top\"></a>\n"
				+ "\t\t\t<div id=\"mw-js-message\" style=\"display:none;\"></div>\n"
				+ "\t\t\t\t\t\t<h1 id=\"firstHeading\" class=\"firstHeading\" lang=\"en\"><span dir=\"auto\">Meeting91</span></h1>\n"
				+ "\t\t\t<div id=\"bodyContent\">\n"
				+ "\t\t\t\t\t\t\t\t<div id=\"siteSub\">From Whitespace (Hackerspace Gent)</div>\n"
				+ "\t\t\t\t\t\t\t\t<div id=\"contentSub\"></div>\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"jump-to-nav\" class=\"mw-jump\">\n"
				+ "\t\t\t\t\tJump to:\t\t\t\t\t<a href=\"#mw-navigation\">navigation</a>, \t\t\t\t\t<a href=\"#p-search\">search</a>\n"
				+ "\t\t\t\t</div>\n"
				+ "\t\t\t\t<div id=\"mw-content-text\" lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\"><div style=\"border: 2px solid #007A20; padding: 0.5em 1em 0.3em 0.5em; color: #007A20;\">\n"
				+ "<p><b>Note:</b> this article is about a passed meeting.\n"
				+ "</p>\n"
				+ "</div>\n"
				+ "<table cellpadding=\"0\" cellspacing=\"0\" style=\"position:relative; margin: 1em 0 1em 2em; padding:0px; border-collapse: collapse; border: 1px solid #ccc; background: #fff; float: right; clear: right; width: 200px\">\n"
				+ "<tr>\n"
				+ "<td align=\"center\" colspan=\"1\" style=\"background-color: white; text-align: center;\">\n"
				+ "</td></tr>\n"
				+ "<tr class=\"ext-header\">\n"
				+ "<th style=\"font-size: 1.5em; color: black; background: #fff\" align=\"center\">  Meeting91\n"
				+ "</th></tr>\n"
				+ "<tr>\n"
				+ "<th style=\"padding: 0px&#160;;\"> <a href=\"/File:Open-closed-meeting.png\" class=\"image\"><img alt=\"Open-closed-meeting.png\" src=\"/smw/images/thumb/7/70/Open-closed-meeting.png/200px-Open-closed-meeting.png\" width=\"200\" height=\"204\" srcset=\"/smw/images/7/70/Open-closed-meeting.png 1.5x, /smw/images/7/70/Open-closed-meeting.png 2x\" /></a>\n"
				+ "</th></tr>\n"
				+ "<tr>\n"
				+ "<td align=\"center\"> From 2011/09/29 19:30:00 to 2011/09/29 23:59:59\n"
				+ "</td></tr>\n"
				+ "<tr>\n"
				+ "<th align=\"center\" style=\"padding: 0px; background: #ccc\"> Attendees (11):\n"
				+ "</th></tr>\n"
				+ "<tr>\n"
				+ "<td align=\"center\"> <a href=\"/Koen\" title=\"Koen\">Koen</a>, <a href=\"/Jeroen_De_Dauw\" title=\"Jeroen De Dauw\">Jeroen De Dauw</a>, <a href=\"/Impy\" title=\"Impy\">Impy</a>, <a href=\"/Hans\" title=\"Hans\">Hans</a>, <a href=\"/Sandb\" title=\"Sandb\">Sandb</a>, <a href=\"/Special:FormEdit/Person/Amelia?redlink=1\" class=\"new\" title=\"Amelia (page does not exist)\">Amelia</a>, <a href=\"/Koenraad\" title=\"Koenraad\">Koenraad</a>, <a href=\"/Johannes\" title=\"Johannes\">Johannes</a>, <a href=\"/Special:FormEdit/Person/Hala?redlink=1\" class=\"new\" title=\"Hala (page does not exist)\">Hala</a>, <a href=\"/Jaroslov\" title=\"Jaroslov\">Jaroslov</a>, <a href=\"/Abe\" title=\"Abe\">abe</a>\n"
				+ "</td></tr>\n"
				+ "\n"
				+ "<tr>\n"
				+ "<th style=\"padding: 0px; background: #ccc\"> Where:\n"
				+ "</th></tr>\n"
				+ "<tr>\n"
				+ "<td align=\"center\"> <a href=\"/Whitespace\" title=\"Whitespace\">Whitespace</a>\n"
				+ "Blekerijstraat 75, Gent, Belgium<div id=\"map_google3_1\" style=\"width: 250px; height: 250px; background-color: #cccccc; overflow: hidden;\" class=\"maps-map maps-googlemaps3\">Loading map...<div style=\"display:none\" class=\"mapdata\">{\"minzoom\":false,\"mappingservice\":\"googlemaps3\",\"type\":\"ROADMAP\",\"geoservice\":\"geonames\",\"types\":[\"ROADMAP\",\"SATELLITE\",\"HYBRID\",\"TERRAIN\"],\"maxzoom\":false,\"width\":\"250px\",\"height\":\"250px\",\"centre\":false,\"title\":\"\",\"label\":\"\",\"icon\":\"\",\"visitedicon\":\"\",\"lines\":[],\"polygons\":[],\"circles\":[],\"rectangles\":[],\"wmsoverlay\":false,\"copycoords\":false,\"static\":false,\"zoom\":14,\"layers\":[],\"controls\":[\"type\"],\"zoomstyle\":\"DEFAULT\",\"typestyle\":\"DEFAULT\",\"autoinfowindows\":false,\"resizable\":false,\"kmlrezoom\":false,\"poi\":true,\"markercluster\":false,\"tilt\":0,\"imageoverlays\":[],\"kml\":[],\"gkml\":[],\"fusiontables\":[],\"searchmarkers\":\"\",\"enablefullscreen\":false,\"locations\":[{\"text\":\"\\u003Cp\\u003EMeeting91@ \\u003Ca href=\\\"/Whitespace\\\" title=\\\"Whitespace\\\"\\u003EWhitespace\\u003C/a\\u003E\\n\\u003C/p\\u003E\",\"title\":\"Meeting91@ Whitespace\\n\",\"link\":\"\",\"lat\":51.0597653,\"lon\":3.7323823,\"alt\":0,\"address\":\"\",\"icon\":\"\",\"group\":\"\",\"inlineLabel\":\"\",\"visitedicon\":\"\"}]}</div></div>\n"
				+ "</td></tr>\n"
				+ "<tr>\n"
				+ "<th style=\"padding: 0px; background: #ccc\"> \n"
				+ "<script language=\"JavaScript\" src=\"https://apis.google.com/js/plusone.js\">\n"
				+ "</script>\n"
				+ "<g:plusone \n"
				+ "></g:plusone> <a href=\"http://twitter.com/share\" class=\"twitter-share-button\" data-count=\"0\" data-via=\"HSGhent\" data-related=\"JeroenDeDauw\">Tweet</a><script type=\"text/javascript\" src=\"https://platform.twitter.com/widgets.js\"></script> <a href=\"http://www.cornify.com\" onclick=\"cornify_add();return false;\"><img src=\"../smw/images/7/74/Cornify.gif\" width=\"61\" height=\"16\" border=\"0\" alt=\"Cornify\" /></a><script type=\"text/javascript\">(function() {\n"
				+ "\tvar js = document.createElement('script');\n"
				+ "\tjs.type = 'text/javascript';\n"
				+ "\tjs.async = true;\n"
				+ "\tjs.src = '../smw/images/cornify.js';\n"
				+ "\t(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(js);\n"
				+ "})();</script>\n"
				+ "</th></tr></table>\n"
				+ "<p style=\"font-size: 1.5em; color: #aaa; font-style: italic;\"></p>\n"
				+ "<p><br />\n"
				+ "</p>\n"
				+ "<h2><span class=\"mw-headline\" id=\"Agenda_points\">Agenda points</span></h2>\n"
				+ "<ul>\n"
				+ "<li> membership box\n"
				+ "</li>\n"
				+ "<li> <a href=\"/IMarkt\" title=\"IMarkt\">iMarkt</a> status\n"
				+ "</li>\n"
				+ "</ul>\n"
				+ "<h2><span class=\"mw-headline\" id=\"Subpages\">Subpages</span></h2>\n"
				+ "<p>\"\" has no sub pages.\n"
				+ "</p>\n"
				+ "<h2><span class=\"mw-headline\" id=\"Meeting_notes\">Meeting notes</span></h2>\n"
				+ "<p><i>Join note taking during the meeting: <a class=\"external free\" href=\"http://ietherpad.com/0x20\">http://ietherpad.com/0x20</a></i>\n"
				+ "</p>\n"
				+ "<!-- \n"
				+ "NewPP limit report\n"
				+ "CPU time usage: 0.372 seconds\n"
				+ "Real time usage: 0.455 seconds\n"
				+ "Preprocessor visited node count: 151/1000000\n"
				+ "Preprocessor generated node count: 856/1000000\n"
				+ "Post‐expand include size: 5975/2097152 bytes\n"
				+ "Template argument size: 701/2097152 bytes\n"
				+ "Highest expansion depth: 7/40\n"
				+ "Expensive parser function count: 0/100\n"
				+ "-->\n"
				+ "\n"
				+ "<!-- Saved in parser cache with key whitespacewiki-mw_:pcache:idhash:1070-0!*!0!!*!2!* and timestamp 20140418053901\n"
				+ " -->\n"
				+ "</div>\t\t\t\t\t\t\t\t<div class=\"printfooter\">\n"
				+ "\t\t\t\tRetrieved from \"<a href=\"http://members.0x20.be/smw/index.php?title=Meeting91&amp;oldid=5187\">http://members.0x20.be/smw/index.php?title=Meeting91&amp;oldid=5187</a>\"\t\t\t\t</div>\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t\t<div id='catlinks' class='catlinks'><div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\"><a href=\"/Special:Categories\" title=\"Special:Categories\">Categories</a>: <ul><li><a href=\"/smw/index.php?title=Category:Pages_with_a_map_rendered_by_the_Maps_extension&amp;action=edit&amp;redlink=1\" class=\"new\" title=\"Category:Pages with a map rendered by the Maps extension (page does not exist)\">Pages with a map rendered by the Maps extension</a></li><li><a href=\"/Category:Meetings\" title=\"Category:Meetings\">Meetings</a></li></ul></div></div>\t\t\t\t\t\t\t\t\t\t\t\t<div id='mw-data-after-content'>\n"
				+ "\t<div class=\"smwfact\"><span class=\"smwfactboxhead\">Facts about \"<span class=\"swmfactboxheadbrowse\"><a href=\"/Special:Browse/Meeting91\" title=\"Special:Browse/Meeting91\">Meeting91</a></span>\"</span><span class=\"smwrdflink\"><span class=\"rdflink\"><a href=\"/Special:ExportRDF/Meeting91\" title=\"Special:ExportRDF/Meeting91\">RDF feed</a></span></span><table class=\"smwfacttable\"><tr class=\"row-odd\"><td class=\"smwpropname\"><a href=\"/Property:Has_attendee\" title=\"Property:Has attendee\">Has&#160;attendee</a></td><td class=\"smwprops\"><a href=\"/Koen\" title=\"Koen\">Koen</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Koen\" title=\"Special:SearchByProperty/Has-20attendee/Koen\">+</a></span>, <a href=\"/Jeroen_De_Dauw\" title=\"Jeroen De Dauw\">Jeroen De Dauw</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Jeroen-20De-20Dauw\" title=\"Special:SearchByProperty/Has-20attendee/Jeroen-20De-20Dauw\">+</a></span>, <a href=\"/Impy\" title=\"Impy\">Impy</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Impy\" title=\"Special:SearchByProperty/Has-20attendee/Impy\">+</a></span>, <a href=\"/Hans\" title=\"Hans\">Hans</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Hans\" title=\"Special:SearchByProperty/Has-20attendee/Hans\">+</a></span>, <a href=\"/Sandb\" title=\"Sandb\">Sandb</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Sandb\" title=\"Special:SearchByProperty/Has-20attendee/Sandb\">+</a></span>, <a href=\"/Special:FormEdit/Person/Amelia?redlink=1\" class=\"new\" title=\"Amelia (page does not exist)\">Amelia</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Amelia\" title=\"Special:SearchByProperty/Has-20attendee/Amelia\">+</a></span>, <a href=\"/Koenraad\" title=\"Koenraad\">Koenraad</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Koenraad\" title=\"Special:SearchByProperty/Has-20attendee/Koenraad\">+</a></span>, <a href=\"/Johannes\" title=\"Johannes\">Johannes</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Johannes\" title=\"Special:SearchByProperty/Has-20attendee/Johannes\">+</a></span>, <a href=\"/Special:FormEdit/Person/Hala?redlink=1\" class=\"new\" title=\"Hala (page does not exist)\">Hala</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Hala\" title=\"Special:SearchByProperty/Has-20attendee/Hala\">+</a></span>, <a href=\"/Jaroslov\" title=\"Jaroslov\">Jaroslov</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Jaroslov\" title=\"Special:SearchByProperty/Has-20attendee/Jaroslov\">+</a></span> and <a href=\"/Abe\" title=\"Abe\">Abe</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee/Abe\" title=\"Special:SearchByProperty/Has-20attendee/Abe\">+</a></span></td></tr><tr class=\"row-even\"><td class=\"smwpropname\"><a href=\"/Property:Has_attendee_amount\" title=\"Property:Has attendee amount\">Has&#160;attendee&#160;amount</a></td><td class=\"smwprops\">11  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20attendee-20amount/11\" title=\"Special:SearchByProperty/Has-20attendee-20amount/11\">+</a></span></td></tr><tr class=\"row-odd\"><td class=\"smwpropname\"><a href=\"/Property:Has_end_date\" title=\"Property:Has end date\">Has&#160;end&#160;date</a></td><td class=\"smwprops\">29 September 2011 23:59:59  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20end-20date/29-20September-202011-2023:59:59\" title=\"Special:SearchByProperty/Has-20end-20date/29-20September-202011-2023:59:59\">+</a></span></td></tr><tr class=\"row-even\"><td class=\"smwpropname\"><a href=\"/Property:Has_event_type\" title=\"Property:Has event type\">Has&#160;event&#160;type</a></td><td class=\"smwprops\">Meeting  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20event-20type/Meeting\" title=\"Special:SearchByProperty/Has-20event-20type/Meeting\">+</a></span></td></tr><tr class=\"row-odd\"><td class=\"smwpropname\"><a href=\"/Property:Has_location\" title=\"Property:Has location\">Has&#160;location</a></td><td class=\"smwprops\"><a href=\"/Whitespace\" title=\"Whitespace\">Whitespace</a>  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20location/Whitespace\" title=\"Special:SearchByProperty/Has-20location/Whitespace\">+</a></span></td></tr><tr class=\"row-even\"><td class=\"smwpropname\"><a href=\"/Property:Has_start_date\" title=\"Property:Has start date\">Has&#160;start&#160;date</a></td><td class=\"smwprops\">29 September 2011 19:30:00  <span class=\"smwsearch\"><a href=\"/Special:SearchByProperty/Has-20start-20date/29-20September-202011-2019:30:00\" title=\"Special:SearchByProperty/Has-20start-20date/29-20September-202011-2019:30:00\">+</a></span></td></tr></table></div>\n"
				+ "\n"
				+ "</div>\n"
				+ "\t\t\t\t\t\t\t\t<div class=\"visualClear\"></div>\n"
				+ "\t\t\t\t\t\t\t</div>\n"
				+ "\t\t</div>\n"
				+ "\t\t<div id=\"mw-navigation\">\n"
				+ "\t\t\t<h2>Navigation menu</h2>\n"
				+ "\t\t\t<div id=\"mw-head\">\n"
				+ "\t\t\t\t<div id=\"p-personal\" role=\"navigation\" class=\"\" aria-labelledby=\"p-personal-label\">\n"
				+ "\t<h3 id=\"p-personal-label\">Personal tools</h3>\n"
				+ "\t<ul>\n"
				+ "<li id=\"pt-createaccount\"><a href=\"/smw/index.php?title=Special:UserLogin&amp;returnto=Meeting91&amp;type=signup\">Create account</a></li><li id=\"pt-login\"><a href=\"/smw/index.php?title=Special:UserLogin&amp;returnto=Meeting91\" title=\"You are encouraged to log in; however, it is not mandatory [o]\" accesskey=\"o\">Log in</a></li>\t</ul>\n"
				+ "</div>\n"
				+ "\t\t\t\t<div id=\"left-navigation\">\n"
				+ "\t\t\t\t\t<div id=\"p-namespaces\" role=\"navigation\" class=\"vectorTabs\" aria-labelledby=\"p-namespaces-label\">\n"
				+ "\t<h3 id=\"p-namespaces-label\">Namespaces</h3>\n"
				+ "\t<ul>\n"
				+ "\t\t\t\t\t<li  id=\"ca-nstab-main\" class=\"selected\"><span><a href=\"/Meeting91\"  title=\"View the content page [c]\" accesskey=\"c\">Page</a></span></li>\n"
				+ "\t\t\t\t\t<li  id=\"ca-talk\" class=\"new\"><span><a href=\"/smw/index.php?title=Talk:Meeting91&amp;action=edit&amp;redlink=1\"  title=\"Discussion about the content page [t]\" accesskey=\"t\">Discussion</a></span></li>\n"
				+ "\t\t\t</ul>\n"
				+ "</div>\n"
				+ "<div id=\"p-variants\" role=\"navigation\" class=\"vectorMenu emptyPortlet\" aria-labelledby=\"p-variants-label\">\n"
				+ "\t<h3 id=\"mw-vector-current-variant\">\n"
				+ "\t\t</h3>\n"
				+ "\t<h3 id=\"p-variants-label\"><span>Variants</span><a href=\"#\"></a></h3>\n"
				+ "\t<div class=\"menu\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "\t\t\t\t</div>\n"
				+ "\t\t\t\t<div id=\"right-navigation\">\n"
				+ "\t\t\t\t\t<div id=\"p-views\" role=\"navigation\" class=\"vectorTabs\" aria-labelledby=\"p-views-label\">\n"
				+ "\t<h3 id=\"p-views-label\">Views</h3>\n"
				+ "\t<ul>\n"
				+ "\t\t\t\t\t<li id=\"ca-view\" class=\"selected\"><span><a href=\"/Meeting91\" >Read</a></span></li>\n"
				+ "\t\t\t\t\t<li id=\"ca-form_edit\" class=\"collapsible\"><span><a href=\"/smw/index.php?title=Meeting91&amp;action=formedit\" >Edit</a></span></li>\n"
				+ "\t\t\t\t\t<li id=\"ca-edit\"><span><a href=\"/smw/index.php?title=Meeting91&amp;action=edit\"  title=\"You can edit this page. Please use the preview button before saving [e]\" accesskey=\"e\">Edit source</a></span></li>\n"
				+ "\t\t\t\t\t<li id=\"ca-history\" class=\"collapsible\"><span><a href=\"/smw/index.php?title=Meeting91&amp;action=history\"  title=\"Past revisions of this page [h]\" accesskey=\"h\">View history</a></span></li>\n"
				+ "\t\t\t</ul>\n"
				+ "</div>\n"
				+ "<div id=\"p-cactions\" role=\"navigation\" class=\"vectorMenu emptyPortlet\" aria-labelledby=\"p-cactions-label\">\n"
				+ "\t<h3 id=\"p-cactions-label\"><span>Actions</span><a href=\"#\"></a></h3>\n"
				+ "\t<div class=\"menu\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "<div id=\"p-search\" role=\"search\">\n"
				+ "\t<h3><label for=\"searchInput\">Search</label></h3>\n"
				+ "\t<form action=\"/smw/index.php\" id=\"searchform\">\n"
				+ "\t\t\t\t<div id=\"simpleSearch\">\n"
				+ "\t\t\t\t\t\t<input name=\"search\" placeholder=\"Search\" title=\"Search Whitespace (Hackerspace Gent) [f]\" accesskey=\"f\" id=\"searchInput\" />\t\t\t\t\t\t<button type=\"submit\" name=\"button\" title=\"Search the pages for this text\" id=\"searchButton\"><img src=\"/smw/skins/vector/images/search-ltr.png?303\" alt=\"Search\" width=\"12\" height=\"13\" /></button>\t\t\t\t\t\t\t\t<input type='hidden' name=\"title\" value=\"Special:Search\"/>\n"
				+ "\t\t</div>\n"
				+ "\t</form>\n"
				+ "</div>\n"
				+ "\t\t\t\t</div>\n"
				+ "\t\t\t</div>\n"
				+ "\t\t\t<div id=\"mw-panel\">\n"
				+ "\t\t\t\t\t<div id=\"p-logo\" role=\"banner\"><a style=\"background-image: url(/smw/images/logo-0x20.png);\" href=\"/Main_Page\"  title=\"Visit the main page\"></a></div>\n"
				+ "\t\t\t\t<div class=\"portal\" role=\"navigation\" id='p-navigation' aria-labelledby='p-navigation-label'>\n"
				+ "\t<h3 id='p-navigation-label'>Navigation</h3>\n"
				+ "\t<div class=\"body\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t<li id=\"n-mainpage-description\"><a href=\"/Main_Page\" title=\"Visit the main page [z]\" accesskey=\"z\">Main page</a></li>\n"
				+ "\t\t\t<li id=\"n-recentchanges\"><a href=\"/Special:RecentChanges\" title=\"A list of recent changes in the wiki [r]\" accesskey=\"r\">Recent changes</a></li>\n"
				+ "\t\t\t<li id=\"n-Newline\"><a href=\"/Newline\">Newline</a></li>\n"
				+ "\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "<div class=\"portal\" role=\"navigation\" id='p-Whitespace' aria-labelledby='p-Whitespace-label'>\n"
				+ "\t<h3 id='p-Whitespace-label'>Whitespace</h3>\n"
				+ "\t<div class=\"body\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t<li id=\"n-Membership\"><a href=\"/Membership\">Membership</a></li>\n"
				+ "\t\t\t<li id=\"n-FAQ\"><a href=\"/FAQ\">FAQ</a></li>\n"
				+ "\t\t\t<li id=\"n-Contact\"><a href=\"/Contact\">Contact</a></li>\n"
				+ "\t\t\t<li id=\"n-Wanted\"><a href=\"/StuffWeNeed\">Wanted</a></li>\n"
				+ "\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "<div class=\"portal\" role=\"navigation\" id='p-Social' aria-labelledby='p-Social-label'>\n"
				+ "\t<h3 id='p-Social-label'>Social</h3>\n"
				+ "\t<div class=\"body\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t<li id=\"n-Google.2B-page\"><a href=\"https://plus.google.com/111440983315819252895\">Google+ page</a></li>\n"
				+ "\t\t\t<li id=\"n-G.2B-community\"><a href=\"https://plus.google.com/communities/109536490791438420196\">G+ community</a></li>\n"
				+ "\t\t\t<li id=\"n-Twitter\"><a href=\"https://twitter.com/hsghent\">Twitter</a></li>\n"
				+ "\t\t\t<li id=\"n-Facebook\"><a href=\"https://www.facebook.com/0x20.be\">Facebook</a></li>\n"
				+ "\t\t\t<li id=\"n-Tumblr\"><a href=\"http://blog.0x20.be/\">Tumblr</a></li>\n"
				+ "\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "<div class=\"portal\" role=\"navigation\" id='p-Add_or_edit_data' aria-labelledby='p-Add_or_edit_data-label'>\n"
				+ "\t<h3 id='p-Add_or_edit_data-label'>Add or edit data</h3>\n"
				+ "\t<div class=\"body\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t<li id=\"n-Events\"><a href=\"/Form:Event\">Events</a></li>\n"
				+ "\t\t\t<li id=\"n-Meetings\"><a href=\"/Form:Meeting\">Meetings</a></li>\n"
				+ "\t\t\t<li id=\"n-Event-images\"><a href=\"/Special:FormEdit/EventImage\">Event images</a></li>\n"
				+ "\t\t\t<li id=\"n-Projects\"><a href=\"/Form:Project\">Projects</a></li>\n"
				+ "\t\t\t<li id=\"n-Locations\"><a href=\"/Form:Location\">Locations</a></li>\n"
				+ "\t\t\t<li id=\"n-People\"><a href=\"/Form:Person\">People</a></li>\n"
				+ "\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "<div class=\"portal\" role=\"navigation\" id='p-Hackerspaces' aria-labelledby='p-Hackerspaces-label'>\n"
				+ "\t<h3 id='p-Hackerspaces-label'>Hackerspaces</h3>\n"
				+ "\t<div class=\"body\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t<li id=\"n-Documentation\"><a href=\"/Documentation\">Documentation</a></li>\n"
				+ "\t\t\t<li id=\"n-List-of-spaces\"><a href=\"http://hackerspaces.org/wiki/List_of_Hacker_Spaces\">List of spaces</a></li>\n"
				+ "\t\t\t<li id=\"n-Belgian-spaces\"><a href=\"http://hackerspaces.be/\">Belgian spaces</a></li>\n"
				+ "\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "<div class=\"portal\" role=\"navigation\" id='p-tb' aria-labelledby='p-tb-label'>\n"
				+ "\t<h3 id='p-tb-label'>Tools</h3>\n"
				+ "\t<div class=\"body\">\n"
				+ "\t\t<ul>\n"
				+ "\t\t\t<li id=\"t-whatlinkshere\"><a href=\"/Special:WhatLinksHere/Meeting91\" title=\"A list of all wiki pages that link here [j]\" accesskey=\"j\">What links here</a></li>\n"
				+ "\t\t\t<li id=\"t-recentchangeslinked\"><a href=\"/Special:RecentChangesLinked/Meeting91\" title=\"Recent changes in pages linked from this page [k]\" accesskey=\"k\">Related changes</a></li>\n"
				+ "\t\t\t<li id=\"t-specialpages\"><a href=\"/Special:SpecialPages\" title=\"A list of all special pages [q]\" accesskey=\"q\">Special pages</a></li>\n"
				+ "\t\t\t<li id=\"t-print\"><a href=\"/smw/index.php?title=Meeting91&amp;printable=yes\" rel=\"alternate\" title=\"Printable version of this page [p]\" accesskey=\"p\">Printable version</a></li>\n"
				+ "\t\t\t<li id=\"t-permalink\"><a href=\"/smw/index.php?title=Meeting91&amp;oldid=5187\" title=\"Permanent link to this revision of the page\">Permanent link</a></li>\n"
				+ "\t\t\t<li id=\"t-info\"><a href=\"/smw/index.php?title=Meeting91&amp;action=info\">Page information</a></li>\n"
				+ "\t\t\t<li id=\"t-smwbrowselink\"><a href=\"/Special:Browse/Meeting91\" rel=\"smw-browse\">Browse properties</a></li>\n"
				+ "\t\t</ul>\n"
				+ "\t</div>\n"
				+ "</div>\n"
				+ "\t\t\t</div>\n"
				+ "\t\t</div>\n"
				+ "\t\t<div id=\"footer\" role=\"contentinfo\">\n"
				+ "\t\t\t\t\t\t\t<ul id=\"footer-info\">\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t<li id=\"footer-info-lastmod\"> This page was last modified on 1 October 2011, at 16:20.</li>\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t<li id=\"footer-info-viewcount\">This page has been accessed 926 times.</li>\n"
				+ "\t\t\t\t\t\t\t\t\t</ul>\n"
				+ "\t\t\t\t\t\t\t<ul id=\"footer-places\">\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t<li id=\"footer-places-privacy\"><a href=\"/Whitespace_(Hackerspace_Gent):Privacy_policy\" title=\"Whitespace (Hackerspace Gent):Privacy policy\">Privacy policy</a></li>\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t<li id=\"footer-places-about\"><a href=\"/Whitespace_(Hackerspace_Gent):About\" title=\"Whitespace (Hackerspace Gent):About\">About Whitespace (Hackerspace Gent)</a></li>\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t<li id=\"footer-places-disclaimer\"><a href=\"/Whitespace_(Hackerspace_Gent):General_disclaimer\" title=\"Whitespace (Hackerspace Gent):General disclaimer\">Disclaimers</a></li>\n"
				+ "\t\t\t\t\t\t\t\t\t</ul>\n"
				+ "\t\t\t\t\t\t\t\t\t\t<ul id=\"footer-icons\" class=\"noprint\">\n"
				+ "\t\t\t\t\t<li id=\"footer-poweredbyico\">\n"
				+ "\t\t\t\t\t\t<a href=\"//www.mediawiki.org/\"><img src=\"/smw/skins/common/images/poweredby_mediawiki_88x31.png\" alt=\"Powered by MediaWiki\" width=\"88\" height=\"31\" /></a>\n"
				+ "\t\t\t\t\t\t<a href=\"https://www.semantic-mediawiki.org/wiki/Semantic_MediaWiki\"><img src=\"/smw/extensions/SemanticMediaWiki/includes/../resources/images/smw_button.png\" alt=\"Powered by Semantic MediaWiki\" width=\"88\" height=\"31\" /></a>\n"
				+ "\t\t\t\t\t</li>\n"
				+ "\t\t\t\t</ul>\n"
				+ "\t\t\t\t\t\t<div style=\"clear:both\"></div>\n"
				+ "\t\t</div>\n"
				+ "\t\t<script>/*<![CDATA[*/window.jQuery && jQuery.ready();/*]]>*/</script><script>if(window.mw){\n"
				+ "mw.loader.state({\"site\":\"loading\",\"user\":\"ready\",\"user.groups\":\"ready\"});\n"
				+ "}</script>\n"
				+ "<script>if(window.mw){\n"
				+ "mw.loader.load([\"ext.maps.googlemaps3\",\"mediawiki.action.view.postEdit\",\"mediawiki.user\",\"mediawiki.hidpi\",\"mediawiki.page.ready\",\"mediawiki.searchSuggest\",\"skins.vector.collapsibleNav\"],null,true);\n"
				+ "}</script>\n"
				+ "<script src=\"http://members.0x20.be/smw/load.php?debug=false&amp;lang=en&amp;modules=site&amp;only=scripts&amp;skin=vector&amp;*\"></script>\n"
				+ "\n"
				+ "<!-- Set $wgGoogleAnalyticsAccount to your account # provided by Google Analytics. --><!-- Served in 0.823 secs. -->\n"
				+ "\t</body>\n" + "<!-- Cached/compressed 20140418053901 -->\n"
				+ "</html>";
		// cleanHtml(sample);

	}
 

	public static ArrayList<String> cleanHtml(String rawHtmlFile) {
		HashSet<String> visualElements = new HashSet<String>(Arrays.asList("a",
				"area", "audio", "embed", "option", "applet", "button",
				"canvas", "figure", "frame", "iframe", "img", "input",
				"textarea", "video"));
		ArrayList<String> selectedElements = new ArrayList<String>();
		int begin = rawHtmlFile.indexOf("<html");
		if (begin != -1) {
			String onlyHtml = rawHtmlFile.substring(begin);

			TagNode node;
			HtmlCleaner cleaner = new HtmlCleaner();
			CleanerProperties props = cleaner.getProperties();
			props.setAllowHtmlInsideAttributes(true);
			props.setAllowMultiWordAttributes(true);
			props.setRecognizeUnicodeChars(false);
			props.setOmitComments(true);
			props.setPruneTags("script,style,CDATA");
			props.setAdvancedXmlEscape(true);
			props.setTransResCharsToNCR(true);
			props.setCharset("ISO-8859-1");
			 
			
			ArrayList<String> xPaths = new ArrayList<String>();

			try { 
				node = cleaner.clean(onlyHtml);
				String xmlContent = new PrettyXmlSerializer(props).getAsString(
						node, "UTF-8");
 
				xPaths = getXpathElements(xmlContent);

				// remove duplicates if any
				Set<String> setItems = new LinkedHashSet<String>(xPaths);
				xPaths.clear();
				xPaths.addAll(setItems);

				Iterator<String> xPathItr = xPaths.iterator();
				while (xPathItr.hasNext()) {
					String singleXPath = xPathItr.next();
					if (!singleXPath.contains("noscript")) {
						int beginIndex = singleXPath.lastIndexOf("/");
						if (beginIndex != -1) {
							String element = singleXPath
									.substring(beginIndex + 1);
							if (visualElements.contains(element)) {
								selectedElements.add(singleXPath);
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return selectedElements;
	}

	public static ArrayList<String> getXpathElements(String xml) {
		ArrayList<String> xPathNodes = new ArrayList<String>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder
					.parse(new InputSource(new StringReader(xml)));
			DocumentTraversal traversal = (DocumentTraversal) doc;
			NodeIterator iterator = traversal.createNodeIterator(
					doc.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null,
					true);

			for (Node n = iterator.nextNode(); n != null; n = iterator
					.nextNode()) {
				NodeList children = ((Element) n).getChildNodes();
				if (n.getNodeName() == "a" || children.getLength() <= 0) {
					// System.out.println(getXPath(n));
					String res = getXPath(n);
					if(!res.contains("noscript") | !res.contains("type=\"hidden\""))
						xPathNodes.add(res);
				}
			}
		 
		} catch (Exception e) {
			 
		
			 
			

//			e.printStackTrace();
		}
		return xPathNodes;
	}
	
 

	public static String getXPath(final Node n) {

		String xpath;

		if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
			// Slightly special case for attributes as they are considered to
			// have no parent
			((Attr) n).getOwnerElement();
			xpath = getXPath(((Attr) n).getOwnerElement()) + "/@"
					+ n.getNodeName();

		} else if (n.getNodeType() == Node.DOCUMENT_NODE) {
			xpath = "/";
		} else if (n.getNodeType() == Node.DOCUMENT_TYPE_NODE) {

			throw new IllegalArgumentException(
					"DocumentType nodes cannot be identified with XPath");

		} else if (n.getParentNode().getNodeType() == Node.DOCUMENT_NODE) {

		 
			xpath = "/" + n.getNodeName();
		} else {

		
			xpath = getXPath(n.getParentNode()) + "/" + n.getNodeName();
		}

		return xpath;
	}

}