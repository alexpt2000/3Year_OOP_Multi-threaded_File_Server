package ie.gmit.sw.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;

/**
 * This method will manage all actions using BLock queue saving within a LOG
 * file
 *
 * @author Alexander Souza - G00317835
 * @version 1.0
 * @since 29/12/2016
 */

public class RequestLogger implements Runnable {

	private BlockingQueue<Request> queue;

	private final static PoisonRequest POISON = new PoisonRequest();
	Request request = null;
	boolean keepRunning = true;

	/**
	 * Instantiates a new request logger.
	 *
	 * @param queue Get BlockingQueue
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RequestLogger(BlockingQueue<Request> queue) throws IOException {
		this.queue = queue;
	}

	public void run() {

		while (keepRunning) {
			FileWriter fw = null;
			try {
				fw = new FileWriter("log.txt", true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter appendFileLog = new PrintWriter(bw);

			try {
				request = queue.take();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}


			// keep in the queue so all workers stop
			 if (request == POISON) {
				 try {
					queue.put(request);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 keepRunning = false;
			 	System.out.println("**** Confirme POISON ****");
			 }



			// Set format for date and time
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM YYYY");

			// Save in file
			appendFileLog.println(request.getCommand() + " requested by " + request.getHost() + " at "+ timeFormat.format(request.getDate()) + " on " + dateFormat.format(request.getDate()));

			appendFileLog.close();
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
