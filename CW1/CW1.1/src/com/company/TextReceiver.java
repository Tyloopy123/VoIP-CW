package com.company;

/*
    Liam Sinclair
*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TextReceiver {

    static DatagramSocket receivingSocket;

    public static void main(String[] args) {

        int PORT = 55555;

        try {

            receivingSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {

            System.out.println("ERROR in 'TextReceiver' : Unable to open receiving UDP socket!");
            e.printStackTrace();
            System.exit(0);
        }

        boolean isRunning = true;

        while (isRunning) {

            try {

                byte[] buffer = new byte[80];
                DatagramPacket packet = new DatagramPacket(buffer, 0, 80);
                receivingSocket.receive(packet);
                String string = new String(buffer);
                System.out.println(string);

                if (string.startsWith("EXIT")) {

                    isRunning = false;
                }

            } catch (IOException e) {

                System.out.println("Error in 'TextReceiver' : Random IO error occurred!");
                e.printStackTrace();
            }
        }

        receivingSocket.close();
    }
}
