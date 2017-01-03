package ie.gmit.sw.client.config;

/* A context represents the entire scope of an application, i.e.
 * we can assign "global variables" to a context.
 *
 * This is a "bean class", containing a constructor and accessor
 * methods only.
 */

public class Context {

	public static final String CONF_FILE = "config.xml";
	private String username;
	private String host;
	private int port;
	private String downloadDir;

	public Context() {
		super();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}


	public static String getConfFile() {
		return CONF_FILE;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDownloadDir() {
		return downloadDir;
	}

	public void setDownloadDir(String downloadDir) {
		this.downloadDir = downloadDir;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
