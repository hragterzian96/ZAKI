package com.example.hrag.zaki;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Hrag on 2/20/2018.
 */

public class Client {
    public Client() {

    }

    public String request(String r) {

        String host = "192.168.1.11";
        Socket clientSocket = null;
        String reply="nopthing";

        try {
            clientSocket = new Socket(host, 6789);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            outToServer.writeBytes(r + '\n');
             reply = inFromServer.readLine();


            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return reply;
    }
}
