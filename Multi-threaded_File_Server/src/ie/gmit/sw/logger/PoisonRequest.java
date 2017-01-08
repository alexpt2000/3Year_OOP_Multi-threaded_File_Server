package ie.gmit.sw.logger;

import java.util.Date;

/**
 * The Class PoisonRequest.
 */
public class PoisonRequest extends Request{

	/**
	 * Instantiates a new poison request.
	 */
	public PoisonRequest() {
		super();
	}

	/**
	 * Instantiates a new poison request.
	 *
	 * @param command the command
	 * @param host the host
	 * @param date the date
	 */
	public PoisonRequest(String command, String host, Date date) {
		super(command, host, date);
	}
	
}
