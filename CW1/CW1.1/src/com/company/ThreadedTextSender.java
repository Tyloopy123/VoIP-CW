package com.company;

/*
    Liam Sinclair
*/

import java.io.*;
import java.net.*;
import java.nio.Buffer;


public class ThreadedTextSender implements Runnable {

    public ThreadedTextSender(NetworkCoursework.SocketType socketType) {

        this.socketType = socketType;
    }

    static DatagramSocket sendingSocket;
    private NetworkCoursework.SocketType socketType = Type1;

    boolean enableUI;

    public void start() {

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {

        int PORT 55555;
        InetAddress clientIP = null;

        try {

            clientIP = InetAddress.getByName("192.168.0.2");
        } catch (UnknownHostException e) {

            System.out.println("Error in 'ThreadedTextSender' : Client IP not found!");
            e.printStackTrace();
            System.exit(0);
        }

        try {

            switch (socketType) {

                case Type1:

                    sendingSocket = new DatagramSocket();
                    break;

                case Type2:

                    sendingSocket = new DatagramSocket2();
                    break;

                case Type3:

                    sendingSocket = new DatagramSocket3();
                    break;

                case Type4:

                    sendingSocket = new DatagramSocket4();
                    break;
            }
        } catch (SocketException e) {

            System.out.println("Error in 'TextSender' : Unable to open receiving UDP socket!");
            e.printStackTrace();
            System.exit(0);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        boolean isRunning = true;

        while (isRunning) {

            try {

                String string = "";
                byte[] buffer = null;
                string = in.readLine();
                buffer = string.getBytes();

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, clientIP, PORT);
                sendingSocket.send(datagramPacket);

                if (string.equals("EXIT")) {

                    isRunning = false;
                }
            } catch (IOException e) {

                System.out.println("Error in 'TextSender' : Random IO error!");
                e.printStackTrace();
            }
        }

        sendingSocket.close();
    }
}
