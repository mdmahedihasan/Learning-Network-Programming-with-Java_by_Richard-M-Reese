package chapter6_udp_and_multicasting;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AudioUDPClient {

    private AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    private void initiateAudio() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(9786);
            byte[] audioBuffers = new byte[10000];
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(audioBuffers, audioBuffers.length);
                datagramSocket.receive(datagramPacket);
                try {
                    byte[] audioData = datagramPacket.getData();
                    InputStream byteInputStream = new ByteArrayInputStream(audioData);
                    AudioFormat audioFormat = getAudioFormat();
                    audioInputStream = new AudioInputStream(byteInputStream, audioFormat,
                            audioData.length / audioFormat.getFrameSize());
                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
                    sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                    sourceDataLine.open(audioFormat);
                    sourceDataLine.start();
                    playAudio();
                } catch (Exception e) {
                    System.out.println(e);
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playAudio() {
        byte[] bytes = new byte[10000];
        try {
            int count;
            while ((count = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
                if (count > 0) {
                    sourceDataLine.write(bytes, 0, count);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public AudioUDPClient() {
        System.out.println("Audio UDP Client Started");
        initiateAudio();
        System.out.println("Audio UDP Client Terminated");
    }

    public static void main(String[] args) {
        new AudioUDPClient();
    }
}
