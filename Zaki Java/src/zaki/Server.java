package zaki;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	Zaki zaki;

	public Server() {
		zaki = new Zaki();

	}

	public void run() {
		runServer();
	}

	public void runServer() {

		String command;
		String result;
		try {
			ServerSocket welcomSocket = new ServerSocket(6788);

			while (true) {
				Socket connectionSocket = welcomSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				System.out.println("here11");
				command = inFromClient.readLine();

				result = zaki.execute(command);

				outToClient.writeBytes(result);
			}
		} catch (Exception E) {
			System.out.println(E.toString());
		}
	}
}
