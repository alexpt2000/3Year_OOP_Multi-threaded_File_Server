package ie.gmit.sw.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
  public static void main(String[] args) throws Exception {
    ServerSocket m_ServerSocket = new ServerSocket(7777,10000);
    int id = 0;
    while (true) {
      Socket clientSocket = m_ServerSocket.accept();
      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
      cliThread.start();
    }
  }
}

class ClientServiceThread extends Thread {
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

  void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client> " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
  public void run() {
    try
    {
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(clientSocket.getInputStream());
		System.out.println("Accepted Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());



		boolean finish = false;

		do{
			try
			{

				message = (String)in.readObject();

				String sendFileList = "Files Availables\n------------------------";

				if(message.compareTo("2") == 0){

					File[] files = new File("/serverFiles").listFiles();
					List<String> results = new ArrayList<String>();

					for (File file : files) {
					    if (file.isFile()) {
					        results.add(file.getName());
					    }
					}

					for(int i = 0; i < results.size(); i++) {
					    sendFileList += "\n" + i + " - " +  results.get(i);
					}

					sendMessage(sendFileList);

				}

				if(message.compareTo("3") == 0){


				}

				if(message.compareTo("4") == 0){

					finish = true;

				}


			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			}

    	}while(finish == false);

		System.out.println("Ending Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

