package ie.gmit.sw.client.config;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

/**
 * Read values from XML file and pass true an object
 * 
 * @author Alexander Souza
 * @version 1.0
 * @since 29/12/2016
 */
public class Parse {

	/** Instance of bean Context */
	private Context ctx;

	/**
	 * Instantiates a new parses the.
	 *
	 * @param ctx the ctx will receive the bean Context, contains Username, Host, port and download Directory.  
	 */
	public Parse(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * Inits will verify the file config.xml and take all elements and pass into an object  ctx
	 *
	 * @throws Throwable the throwable
	 */
	public void init() throws Throwable {
		try {

			// Read the file name
			File inputFile = new File(ctx.getConfFile());
			
			// Reading parametrs inside XML file 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);

			//Passing values to object
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
