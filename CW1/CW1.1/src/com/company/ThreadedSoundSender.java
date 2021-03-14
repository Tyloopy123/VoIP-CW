package com.company;

/*
 bolaji onanuga 14/3/2021
 */

import CMPC3M06.AudioRecorder;
import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

import static com.company.NetworkCoursework.SocketType.Type1;
import static java.lang.System.exit;

public class ThreadedSoundSender implements Runnable{

    static DatagramSocket sending_socket;
    NetworkCoursework.SocketType socketType = Type1;

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run(){
        //Port we are sending to
        int port = 55555;
        //IP address we are sending to
        InetAddress clientIP = null;

        try {
            clientIP = InetAddress.getByName("192.168.0.11");   //My laptop's IP Address.
        } catch (UnknownHostException e){
            System.out.println("ERROR :  ThreadedSoundSender: No client found matching that IP address.");
            e.printStackTrace();
            exit(0);
        }

        //Open a socket to send sound from.
        try {
            sending_socket = new DatagramSocket();
        } catch (SocketException e){
            System.out.println("ERROR: ThreadedSoundSender: Could not open the given socket to send from.");
            e.printStackTrace();
            System.exit(0);
        }

        //Create & initialise AudioRecorder object.
        AudioRecorder recorder = null;
        try {
            recorder = new AudioRecorder();
        } catch (LineUnavailableException e){
            System.out.println("ERROR: ThreadedSoundSender :Could not open line, because it is unavailable.");
            e.printStackTrace();
        }

        int cipher   = 196157828;

        //In while loop, sound is recorded and encrypted
        boolean running = true;
        while (running){
            try {
                byte[] buffer = recorder.getBlock();
                ByteBuffer unwrapEncrypt = ByteBuffer.allocate(buffer.length);
                ByteBuffer plainAudio    = ByteBuffer.wrap(buffer);

                for (int i = 0; i < buffer.length / 4; i++) {
                    int fourByte = plainAudio.getInt();
                    fourByte = fourByte ^ cipher;
                    unwrapEncrypt.putInt(fourByte);
                }

                buffer = unwrapEncrypt.array();

                //Create and send the encrypted packet to clientIP address.
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientIP, port);
                sending_socket.send(packet);
            } catch (IOException e){
                System.out.println("ERROR: ThreadedSoundSender: IO error occurred recording the audio.");
            }
       }
        sending_socket.close();
    }
}
