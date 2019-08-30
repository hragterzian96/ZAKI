package zaki;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Relays {
	String ip;

	public Relays(String ip) {
		this.ip = ip;

	}

	public String command(String com) {
		StringBuilder result = new StringBuilder();
		try {
			System.out.println("connecting");
			URL url = new URL("http://" + ip + "/" + com);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
		} catch (Exception E) {
			System.out.println("relays error");
			System.out.println(E.toString());
		}
		return result.toString();
	}
}
