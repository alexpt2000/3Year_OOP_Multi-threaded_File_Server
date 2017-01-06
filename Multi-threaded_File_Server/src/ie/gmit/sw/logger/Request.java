package ie.gmit.sw.logger;

import java.io.Serializable;
import java.util.Date;

/**
 * A Request hold values information to save on file log
 * "global variables" to a Request.
 *
 * This is a "bean class", containing a constructor and accessor methods only.
 * 
 * @author Alexander Souza - G00317835
 * @version 1.0
 * @since 29/12/2016
 */

public class Request implements Serializable {


	private String command;
	private String host;
	private Date date;

	/**
	 * Instantiates a new request.
	 */
	public Request() {

	}

	/**
	 * Instantiates a new request.
	 *
	 * @param command the String Information type (Info, Error and Warning)
	 * @param host the IP server address
	 * @param date Current date
	 */
	public Request(String command, String host, Date date) {
		this.command = command;
		this.host = host;
		this.date = date;
	}

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Sets the command.
	 *
	 * @param command the new command
	 */
	public void setCommand(String command) {
		this.command = command;
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
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}


//	/**
//	 * Logging.
//	 *
//	 * @param command Pass String Information type (Info, Error and Warning)
//	 * @param host the host
//	 * @param date the date
//	 */
//	public void logging(String command, String host, Date date){
//		this.command = command;
//		this.host = host;
//		this.date = date;
//	}

}
