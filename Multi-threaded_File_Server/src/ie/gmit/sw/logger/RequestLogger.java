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
	Request r = null;
	boolean keepRunning = true;

	/**
	 * Instantiates a new request logger.
	 *
	 * @param queue
	 *            the queue
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter appendFileLog = new PrintWriter(bw);

			System.out.println("Print Run Method");

			try {
				r = queue.take();
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
			
			// if (r instanceof PoisonRequest) {
			// keepRunning = false;
			// }
			
			//**************************************************************************************************************************************************
			
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM YYYY");
			
			appendFileLog.println(r.getCommand() + " requested by " + r.getHost() + " at "+ timeFormat.format(r.getDate()) + " on " + dateFormat.format(r.getDate()));
			
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
