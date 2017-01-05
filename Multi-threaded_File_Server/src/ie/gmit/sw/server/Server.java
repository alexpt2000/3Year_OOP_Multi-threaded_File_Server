package ie.gmit.sw.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.logger.Request;
import ie.gmit.sw.logger.RequestLogger;

public class Server {
	private static ServerSocket m_ServerSocket;

	public static void main(String[] args) throws Exception {
		m_ServerSocket = new ServerSocket(Integer.parseInt(args[0]), 10000);

		BlockingQueue<Request> queue = new ArrayBlockingQueue<>(7);

		new Thread(new RequestLogger(queue)).start();

		int id = 0;
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++, args[1]);

			cliThread.start();

			Date dateNow = new Date();

			queue.put(new Request("Conecting", clientSocket.getInetAddress().getHostName(), dateNow));

			Request r = queue.take();

			System.out.println("Print Start Method.: " + r.getCommand() + "   " + r.getHost() + "   " + r.getDate());
			//cliThread.start();
		}
	}
}
