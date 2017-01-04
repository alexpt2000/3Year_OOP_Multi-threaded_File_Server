package ie.gmit.sw.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.logger.Request;

public class ClientServiceThread extends Thread {
	Socket clientSocket;
	String message;
	int clientID = 0;
	boolean running = true;
	ObjectOutputStream out;
	ObjectInputStream in;



	ClientServiceThread(Socket s, int i) {
		clientSocket = s;
		clientID = i;
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client> " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void run() {
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());

			boolean finish = false;

			do {
				try {

					message = (String) in.readObject();

					String sendFileList = "Files Availables\n------------------------";

					if (message.compareTo("2") == 0) {

						File[] files = new File("/serverFiles").listFiles();
						List<String> results = new ArrayList<String>();

						Date dateNow = new Date();

						//queue.put(new Request("List Files", clientSocket.getInetAddress().getHostName(), dateNow));

						for (File file : files) {
							if (file.isFile()) {
								results.add(file.getName());
							}
						}

						for (int i = 0; i < results.size(); i++) {
							sendFileList += "\n" + i + " - " + results.get(i);
						}

						sendMessage(sendFileList);

					}

					if (message.compareTo("3") == 0) {

					}

					if (message.compareTo("4") == 0) {

						finish = true;

					}

				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}

			} while (finish == false);

			System.out.println(
					"Ending Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
