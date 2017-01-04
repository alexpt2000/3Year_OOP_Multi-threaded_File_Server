package ie.gmit.sw.logger;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

	private String command;
	private String host;
	private Date date;

	public Request() {

	}

	public Request(String command, String host, Date date) {
		this.command = command;
		this.host = host;
		this.date = date;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
