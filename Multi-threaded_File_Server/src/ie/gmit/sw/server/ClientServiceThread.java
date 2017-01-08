package ie.gmit.sw.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;

import javax.swing.plaf.FontUIResource;

import ie.gmit.sw.logger.Request;

/**
 * Manage all client connections
 * 
 * 
 * @author Alexander Souza - G00317835
 * @version 1.0
 * @since 29/12/2016
 */
public class ClientServiceThread extends Thread {

	Socket clientSocket;
	String message;
	String pathFile = "";
	int clientID = 0;
	boolean running = true;
	ObjectOutputStream out;
	ObjectInputStream in;
	private BufferedReader inFile = null;
	BlockingQueue<Request> queue;

	/**
	 * Instantiates a new client service thread.
	 *
	 * @param s
	 *            the Type socket number
	 * @param queue
	 * @param i
	 *            the Int ID for eache conection
	 * @param args
	 *            the String takes args (files location)
	 */
	ClientServiceThread(Socket s, BlockingQueue<Request> queue, int i, String args) {
		clientSocket = s;
		this.queue = queue;
		clientID = i;
		pathFile = args;
	}

	/**
	 * Send message to client.
	 *
	 * @param msg
	 *            String message
	 */
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

			inFile = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			boolean finish = false;

			Date dateNow = new Date();
			queue.put(new Request("[INFO] Connecting", clientSocket.getInetAddress().getHostName(), dateNow));

			do {
				try {

					message = (String) in.readObject();

					String sendFileList = "Files Availables\n------------------------";

					if (message.compareTo("2") == 0) {

						File[] files = new File(pathFile).listFiles();
						List<String> results = new ArrayList<String>();

						dateNow = new Date();
						queue.put(new Request("[INFO] Listing files", clientSocket.getInetAddress().getHostName(),
								dateNow));

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

						sendMessage("\nEnter the file name.: ");

						message = (String) in.readObject();

						dateNow = new Date();
						queue.put(
								new Request("[INFO] " + message, clientSocket.getInetAddress().getHostName(), dateNow));

						String outGoingFileName = pathFile + "/" + message;

						// while ((outGoingFileName = inFile.readLine()) !=
						// null) {
						sendFile(outGoingFileName);
						// }

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

			dateNow = new Date();
			queue.put(new Request("[INFO] End conection", clientSocket.getInetAddress().getHostName(), dateNow));

		} catch (Exception e) {
			e.printStackTrace();
			Date dateNow = new Date();
			try {
				queue.put(new Request("[ERROR] Connection reset", clientSocket.getInetAddress().getHostName(), dateNow));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}
	}

	/**
	 * Send file to client
	 *
	 * @param fileName
	 *            String file name
	 * @throws InterruptedException
	 */
	public void sendFile(String fileName) throws InterruptedException {

		Date dateNow = new Date();

		try {

			File myFile = new File(fileName);
			byte[] mybytearray = new byte[(int) myFile.length()];

			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);

			DataInputStream dis = new DataInputStream(bis);
			dis.readFully(mybytearray, 0, mybytearray.length);

			OutputStream os = clientSocket.getOutputStream();

			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(myFile.getName());
			dos.writeLong(mybytearray.length);
			dos.write(mybytearray, 0, mybytearray.length);
			dos.flush();

			System.out.println("File " + fileName + " send to client.");

		} catch (Exception e) {
			queue.put(new Request("[WARNING] file " + fileName + " does not exist",
					clientSocket.getInetAddress().getHostName(), dateNow));
			// System.err.println("Error! " + e);

		}
	}

}
