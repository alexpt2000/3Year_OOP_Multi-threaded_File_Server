package ie.gmit.sw.client.config;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

public class Parse {

	private Context ctx;

	public Parse(Context ctx) {
		super();
		this.ctx = ctx;
	}

	public void init() throws Throwable {
		try {

			File inputFile = new File(ctx.getConfFile());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);

			ctx.setUsername(doc.getDocumentElement().getAttribute("username"));
			ctx.setHost(doc.getDocumentElement().getElementsByTagName("server-host").item(0).getTextContent());
			ctx.setPort(Integer
					.parseInt(doc.getDocumentElement().getElementsByTagName("server-port").item(0).getTextContent()));
			ctx.setDownloadDir(doc.getDocumentElement().getElementsByTagName("download-dir").item(0).getTextContent());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
