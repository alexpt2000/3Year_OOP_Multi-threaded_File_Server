package ie.gmit.sw.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class RequestLogger implements Runnable {

	private BlockingQueue q;
	private FileWriter fw;

	public void Requestloger(BlockingQueue q) throws IOException {
		this.q = q;
		fw = new FileWriter(new File("log.txt"));
	}

	public void run() {
		boolean keepRunning = true;

		while(keepRunning ){

			Request r = q.take();

			if(r instanceof PoisonRequest)
			{
				keepRunning = false;
			}
			fw.write(r.toString() + "\n");
		}
			fw.close();
		}

}
