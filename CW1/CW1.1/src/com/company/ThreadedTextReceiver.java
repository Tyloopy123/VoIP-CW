package com.company;

/*
    Liam Sinclair
*/

import java.io.*;
import java.net.*;
import com.company.NetworkCoursework.SocketType;
import com.company.*;

public class ThreadedTextReceiver implements Runnable {

    public ThreadedTextReceiver(SocketType socketType) {

        this.socketType = socketType;
    }

    static DatagramSocket receivingSocket;
    private SocketType socketType = Type1;

    public void start() {

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        int PORT = 55555;

        try {

            switch (socketType) {

                case Type1:

                    receivingSocket = new DatagramSocket(PORT);
                    break;

                case Type2:

                    receivingSocket = new DatagramSocket2(PORT);
                    break;

                case Type3:

                    receivingSocket = new DatagramSocket3(PORT);
                    break;

                case Type4:

                    receivingSocket = new DatagramSocket4(PORT);
                    break;
            }
        } catch (SocketException e) {

            System.out.println("Error in 'TextReceiver' : Unable to open receiving UDP socket!");
            e.printStackTrace();
            System.exit(0);
        }

        boolean isRunning = true;

        while (isRunning) {

            try {

                byte[] buffer = new byte[80];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, 80);
                System.out.println(new String(datagramPacket.getData()));
                receivingSocket.receive(datagramPacket);

                String string = new String(buffer);

                if (!string.isEmpty()) {

                    System.out.println(string.trim());
                }

                if (string.substring(0, 4).equalsIgnoreCase("EXIT")) {

                    isRunning = false;
                }
            } catch (IOException e) {

                System.out.println("Error in 'TextReceiver' : Random IO error!");
                e.printStackTrace();
            }
        }

        receivingSocket.close();
    }
}
