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

import ie.gmit.sw.logger.Request;

public class ClientServiceThread extends Thread {
	
	private BufferedReader inFile = null;
	
	Socket clientSocket;
	String message;
	String pathFile = "";
	int clientID = 0;
	boolean running = true;
	ObjectOutputStream out;
	ObjectInputStream in;



	ClientServiceThread(Socket s, int i, String args) {
		clientSocket = s;
		clientID = i;
		pathFile = args;
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
			
			inFile = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			boolean finish = false;

			do {
				try {

					message = (String) in.readObject();

					String sendFileList = "Files Availables\n------------------------";

					if (message.compareTo("2") == 0) {

						File[] files = new File(pathFile).listFiles();
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
						
						
						sendMessage("\nEnter the file name.: ");
						
						message = (String) in.readObject();
						
                        String outGoingFileName = message;

                        while ((outGoingFileName = inFile.readLine()) != null) {
                            sendFile(outGoingFileName);
                        }

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
	
	
	//https://www.mkyong.com/java/how-to-construct-a-file-path-in-java/
	//http://stackoverflow.com/questions/25772640/java-socket-multithread-file-transfer
	
	public void sendFile(String fileName) {
        try {

            File myFile = new File(pathFile + fileName);
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
            System.err.println("Error! " + e);
        }
    }
	
	
	
	
	
	
}
