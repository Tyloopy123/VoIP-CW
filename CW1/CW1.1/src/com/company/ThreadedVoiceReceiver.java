package com.company;

/*
    Liam Sinclair
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

import CMPC3M06.AudioPlayer;
import com.company.NetworkCoursework.SocketType;

public class ThreadedVoiceReceiver implements Runnable {

    static DatagramSocket receivingSocket;
    private SocketType socketType;
    VoIPController voipController;

    public ThreadedVoiceReceiver(SocketType socketType) {

        this.voipController = new VoIPController(socketType);
        this.socketType = socketType;
    }

    AudioController audioController = new AudioController();
    int PORT = 55555;

    public void start() {

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        try {

            try {

                voipController.initialiseSocket(socketType, 'r');
            } catch (SocketException e) {

                System.out.println("Error in 'TextReceiver' : Unable to open receiving UDP socket!");
                e.printStackTrace();
                System.exit(0);
            }

            boolean isRunning = true;
            AudioPlayer audioPlayer = new AudioPlayer();

            while (isRunning) {

                try {

                    int bufferSize = 528;
                    byte[] buffer = new byte[bufferSize];
                    voipController.VoiceReceiver(buffer, bufferSize);
                } catch (IOException e) {

                    System.out.println("Error in 'TextReceiver' : Random IO error!");
                    e.printStackTrace();
                }
            }

            receivingSocket.close();
        } catch (LineUnavailableException ex) {

            Logger.getLogger(ThreadedVoiceReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {

        return "VoiceReceiverThread{" + "socketType = " + socketType + ", voipController = " + voipController + ", audioController=" + audioController + ", PORT = " + PORT + '}';
    }
}
