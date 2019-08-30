#include <ESP8266WiFi.h>
 
const char* ssid = "Blinkv8RgRM";//wifi ssid
const char* password = "drAsES8pyC";//wifi password
//setup gpio pin numbers
int doorPin = 5;
int doorIn=4;
int light1out= 16;
int light2out=13;
int light1in=12;
int light2in=14;

//create int to know what are the values atm
int door=LOW;
int light1=LOW;
int light2=LOW;
//create int to know the values of the switch
int doors=LOW;
int light1s=LOW;
int light2s=LOW;

WiFiServer server(80);//Service Port
 
void setup() {
  ESP.wdtEnable(100000);
  Serial.begin(115200);
  delay(10);
//setup pin modes
  pinMode(doorPin, OUTPUT);
  digitalWrite(doorPin, LOW);
  
  pinMode(light1out, OUTPUT);
  digitalWrite(light1out, LOW);
  
  pinMode(light2out, OUTPUT);
  digitalWrite(light2out, LOW);
  
  pinMode(doorIn,  INPUT_PULLUP);
  pinMode(light1in,  INPUT_PULLUP);
  pinMode(light2in,  INPUT_PULLUP);


  // Connect to WiFi network
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
   
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
   
  // Start the server
  server.begin();
  Serial.println("Server started");
 
  // Print the IP address
  Serial.print("Use this URL to connect: ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.println("/");
}
 
void loop() {

  //switch controller
  if(digitalRead(light1in)==LOW){
    
    light1=!light1;
    digitalWrite(light1out,light1);
    delay(1000);
  }

   if(digitalRead(light2in)==LOW){
   
    light2=!light2;
    digitalWrite(light2out,light2);
    delay(1000);
    
  }


   if(digitalRead(doorIn)==LOW){
    
    door=!door;
    digitalWrite(doorPin,door);
    ESP.wdtFeed();
    delay(3000);
    door=!door;
    digitalWrite(doorPin,door);
    
  }


  
  ESP.wdtFeed();
                       
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
   
  // Wait until the client sends some data
 
  while(!client.available()){
    ESP.wdtFeed();
    delay(1);
  }
   
  // Read the first line of the request
  String request = client.readStringUntil('\r');
  
  client.flush();
   
  // Match the request
 
 
  if (request.indexOf("/12UNLOCK") != -1) {
    digitalWrite(doorPin, HIGH);
    
  }
  if (request.indexOf("/13LOCK") != -1){
    digitalWrite(doorPin, LOW);
   
  }
  if (request.indexOf("/14PULSE") != -1){
    digitalWrite(doorPin, HIGH);
    ESP.wdtFeed();
    delay(3000);
    digitalWrite(doorPin, LOW);

    
  }
 if (request.indexOf("/15TOGGLE1") != -1){
    light1=!light1;
    digitalWrite(light1out,light1);
   
  }
  if (request.indexOf("/16TOGGLE2") != -1){
    light2=!light2;
    digitalWrite(light2out,light2);
    
  }
  //shake: hand shake to know what are the relays and what are thier commands
  String shake="";
    if (request.indexOf("/0SHAKE") != -1){
      shake="DOOR/12UNLOCK/13LOCK/14PULSE//LIGHT1/15TOGGLE1//LIGHT2/16TOGGLE2//\n";
    }
    
  
   
  // Return the response
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println(""); //  do not forget this one
  client.println("<!DOCTYPE HTML>");
  client.println("<html>");
  client.print(shake);
  
  String Tdoor="//DOOR/";
  if(door==HIGH)Tdoor=Tdoor+"OPEN";
  else Tdoor=Tdoor+"CLOSED";
  String Tlight1="//LIGHT1/";
  if(light1==HIGH)Tlight1=Tlight1+"ON";
  else Tlight1=Tlight1+"OFF";
  String Tlight2="//LIGHT2/";
  if(light2==HIGH)Tlight2=Tlight2+"ON";
  else Tlight2=Tlight2+"OFF";
  String hrag = Tdoor+Tlight1+Tlight2;
  client.print(hrag);
  delay(1);
  Serial.println("Client disconnected");
  Serial.println("");
}
