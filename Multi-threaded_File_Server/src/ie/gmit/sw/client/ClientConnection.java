package ie.gmit.sw.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import ie.gmit.sw.client.config.Context;
import ie.gmit.sw.client.config.Parse;

/**
 * The Class ClientConnection.
 * 
 * @author Alexander Souza - G00317835
 * @version 1.0
 * @since 29/12/2016
 */

public class ClientConnection {

	static Socket requestSocket;

	ObjectOutputStream out;
	ObjectInputStream in;

	String op = "";

	String clientResonse;

	Scanner clientInput;

	// Local variables to takes values from Context bean
	String username;
	String ipaddress;
	int port;
	String downloadDir;

	/**
	 * Instantiates a new client connection.
	 */
	ClientConnection() {
	}

	/**
	 * Run.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	void run() throws Throwable {
		clientInput = new Scanner(System.in);
		
		// Call Parse and Instantiates values from XML
		Context ctx = new Context();
		Parse cp = new Parse(ctx);
		cp.init();

		do {
			
			// Print Menu Option on Screen
			System.out.println("\n---------------------------");
			System.out.println("1. Connect to Server");
			System.out.println("2. Print File Listing");
			System.out.println("3. Download File");
			System.out.println("4. Quit");
			System.out.println("\nType Option [1-4]");

			// Prompt list of options
			op = clientInput.next();

			System.out.println("---------------------------");

			
			// 1. Connect to Server
			if (op.compareTo("1") == 0) {

				// 1. creating a socket to connect to the server
				ipaddress = ctx.getHost();
				port = ctx.getPort();
				downloadDir = ctx.getDownloadDir();

				// Creating a new Socket
				requestSocket = new Socket(ipaddress, port);

				System.out.println("Connected to " + ipaddress + " in port " + port);

				// get Input and Output streams
				out = new ObjectOutputStream(requestSocket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(requestSocket.getInputStream());
			}

			
			// 2. Print File Listing
			if (op.compareTo("2") == 0) {
				
				// Passing value to method sendMessage to delivery to the server
				sendMessage("2");
				
				// Values from Server
				clientResonse = (String) in.readObject();
				
				// Print a list of files from server
				System.out.println(clientResonse);
			}

			
			// 3. Download File
			if (op.compareTo("3") == 0) {

				// Passing value to method sendMessage to delivery to the server
				sendMessage("3");

				// Values from Server
				clientResonse = (String) in.readObject();
				
				// Printing question from server
				System.out.println(clientResonse);

				// answer the server
				op = clientInput.next();

				// Send back to server
				sendMessage(op);
				
				//receiving file
				receiveFile(op);

			}

			// 4. Quit
			if (op.compareTo("4") == 0) {
				sendMessage("4");
			}

		} while (!op.equals("4")); //Type Option [1-4]
	}

	/**
	 * Passing true all messages the server
	 *
	 * @param msg Is String format
	 */
	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("User OP.: " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	/**
	 * Receive file, from server
	 *
	 * @param fileName
	 *            the file name
	 */
	public static void receiveFile(String fileName) {
		try {
			int bytesRead;
			InputStream in = requestSocket.getInputStream();

			DataInputStream clientData = new DataInputStream(in);

			fileName = clientData.readUTF();
			OutputStream output = new FileOutputStream(fileName);
			long size = clientData.readLong();
			byte[] buffer = new byte[1024];

			while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
				output.write(buffer, 0, bytesRead);
				size -= bytesRead;
			}
			output.flush();

			System.out.println("File " + fileName + " received from Server.");
		} catch (IOException ex) {

		}
	}

}