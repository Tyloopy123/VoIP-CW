package com.company;

/*
    Liam Sinclair
*/

import CMPC3M06.AudioPlayer;
import CMPC3M06.AudioRecorder;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class AudioController {

    int i = 0;
    Vector<byte[]> voipVector = new Vector<>();

    public Vector<byte[]> AudioRecord(float time) throws LineUnavailableException, IOException {

        AudioRecorder recorder = new AudioRecorder();
        float recordLength = time;
        System.out.println("Recording...");

        for (int i = 0; i < Math.ceil(recordLength / 0.032); i++) {
            byte[] buffer = recorder.getBlock();
            voipVector.add(buffer);
        }

        recorder.close();

        return voipVector;
    }

    public void AudioPlayback(Vector<byte[]> voipVector) throws LineUnavailableException, IOException {

        AudioPlayer player = new AudioPlayer();
        System.out.println("Playing...");

        Iterator<byte[]> voiceIterator = voipVector.iterator();

        while (voiceIterator.hasNext()) {

            player.playBlock(voiceIterator.next());
        }

        player.close();
    }

    public void AudioBytePlayback(byte[] voipByteArray) throws LineUnavailableException, IOException {

        voipVector.add(voipByteArray);
        AudioPlayer player = new AudioPlayer();
        System.out.println("Playing... " + i);
        i++;

        Iterator<byte[]> voiceIterator = voipVector.iterator();

        while (voiceIterator.hasNext()) {

            player.playBlock(voiceIterator.next());
        }

        player.close();
    }
}
