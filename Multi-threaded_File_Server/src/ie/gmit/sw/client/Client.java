package ie.gmit.sw.client;

/**
 * The main method.
 * 
 * Creates the new object of ClientConnection, and execute the method run.
 * 
 * 
 * @author Alexander Souza
 * @version 1.0
 * @since 29/12/2016
 */
public class Client {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Throwable
	 *             the throwable
	 */
	public static void main(String[] args) throws Throwable {
		ClientConnection client = new ClientConnection();

		// Call method run
		client.run();
	}

}
