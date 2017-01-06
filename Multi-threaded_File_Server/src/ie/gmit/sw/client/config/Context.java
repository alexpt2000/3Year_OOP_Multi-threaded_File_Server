package ie.gmit.sw.client.config;

/**
 * A context represents the entire scope of an application, i.e. we can assign
 * "global variables" to a context.
 *
 * This is a "bean class", containing a constructor and accessor methods only.
 * 
 * @author Alexander Souza - G00317835
 * @version 1.0
 * @since 29/12/2016
 */

public class Context {

	/** The Constant CONF_FILE. */
	public static final String CONF_FILE = "config.xml";
	
	/** The username. */
	private String username;
	
	/** The host. */
	private String host;
	
	/** The port. */
	private int port;
	
	/** The download dir. */
	private String downloadDir;

	 /** 
	    * Class constructor.
	    */
	public Context() {

	}

	/**
	 * This constructor will take values from XML file.
	 *
	 * @param username the user
	 * @param host the server IP address
	 * @param port the port to connect to the server
	 * @param downloadDir the download dir
	 */
	public Context(String username, String host, int port, String downloadDir) {
		this.username = username;
		this.host = host;
		this.port = port;
		this.downloadDir = downloadDir;
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the conf file.
	 *
	 * @return the conf file
	 */
	public static String getConfFile() {
		return CONF_FILE;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the download dir.
	 *
	 * @return the download dir
	 */
	public String getDownloadDir() {
		return downloadDir;
	}

	/**
	 * Sets the download dir.
	 *
	 * @param downloadDir the new download dir
	 */
	public void setDownloadDir(String downloadDir) {
		this.downloadDir = downloadDir;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
