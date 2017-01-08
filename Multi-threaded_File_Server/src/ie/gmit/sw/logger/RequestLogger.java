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
	 * @param queue Get BlockingQueue<Request> 
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
			
			//**************************************************************************************************************************************************
			
			// Referencias Sobre POISON
			
			// https://dzone.com/articles/producers-and-consumers-part-3
			// http://stackoverflow.com/questions/15489067/cant-stop-producer-consumer-threads-with-poison-pill
			// http://stackoverflow.com/questions/8974638/blocking-queue-and-multi-threaded-consumer-how-to-know-when-to-stop
			
			// http://codereview.stackexchange.com/questions/120059/executor-service-with-blocking-queue
			// https://coderanch.com/t/614729/java/Producer-Consumer-Thread
			
			
			// I need implement PoisonRequest
			
			 if (request instanceof PoisonRequest) {
				 keepRunning = false;
			 }
			
			//**************************************************************************************************************************************************
			
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
