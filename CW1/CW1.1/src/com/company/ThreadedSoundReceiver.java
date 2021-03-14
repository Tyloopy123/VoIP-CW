package com.company;

import CMPC3M06.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.lang.Runnable;
import java.net.*;
import java.nio.ByteBuffer;

public class ThreadedSoundReceiver implements Runnable{

    static DatagramSocket receiving_socket;
    public void start(){
        Thread thread = new Thread();
        thread.start();
    }

    public void run(){
        //Port to open socket on
        int port = 55555;

        //Open a  socket to receive from
        try {
            receiving_socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("ERROR: TextReceiver: Could not open socket to receive from.");
            e.printStackTrace();
            System.exit(0);
        }

        // Create & initialise a AudioRecorder
        AudioPlayer player = null;
        try {
            player = new AudioPlayer();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }


        //Create DatagramPacket to put received data into.
        byte[] buffer = new byte[512];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 512);
        int cipher = 196157828;

        //In while loop, the sound packet is decrypted and then played.
        boolean running = true;
        while (running) {
            try {
                receiving_socket.receive(packet);
                ByteBuffer unwrapDecrypt = ByteBuffer.allocate(buffer.length);
                ByteBuffer encryptedAudio = ByteBuffer.wrap(buffer);

                for (int i = 0; i < buffer.length/4; i++) {
                    int fourByte = encryptedAudio.getInt();
                    fourByte = fourByte ^ cipher;
                    unwrapDecrypt.putInt(fourByte);
                }

                //Convert into byte array and play sound.
                byte[] decryptedSound = unwrapDecrypt.array();
                player.playBlock(decryptedSound);

            } catch (IOException e) {
                System.out.println("ERROR: TextReceiver: Some random IO error occured!");
                e.printStackTrace();

            }
        }
        receiving_socket.close();
    }
}
