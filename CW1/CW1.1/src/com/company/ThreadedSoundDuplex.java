package com.company;

/*
@bobby onanuga 14/3/2021
 */

public class ThreadedSoundDuplex {

    public  static void main(String[] args){
        ThreadedSoundReceiver rec  = new ThreadedSoundReceiver();
        ThreadedSoundSender sender = new ThreadedSoundSender();
        rec.start();
        sender.start();
    }
}
