package chapter6_udp_and_multicasting;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AudioUDPServer {

    private TargetDataLine targetDataLine;

    public AudioUDPServer() {
        System.out.println("Audio UDP Server Started");
        setupAudio();
        broadcastAudio();
        System.out.println("Audio UDP Server Terminated");
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    private void setupAudio() {
        try {
            AudioFormat audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void broadcastAudio() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(8000);
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            final byte[] audioBuffers = new byte[10000];
            while (true) {
                int count = targetDataLine.read(audioBuffers, 0, audioBuffers.length);
                if (count > 0) {
                    DatagramPacket datagramPacket = new DatagramPacket(audioBuffers, audioBuffers.length, inetAddress,
                            9786);
                    datagramSocket.send(datagramPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AudioUDPServer();
    }
}
