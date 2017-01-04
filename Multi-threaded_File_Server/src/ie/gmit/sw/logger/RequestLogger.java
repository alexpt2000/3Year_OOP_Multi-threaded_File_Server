package ie.gmit.sw.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.server.*;

public class RequestLogger implements Runnable {

	private BlockingQueue<Request> queue;
	private FileWriter fw;
	Request r = null;
	boolean keepRunning = true;

	public RequestLogger(BlockingQueue<Request> queue) throws IOException {
		this.queue = queue;
		fw = new FileWriter(new File("log.txt"));
	}


	public void run() {


		while (keepRunning) {

			System.out.println("Print Run Method");


			try {
				r = queue.take();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//
//			if (r instanceof PoisonRequest) {
//				keepRunning = false;
//			}

			try {
				fw.write(r.toString() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
