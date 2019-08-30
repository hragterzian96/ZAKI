package zaki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AutoDoor extends Thread {
	String ip;
	Zaki zaki;

	public AutoDoor(String ip) {
		this.ip = ip;
		zaki = new Zaki();
	}

	public void run() {
		System.out.println("thread running");
		try {
			open();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("error 2");
		}
	}

	public void open() throws InterruptedException {
		System.out.println("trying open");
		boolean here = false;

		while (true) {
			System.out.println("enetered while");
			if (here == false) {
				System.out.println("not here");
				if (ping()) {
					System.out.println("here");
					
//					zaki.execute("LIGHT1");
//					zaki.execute("LIGHT2");		
					zaki.execute("PULSE");
					here = true;
				} else {
					System.out.println("still not here");
					System.out.println("slept");

					Thread.sleep(5000);
				}

			} else {
				System.out.println("still here");
				System.out.println("slept");
				Thread.sleep(600000);
				here = ping();
				if(here==false) {
					Thread.sleep(3000);
					here=ping();
				}
			}

		}

	}

	public boolean ping() {

		String pingResult = "";

		String pingCmd = "ping -c 1 " + ip;
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(pingCmd);

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
				pingResult += inputLine;
			}
			in.close();

		} catch (IOException e) {
			System.out.println(e);
		}

		if (pingResult.contains("64 bytes from " + ip)) {
			return true;
		} else
			return false;
	}

}
