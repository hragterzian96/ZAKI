package zaki;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class IntraServer extends Thread{
	Zaki zaki;

	public IntraServer() {
		zaki = new Zaki();
	}

	public void run() {
		runServer();
	}

	public void runServer() {

		String command;
		String result;
		try {
			ServerSocket welcomeSocket = new ServerSocket(6789);

			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
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
