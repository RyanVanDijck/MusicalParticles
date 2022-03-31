import javax.sound.sampled.*;
import java.net.URL;

public class SoundPlayer extends Thread {
    URL url;

    public SoundPlayer(URL url){
        this.url = url;
    }

    public void run() {
        try {
            class AudioListener implements LineListener {
                private boolean done = false;

                @Override
                public synchronized void update(LineEvent event) {
                    LineEvent.Type eventType = event.getType();
                    if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE) {
                        done = true;
                        notifyAll();
                    }
                }

                public synchronized void waitUntilDone() throws InterruptedException {
                    while (!done) {
                        wait();
                    }
                }
            }
            AudioListener listener = new AudioListener();
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url)) {
                Clip clip = AudioSystem.getClip();
                clip.addLineListener(listener);
                clip.open(audioInputStream);
                try {
                    clip.start();
                    listener.waitUntilDone();
                } finally {
                    clip.close();
                }
            }
        }
        catch (Exception ignored){}
    }
}
