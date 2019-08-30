package zaki;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {
	public static void main(String argv[]) throws Exception {
		AutoDoor auto = new AutoDoor("192.168.1.20");
		IntraServer inter = new IntraServer();
		Server serv = new Server();
		auto.start();
		inter.start();
		serv.start();
		
	
		
		
	}
}
