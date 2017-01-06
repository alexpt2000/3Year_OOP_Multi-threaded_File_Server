package ie.gmit.sw.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.logger.Request;
import ie.gmit.sw.logger.RequestLogger;

/**
 * This method will manage all actions using BLock queue saving within a LOG
 * file
 * 
 * @author Alexander Souza - G00317835
 * @version 1.0
 * @since 29/12/2016
 */

public class Server {
	

	private static ServerSocket m_ServerSocket;
	static BlockingQueue<Request> queue = new ArrayBlockingQueue<>(7);

	/**
	 * The main method.
	 *
	 * @param args Pass args, Port number eg. 7777 and local were file are /myFiles 
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		
		m_ServerSocket = new ServerSocket(Integer.parseInt(args[0]), 10000);

		new Thread(new RequestLogger(queue)).start();

		int id = 0;
		
	
		while (true) {

			// Waiting for client conection and start a new thread
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, queue, id++, args[1]);
			cliThread.start();

//			Date dateNow = new Date();
//
//			queue.put(new Request("Conecting", clientSocket.getInetAddress().getHostName(), dateNow));
//
//			Request r = queue.take();
//
//			System.out.println("Print Start Method.: " + r.getCommand() + "   " + r.getHost() + "   " + r.getDate());
//			//cliThread.start();
		}
	}


}
