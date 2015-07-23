import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

class WARCOutput {
	private String warcURI;
	private String warcDate;
	private String crawlDate;
	private String serverName;
	private String htmlContent;

	public String getWarcURI() {
		return warcURI;
	}

	public void setWarcURI(String warcURI) {
		this.warcURI = warcURI;
	}

	public String getWarcDate() {
		return warcDate;
	}

	public void setWarcDate(String warcDate) {
		this.warcDate = warcDate;
	}

	public String getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(String crawlDate) {
		this.crawlDate = crawlDate;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
}

class WARCProcessing {
	private String warcFile = "C:/Users/Pramodh/Desktop/Hadoop/sample.warc";
	private HashMap<String, WARCOutput> warcOutput;

	public void warcProcessing() {
		try {
			File file = new File(warcFile);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			String[] result;
			String htmlContent = "";
			warcOutput = new HashMap<String, WARCOutput>();
			int count = 0;
			boolean isHtml = false;
			WARCOutput output = new WARCOutput();
			while ((line = reader.readLine()) != null) {
				if (line.contains("WARC-Target-URI")) {
					result = line.split(":", 2);
					output.setWarcURI(result[1]);
				} else if (line.contains("WARC-Date")) {
					result = line.split(":", 2);
					output.setWarcDate(result[1]);
				} else if (line.contains("Date")) {
					result = line.split(":", 2);
					if (result.length > 1)
						output.setCrawlDate(result[1]);
				} else if (line.contains("Server")) {
					result = line.split(":", 2);
					output.setServerName(result[1]);
				} else if (line.contains("Content-Type")) {
					if (!line.contains("html")) {
						// while ((line = reader.readLine()) != null) {
						// if (line.contains("Content-Type") && line.contains("html"))
						// break;
						// else if (line.contains("WARC/1.0")) {
						// output = new WARCOutput();
						// break;
						// }
						// }
						isHtml = false;
					} else
						isHtml = true;
				} else if (line.matches("(?i:<.*html.*>)") && isHtml) {
					while ((line = reader.readLine()) != null) {
						htmlContent += line;
						if (line.matches("(?i:</html>)"))
							break;
					}
					output.setHtmlContent(htmlContent);
					warcOutput.put(output.getWarcURI(), output);
					htmlContent = "";
					output = new WARCOutput();
				}
			}

			System.out.println("Success!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class WARCReaderTest {

	// static String warcFile = "/home/nicl/Downloads/MYWARC.warc";

	public static void main(String[] args) {
		WARCProcessing wp = new WARCProcessing();
		wp.warcProcessing();
	}

}