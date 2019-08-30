package zaki;

public class Zaki {
	private Relays main = new Relays("192.168.1.10"); 
	public Zaki() {
	
	}
	public String execute(String com) {
		switch (com) {
		case "OPEN" :
			return main.command("12UNLOCK");
		case "CLOSE" :
			return main.command("13LOCK");
		case "PULSE" :
			return main.command("14PULSE");
		case "LIGHT1" :
			return main.command("15TOGGLE1");
		case "LIGHT2" :
			return main.command("16TOGGLE2");
		
		}
		return "error";	
	}
	
}
