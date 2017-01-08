package ie.gmit.sw.client;

/**
 * The Client class, includes Main Method.
 *
 * Creates the new object of ClientConnection, and execute the method run.
 *
 *
 * @author Alexander Souza
 * @version 1.0
 * @since 29/12/2016
 *
 */
public class Client {

	/**
	 * The main method will run the client side, creating a new connection and call the method run
	 *
	 * Run - creates a new thread for client
	 *
	 * @param args No takes args
	 *
	 */
	public static void main(String[] args) throws Throwable{

		ClientConnection client = new ClientConnection();

		// Call method run
		client.run();
	}

}
