package com.company;

/*
    Liam Sinclair
*/

import com.company.*;
import java.io.*;
import java.net.*;

public class TextSender {

    static DatagramSocket sendingSocket;
    private AudioController audioController;

    public static void main(String[] args) {

        int PORT = 55555;

        InetAddress clientIP = null;

        try {

            clientIP = InetAddress.getByName("192.168.0.16");
        } catch (UnknownHostException e){

            System.out.println("Error in 'TextSender' : Client IP not found!");
            e.printStackTrace();
            System.exit(0);
        }

        try {

            sendingSocket = new DatagramSocket();
        } catch (SocketException e) {

            System.out.println("ERROR in 'TextSender' : Unable to open receiving UDP socket!");
            e.printStackTrace();
            System.exit(0);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        boolean isRunning = true;

        while (isRunning) {

            try {

                String string = in.readLine();
                byte[] buffer = string.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientIP, PORT);
                sendingSocket.send(packet);

                if (string.equals("EXIT")) {

                    isRunning = false;
                }
            } catch (IOException e) {

                System.out.println("Error in 'TextSender' : Random IO error occurred!");
                e.printStackTrace();
            }
        }

        sendingSocket.close();
    }
}
